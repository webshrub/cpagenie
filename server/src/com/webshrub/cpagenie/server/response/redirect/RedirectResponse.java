package com.webshrub.cpagenie.server.response.redirect;

import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;
import com.webshrub.cpagenie.server.response.CPAGenieResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 13, 2010
 * Time: 6:37:12 PM
 */
public class RedirectResponse extends CPAGenieResponse {

    public RedirectResponse() {
        super();
    }

    public RedirectResponse(CPAGenieCampaignResponseType responseType, String response) {
        super();
        setResponseType(responseType);
        setResponse(response);
    }
}
