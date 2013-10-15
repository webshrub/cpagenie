package com.webshrub.cpagenie.app.mvc.controller;

import com.webshrub.cpagenie.app.mvc.authentication.AuthenticationUtil;
import com.webshrub.cpagenie.app.mvc.dto.User;
import com.webshrub.cpagenie.app.mvc.dto.UserStatus;
import com.webshrub.cpagenie.app.mvc.propertyeditor.UserStatusEditor;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import com.webshrub.cpagenie.app.mvc.validator.UserValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 18, 2010
 * Time: 9:48:22 PM
 */
@Controller
@SessionAttributes("user")
public class AccountController {
    public static final Logger LOGGER = Logger.getLogger(AccountController.class);

    private static final String USER_SETTINGS_URL_KEY = "/secure/account/settings.htm";
    private static final String USER_SETTINGS_VIEW_KEY = "secure/account/settings";
    private static final String USER_SETTINGS_REDIRECT_VIEW_KEY = "settings.htm";

    private static final String ID_KEY = "id";
    private static final String USERNAME_KEY = "username";
    private static final String EMAIL_KEY = "email";
    private static final String FIRST_NAME_KEY = "firstName";
    private static final String LAST_NAME_KEY = "lastName";
    private static final String STATUS_KEY = "status";
    private static final String USER_KEY = "user";
    private static final String STATUS_LIST_KEY = "statusList";

    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserStatusEditor statusEditor;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(ID_KEY);
        dataBinder.setRequiredFields(USERNAME_KEY, EMAIL_KEY, FIRST_NAME_KEY, LAST_NAME_KEY, STATUS_KEY);
        dataBinder.registerCustomEditor(UserStatus.class, statusEditor);
    }

    //Called only once at session start up because once it is added to session attribute map, it is only updated not created at subsequent calls of CRUD methods.

    @ModelAttribute(USER_KEY)
    public User getUser() {
        return new User();
    }

    @RequestMapping(value = USER_SETTINGS_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupSettingsForm() {
        LOGGER.info("Inside AccountController.setupSettingsForm");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(USER_KEY, authenticationUtil.getCurrentUser());
        modelMap.addAttribute(STATUS_LIST_KEY, UserStatus.getUserStatusList());
        return new ModelAndView(USER_SETTINGS_VIEW_KEY, modelMap);
    }

    @RequestMapping(value = USER_SETTINGS_URL_KEY, method = RequestMethod.POST)
    public ModelAndView updateSettings(User user, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside AccountController.updateSettings");
        userValidator.validate(user, result, false);
        if (result.hasErrors()) {
            return setupSettingsForm();
        }
        changeOrResetPassword(user);
        userService.updateUser(user);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_LIST_KEY, UserStatus.getUserStatusList());
        return new ModelAndView(new RedirectView(USER_SETTINGS_REDIRECT_VIEW_KEY), modelMap);
    }

    private void changeOrResetPassword(User user) {
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();
        if (password.equals("") && confirmPassword.equals("")) {
            LOGGER.info("Setting the password to original one since user has not changed the password.");
            user.setPassword(userService.getUser(user.getId()).getPassword());
        } else {
            LOGGER.info("Setting the password to new one since user has changed the password.");
            user.setPassword(userService.getEncodedPassword(user, password));
        }
    }
}
