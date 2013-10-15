package com.webshrub.cpagenie.app.mvc.controller;

import com.webshrub.cpagenie.app.mvc.authentication.AuthenticationUtil;
import com.webshrub.cpagenie.app.mvc.dto.Advertiser;
import com.webshrub.cpagenie.app.mvc.dto.Vertical;
import com.webshrub.cpagenie.app.mvc.propertyeditor.VerticalEditor;
import com.webshrub.cpagenie.app.mvc.service.AdvertiserService;
import com.webshrub.cpagenie.app.mvc.service.VerticalService;
import com.webshrub.cpagenie.app.mvc.validator.AdvertiserValidator;
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
@SessionAttributes("advertiser")
public class AdvertiserController {
    public static final Logger LOGGER = Logger.getLogger(AdvertiserController.class);

    private static final String ADVERTISER_CREATE_URL_KEY = "/secure/advertiser/create.htm";
    private static final String ADVERTISER_UPDATE_URL_KEY = "/secure/advertiser/update.htm";
    private static final String ADVERTISER_LIST_URL_KEY = "/secure/advertiser/list.htm";

    private static final String ADVERTISER_CREATE_VIEW_KEY = "secure/advertiser/create";
    private static final String ADVERTISER_LIST_VIEW_KEY = "secure/advertiser/list";

    private static final String ADVERTISER_LIST_REDIRECT_VIEW_KEY = "list.htm";

    private static final String ADVERTISER_KEY = "advertiser";
    private static final String ADVERTISER_LIST_KEY = "advertiserList";
    private static final String VERTICAL_LIST_KEY = "verticalList";
    private static final String ERRORS_KEY = "errors";
    private static final String STATUS_KEY = "status";
    private static final String SUCCESS_KEY = "success";
    private static final String FAILURE_KEY = "failure";
    private static final String UPDATE_KEY = "update";
    private static final String DELETE_KEY = "delete";
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String EMAIL_KEY = "email";
    private static final String VERTICAL_KEY = "vertical";

    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private AdvertiserService advertiserService;
    @Autowired
    private VerticalService verticalService;
    @Autowired
    private AdvertiserValidator advertiserValidator;
    @Autowired
    private VerticalEditor verticalEditor;
    @Autowired
    private FlexJSONView flexJSONView;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(ID_KEY);
        dataBinder.setRequiredFields(NAME_KEY, EMAIL_KEY, VERTICAL_KEY);
        dataBinder.registerCustomEditor(Vertical.class, verticalEditor);
    }

    //Called only once at session start up because once it is added to session attribute map, it is only updated not created at subsequent calls of CRUD methods.
    @ModelAttribute(ADVERTISER_KEY)
    public Advertiser getAdvertiser() {
        return new Advertiser();
    }

    @RequestMapping(value = ADVERTISER_CREATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupCreateForm() {
        LOGGER.info("Inside AdvertiserController.setupCreateForm");
        ModelMap modelMap = new ModelMap();
        //Always return a new advertiser to clear old modelAttribute from previous method executions.
        modelMap.addAttribute(ADVERTISER_KEY, new Advertiser());
        modelMap.addAttribute(VERTICAL_LIST_KEY, verticalService.getVerticalList());
        return new ModelAndView(ADVERTISER_CREATE_VIEW_KEY, modelMap);
    }

    @RequestMapping(value = ADVERTISER_CREATE_URL_KEY, method = RequestMethod.POST)
    public ModelAndView createAdvertiser(Advertiser advertiser, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside AdvertiserController.create");
        advertiserValidator.validate(advertiser, result);
        if (result.hasErrors()) {
            return setupCreateForm();
        }
        advertiserService.saveAdvertiser(advertiser);
        status.setComplete();
        return new ModelAndView(new RedirectView(ADVERTISER_LIST_REDIRECT_VIEW_KEY));
    }

    @RequestMapping(value = ADVERTISER_UPDATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupUpdateForm(@RequestParam(value = ID_KEY, required = true) Integer advertiserId) {
        LOGGER.info("Inside AdvertiserController.setupUpdateForm");
        ModelMap modelMap = new ModelMap();
        Advertiser advertiser = advertiserService.getAdvertiser(advertiserId);
        modelMap.addAttribute(ADVERTISER_KEY, advertiser);
        modelMap.addAttribute(VERTICAL_LIST_KEY, verticalService.getVerticalList());
        return new ModelAndView(flexJSONView, modelMap);
    }

    private ModelAndView setupUpdateFormWithErrors(Advertiser advertiser, BindingResult result) {
        ModelAndView modelAndView = setupUpdateForm(advertiser.getId());
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.addAttribute(STATUS_KEY, FAILURE_KEY);
        modelMap.addAttribute(ERRORS_KEY, result.getAllErrors());
        return modelAndView;
    }

    @RequestMapping(value = ADVERTISER_UPDATE_URL_KEY, params = UPDATE_KEY, method = RequestMethod.POST)
    public ModelAndView updateAdvertiser(Advertiser advertiser, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside AdvertiserController.update");
        advertiserValidator.validate(advertiser, result);
        if (result.hasErrors()) {
            return setupUpdateFormWithErrors(advertiser, result);
        }
        advertiserService.updateAdvertiser(advertiser);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = ADVERTISER_UPDATE_URL_KEY, params = DELETE_KEY, method = RequestMethod.POST)
    public ModelAndView deleteAdvertiser(Advertiser advertiser, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside AdvertiserController.delete");
        advertiserValidator.validate(advertiser, result);
        if (result.hasErrors()) {
            return setupUpdateFormWithErrors(advertiser, result);
        }
        advertiserService.deleteAdvertiser(advertiser);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = ADVERTISER_LIST_URL_KEY, method = RequestMethod.GET)
    public ModelAndView getAllAdvertisers() {
        LOGGER.info("Inside AdvertiserController.getAllAdvertisers");
        return new ModelAndView(ADVERTISER_LIST_VIEW_KEY, ADVERTISER_LIST_KEY, advertiserService.getAdvertiserList(authenticationUtil.getCurrentUser()));
    }
}
