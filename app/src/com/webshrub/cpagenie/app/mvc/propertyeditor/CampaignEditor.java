package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.Campaign;
import com.webshrub.cpagenie.app.mvc.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class CampaignEditor extends PropertyEditorSupport {
    @Autowired
    private CampaignService campaignService;

    @Override
    public String getAsText() {
        Campaign campaign = (Campaign) getValue();
        if (campaign == null) {
            return "No campaign set";
        }
        return campaign.getId().toString();
    }

    @Override
    public void setAsText(String campaignIdStr) throws IllegalArgumentException {
        setValue(campaignService.getCampaign(Integer.parseInt(campaignIdStr)));
    }
}