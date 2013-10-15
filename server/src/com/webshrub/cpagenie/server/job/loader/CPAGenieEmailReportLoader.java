package com.webshrub.cpagenie.server.job.loader;

import com.webshrub.cpagenie.server.db.job.CPAGenieJob;
import com.webshrub.cpagenie.server.db.job.CPAGenieJobManager;
import com.webshrub.cpagenie.server.db.util.ServerDataUtil;
import com.webshrub.cpagenie.server.job.CPAGenieEmailReportJob;
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
public class CPAGenieEmailReportLoader {
    private CPAGenieJobManager jobManager;
    private Integer repeatDurationInMinutes;

    public CPAGenieEmailReportLoader(Integer repeatDurationInMinutes) throws SchedulerException {
        this.repeatDurationInMinutes = repeatDurationInMinutes;
        this.jobManager = CPAGenieJobManager.getInstance(ServerDataUtil.getInstance());
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        JobDetail jobDetail = new JobDetail(CPAGenieJobConstants.EMIAL_REPORT_LOADER_JOB_DETAIL_KEY, Scheduler.DEFAULT_GROUP, CPAGenieEmailReportJob.class);
        SimpleTrigger simpleTrigger = new SimpleTrigger(CPAGenieJobConstants.EMIAL_REPORT_LOADER_TRIGGER_KEY,
                Scheduler.DEFAULT_GROUP, getRunTime(CPAGenieJobConstants.EMIAL_REPORT_LOADER_JOB_NAME), null, SimpleTrigger.REPEAT_INDEFINITELY, repeatDurationInMinutes * 60L * 1000L);
        scheduler.scheduleJob(jobDetail, simpleTrigger);
    }

    private Date getRunTime(String jobName) {
        Date currentTime = java.util.Calendar.getInstance().getTime();
        CPAGenieJob job = jobManager.getJob(jobName);
        if (job == null) {
            return currentTime;
        } else {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(job.getLastRunTime());
            calendar.add(java.util.Calendar.MINUTE, repeatDurationInMinutes);
            if (calendar.getTime().before(currentTime)) {
                return currentTime;
            } else {
                return calendar.getTime();
            }
        }
    }
}