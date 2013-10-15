package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.CampaignSupportedField;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 5:14:23 PM
 */
@Component
public class CampaignSupportedFieldEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        CampaignSupportedField supportedField = (CampaignSupportedField) getValue();
        if (supportedField == null) {
            return "No campaign supported field set";
        }
        return supportedField.getId().toString();
    }

    @Override
    public void setAsText(String idStr) throws IllegalArgumentException {
        setValue(CampaignSupportedField.getField(Integer.parseInt(idStr)));
    }
}