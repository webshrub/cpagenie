package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.LeadStatus;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 23, 2010
 * Time: 11:35:38 PM
 */
@Component
public class LeadStatusEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        LeadStatus status = (LeadStatus) getValue();
        if (status == null) {
            return "No lead status set";
        }
        return status.getId().toString();
    }

    @Override
    public void setAsText(String idStr) throws IllegalArgumentException {
        setValue(LeadStatus.getStatus(Integer.parseInt(idStr)));
    }
}
