package com.webshrub.cpagenie.server.job;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaignStatus;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLeadStatus;
import com.webshrub.cpagenie.core.db.report.CPAGenieImpressionReportRow;
import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import com.webshrub.cpagenie.server.db.job.CPAGenieJobManager;
import com.webshrub.cpagenie.server.db.job.CPAGenieJobStatus;
import com.webshrub.cpagenie.server.db.util.ServerDataUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 16, 2010
 * Time: 1:49:03 PM
 */
public class CPAGenieCampaignStatusJob implements Job {
    private static final Logger LOGGER = Logger.getLogger(CPAGenieCampaignStatusJob.class);

    private DbDataUtil dataUtil;
    private CPAGenieJobManager jobManager;

    public CPAGenieCampaignStatusJob() {
        dataUtil = ServerDataUtil.getInstance();
        jobManager = CPAGenieJobManager.getInstance(dataUtil);
    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date currentDate = Calendar.getInstance().getTime();
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            long startTime = System.currentTimeMillis();
            LOGGER.info("CampaignStatus loading has been started at " + new Date());
            sessionHolder.beginTransaction();
            updateCampaignStatus(sessionHolder);
            jobManager.saveOrUpdateJob(sessionHolder, CPAGenieJobConstants.CAMPAIGN_STATUS_LOADER_JOB_NAME, currentDate, CPAGenieJobStatus.SUCCESS, "Success");
            sessionHolder.commitTransaction();
            long endTime = System.currentTimeMillis();
            LOGGER.info("CampaignStatus loading has been completed in " + (endTime - startTime) / 1000 + " seconds at " + new Date());
        } catch (Exception e) {
            LOGGER.error("Error occurred while executing CampaignStatusJob " + e);
            sessionHolder.rollbackTransaction();
            jobManager.saveOrUpdateJob(sessionHolder, CPAGenieJobConstants.CAMPAIGN_STATUS_LOADER_JOB_NAME, currentDate, CPAGenieJobStatus.FAILURE, e.getMessage());
        } finally {
            sessionHolder.closeSession();
        }
    }

    @SuppressWarnings("unchecked")
    private void updateCampaignStatus(SessionHolder sessionHolder) {
        Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieCampaign.class);
        List<CPAGenieCampaign> campaigns = criteria.list();
        for (CPAGenieCampaign campaign : campaigns) {
            CPAGenieCampaignStatus oldStatus = campaign.getStatus();
            if (notYetStarted(campaign)) {
                if (!oldStatus.equals(CPAGenieCampaignStatus.PAUSED_BEFORE_START)) {
                    campaign.setStatus(CPAGenieCampaignStatus.PAUSED_BEFORE_START);
                    sessionHolder.getSession().saveOrUpdate(campaign);
                    LOGGER.debug("Updated status of campaignId = " + campaign.getId() + " to " + CPAGenieCampaignStatus.PAUSED_BEFORE_START);
                }
            } else if (hasMaxLeadCount(campaign)) {
                if (!oldStatus.equals(CPAGenieCampaignStatus.STOPPED_MAX_LEAD)) {
                    campaign.setStatus(CPAGenieCampaignStatus.STOPPED_MAX_LEAD);
                    campaign.setCompletionDate(Calendar.getInstance().getTime());
                    sessionHolder.getSession().saveOrUpdate(campaign);
                    LOGGER.debug("Updated status of campaignId = " + campaign.getId() + " to " + CPAGenieCampaignStatus.STOPPED_MAX_LEAD);
                }
            } else if (hasExpired(campaign)) {
                if (!oldStatus.equals(CPAGenieCampaignStatus.STOPPED_AFTER_END)) {
                    campaign.setStatus(CPAGenieCampaignStatus.STOPPED_AFTER_END);
                    campaign.setCompletionDate(Calendar.getInstance().getTime());
                    sessionHolder.getSession().saveOrUpdate(campaign);
                    LOGGER.debug("Updated status of campaignId = " + campaign.getId() + " to " + CPAGenieCampaignStatus.STOPPED_AFTER_END);
                }
            }
        }
    }

    private boolean notYetStarted(CPAGenieCampaign campaign) {
        return campaign.getStartDate().after(Calendar.getInstance().getTime());
    }

    private boolean hasMaxLeadCount(CPAGenieCampaign campaign) {
        Double totalBudget = campaign.getTotalBudget();
        Double spentBudget = getRecordedSpent(campaign) + getUnRecordedSpent(campaign);
        Integer futureLeadCount = (int) ((totalBudget - spentBudget) / campaign.getCostPerLead());
        return futureLeadCount <= 0;
    }

    //This represents entire spent which has been tracked by the system so far, up to the time of last execution of ImpressionReport loader job.

    @SuppressWarnings("unchecked")
    public Double getRecordedSpent(CPAGenieCampaign campaign) {
        Double recordedBudget = 0d;
        for (CPAGenieImpressionReportRow impressionReportRow : campaign.getImpressionReportRows()) {
            recordedBudget = recordedBudget + (impressionReportRow.getLeadCount() * impressionReportRow.getCostPerLead());
        }
        return recordedBudget;
    }

    //This represents entire spent which has not yet been tracked by the system.
    //Only covers the spent between last execution time of ImpressionReport loader job and current time.
    //Also ignores the CPL variation between last execution time of ImpressionReport loader job and current time.
    //Uses only the latest CPL value to calculate spent.

    private Double getUnRecordedSpent(CPAGenieCampaign campaign) {
        Date lastRunTime = getLastRunTime(campaign);
        Date currentTime = Calendar.getInstance().getTime();
        Integer leadCount = 0;
        for (CPAGenieLead lead : campaign.getLeads()) {
            if (lead.getStatus().equals(CPAGenieLeadStatus.VALID) && lead.getCaptureTime().after(lastRunTime) && lead.getCaptureTime().before(currentTime)) {
                leadCount++;
            }
        }
        return leadCount * campaign.getCostPerLead();
    }

    @SuppressWarnings("unchecked")
    private Date getLastRunTime(CPAGenieCampaign campaign) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        Date lastRunTime = calendar.getTime();
        for (CPAGenieImpressionReportRow impressionReportRow : campaign.getImpressionReportRows()) {
            if (lastRunTime.before(impressionReportRow.getRunTime())) {
                lastRunTime = impressionReportRow.getRunTime();
            }
        }
        return lastRunTime;
    }

    private boolean hasExpired(CPAGenieCampaign campaign) {
        return campaign.getEndDate().before(Calendar.getInstance().getTime());
    }
}
