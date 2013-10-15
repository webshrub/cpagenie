package com.webshrub.cpagenie.server.job;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLeadStatus;
import com.webshrub.cpagenie.core.db.report.CPAGenieImpressionReportRow;
import com.webshrub.cpagenie.core.db.tracking.CPAGenieTracking;
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
 * Date: Sep 15, 2010
 * Time: 1:05:49 PM
 */
public class CPAGenieImpressionReportJob implements Job {
    private static final Logger LOGGER = Logger.getLogger(CPAGenieImpressionReportJob.class);

    private DbDataUtil dataUtil;
    private CPAGenieJobManager jobManager;

    public CPAGenieImpressionReportJob() {
        dataUtil = ServerDataUtil.getInstance();
        jobManager = CPAGenieJobManager.getInstance(dataUtil);
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        Date currentDate = Calendar.getInstance().getTime();
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            long startTime = System.currentTimeMillis();
            LOGGER.info("ImpressionReport loading has been started at " + new Date());
            sessionHolder.beginTransaction();
            checkAndPopulateImpressionReportData(sessionHolder);
            jobManager.saveOrUpdateJob(sessionHolder, CPAGenieJobConstants.IMPRESSION_REPORT_LOADER_JOB_NAME, currentDate, CPAGenieJobStatus.SUCCESS, "Success");
            sessionHolder.commitTransaction();
            long endTime = System.currentTimeMillis();
            LOGGER.info("ImpressionReport loading has been completed in " + (endTime - startTime) / 1000 + " seconds at " + new Date());
        } catch (Exception e) {
            LOGGER.error("Error occurred while executing ImpressionReportJob " + e);
            sessionHolder.rollbackTransaction();
            jobManager.saveOrUpdateJob(sessionHolder, CPAGenieJobConstants.IMPRESSION_REPORT_LOADER_JOB_NAME, currentDate, CPAGenieJobStatus.FAILURE, e.getMessage());
        } finally {
            sessionHolder.closeSession();
        }
    }

    @SuppressWarnings({"unchecked"})
    private void checkAndPopulateImpressionReportData(SessionHolder sessionHolder) {
        Date currentDate = Calendar.getInstance().getTime();
        Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieCampaign.class);
        List<CPAGenieCampaign> campaigns = criteria.list();
        for (CPAGenieCampaign campaign : campaigns) {
            Date lastRunTime = getLastRunTime(campaign);
            Integer impressions = getImpressions(campaign, lastRunTime, currentDate);
            Integer submitCount = getLeadCount(campaign, lastRunTime, currentDate, false);
            Integer leadCount = getLeadCount(campaign, lastRunTime, currentDate, true);
            if (impressions != 0 || submitCount != 0 || leadCount != 0) {
                insertImpressionReportData(sessionHolder, currentDate, campaign, impressions, submitCount, leadCount);
                LOGGER.debug("Inserted ImpressionReport for campaignId = " + campaign.getId() + " with impressions = " + impressions + ", submitCount = " + submitCount + ", leadCount = " + leadCount);
            }
        }
    }

    //This method only calculates the revenue with last CPL and ignores CPL variation in between the last execution time and current execution time,
    //Hence the revenue calculation is not absolute.

    private void insertImpressionReportData(SessionHolder sessionHolder, Date currentDate, CPAGenieCampaign campaign, Integer impressions, Integer submitCount, Integer leadCount) {
        CPAGenieImpressionReportRow reportRow = new CPAGenieImpressionReportRow();
        reportRow.setRunTime(currentDate);
        reportRow.setCampaign(campaign);
        reportRow.setImpressions(impressions);
        reportRow.setSubmitCount(submitCount);
        reportRow.setLeadCount(leadCount);
        reportRow.setCostPerLead(campaign.getCostPerLead());
        reportRow.setRevenue(leadCount * campaign.getCostPerLead());
        sessionHolder.getSession().saveOrUpdate(reportRow);
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

    private Integer getImpressions(CPAGenieCampaign campaign, Date lastRuntime, Date currentDate) {
        Integer impressions = 0;
        for (CPAGenieTracking tracking : campaign.getTrackings()) {
            if (tracking.getCaptureTime().after(lastRuntime) && tracking.getCaptureTime().before(currentDate)) {
                impressions++;
            }
        }
        return impressions;
    }

    private Integer getLeadCount(CPAGenieCampaign campaign, Date lastRuntime, Date currentDate, boolean fetchValidOnly) {
        Integer submitCount = 0;
        Integer leadCount = 0;
        for (CPAGenieLead lead : campaign.getLeads()) {
            if (lead.getCaptureTime().after(lastRuntime) && lead.getCaptureTime().before(currentDate)) {
                submitCount++;
                if (lead.getStatus().equals(CPAGenieLeadStatus.VALID)) {
                    leadCount++;
                }
            }
        }
        return fetchValidOnly ? leadCount : submitCount;
    }
}