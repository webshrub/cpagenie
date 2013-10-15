package com.webshrub.cpagenie.app.mvc.controller;

import com.webshrub.cpagenie.app.common.properties.AppProperties;
import com.webshrub.cpagenie.app.common.util.AppUtil;
import com.webshrub.cpagenie.app.mvc.authentication.AuthenticationUtil;
import com.webshrub.cpagenie.app.mvc.dto.*;
import com.webshrub.cpagenie.app.mvc.propertyeditor.*;
import com.webshrub.cpagenie.app.mvc.service.CampaignService;
import com.webshrub.cpagenie.app.mvc.service.AdvertiserService;
import com.webshrub.cpagenie.app.mvc.validator.CampaignValidator;
import com.webshrub.cpagenie.app.mvc.view.FlexJSONView;
import com.webshrub.cpagenie.core.common.exception.DataException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

@Controller
@SessionAttributes("campaign")
public class CampaignController {
    private static final Logger LOGGER = Logger.getLogger(CampaignController.class);

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String CAMPAIGN_CREATE_URL_KEY = "/secure/campaign/create.htm";
    private static final String CAMPAIGN_UPDATE_URL_KEY = "/secure/campaign/update.htm";
    private static final String CAMPAIGN_FORM_PREVIEW_URL_KEY = "/secure/campaign/formPreview.htm";
    private static final String CAMPAIGN_SCRIPT_PREVIEW_URL_KEY = "/secure/campaign/scriptPreview.htm";
    private static final String CAMPAIGN_SOURCE_OPTIONS_URL_KEY = "/secure/campaign/sourceOptions.htm";
    private static final String CAMPAIGN_LIST_URL_KEY = "/secure/campaign/list.htm";
    private static final String ADD_CAMPAIGN_FIELD_URL_KEY = "/secure/campaign/addCampaignField.htm";

    private static final String CAMPAIGN_CREATE_VIEW_KEY = "secure/campaign/create";
    private static final String CAMPAIGN_LIST_VIEW_KEY = "secure/campaign/list";

    private static final String CAMPAIGN_LIST_REDIRECT_VIEW_KEY = "list.htm";

    private static final String CAMPAIGN_KEY = "campaign";
    private static final String CAMPAIGN_LIST_KEY = "campaignList";
    private static final String STATUS_LIST_KEY = "statusList";
    private static final String RESPONSE_TYPE_LIST_KEY = "responseTypeList";
    private static final String ADVERTISER_LIST_KEY = "advertiserList";
    private static final String ERRORS_KEY = "errors";
    private static final String STATUS_KEY = "status";
    private static final String SUCCESS_KEY = "success";
    private static final String FAILURE_KEY = "failure";
    private static final String UPDATE_KEY = "update";
    private static final String DELETE_KEY = "delete";
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String START_DATE_KEY = "startDate";
    private static final String END_DATE_KEY = "endDate";
    private static final String EMAIL_KEY = "email";
    private static final String COST_PER_LEAD_KEY = "costPerLead";
    private static final String TOTAL_BUDGET_KEY = "totalBudget";
    private static final String RESPONSE_TYPE_KEY = "responseType";
    private static final String SUCCESS_RESPONSE_KEY = "successResponse";
    private static final String FAILURE_RESPONSE_KEY = "failureResponse";
    private static final String ADVERTISER_KEY = "advertiser";
    private static final String FIELDS_KEY = "fields";
    private static final String SUPPORTED_FIELD_LIST_KEY = "supportedFieldList";
    private static final String FIELD_TYPE_LIST_KEY = "fieldTypeList";
    private static final String FIELD_VALIDATION_TYPE_LIST_KEY = "fieldValidationTypeList";
    private static final String FIELD_POSITION_KEY = "fieldPosition";
    private static final String REMOVE_ID_SEPARATOR_KEY = "\\:";

    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private CampaignService campaignService;
    @Autowired
    private AdvertiserService advertiserService;
    @Autowired
    private CampaignValidator campaignValidator;
    @Autowired
    private CampaignStatusEditor statusEditor;
    @Autowired
    private CampaignResponseTypeEditor responseTypeEditor;
    @Autowired
    private CampaignSupportedFieldEditor supportedFieldEditor;
    @Autowired
    private CampaignFieldTypeEditor fieldTypeEditor;
    @Autowired
    private CampaignFieldValidationTypeEditor validationTypeEditor;
    @Autowired
    private AdvertiserEditor advertiserEditor;
    @Autowired
    private FlexJSONView flexJSONView;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(ID_KEY);
        dataBinder.setRequiredFields(NAME_KEY, START_DATE_KEY, END_DATE_KEY, EMAIL_KEY, COST_PER_LEAD_KEY, TOTAL_BUDGET_KEY, STATUS_KEY, RESPONSE_TYPE_KEY, SUCCESS_RESPONSE_KEY, FAILURE_RESPONSE_KEY, ADVERTISER_KEY);
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(DATE_FORMAT, false));
        dataBinder.registerCustomEditor(CampaignStatus.class, statusEditor);
        dataBinder.registerCustomEditor(CampaignResponseType.class, responseTypeEditor);
        dataBinder.registerCustomEditor(CampaignSupportedField.class, supportedFieldEditor);
        dataBinder.registerCustomEditor(CampaignFieldType.class, fieldTypeEditor);
        dataBinder.registerCustomEditor(CampaignFieldValidationType.class, validationTypeEditor);
        dataBinder.registerCustomEditor(Advertiser.class, advertiserEditor);
    }

    //Called only once at session start up because once it is added to session attribute map, it is only updated not created at subsequent calls of CRUD methods.
    @ModelAttribute(CAMPAIGN_KEY)
    public Campaign getCampaign() {
        return new Campaign();
    }

    @RequestMapping(value = CAMPAIGN_CREATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupCreateForm() {
        LOGGER.info("Inside CampaignController.setupCreateForm");
        ModelMap modelMap = new ModelMap();
        //Always return a new campaign to clear old modelAttribute from previous method executions.
        modelMap.addAttribute(CAMPAIGN_KEY, new Campaign());
        modelMap.addAttribute(START_DATE_KEY, new Date());
        modelMap.addAttribute(END_DATE_KEY, new Date());
        modelMap.addAttribute(STATUS_LIST_KEY, CampaignStatus.getStatusList());
        modelMap.addAttribute(RESPONSE_TYPE_LIST_KEY, CampaignResponseType.getResponseTypeList());
        modelMap.addAttribute(ADVERTISER_LIST_KEY, advertiserService.getAdvertiserList(authenticationUtil.getCurrentUser()));
        modelMap.addAttribute(SUPPORTED_FIELD_LIST_KEY, CampaignSupportedField.getSupportedFieldList());
        modelMap.addAttribute(FIELD_TYPE_LIST_KEY, CampaignFieldType.getFieldTypeList());
        modelMap.addAttribute(FIELD_VALIDATION_TYPE_LIST_KEY, CampaignFieldValidationType.getValidationTypeList());
        return new ModelAndView(CAMPAIGN_CREATE_VIEW_KEY, modelMap);
    }

    @RequestMapping(value = CAMPAIGN_CREATE_URL_KEY, method = RequestMethod.POST)
    public ModelAndView createCampaign(Campaign campaign, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside CampaignController.create");
        campaignValidator.validate(campaign, result);
        if (result.hasErrors()) {
            ModelAndView modelAndView = setupCreateForm();
            ModelMap modelMap = modelAndView.getModelMap();
            modelMap.addAttribute(START_DATE_KEY, campaign.getStartDate());
            modelMap.addAttribute(END_DATE_KEY, campaign.getEndDate());
            modelMap.addAttribute(FIELDS_KEY, campaign.getFields());
            return modelAndView;
        }
        campaignService.saveCampaign(campaign);
        status.setComplete();
        return new ModelAndView(new RedirectView(CAMPAIGN_LIST_REDIRECT_VIEW_KEY));
    }

    @RequestMapping(value = CAMPAIGN_UPDATE_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupUpdateForm(@RequestParam(value = ID_KEY, required = true) Integer campaignId) {
        LOGGER.info("Inside CampaignController.setupUpdateForm");
        ModelMap modelMap = new ModelMap();
        Campaign campaign = campaignService.getCampaign(campaignId);
        modelMap.addAttribute(CAMPAIGN_KEY, campaign);
        modelMap.addAttribute(STATUS_LIST_KEY, CampaignStatus.getStatusList());
        modelMap.addAttribute(RESPONSE_TYPE_LIST_KEY, CampaignResponseType.getResponseTypeList());
        modelMap.addAttribute(ADVERTISER_LIST_KEY, advertiserService.getAdvertiserList(authenticationUtil.getCurrentUser()));
        modelMap.addAttribute(SUPPORTED_FIELD_LIST_KEY, CampaignSupportedField.getSupportedFieldList());
        modelMap.addAttribute(FIELD_TYPE_LIST_KEY, CampaignFieldType.getFieldTypeList());
        modelMap.addAttribute(FIELD_VALIDATION_TYPE_LIST_KEY, CampaignFieldValidationType.getValidationTypeList());
        modelMap.addAttribute(FIELDS_KEY, campaign.getFields());
        return new ModelAndView(flexJSONView, modelMap);
    }
    
    @RequestMapping(value = CAMPAIGN_FORM_PREVIEW_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupPreviewForm(@RequestParam(value = ID_KEY, required = true) Integer campaignId) {
        LOGGER.info("Inside CampaignController.setupPreviewForm");
        ModelMap modelMap = new ModelMap();
        Campaign campaign = campaignService.getCampaign(campaignId);
        modelMap.addAttribute(CAMPAIGN_KEY, campaign);
        CampaignField [] fields = campaign.getFields().toArray(new CampaignField[campaign.getFields().size()]);
        Arrays.sort(fields, new Comparator<CampaignField>() {
			@Override
			public int compare(CampaignField o1,
					CampaignField o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
        modelMap.addAttribute(FIELDS_KEY, fields);
        return new ModelAndView(flexJSONView, modelMap);
    }
    
    @RequestMapping(value = CAMPAIGN_SCRIPT_PREVIEW_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupPreviewScript(@RequestParam(value = ID_KEY, required = true) Integer campaignId) throws DataException {
        LOGGER.info("Inside CampaignController.setupPreviewScript");
        ModelMap modelMap = new ModelMap();
        Campaign campaign = campaignService.getCampaign(campaignId);
        String serverAddresss= AppProperties.getInstance().getPropertyValue("app.server.address");
        modelMap.addAttribute("serverAddress", serverAddresss); 
        modelMap.addAttribute("sourceId", "3");
        modelMap.addAttribute("containerId", "cpagenieCampaign");
        modelMap.addAttribute("campaignId", campaignId);
        return new ModelAndView(flexJSONView, modelMap);
    }

    
    @RequestMapping(value = CAMPAIGN_SOURCE_OPTIONS_URL_KEY, method = RequestMethod.GET)
    public ModelAndView setupSourceOptions(@RequestParam(value = ID_KEY, required = true) Integer campaignId) {
        LOGGER.info("Inside CampaignController.setupSource");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("sourceOptions", campaignService.getSourceList());
        modelMap.addAttribute("campaignId", campaignId);
        return new ModelAndView(flexJSONView, modelMap);
    }
    
    private ModelAndView setupUpdateFormWithErrors(Campaign campaign, BindingResult result) {
        ModelAndView modelAndView = setupUpdateForm(campaign.getId());
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.addAttribute(STATUS_KEY, FAILURE_KEY);
        modelMap.addAttribute(ERRORS_KEY, result.getAllErrors());
        return modelAndView;
    }

    @RequestMapping(value = CAMPAIGN_UPDATE_URL_KEY, params = UPDATE_KEY, method = RequestMethod.POST)
    public ModelAndView updateCampaign(Campaign campaign, BindingResult result, SessionStatus status, @RequestParam(value = "removeIds", required = true) String removeIds) {
        LOGGER.info("Inside CampaignController.update");
        removeDeletedFields(campaign, removeIds);
        campaignValidator.validate(campaign, result);
        if (result.hasErrors()) {
            return setupUpdateFormWithErrors(campaign, result);
        }
        campaignService.updateCampaign(campaign);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = CAMPAIGN_UPDATE_URL_KEY, params = DELETE_KEY, method = RequestMethod.POST)
    public ModelAndView deleteCampaign(Campaign campaign, BindingResult result, SessionStatus status) {
        LOGGER.info("Inside CampaignController.delete");
        campaignValidator.validate(campaign, result);
        if (result.hasErrors()) {
            return setupUpdateFormWithErrors(campaign, result);
        }
        campaignService.deleteCampaign(campaign);
        status.setComplete();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(STATUS_KEY, SUCCESS_KEY);
        return new ModelAndView(flexJSONView, modelMap);
    }

    @RequestMapping(value = CAMPAIGN_LIST_URL_KEY, method = RequestMethod.GET)
    public ModelAndView getAllCampaigns() {
        LOGGER.info("Inside CampaignController.getAllCampaigns");
        return new ModelAndView(CAMPAIGN_LIST_VIEW_KEY, CAMPAIGN_LIST_KEY, campaignService.getCampaignList(authenticationUtil.getCurrentUser()));
    }

    @RequestMapping(value = ADD_CAMPAIGN_FIELD_URL_KEY, method = RequestMethod.GET)
    public ModelAndView addCampaignField(@RequestParam(value = FIELD_POSITION_KEY, required = true) Integer fieldPosition) {
        LOGGER.info("Inside CampaignController.addCampaignField");
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute(FIELD_POSITION_KEY, fieldPosition);
        modelMap.addAttribute(SUPPORTED_FIELD_LIST_KEY, CampaignSupportedField.getSupportedFieldList());
        modelMap.addAttribute(FIELD_TYPE_LIST_KEY, CampaignFieldType.getFieldTypeList());
        modelMap.addAttribute(FIELD_VALIDATION_TYPE_LIST_KEY, CampaignFieldValidationType.getValidationTypeList());
        return new ModelAndView(flexJSONView, modelMap);
    }

    private void removeDeletedFields(Campaign campaign, String removeIds) {
        if (!AppUtil.isEmpty(removeIds)) {
            String[] idStrArray = removeIds.split(REMOVE_ID_SEPARATOR_KEY);
            for (String idStr : idStrArray) {
                Integer fieldId = Integer.parseInt(idStr);
                Iterator<CampaignField> iterator = campaign.getFields().iterator();
                while (iterator.hasNext()) {
                    CampaignField field = iterator.next();
                    if (fieldId.equals(field.getId())) {
                        iterator.remove();
                    }
                }
            }
        }
    }
}
