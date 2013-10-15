package com.webshrub.cpagenie.app.mvc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshrub.cpagenie.app.mvc.dto.Advertiser;
import com.webshrub.cpagenie.app.mvc.dto.Campaign;
import com.webshrub.cpagenie.app.mvc.dto.CampaignField;
import com.webshrub.cpagenie.app.mvc.dto.Source;
import com.webshrub.cpagenie.app.mvc.dto.User;
import com.webshrub.cpagenie.app.mvc.service.CampaignService;
import com.webshrub.cpagenie.core.db.advertiser.CPAGenieAdvertiser;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaignStatus;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignField;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignFieldType;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignFieldValidationType;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignSupportedField;
import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponse;
import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;
import com.webshrub.cpagenie.core.db.source.CPAGenieSource;
import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 7, 2010
 * Time: 1:41:54 PM
 */
@Service
public class CampaignServiceImpl implements CampaignService {
    private static final Logger LOGGER = Logger.getLogger(CampaignServiceImpl.class);
    private DbDataUtil dataUtil;

    @Autowired
    public CampaignServiceImpl(DbDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @SuppressWarnings("unchecked")
    public List<Campaign> getCampaignList() {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            List<Campaign> campaignList = new ArrayList<Campaign>();
            List<CPAGenieCampaign> campaigns = sessionHolder.getSession().createCriteria(CPAGenieCampaign.class).list();
            for (CPAGenieCampaign dbCampaign : campaigns) {
                campaignList.add(new Campaign().fill(dbCampaign));
            }
            sessionHolder.commitTransaction();
            return campaignList;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getCampaignList()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getCampaignList()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public List<Source> getSourceList(){
    	 SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
         try {
             sessionHolder.beginTransaction();
             List<Source> sourceList = new ArrayList<Source>();
             List<CPAGenieSource> sources = sessionHolder.getSession().createCriteria(CPAGenieSource.class).list();
             for (CPAGenieSource dbsource : sources) {
                 sourceList.add(new Source().fill(dbsource));
             }
             sessionHolder.commitTransaction();
             return sourceList;
         } catch (HibernateException e) {
             LOGGER.error("Exception occurred in getSourceList()", e);
             sessionHolder.rollbackTransaction();
             throw new RuntimeException("Exception occurred in getSourceList()", e);
         } finally {
             sessionHolder.closeSession();
         }
    }
    
    public List<Campaign> getCampaignList(User user) {
        List<Campaign> campaignList = new ArrayList<Campaign>();
        Set<Advertiser> advertiserSet = user.getAdvertisers();
        for (Campaign campaign : getCampaignList()) {
            if (advertiserSet.contains(campaign.getAdvertiser())) {
                campaignList.add(campaign);
            }
        }
        return campaignList;
    }

    public Campaign getCampaign(Integer id) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieCampaign dbCampaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, id);
            sessionHolder.commitTransaction();
            return new Campaign().fill(dbCampaign);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getCampaign()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getCampaign()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void saveCampaign(Campaign campaign) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieCampaign dbCampaign = new CPAGenieCampaign();
            dbCampaign.setName(campaign.getName());
            dbCampaign.setDescription(campaign.getDescription());
            dbCampaign.setStartDate(campaign.getStartDate());
            dbCampaign.setEndDate(campaign.getEndDate());
            dbCampaign.setCompletionDate(campaign.getCompletionDate());
            dbCampaign.setEmail(campaign.getEmail());
            dbCampaign.setCostPerLead(campaign.getCostPerLead());
            dbCampaign.setTotalBudget(campaign.getTotalBudget());
            dbCampaign.setStatus(CPAGenieCampaignStatus.getStatus(campaign.getStatus().getId()));
            CPAGenieCampaignResponse response = new CPAGenieCampaignResponse();
            response.setResponseType(CPAGenieCampaignResponseType.getResponseType(campaign.getResponseType().getId()));
            response.setSuccessResponse(campaign.getSuccessResponse());
            response.setFailureResponse(campaign.getFailureResponse());
            dbCampaign.setResponse(response);
            dbCampaign.setCreationTime(new Date());
            CPAGenieAdvertiser dbAdvertiser = (CPAGenieAdvertiser) sessionHolder.getSession().get(CPAGenieAdvertiser.class, campaign.getAdvertiser().getId());
            dbCampaign.setAdvertiser(dbAdvertiser);
            List<CampaignField> campaignFields = campaign.getFields();
            for (CampaignField campaignField : campaignFields) {
                CPAGenieCampaignField dbField = new CPAGenieCampaignField(dbCampaign);
                dbField.setField(CPAGenieCampaignSupportedField.getField(campaignField.getField().getId()));
                dbField.setDescription(campaignField.getDescription());
                dbField.setParameter(campaignField.getParameter());
                dbField.setFieldType(CPAGenieCampaignFieldType.getFieldType(campaignField.getFieldType().getId()));
                dbField.setFieldValidationType(CPAGenieCampaignFieldValidationType.getValidationType(campaignField.getFieldValidationType().getId()));
                dbCampaign.addField(dbField);
            }
            sessionHolder.getSession().saveOrUpdate(dbCampaign);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in saveCampaign()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in saveCampaign()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void updateCampaign(Campaign campaign) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieCampaign dbCampaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, campaign.getId());
            dbCampaign.setName(campaign.getName());
            dbCampaign.setDescription(campaign.getDescription());
            dbCampaign.setStartDate(campaign.getStartDate());
            dbCampaign.setEndDate(campaign.getEndDate());
            dbCampaign.setCompletionDate(campaign.getCompletionDate());
            dbCampaign.setEmail(campaign.getEmail());
            dbCampaign.setCostPerLead(campaign.getCostPerLead());
            dbCampaign.setTotalBudget(campaign.getTotalBudget());
            dbCampaign.setStatus(CPAGenieCampaignStatus.getStatus(campaign.getStatus().getId()));
            CPAGenieCampaignResponse response = dbCampaign.getResponse();
            response.setResponseType(CPAGenieCampaignResponseType.getResponseType(campaign.getResponseType().getId()));
            response.setSuccessResponse(campaign.getSuccessResponse());
            response.setFailureResponse(campaign.getFailureResponse());
            dbCampaign.setResponse(response);
            dbCampaign.setUpdateTime(campaign.getUpdateTime());
            dbCampaign.setUpdateComments(campaign.getUpdateComments());
            CPAGenieAdvertiser dbAdvertiser = (CPAGenieAdvertiser) sessionHolder.getSession().get(CPAGenieAdvertiser.class, campaign.getAdvertiser().getId());
            dbCampaign.setAdvertiser(dbAdvertiser);
            addDeleteUpdateCampaignFields(campaign, dbCampaign, sessionHolder);
            sessionHolder.getSession().saveOrUpdate(dbCampaign);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in updateCampaign()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in updateCampaign()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    private void addDeleteUpdateCampaignFields(Campaign campaign, CPAGenieCampaign dbCampaign, SessionHolder sessionHolder) {
        List<CampaignField> fields = campaign.getFields();
        List<CampaignField> newFields = new ArrayList<CampaignField>();
        Map<Integer, CampaignField> oldIdFieldMap = new HashMap<Integer, CampaignField>();
        for (CampaignField field : fields) {
            if (field.getId() == null) {
                newFields.add(field);
            } else {
                oldIdFieldMap.put(field.getId(), field);
            }
        }
        Set<CPAGenieCampaignField> dbFields = dbCampaign.getFields();
        Iterator<CPAGenieCampaignField> iterator = dbFields.iterator();
        while (iterator.hasNext()) {
            CPAGenieCampaignField dbField = iterator.next();
            if (!oldIdFieldMap.keySet().contains(dbField.getId())) {
                iterator.remove();
                CPAGenieCampaignField dbCampaignField = (CPAGenieCampaignField) sessionHolder.getSession().get(CPAGenieCampaignField.class, dbField.getId());
                sessionHolder.getSession().delete(dbCampaignField);
            } else {
                CampaignField oldField = oldIdFieldMap.get(dbField.getId());
                dbField.setField(CPAGenieCampaignSupportedField.getField(oldField.getField().getId()));
                dbField.setDescription(oldField.getDescription());
                dbField.setParameter(oldField.getParameter());
                dbField.setFieldType(CPAGenieCampaignFieldType.getFieldType(oldField.getFieldType().getId()));
                dbField.setFieldValidationType(CPAGenieCampaignFieldValidationType.getValidationType(oldField.getFieldValidationType().getId()));
            }
        }
        for (CampaignField newField : newFields) {
            CPAGenieCampaignField dbField = new CPAGenieCampaignField(dbCampaign);
            dbField.setField(CPAGenieCampaignSupportedField.getField(newField.getField().getId()));
            dbField.setDescription(newField.getDescription());
            dbField.setParameter(newField.getParameter());
            dbField.setFieldType(CPAGenieCampaignFieldType.getFieldType(newField.getFieldType().getId()));
            dbField.setFieldValidationType(CPAGenieCampaignFieldValidationType.getValidationType(newField.getFieldValidationType().getId()));
            dbCampaign.addField(dbField);
        }
    }

    public void deleteCampaign(Campaign campaign) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieCampaign dbCampaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, campaign.getId());
            sessionHolder.getSession().delete(dbCampaign);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in deleteCampaign()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in deleteCampaign()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }
}
