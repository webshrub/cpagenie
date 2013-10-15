package com.webshrub.cpagenie.server.response.normal;

import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;
import com.webshrub.cpagenie.server.response.CPAGenieResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 4, 2010
 * Time: 10:31:36 AM
 */
public class NormalResponse extends CPAGenieResponse {

    public NormalResponse() {
        super();
    }

    public NormalResponse(CPAGenieCampaignResponseType responseType, String response) {
        super();
        setResponseType(responseType);
        setResponse(response);
    }
}
