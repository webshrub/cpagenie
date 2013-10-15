package com.webshrub.cpagenie.app.mvc.controller;

import com.webshrub.cpagenie.app.mvc.dto.*;
import com.webshrub.cpagenie.app.mvc.propertyeditor.AdvertiserEditor;
import com.webshrub.cpagenie.app.mvc.propertyeditor.AuthorityEditor;
import com.webshrub.cpagenie.app.mvc.propertyeditor.UserEditor;
import com.webshrub.cpagenie.app.mvc.service.AdvertiserService;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import com.webshrub.cpagenie.app.mvc.view.FlexJSONView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 10, 2010
 * Time: 4:41:54 PM
 */
@Controller
public class UserMappingController {
    public static final Logger LOGGER = Logger.getLogger(UserMappingController.class);

    private static final String USER_ASSIGNADVERTISER_URL_KEY = "/admin/user/assignadvertiser.htm";
    private static final String USER_ASSIGNADVERTISER_VIEW_KEY = "admin/user/assignadvertiser";
    private static final String USER_ASSIGNADVERTISER_REDIRECT_VIEW_KEY = "assignadvertiser.htm";

    private static final String USER_ASSIGNROLE_URL_KEY = "/admin/user/assignrole.htm";
    private static final String USER_ASSIGNROLE_VIEW_KEY = "admin/user/assignrole";
    private static final String USER_ASSIGNROLE_REDIRECT_VIEW_KEY = "assignrole.htm";

    private static final String USER_GETROLES_URL_KEY = "/admin/user/getroles.htm";
    private static final String USER_GETADVERTISERS_URL_KEY = "/admin/user/getadvertisers.htm";

    private static final String USER_ADVERTISER_MAPPING_KEY = "userAdvertiserMapping";
    private static final String USER_AUTHORITY_MAPPING_KEY = "userAuthorityMapping";
    private static final String ADVERTISER_LIST_KEY = "advertiserList";
    private static final String AUTHORITY_LIST_KEY = "authorityList";
    private static final String USER_AUTHORITY_SET_KEY = "userAuthoritySet";
    private static final String USER_ADVERTISER_SET_KEY = "userAdvertiserSet";
    private static final String ID_KEY = "id";
    private static final String USER_LIST_KEY = "userList";

    @Autowired
    private AdvertiserService advertiserService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdvertiserEditor advertiserEditor;
    @Autowired
    private AuthorityEditor authorityEditor;
    @Autowired
    private UserEditor userEditor;
    @Autowired
    private FlexJSONView flexJSONView;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Advertiser.class, advertiserEditor);
        dataBinder.registerCustomEditor(Authority.class, authorityEditor);
        dataBinder.registerCustomEditor(User.class, userEditor);
    }

    @ModelAttribute(USER_ADVERTISER_MAPPING_KEY)
    public UserAdvertiserMapping getUserAdvertiserMapping() {
        return new UserAdvertiserMapping();
    }

    @ModelAttribute(USER_AUTHORITY_MAPPING_KEY)
    public UserAuthorityMapping getUserAuthorityMapping() {
        return new UserAuthorityMapping();
    }


    @RequestMapping(value = USER_ASSIGNADVERTISER_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupAssignAdvertiserForm() {
        LOGGER.info("Inside UserMappingController.setupAssignAdvertiserForm");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(USER_LIST_KEY, userService.getUserList());
        return new ModelAndView(USER_ASSIGNADVERTISER_VIEW_KEY, modelMap);
    }

    @RequestMapping(value = USER_ASSIGNADVERTISER_URL_KEY, method = RequestMethod.POST)
    public ModelAndView assignAdvertiser(UserAdvertiserMapping userAdvertiserMapping) {
        LOGGER.info("Inside UserMappingController.assignAdvertiser");
        if (userAdvertiserMapping.getUser().getId() != 0) {
            User user = userAdvertiserMapping.getUser();
            if (userAdvertiserMapping.getAdvertisers() != null) {
                user.setAdvertisers(new HashSet<Advertiser>(userAdvertiserMapping.getAdvertisers()));
            } else {
                user.setAdvertisers(new HashSet<Advertiser>());
            }
            userService.updateUser(user);
        }
        return new ModelAndView(new RedirectView(USER_ASSIGNADVERTISER_REDIRECT_VIEW_KEY));
    }

    @RequestMapping(value = USER_GETADVERTISERS_URL_KEY, method = RequestMethod.GET)
    public ModelAndView getAdvertisers(@RequestParam(value = ID_KEY, required = true) Integer userId) {
        LOGGER.info("Inside UserMappingController.getAdvertisers");
        ModelMap modelMap = new ModelMap();
        Set<Advertiser> userAdvertiserSet = userService.getUser(userId).getAdvertisers();
        modelMap.addAttribute(USER_ADVERTISER_SET_KEY, userAdvertiserSet);
        modelMap.addAttribute(ADVERTISER_LIST_KEY, advertiserService.getAdvertiserList());
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = USER_ASSIGNROLE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupAssignRoleForm() {
        LOGGER.info("Inside UserMappingController.setupAssignRoleForm");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(USER_LIST_KEY, userService.getUserList());
        return new ModelAndView(USER_ASSIGNROLE_VIEW_KEY, modelMap);
    }

    @RequestMapping(value = USER_ASSIGNROLE_URL_KEY, method = RequestMethod.POST)
    public ModelAndView assignRole(UserAuthorityMapping userAuthorityMapping) {
        LOGGER.info("Inside UserMappingController.assignRole");
        if (userAuthorityMapping.getUser().getId() != 0) {
            User user = userAuthorityMapping.getUser();
            if (userAuthorityMapping.getAuthorities() != null) {
                user.setAuthorities(new HashSet<Authority>(userAuthorityMapping.getAuthorities()));
            } else {
                user.setAuthorities(new HashSet<Authority>());
            }
            userService.updateUser(user);
        }
        return new ModelAndView(new RedirectView(USER_ASSIGNROLE_REDIRECT_VIEW_KEY));
    }

    @RequestMapping(value = USER_GETROLES_URL_KEY, method = RequestMethod.GET)
    public ModelAndView getRoles(@RequestParam(value = ID_KEY, required = true) Integer userId) {
        LOGGER.info("Inside UserMappingController.getRoles");
        ModelMap modelMap = new ModelMap();
        Set<Authority> userAuthoritySet = userService.getUser(userId).getAuthorities();
        modelMap.addAttribute(USER_AUTHORITY_SET_KEY, userAuthoritySet);
        modelMap.addAttribute(AUTHORITY_LIST_KEY, userService.getAuthorityList());
        return new ModelAndView(flexJSONView, modelMap);
    }
}
