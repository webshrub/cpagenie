package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.CampaignFieldType;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 5:14:23 PM
 */
@Component
public class CampaignFieldTypeEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        CampaignFieldType fieldType = (CampaignFieldType) getValue();
        if (fieldType == null) {
            return "No campaign field type set";
        }
        return fieldType.getId().toString();
    }

    @Override
    public void setAsText(String idStr) throws IllegalArgumentException {
        setValue(CampaignFieldType.getFieldType(Integer.parseInt(idStr)));
    }
}