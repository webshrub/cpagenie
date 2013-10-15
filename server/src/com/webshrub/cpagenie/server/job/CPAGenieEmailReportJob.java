package com.webshrub.cpagenie.server.job;

import com.webshrub.cpagenie.core.common.email.InputStreamAttachment;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.delivery.CPAGenieCampaignDelivery;
import com.webshrub.cpagenie.core.db.campaign.delivery.CPAGenieCampaignDeliveryStatus;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignField;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignSupportedField;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLeadStatus;
import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import com.webshrub.cpagenie.server.common.email.ServerEmailSender;
import com.webshrub.cpagenie.server.common.util.ServerUtil;
import com.webshrub.cpagenie.server.db.job.CPAGenieJobManager;
import com.webshrub.cpagenie.server.db.job.CPAGenieJobStatus;
import com.webshrub.cpagenie.server.db.util.ServerDataUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 21, 2010
 * Time: 10:23:33 PM
 */
public class CPAGenieEmailReportJob implements Job {
    private static final Logger LOGGER = Logger.getLogger(CPAGenieEmailReportJob.class);
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String COLUMN_SEPARATOR = "\t";

    private DbDataUtil dataUtil;
    private CPAGenieJobManager jobManager;

    public CPAGenieEmailReportJob() {
        dataUtil = ServerDataUtil.getInstance();
        jobManager = CPAGenieJobManager.getInstance(dataUtil);
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date currentTime = Calendar.getInstance().getTime();
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            long startTime = System.currentTimeMillis();
            LOGGER.info("EmailReport loading has been started at " + new Date());
            sessionHolder.beginTransaction();
            sendLeadReports(sessionHolder, currentTime);
            jobManager.saveOrUpdateJob(sessionHolder, CPAGenieJobConstants.EMIAL_REPORT_LOADER_JOB_NAME, currentTime, CPAGenieJobStatus.SUCCESS, "Success");
            sessionHolder.commitTransaction();
            long endTime = System.currentTimeMillis();
            LOGGER.info("EmailReport loading has been completed in " + (endTime - startTime) / 1000 + " seconds at " + new Date());
        } catch (Exception e) {
            LOGGER.error("Error occurred while executing EmailReportJob " + e);
            sessionHolder.rollbackTransaction();
            jobManager.saveOrUpdateJob(sessionHolder, CPAGenieJobConstants.EMIAL_REPORT_LOADER_JOB_NAME, currentTime, CPAGenieJobStatus.FAILURE, e.getMessage());
        } finally {
            sessionHolder.closeSession();
        }
    }

    @SuppressWarnings("unchecked")
    private void sendLeadReports(SessionHolder sessionHolder, Date currentTime) {
        Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieCampaign.class);
        List<CPAGenieCampaign> campaigns = criteria.list();
        for (CPAGenieCampaign campaign : campaigns) {
            Date lastRunTime = getLastDeliveryTime(campaign);
            Set<CPAGenieLead> validLeads = getValidLeads(campaign, lastRunTime, currentTime);
            try {
                if (validLeads.size() != 0) {
                    emailLeadReport(lastRunTime, currentTime, campaign, validLeads);
                    addDelivery(sessionHolder, currentTime, campaign, validLeads, CPAGenieCampaignDeliveryStatus.DELIVERED);
                }
            } catch (Exception e) {
                LOGGER.info("Could not send lead report for campaignId " + campaign.getId() + " for date range [" + ServerUtil.getFormattedDateStr(lastRunTime) + " - " + ServerUtil.getFormattedDateStr(currentTime) + "]", e);
                addDelivery(sessionHolder, currentTime, campaign, validLeads, CPAGenieCampaignDeliveryStatus.UNDELIVERED);
            }
        }
    }

    private void addDelivery(SessionHolder sessionHolder, Date currentTime, CPAGenieCampaign campaign, Set<CPAGenieLead> validLeads, CPAGenieCampaignDeliveryStatus status) {
        CPAGenieCampaignDelivery delivery = new CPAGenieCampaignDelivery();
        delivery.setLeads(validLeads);
        delivery.setStatus(status);
        delivery.setDeliveryTime(currentTime);
        campaign.addDelivery(delivery);
        sessionHolder.getSession().saveOrUpdate(campaign);
        LOGGER.debug("Added delivery to campaignId " + campaign.getId() + " with " + validLeads.size() + " leads and status = " + status);
    }

    private Date getLastDeliveryTime(CPAGenieCampaign campaign) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        Date lastDeliveryTime = calendar.getTime();
        for (CPAGenieCampaignDelivery delivery : campaign.getDeliveries()) {
            if (delivery.getStatus().equals(CPAGenieCampaignDeliveryStatus.DELIVERED) && lastDeliveryTime.before(delivery.getDeliveryTime())) {
                lastDeliveryTime = delivery.getDeliveryTime();
            }
        }
        return lastDeliveryTime;
    }

    private Set<CPAGenieLead> getValidLeads(CPAGenieCampaign campaign, Date lastRuntime, Date currentTime) {
        Set<CPAGenieLead> leads = new HashSet<CPAGenieLead>();
        for (CPAGenieLead lead : campaign.getLeads()) {
            if (lead.getCaptureTime().after(lastRuntime) && lead.getCaptureTime().before(currentTime) && lead.getStatus().equals(CPAGenieLeadStatus.VALID)) {
                leads.add(lead);
            }
        }
        return leads;
    }

    public void emailLeadReport(Date lastRuntime, Date currentTime, CPAGenieCampaign campaign, Set<CPAGenieLead> validLeads) throws Exception {
        String fileName = "LeadReport_" + campaign.getName() + "_" + ServerUtil.getFormattedDateStr(lastRuntime) + "_" + ServerUtil.getFormattedDateStr(currentTime) + ".xls";

        String leadReport = getLeadReport(validLeads, campaign.getFields());

        String subject = "Lead Report - Campaign [" + campaign.getName() + "], Date [" + ServerUtil.getFormattedDateStr(lastRuntime) + " - " + ServerUtil.getFormattedDateStr(currentTime) + "]";

        String body = "Please find attached the Lead Report for Campaign '" + campaign.getName() + "' for Date Range [" + ServerUtil.getFormattedDateStr(lastRuntime) + " - " + ServerUtil.getFormattedDateStr(currentTime) + "].";
        body += "\r\n \r\nThanks";
        body += "\r\nCPAGenie Team";
        body += "\r\n \r\n \r\nNote : This is a system generated email";

        String htmlBody = "Please find attached the Lead Report for Campaign <b>" + campaign.getName() + "</b> for Date Range <i>[" + lastRuntime + "-" + currentTime + "]</i>.";
        htmlBody += "<br><br>Thanks";
        htmlBody += "<br>CPAGenie Team";
        htmlBody += "<br><br><br>Note : This is a system generated email.";

        List<InputStreamAttachment> attachmentList = new ArrayList<InputStreamAttachment>();
        InputStreamAttachment attachment = new InputStreamAttachment(fileName, "application/vnd.ms-excel", new ByteArrayInputStream(leadReport.getBytes()));
        attachmentList.add(attachment);
        ServerEmailSender.getInstance().sendMail("admin@webshrub.com", campaign.getEmail(), null, null, subject, body, htmlBody, attachmentList, null);
        LOGGER.debug("Sent lead report to email " + campaign.getEmail() + " for campaignId " + campaign.getId() + " with " + validLeads.size() + " leads");
    }

    private String getLeadReport(Set<CPAGenieLead> leads, Set<CPAGenieCampaignField> fields) {
        StringBuffer contentRows = new StringBuffer();

        StringBuffer headerRow = new StringBuffer().
                append("CAPTURE_TIME").
                append(COLUMN_SEPARATOR).
                append("CAMPAIGN_NAME").
                append(COLUMN_SEPARATOR).
                append("IP_ADDRESS").
                append(COLUMN_SEPARATOR);

        for (CPAGenieCampaignField field : fields) {
            headerRow.append(field.getParameter()).append(COLUMN_SEPARATOR);
        }

        contentRows.append(headerRow);

        contentRows.append(LINE_SEPARATOR);

        for (CPAGenieLead lead : leads) {
            StringBuffer data = new StringBuffer().
                    append(ServerUtil.getFormattedDateStr(lead.getCaptureTime())).
                    append(COLUMN_SEPARATOR).
                    append(lead.getCampaign().getName()).
                    append(COLUMN_SEPARATOR).
                    append(lead.getIpAddress()).
                    append(COLUMN_SEPARATOR);
            for (CPAGenieCampaignField field : fields) {
                setDataFieldValue(data, lead, field.getField());
                data.append(COLUMN_SEPARATOR);
            }
            contentRows.append(data);
            contentRows.append(LINE_SEPARATOR);
        }
        return contentRows.toString();
    }

    private void setDataFieldValue(StringBuffer data, CPAGenieLead lead, CPAGenieCampaignSupportedField field) {
        if (field.equals(CPAGenieCampaignSupportedField.DAY_OF_BIRTH)) {
            data.append(lead.getDayOfBirth());
        } else if (field.equals(CPAGenieCampaignSupportedField.MONTH_OF_BIRTH)) {
            data.append(lead.getMonthOfBirth());
        } else if (field.equals(CPAGenieCampaignSupportedField.YEAR_OF_BIRTH)) {
            data.append(lead.getYearOfBirth());
        } else if (field.equals(CPAGenieCampaignSupportedField.FIRST_NAME)) {
            data.append(lead.getFirstName());
        } else if (field.equals(CPAGenieCampaignSupportedField.LAST_NAME)) {
            data.append(lead.getLastName());
        } else if (field.equals(CPAGenieCampaignSupportedField.EMAIL)) {
            data.append(lead.getEmail());
        } else if (field.equals(CPAGenieCampaignSupportedField.ADDRESS1)) {
            data.append(lead.getAddress1());
        } else if (field.equals(CPAGenieCampaignSupportedField.ADDRESS2)) {
            data.append(lead.getAddress2());
        } else if (field.equals(CPAGenieCampaignSupportedField.CITY)) {
            data.append(lead.getCity());
        } else if (field.equals(CPAGenieCampaignSupportedField.STATE)) {
            data.append(lead.getState());
        } else if (field.equals(CPAGenieCampaignSupportedField.COUNTRY)) {
            data.append(lead.getCountry());
        } else if (field.equals(CPAGenieCampaignSupportedField.PIN_CODE)) {
            data.append(lead.getPinCode());
        } else if (field.equals(CPAGenieCampaignSupportedField.HOME_PHONE)) {
            data.append(lead.getHomePhone());
        } else if (field.equals(CPAGenieCampaignSupportedField.WORK_PHONE)) {
            data.append(lead.getWorkPhone());
        } else if (field.equals(CPAGenieCampaignSupportedField.WORK_EXT)) {
            data.append(lead.getWorkExt());
        } else if (field.equals(CPAGenieCampaignSupportedField.OTHER_PHONE)) {
            data.append(lead.getOtherPhone());
        } else if (field.equals(CPAGenieCampaignSupportedField.MOBILE_PHONE)) {
            data.append(lead.getMobilePhone());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM1)) {
            data.append(lead.getCustom1());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM2)) {
            data.append(lead.getCustom2());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM3)) {
            data.append(lead.getCustom3());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM4)) {
            data.append(lead.getCustom4());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM5)) {
            data.append(lead.getCustom5());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM6)) {
            data.append(lead.getCustom6());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM7)) {
            data.append(lead.getCustom7());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM8)) {
            data.append(lead.getCustom8());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM9)) {
            data.append(lead.getCustom9());
        } else if (field.equals(CPAGenieCampaignSupportedField.CUSTOM10)) {
            data.append(lead.getCustom10());
        }
    }
}
