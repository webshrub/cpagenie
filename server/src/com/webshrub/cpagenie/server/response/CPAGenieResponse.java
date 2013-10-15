package com.webshrub.cpagenie.server.response;

import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 11, 2010
 * Time: 7:05:10 PM
 */
public abstract class CPAGenieResponse {
    private CPAGenieCampaignResponseType responseType;
    private String response;

    protected CPAGenieResponse() {
        super();
    }

    public CPAGenieCampaignResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(CPAGenieCampaignResponseType responseType) {
        this.responseType = responseType;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
