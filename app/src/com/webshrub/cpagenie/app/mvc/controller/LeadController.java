package com.webshrub.cpagenie.app.mvc.controller;

import com.webshrub.cpagenie.app.mvc.dto.Lead;
import com.webshrub.cpagenie.app.mvc.dto.LeadStatus;
import com.webshrub.cpagenie.app.mvc.propertyeditor.LeadStatusEditor;
import com.webshrub.cpagenie.app.mvc.service.LeadService;
import com.webshrub.cpagenie.app.mvc.validator.LeadValidator;
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

@Controller
@SessionAttributes("lead")
public class LeadController {
    public static final Logger LOGGER = Logger.getLogger(LeadController.class);

    private static final String LEAD_UPDATE_URL_KEY = "/secure/lead/update.htm";

    private static final String STATUS_LIST_KEY = "statusList";
    private static final String LEAD_KEY = "lead";
    private static final String ERRORS_KEY = "errors";
    private static final String STATUS_KEY = "status";
    private static final String SUCCESS_KEY = "success";
    private static final String FAILURE_KEY = "failure";
    private static final String UPDATE_KEY = "update";
    private static final String DELETE_KEY = "delete";
    private static final String ID_KEY = "id";

    @Autowired
    private LeadService leadService;
    @Autowired
    private FlexJSONView flexJSONView;
    @Autowired
    private LeadStatusEditor statusEditor;
    @Autowired
    private LeadValidator leadValidator;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(ID_KEY);
        dataBinder.registerCustomEditor(LeadStatus.class, statusEditor);
    }

    //Called only once at session start up because once it is added to session attribute map, it is only updated not created at subsequent calls of CRUD methods.
    @ModelAttribute(LEAD_KEY)
    public Lead getLead() {
        return new Lead();
    }

    @RequestMapping(value = LEAD_UPDATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupUpdateForm(@RequestParam(value = ID_KEY, required = true) Integer leadId) {
        LOGGER.info("Inside LeadController.setupUpdateForm");
        ModelMap modelMap = new ModelMap();
        Lead lead = leadService.getLead(leadId);
        modelMap.addAttribute(LEAD_KEY, lead);
        modelMap.addAttribute(STATUS_LIST_KEY, LeadStatus.getStatusList());
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = LEAD_UPDATE_URL_KEY, params = UPDATE_KEY, method = RequestMethod.POST)
    public ModelAndView updateLead(Lead lead, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside LeadController.update");
        leadValidator.validate(lead, result);
        if (result.hasErrors()) {
            ModelAndView modelAndView = setupUpdateForm(lead.getId());
            ModelMap modelMap = modelAndView.getModelMap();
            modelMap.addAttribute(STATUS_KEY, FAILURE_KEY);
            modelMap.addAttribute(ERRORS_KEY, result.getAllErrors());
            return modelAndView;
        } else {
            leadService.updateLead(lead);
            status.setComplete();
            ModelMap modelMap = new ModelMap();
            modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
            return new ModelAndView(flexJSONView, modelMap);
        }
    }

    @RequestMapping(value = LEAD_UPDATE_URL_KEY, params = DELETE_KEY, method = RequestMethod.POST)
    public ModelAndView deleteLead(Lead lead, SessionStatus status) {
        LOGGER.info("Inside LeadController.delete");
        leadService.deleteLead(lead);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }
}