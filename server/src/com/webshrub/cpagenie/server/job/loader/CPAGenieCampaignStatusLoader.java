package com.webshrub.cpagenie.server.job.loader;

import com.webshrub.cpagenie.server.job.CPAGenieCampaignStatusJob;
import com.webshrub.cpagenie.server.job.CPAGenieJobConstants;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 15, 2010
 * Time: 11:32:46 AM
 */
public class CPAGenieCampaignStatusLoader {

    public CPAGenieCampaignStatusLoader(Integer repeatDurationInMinutes) throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        JobDetail jobDetail = new JobDetail(CPAGenieJobConstants.CAMPAIGN_STATUS_LOADER_JOB_DETAIL_KEY, Scheduler.DEFAULT_GROUP, CPAGenieCampaignStatusJob.class);
        SimpleTrigger simpleTrigger = new SimpleTrigger(CPAGenieJobConstants.CAMPAIGN_STATUS_LOADER_TRIGGER_KEY,
                Scheduler.DEFAULT_GROUP, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, repeatDurationInMinutes * 60L * 1000L);
        scheduler.scheduleJob(jobDetail, simpleTrigger);
    }
}