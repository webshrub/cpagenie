package com.webshrub.cpagenie.server.response.forward;

import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;
import com.webshrub.cpagenie.server.response.CPAGenieResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 13, 2010
 * Time: 6:38:51 PM
 */
public class ForwardResponse extends CPAGenieResponse {

    public ForwardResponse() {
        super();
    }

    public ForwardResponse(CPAGenieCampaignResponseType responseType, String response) {
        super();
        setResponseType(responseType);
        setResponse(response);
    }
}
