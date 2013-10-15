package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.UserStatus;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 5:14:23 PM
 */
@Component
public class UserStatusEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        UserStatus status = (UserStatus) getValue();
        if (status == null) {
            return "No user status set";
        }
        return status.getId().toString();
    }

    @Override
    public void setAsText(String idStr) throws IllegalArgumentException {
        setValue(UserStatus.getStatus(Integer.parseInt(idStr)));
    }
}