package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.Authority;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * Authority: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 5:14:23 PM
 */
@Component
public class AuthorityEditor extends PropertyEditorSupport {

    @Autowired
    private UserService userService;

    @Override
    public String getAsText() {
        Authority authority = (Authority) getValue();
        if (authority == null) {
            return "No authority  set";
        }
        return authority.getId().toString();
    }

    @Override
    public void setAsText(String idStr) throws IllegalArgumentException {
        setValue(userService.getAuthority(Integer.parseInt(idStr)));
    }
}