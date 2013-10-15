package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.CampaignResponseType;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 23, 2010
 * Time: 11:35:38 PM
 */
@Component
public class CampaignResponseTypeEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        CampaignResponseType responseType = (CampaignResponseType) getValue();
        if (responseType == null) {
            return "No campaign response type set";
        }
        return responseType.getId().toString();
    }

    @Override
    public void setAsText(String idStr) throws IllegalArgumentException {
        setValue(CampaignResponseType.getResponseType(Integer.parseInt(idStr)));
    }
}