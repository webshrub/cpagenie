package com.webshrub.cpagenie.app.mvc.service.impl;

import com.webshrub.cpagenie.app.mvc.dto.Campaign;
import com.webshrub.cpagenie.app.mvc.dto.ImpressionReportRow;
import com.webshrub.cpagenie.app.mvc.service.ReportService;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.report.CPAGenieImpressionReportRow;
import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 19, 2010
 * Time: 2:54:10 PM
 */
@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class);
    private DbDataUtil dataUtil;

    @Autowired
    public ReportServiceImpl(DbDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @SuppressWarnings("unchecked")
    public List<ImpressionReportRow> getImpressionReportRowList(Campaign campaign, Date startDate, Date endDate) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            List<ImpressionReportRow> impressionReportRows = new ArrayList<ImpressionReportRow>();
            CPAGenieCampaign dbCampaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, campaign.getId());
            for (CPAGenieImpressionReportRow dbRow : dbCampaign.getImpressionReportRows()) {
                if (dbRow.getRunTime().after(startDate) && dbRow.getRunTime().before(endDate)) {
                    impressionReportRows.add(new ImpressionReportRow().fill(dbRow));
                }
            }
            sessionHolder.commitTransaction();
            return impressionReportRows;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getImpressionReportRowList()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getImpressionReportRowList()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }
}