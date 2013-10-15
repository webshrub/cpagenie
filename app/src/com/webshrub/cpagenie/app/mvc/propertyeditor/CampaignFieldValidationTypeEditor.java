package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.CampaignFieldValidationType;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 5:15:42 PM
 */
@Component
public class CampaignFieldValidationTypeEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        CampaignFieldValidationType validationType = (CampaignFieldValidationType) getValue();
        if (validationType == null) {
            return "No campaign field validation type set";
        }
        return validationType.getId().toString();
    }

    @Override
    public void setAsText(String idStr) throws IllegalArgumentException {
        setValue(CampaignFieldValidationType.getValidationType(Integer.parseInt(idStr)));
    }
}
