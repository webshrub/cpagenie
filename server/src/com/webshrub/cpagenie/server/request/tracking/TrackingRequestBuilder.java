package com.webshrub.cpagenie.server.request.tracking;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaignStatus;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignField;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignSupportedField;
import com.webshrub.cpagenie.core.db.source.CPAGenieSource;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import com.webshrub.cpagenie.server.request.CPAGenieRequest;
import com.webshrub.cpagenie.server.request.CPAGenieRequestBuilder;
import com.webshrub.cpagenie.server.request.CPAGenieRequestConstants;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 27, 2010
 * Time: 4:49:59 PM
 */
public class TrackingRequestBuilder extends CPAGenieRequestBuilder {
    public static final Logger LOGGER = Logger.getLogger(TrackingRequestBuilder.class);
    private static final TrackingRequestBuilder instance = new TrackingRequestBuilder();

    private TrackingRequestBuilder() {
        super();
    }

    public static TrackingRequestBuilder getInstance() {
        return instance;
    }

    public CPAGenieRequest buildRequest(HttpServletRequest servletRequest) {
        Integer campaignId = Integer.parseInt(servletRequest.getParameter(CPAGenieRequestConstants.CAMPAIGN_ID_PARAM_STR));
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieCampaign campaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, campaignId);
            if (campaign == null) {
                LOGGER.info("Returning null tracking request due to unknown campaign with campaignId = " + campaignId);
                return null;
            }
            if (!campaign.getStatus().equals(CPAGenieCampaignStatus.STARTED)) {
                LOGGER.info("Returning null tracking request due to inactive campaign with campaignId = " + campaignId);
                return null;
            }
            TrackingRequest trackingRequest = new TrackingRequest();
            trackingRequest.setIpAddress(servletRequest.getRemoteAddr());
            trackingRequest.setUserAgent(servletRequest.getHeader(CPAGenieRequestConstants.USER_AGENT_HEADER_STR));
            trackingRequest.setCaptureTime(new Date());
            Set<CPAGenieCampaignField> campaignFields = campaign.getFields();
            for (CPAGenieCampaignField campaignField : campaignFields) {
                CPAGenieCampaignSupportedField field = campaignField.getField();
                String parameter = field.getName();
                if (servletRequest.getParameter(campaignField.getParameter()) != null && !servletRequest.getParameter(campaignField.getParameter()).equals("")) {
                    parameter = campaignField.getParameter();
                }
                String parameterValue = servletRequest.getParameter(parameter);
                if (parameterValue == null || parameterValue.equalsIgnoreCase("")) {
                    continue;
                }
                parseAndSetFieldValue(trackingRequest, field, parameterValue);
            }
            trackingRequest.setCampaign(campaign);
            CPAGenieSource source = (CPAGenieSource) sessionHolder.getSession().get(CPAGenieSource.class, Integer.parseInt(servletRequest.getParameter(CPAGenieRequestConstants.SOURCE_ID_PARAM_STR)));
            trackingRequest.setSource(source);
            sessionHolder.commitTransaction();
            return trackingRequest;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in buildRequest()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in buildRequest()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    private static void parseAndSetFieldValue(TrackingRequest trackingRequest, CPAGenieCampaignSupportedField field, String parameterValue) {
        if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM1_PARAM_STR)) {
            trackingRequest.setCustom1(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM2_PARAM_STR)) {
            trackingRequest.setCustom2(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM3_PARAM_STR)) {
            trackingRequest.setCustom3(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM4_PARAM_STR)) {
            trackingRequest.setCustom4(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM5_PARAM_STR)) {
            trackingRequest.setCustom5(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM6_PARAM_STR)) {
            trackingRequest.setCustom6(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM7_PARAM_STR)) {
            trackingRequest.setCustom7(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM8_PARAM_STR)) {
            trackingRequest.setCustom8(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM9_PARAM_STR)) {
            trackingRequest.setCustom9(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM10_PARAM_STR)) {
            trackingRequest.setCustom10(parameterValue);
        }
    }
}
