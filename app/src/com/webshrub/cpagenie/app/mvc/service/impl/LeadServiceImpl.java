package com.webshrub.cpagenie.app.mvc.service.impl;

import com.webshrub.cpagenie.app.mvc.dto.Campaign;
import com.webshrub.cpagenie.app.mvc.dto.Lead;
import com.webshrub.cpagenie.app.mvc.service.LeadService;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLeadStatus;
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
public class LeadServiceImpl implements LeadService {
    private static final Logger LOGGER = Logger.getLogger(LeadServiceImpl.class);
    private DbDataUtil dataUtil;

    @Autowired
    public LeadServiceImpl(DbDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    public Lead getLead(Integer id) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieLead dbLead = (CPAGenieLead) sessionHolder.getSession().get(CPAGenieLead.class, id);
            sessionHolder.commitTransaction();
            return new Lead().fill(dbLead);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getLead()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getLead()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void updateLead(Lead lead) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieLead dbLead = (CPAGenieLead) sessionHolder.getSession().get(CPAGenieLead.class, lead.getId());
            dbLead.setStatus(CPAGenieLeadStatus.getStatus(lead.getStatus().getId()));
            dbLead.setUpdateTime(new Date());
            dbLead.setUpdateComments(lead.getUpdateComments());
            dbLead.setFirstName(lead.getFirstName());
            dbLead.setLastName(lead.getLastName());
            dbLead.setEmail(lead.getEmail());
            dbLead.setAddress1(lead.getAddress1());
            dbLead.setAddress2(lead.getAddress2());
            dbLead.setCity(lead.getCity());
            dbLead.setState(lead.getState());
            dbLead.setHomePhone(lead.getHomePhone());
            dbLead.setWorkPhone(lead.getWorkPhone());
            dbLead.setMobilePhone(lead.getMobilePhone());
            sessionHolder.getSession().saveOrUpdate(dbLead);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in updateLead()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in updateLead()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void deleteLead(Lead lead) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieLead dbLead = (CPAGenieLead) sessionHolder.getSession().get(CPAGenieLead.class, lead.getId());
            sessionHolder.getSession().delete(dbLead);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in deleteLead()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in deleteLead()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Lead> getLeadList(Campaign campaign, Date startDate, Date endDate) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieCampaign dbCampaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, campaign.getId());
            List<Lead> leadList = new ArrayList<Lead>();
            for (CPAGenieLead dbLead : dbCampaign.getLeads()) {
                if (dbLead.getCaptureTime().after(startDate) && dbLead.getCaptureTime().before(endDate)) {
                    leadList.add(new Lead().fill(dbLead));
                }
            }
            sessionHolder.commitTransaction();
            return leadList;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getLeadList()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getLeadList()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }
}
