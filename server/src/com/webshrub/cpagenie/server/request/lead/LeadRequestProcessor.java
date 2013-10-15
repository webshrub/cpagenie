package com.webshrub.cpagenie.server.request.lead;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import com.webshrub.cpagenie.server.request.CPAGenieRequest;
import com.webshrub.cpagenie.server.request.CPAGenieRequestProcessor;
import com.webshrub.cpagenie.server.response.CPAGenieResponse;
import com.webshrub.cpagenie.server.response.forward.ForwardResponse;
import com.webshrub.cpagenie.server.response.normal.NormalResponse;
import com.webshrub.cpagenie.server.response.redirect.RedirectResponse;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 13, 2010
 * Time: 6:27:38 PM
 */
public class LeadRequestProcessor extends CPAGenieRequestProcessor {
    private static final Logger LOGGER = Logger.getLogger(LeadRequestProcessor.class);
    private static final LeadRequestProcessor instance = new LeadRequestProcessor();

    private LeadRequestProcessor() {
        super();
    }

    public static LeadRequestProcessor getInstance() {
        return instance;
    }

    @Override
    public CPAGenieResponse processRequest(CPAGenieRequest request) {
        if (request == null) {
            LOGGER.info("Returning normal failure response due to null lead request");
            return new NormalResponse(CPAGenieCampaignResponseType.NORMAL, "Failure");
        }
        LeadRequest leadRequest = (LeadRequest) request;
        CPAGenieCampaign campaign = leadRequest.getCampaign();
        Integer campaignId = campaign.getId();
        Integer sourceId = leadRequest.getSource().getId();
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            LOGGER.info("Adding lead request with campaignId " + campaignId + " and sourceId " + sourceId);
            CPAGenieLead lead = leadRequest.populateLead();
            sessionHolder.getSession().saveOrUpdate(lead);
            sessionHolder.commitTransaction();
            return buildResponse(campaign, false);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while adding request to requestQueue. ", e);
            sessionHolder.rollbackTransaction();
            return buildResponse(campaign, true);
        } finally {
            sessionHolder.closeSession();
        }
    }

    private CPAGenieResponse buildResponse(CPAGenieCampaign campaign, boolean errorOccurred) {
        CPAGenieResponse response;
        CPAGenieCampaignResponseType responseType = CPAGenieCampaignResponseType.getResponseType(campaign.getResponse().getResponseType().getId());
        switch (responseType) {
            case NORMAL:
                response = new NormalResponse();
                break;
            case REDIRECT:
                response = new RedirectResponse();
                break;
            case FORWARD:
                response = new ForwardResponse();
                break;
            default:
                throw new RuntimeException("No such responseHandler found for responseType " + responseType);
        }
        response.setResponseType(responseType);
        if (!errorOccurred) {
            response.setResponse(campaign.getResponse().getSuccessResponse());
        } else {
            response.setResponse(campaign.getResponse().getFailureResponse());
        }
        return response;
    }
}
