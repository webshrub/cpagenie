package com.webshrub.cpagenie.app.mvc.validator;

import com.webshrub.cpagenie.app.mvc.dto.User;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import com.webshrub.cpagenie.app.common.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 11, 2010
 * Time: 3:06:00 PM
 */
@Component
public class UserValidator {
    private static final String USERNAME_KEY = "username";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String CONFIRM_PASSWORD_KEY = "confirmPassword";
    private static final String REQUIRED_USER_PASSWORD_KEY = "required.user.password";
    private static final String REQUIRED_USER_CONFIRM_PASSWORD_KEY = "required.user.confirmPassword";
    private static final String VALIDATION_USER_EMAIL_INVALID_KEY = "validation.user.email.invalid";
    private static final String VALIDATION_USER_PASSWORD_DO_NOT_MATCH_KEY = "validation.user.password.do.not.match";
    private static final String VALIDATION_USER_USERNAME_EXISTS_KEY = "validation.user.username.exists";

    @Autowired
    private UserService userService;

    public void validate(User user, Errors errors, boolean validateForCreate) {
        //Check if there are any errors due to binding failures. No need to move ahead then.
        if (errors.hasErrors()) {
            return;
        }
        if (validateForCreate) {
            if (user.getPassword() == null || user.getPassword().equals("")) {
                errors.rejectValue(PASSWORD_KEY, REQUIRED_USER_PASSWORD_KEY, "User password is required.");
            }
            if (user.getConfirmPassword() == null || user.getConfirmPassword().equals("")) {
                errors.rejectValue(CONFIRM_PASSWORD_KEY, REQUIRED_USER_CONFIRM_PASSWORD_KEY, "User confirm password is required.");
            }
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue(PASSWORD_KEY, VALIDATION_USER_PASSWORD_DO_NOT_MATCH_KEY, "Password and Confirm password do not match");
        }
        //If object contains id field, means it is the object to be updated.
        if (user.getId() != null) {
            if (userService.getUserList().contains(user)) {
                //This is the case where we are sure that we already have a object with same key fields.
                //Hence find the matching object.
                User matchingUser = findMatchingUser(user);
                //If id fields are different, it means we got a matching object, hence update should not be allowed.
                if (!matchingUser.getId().equals(user.getId())) {
                    errors.rejectValue(USERNAME_KEY, VALIDATION_USER_USERNAME_EXISTS_KEY, "Username already exists.");
                }
            }
            if (!AppUtil.validEmail(user.getEmail())) {
                errors.rejectValue(EMAIL_KEY, VALIDATION_USER_EMAIL_INVALID_KEY, "User email is not valid.");
            }
        } else {
            //This object is newly created object. Validate if it matches with any of the already existing objects.
            if (userService.getUserList().contains(user)) {
                errors.rejectValue(USERNAME_KEY, VALIDATION_USER_USERNAME_EXISTS_KEY, "Username already exists.");
            }
            if (!AppUtil.validEmail(user.getEmail())) {
                errors.rejectValue(EMAIL_KEY, VALIDATION_USER_EMAIL_INVALID_KEY, "User email is not valid.");
            }
        }
    }

    private User findMatchingUser(User user) {
        //This method must compare objects using key fields only.
        User matchingUser = null;
        for (User userItem : userService.getUserList()) {
            if (userItem.getUsername().equalsIgnoreCase(user.getUsername())) {
                matchingUser = userItem;
            }
        }
        return matchingUser;
    }
}
