package com.webshrub.cpagenie.app.mvc.controller;

import com.webshrub.cpagenie.app.mvc.dto.ValidationRule;
import com.webshrub.cpagenie.app.mvc.service.ValidationRuleService;
import com.webshrub.cpagenie.app.mvc.validator.ValidationRuleValidator;
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
@SessionAttributes("validationRule")
public class ValidationRuleController {
    public static final Logger LOGGER = Logger.getLogger(ValidationRuleController.class);

    private static final String VALIDATION_RULE_CREATE_URL_KEY = "/admin/validationrule/create.htm";
    private static final String VALIDATION_RULE_UPDATE_URL_KEY = "/admin/validationrule/update.htm";
    private static final String VALIDATION_RULE_LIST_URL_KEY = "/admin/validationrule/list.htm";

    private static final String VALIDATION_RULE_CREATE_VIEW_KEY = "admin/validationrule/create";
    private static final String VALIDATION_RULE_LIST_VIEW_KEY = "admin/validationrule/list";

    private static final String VALIDATION_RULE_LIST_REDIRECT_VIEW_KEY = "list.htm";

    private static final String VALIDATION_RULE_KEY = "validationRule";
    private static final String VALIDATION_RULE_LIST_KEY = "validationRuleList";
    private static final String ERRORS_KEY = "errors";
    private static final String STATUS_KEY = "status";
    private static final String SUCCESS_KEY = "success";
    private static final String FAILURE_KEY = "failure";
    private static final String UPDATE_KEY = "update";
    private static final String DELETE_KEY = "delete";
    private static final String ID_KEY = "id";
    private static final String PROFANE_KEY = "profane";

    @Autowired
    private ValidationRuleService validationRuleService;
    @Autowired
    private ValidationRuleValidator validationRuleValidator;
    @Autowired
    private FlexJSONView flexJSONView;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(ID_KEY);
        dataBinder.setRequiredFields(PROFANE_KEY);
    }

    //Called only once at session start up because once it is added to session attribute map, it is only updated not created at subsequent calls of CRUD methods.
    @ModelAttribute(VALIDATION_RULE_KEY)
    public ValidationRule getValidationRule() {
        return new ValidationRule();
    }

    @RequestMapping(value = VALIDATION_RULE_CREATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupCreateForm() {
        LOGGER.info("Inside ValidationRuleController.setupCreateForm");
        ModelMap modelMap = new ModelMap();
        //Always return a new validationRule to clear old modelAttribute from previous method executions.
        modelMap.addAttribute(VALIDATION_RULE_KEY, new ValidationRule());
        return new ModelAndView(VALIDATION_RULE_CREATE_VIEW_KEY, modelMap);
    }

    @RequestMapping(value = VALIDATION_RULE_CREATE_URL_KEY, method = RequestMethod.POST)
    public ModelAndView createValidationRule(ValidationRule validationRule, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside ValidationRuleController.create");
        validationRuleValidator.validate(validationRule, result);
        if (result.hasErrors()) {
            return setupCreateForm();
        }
        validationRuleService.saveValidationRule(validationRule);
        status.setComplete();
        return new ModelAndView(new RedirectView(VALIDATION_RULE_LIST_REDIRECT_VIEW_KEY));
    }

    @RequestMapping(value = VALIDATION_RULE_UPDATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupUpdateForm(@RequestParam(value = ID_KEY, required = true) Integer validationRuleId) {
        LOGGER.info("Inside ValidationRuleController.setupUpdateForm");
        ModelMap modelMap = new ModelMap();
        ValidationRule validationRule = validationRuleService.getValidationRule(validationRuleId);
        modelMap.addAttribute(VALIDATION_RULE_KEY, validationRule);
        return new ModelAndView(flexJSONView, modelMap);
    }

    private ModelAndView setupUpdateFormWithErrors(ValidationRule validationRule, BindingResult result) {
        ModelAndView modelAndView = setupUpdateForm(validationRule.getId());
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.addAttribute(STATUS_KEY, FAILURE_KEY);
        modelMap.addAttribute(ERRORS_KEY, result.getAllErrors());
        return modelAndView;
    }

    @RequestMapping(value = VALIDATION_RULE_UPDATE_URL_KEY, params = UPDATE_KEY, method = RequestMethod.POST)
    public ModelAndView updateValidationRule(ValidationRule validationRule, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside ValidationRuleController.update");
        validationRuleValidator.validate(validationRule, result);
        if (result.hasErrors()) {
            return setupUpdateFormWithErrors(validationRule, result);
        }
        validationRuleService.updateValidationRule(validationRule);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = VALIDATION_RULE_UPDATE_URL_KEY, params = DELETE_KEY, method = RequestMethod.POST)
    public ModelAndView deleteValidationRule(ValidationRule validationRule, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside ValidationRuleController.delete");
        validationRuleValidator.validate(validationRule, result);
        if (result.hasErrors()) {
            return setupUpdateFormWithErrors(validationRule, result);
        }
        validationRuleService.deleteValidationRule(validationRule);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = VALIDATION_RULE_LIST_URL_KEY, method = RequestMethod.GET)
    public ModelAndView getAllValidationRules() {
        LOGGER.info("Inside ValidationRuleController.getAllValidationRules");
        return new ModelAndView(VALIDATION_RULE_LIST_VIEW_KEY, VALIDATION_RULE_LIST_KEY, validationRuleService.getValidationRuleList());
    }
}
