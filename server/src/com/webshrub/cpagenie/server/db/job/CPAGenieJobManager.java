package com.webshrub.cpagenie.server.db.job;

import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 7, 2010
 * Time: 2:25:19 PM
 */
public class CPAGenieJobManager {
    private static final Logger LOGGER = Logger.getLogger(CPAGenieJobManager.class);
    private static final String NAME_KEY = "name";

    private static CPAGenieJobManager instance;
    private DbDataUtil dataUtil;

    private CPAGenieJobManager(DbDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public static synchronized CPAGenieJobManager getInstance(DbDataUtil dataUtil) {
        if (instance == null) {
            instance = new CPAGenieJobManager(dataUtil);
        }
        return instance;
    }

    public CPAGenieJob getJob(String jobName) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieJob.class);
            criteria.add(Restrictions.eq(NAME_KEY, jobName));
            CPAGenieJob job = (CPAGenieJob) criteria.uniqueResult();
            sessionHolder.commitTransaction();
            return job;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getJob()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getJob()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    //User can call this method independently.

    public void saveOrUpdateJob(String jobName, Date lastRunTime, CPAGenieJobStatus status, String updateComments) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            saveOrUpdateJob(sessionHolder, jobName, lastRunTime, status, updateComments);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in saveOrUpdateJob()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in saveOrUpdateJob()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    //User needs to call this method from already created session. Probably from a currently executing transaction.

    public void saveOrUpdateJob(SessionHolder sessionHolder, String jobName, Date lastRunTime, CPAGenieJobStatus status, String updateComments) {
        Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieJob.class);
        criteria.add(Restrictions.eq(NAME_KEY, jobName));
        CPAGenieJob job = (CPAGenieJob) criteria.uniqueResult();
        if (job == null) {
            job = new CPAGenieJob();
        }
        job.setName(jobName);
        job.setLastRunTime(lastRunTime);
        job.setStatus(status);
        job.setUpdateComments(updateComments);
        sessionHolder.getSession().saveOrUpdate(job);
    }
}
