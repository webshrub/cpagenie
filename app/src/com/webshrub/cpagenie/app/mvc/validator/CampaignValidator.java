package com.webshrub.cpagenie.app.mvc.validator;

import com.webshrub.cpagenie.app.common.util.AppUtil;
import com.webshrub.cpagenie.app.mvc.authentication.AuthenticationUtil;
import com.webshrub.cpagenie.app.mvc.dto.Campaign;
import com.webshrub.cpagenie.app.mvc.dto.CampaignField;
import com.webshrub.cpagenie.app.mvc.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 11, 2010
 * Time: 3:06:00 PM
 */
@Component
public class CampaignValidator {
    private static final String NAME_KEY = "name";
    private static final String START_DATE_KEY = "startDate";
    private static final String EMAIL_KEY = "email";
    private static final String VALIDATION_CAMPAIGN_NAME_EXISTS_KEY = "validation.campaign.name.exists";
    private static final String VALIDATION_CAMPAIGN_START_DATE_INVALID_KEY = "validation.campaign.startDate.invalid";
    private static final String VALIDATION_CAMPAIGN_EMAIL_INVALID_KEY = "validation.campaign.email.invalid";
    private static final String VALIDATION_CAMPAIGN_FIELDS_NONE_KEY = "validation.campaign.fields.none";
    private static final String VALIDATION_CAMPAIGN_FIELDS_DUPLICATE_KEY = "validation.campaign.fields.duplicate";
    private static final String VALIDATION_CAMPAIGN_PARAMETER_DESCRIPTION_MISSING_KEY = "validation.campaign.parameter.description.missing";

    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private CampaignService campaignService;
    private static final String DESCRIPTION_KEY = "description";

    public void validate(Campaign campaign, Errors errors) {
        //Check if there are any errors due to binding failures. No need to move ahead then.
        if (errors.hasErrors()) {
            return;
        }
        //If object contains id field, means it is the object to be updated.
        if (campaign.getId() != null) {
            if (campaignService.getCampaignList(authenticationUtil.getCurrentUser()).contains(campaign)) {
                //This is the case where we are sure that we already have a object with same key fields.
                //Hence find the matching object.
                Campaign matchingCampaign = findMatchingCampaign(campaign);
                //If id fields are different, it means we got a matching object, hence update should not be allowed.
                if (!matchingCampaign.getId().equals(campaign.getId())) {
                    errors.rejectValue(NAME_KEY, VALIDATION_CAMPAIGN_NAME_EXISTS_KEY, "Campaign name already exists for selected advertiser.");
                }
            }
            validateCampaignForCreateUpdate(campaign, errors);
        } else {
            //This object is newly created object. Validate if it matches with any of the already existing objects.
            if (campaignService.getCampaignList(authenticationUtil.getCurrentUser()).contains(campaign)) {
                errors.rejectValue(NAME_KEY, VALIDATION_CAMPAIGN_NAME_EXISTS_KEY, "Campaign name already exists for selected advertiser.");
            }
            validateCampaignForCreateUpdate(campaign, errors);
        }
    }

    private void validateCampaignForCreateUpdate(Campaign campaign, Errors errors) {
        if (campaign.getStartDate().after(campaign.getEndDate())) {
            errors.rejectValue(START_DATE_KEY, VALIDATION_CAMPAIGN_START_DATE_INVALID_KEY, "Campaign start date should be less than end date.");
        }
        if (!AppUtil.validEmail(campaign.getEmail())) {
            errors.rejectValue(EMAIL_KEY, VALIDATION_CAMPAIGN_EMAIL_INVALID_KEY, "Campaign email is not valid.");
        }
        if (campaign.getFields().size() == 0) {
            //This is a hack to use description here to show error messages
            errors.rejectValue(DESCRIPTION_KEY, VALIDATION_CAMPAIGN_FIELDS_NONE_KEY, "Please specify some field mapping for campaign.");
        } else {
            Set<String> fieldNameSet = new HashSet<String>();
            for (CampaignField field : campaign.getFields()) {
                fieldNameSet.add(field.getField().getName());
                if (AppUtil.isEmpty(field.getParameter()) || AppUtil.isEmpty(field.getDescription())) {
                    errors.rejectValue(DESCRIPTION_KEY, VALIDATION_CAMPAIGN_PARAMETER_DESCRIPTION_MISSING_KEY, "Please specify the parameter and description for field mapping.");
                }
            }
            if (fieldNameSet.size() < campaign.getFields().size()) {
                errors.rejectValue(DESCRIPTION_KEY, VALIDATION_CAMPAIGN_FIELDS_DUPLICATE_KEY, "You can not specify same field mapping for more than one parameter.");
            }
        }
    }

    private Campaign findMatchingCampaign(Campaign campaign) {
        //This method must compare objects using key fields only.
        Campaign matchingCampaign = null;
        for (Campaign campaignItem : campaignService.getCampaignList(authenticationUtil.getCurrentUser())) {
            if (campaignItem.getName().equalsIgnoreCase(campaign.getName()) && campaignItem.getAdvertiser().equals(campaign.getAdvertiser())) {
                matchingCampaign = campaignItem;
            }
        }
        return matchingCampaign;
    }
}
