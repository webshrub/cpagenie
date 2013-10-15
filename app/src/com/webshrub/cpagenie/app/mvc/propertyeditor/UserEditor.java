package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.User;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 5:14:23 PM
 */
@Component
public class UserEditor extends PropertyEditorSupport {
    private static final User DEFAULT_USER_OPTION = new User();

    static {
        DEFAULT_USER_OPTION.setId(0);
        DEFAULT_USER_OPTION.setUsername("Select User");
    }

    @Autowired
    private UserService userService;

    @Override
    public String getAsText() {
        User user = (User) getValue();
        if (user == null) {
            return "No user  set";
        }
        return user.getId().toString();
    }

    @Override
    public void setAsText(String idStr) throws IllegalArgumentException {
        if (idStr.equals("0")) {
            setValue(DEFAULT_USER_OPTION);
        } else {
            setValue(userService.getUser(Integer.parseInt(idStr)));
        }
    }
}