package com.webshrub.cpagenie.server.request.tracking;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import com.webshrub.cpagenie.server.request.CPAGenieRequest;
import com.webshrub.cpagenie.server.request.CPAGenieRequestProcessor;
import com.webshrub.cpagenie.server.response.CPAGenieResponse;
import com.webshrub.cpagenie.server.response.normal.NormalResponse;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 13, 2010
 * Time: 6:27:53 PM
 */
public class TrackingRequestProcessor extends CPAGenieRequestProcessor {
    private static final Logger LOGGER = Logger.getLogger(TrackingRequestProcessor.class);
    private static final TrackingRequestProcessor instance = new TrackingRequestProcessor();

    private TrackingRequestProcessor() {
        super();
    }

    public static TrackingRequestProcessor getInstance() {
        return instance;
    }

    @Override
    public CPAGenieResponse processRequest(CPAGenieRequest request) {
        if (request == null) {
            LOGGER.info("Returning normal failure response due to null tracking request");
            return new NormalResponse(CPAGenieCampaignResponseType.NORMAL, "Failure");
        }
        TrackingRequest trackingRequest = (TrackingRequest) request;
        CPAGenieCampaign campaign = trackingRequest.getCampaign();
        Integer campaignId = campaign.getId();
        Integer sourceId = trackingRequest.getSource().getId();
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            LOGGER.info("Adding tracking request with campaignId " + campaignId + " and sourceId " + sourceId);
            CPAGenieCampaignResponseType responseType = CPAGenieCampaignResponseType.getResponseType(campaign.getResponse().getResponseType().getId());
            sessionHolder.getSession().saveOrUpdate(trackingRequest.populateTracking());
            sessionHolder.commitTransaction();
            return new NormalResponse(responseType, campaign.getResponse().getSuccessResponse());
        } catch (Exception e) {
            LOGGER.error("Error occurred in processRequest()" + e);
            sessionHolder.rollbackTransaction();
            return new NormalResponse(CPAGenieCampaignResponseType.NORMAL, "Failure");
        } finally {
            sessionHolder.closeSession();
        }
    }
}
