package com.webshrub.cpagenie.app.mvc.controller;

import com.webshrub.cpagenie.app.mvc.dto.User;
import com.webshrub.cpagenie.app.mvc.dto.UserStatus;
import com.webshrub.cpagenie.app.mvc.propertyeditor.UserStatusEditor;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import com.webshrub.cpagenie.app.mvc.validator.UserValidator;
import com.webshrub.cpagenie.app.mvc.view.FlexJSONView;
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

@Controller
@SessionAttributes("user")
public class UserController {
    public static final Logger LOGGER = Logger.getLogger(UserController.class);

    private static final String USER_CREATE_URL_KEY = "/admin/user/create.htm";
    private static final String USER_UPDATE_URL_KEY = "/admin/user/update.htm";
    private static final String USER_LIST_URL_KEY = "/admin/user/list.htm";

    private static final String USER_CREATE_VIEW_KEY = "admin/user/create";
    private static final String USER_LIST_VIEW_KEY = "admin/user/list";

    private static final String USER_LIST_REDIRECT_VIEW_KEY = "list.htm";

    private static final String USER_KEY = "user";
    private static final String USER_LIST_KEY = "userList";
    private static final String STATUS_LIST_KEY = "statusList";
    private static final String ERRORS_KEY = "errors";
    private static final String STATUS_KEY = "status";
    private static final String SUCCESS_KEY = "success";
    private static final String FAILURE_KEY = "failure";
    private static final String UPDATE_KEY = "update";
    private static final String DELETE_KEY = "delete";
    private static final String ID_KEY = "id";
    private static final String USERNAME_KEY = "username";
    private static final String EMAIL_KEY = "email";
    private static final String FIRST_NAME_KEY = "firstName";
    private static final String LAST_NAME_KEY = "lastName";

    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserStatusEditor statusEditor;
    @Autowired
    private FlexJSONView flexJSONView;

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

    @RequestMapping(value = USER_CREATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupCreateForm() {
        LOGGER.info("Inside UserController.setupCreateForm");
        ModelMap modelMap = new ModelMap();
        //Always return a new user to clear old modelAttribute from previous method executions.
        modelMap.addAttribute(USER_KEY, new User());
        modelMap.addAttribute(STATUS_LIST_KEY, UserStatus.getUserStatusList());
        return new ModelAndView(USER_CREATE_VIEW_KEY, modelMap);
    }

    @RequestMapping(value = USER_CREATE_URL_KEY, method = RequestMethod.POST)
    public ModelAndView createUser(User user, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside UserController.create");
        userValidator.validate(user, result, true);
        if (result.hasErrors()) {
            return setupCreateForm();
        }
        user.setPassword(userService.getEncodedPassword(user, user.getPassword()));
        userService.saveUser(user);
        status.setComplete();
        return new ModelAndView(new RedirectView(USER_LIST_REDIRECT_VIEW_KEY));
    }

    @RequestMapping(value = USER_UPDATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupUpdateForm(@RequestParam(value = ID_KEY, required = true) Integer userId) {
        LOGGER.info("Inside UserController.setupUpdateForm");
        ModelMap modelMap = new ModelMap();
        User user = userService.getUser(userId);
        modelMap.addAttribute(USER_KEY, user);
        modelMap.addAttribute(STATUS_LIST_KEY, UserStatus.getUserStatusList());
        return new ModelAndView(flexJSONView, modelMap);
    }

    private ModelAndView setupUpdateFormWithErrors(User user, BindingResult result) {
        ModelAndView modelAndView = setupUpdateForm(user.getId());
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.addAttribute(STATUS_KEY, FAILURE_KEY);
        modelMap.addAttribute(ERRORS_KEY, result.getAllErrors());
        return modelAndView;
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

    @RequestMapping(value = USER_UPDATE_URL_KEY, params = UPDATE_KEY, method = RequestMethod.POST)
    public ModelAndView updateUser(User user, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside UserController.update");
        userValidator.validate(user, result, false);
        if (result.hasErrors()) {
            return setupUpdateFormWithErrors(user, result);
        }
        changeOrResetPassword(user);
        userService.updateUser(user);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = USER_UPDATE_URL_KEY, params = DELETE_KEY, method = RequestMethod.POST)
    public ModelAndView deleteUser(User user, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside UserController.delete");
        userValidator.validate(user, result, false);
        if (result.hasErrors()) {
            return setupUpdateFormWithErrors(user, result);
        }
        userService.deleteUser(user);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = USER_LIST_URL_KEY, method = RequestMethod.GET)
    public ModelAndView getAllUsers() {
        LOGGER.info("Inside UserController.getAllUsers");
        return new ModelAndView(USER_LIST_VIEW_KEY, USER_LIST_KEY, userService.getUserList());
    }
}
