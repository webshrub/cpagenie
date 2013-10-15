package com.webshrub.cpagenie.app.mvc.validator;

import com.webshrub.cpagenie.app.common.util.AppUtil;
import com.webshrub.cpagenie.app.mvc.dto.Advertiser;
import com.webshrub.cpagenie.app.mvc.service.AdvertiserService;
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
public class AdvertiserValidator {
    private static final String NAME_KEY = "name";
    private static final String EMAIL_KEY = "email";
    private static final String VALIDATION_ADVERTISER_NAME_EXISTS_KEY = "validation.advertiser.name.exists";
    private static final String VALIDATION_ADVERTISER_EMAIL_INVALID_KEY = "validation.advertiser.email.invalid";

    @Autowired
    private AdvertiserService advertiserService;

    public void validate(Advertiser advertiser, Errors errors) {
        //Check if there are any errors due to binding failures. No need to move ahead then.
        if (errors.hasErrors()) {
            return;
        }
        //If object contains id field, means it is the object to be updated.
        if (advertiser.getId() != null) {
            if (advertiserService.getAdvertiserList().contains(advertiser)) {
                //This is the case where we are sure that we already have a object with same key fields.
                //Hence find the matching object.
                Advertiser matchingAdvertiser = findMatchingAdvertiser(advertiser);
                //If id fields are different, it means we got a matching object, hence update should not be allowed.
                if (!matchingAdvertiser.getId().equals(advertiser.getId())) {
                    errors.rejectValue(NAME_KEY, VALIDATION_ADVERTISER_NAME_EXISTS_KEY, "Advertiser name already exists for selected vertical.");
                }
            }
            if (!AppUtil.validEmail(advertiser.getEmail())) {
                errors.rejectValue(EMAIL_KEY, VALIDATION_ADVERTISER_EMAIL_INVALID_KEY, "Advertiser email is not valid.");
            }
        } else {
            //This object is newly created object. Validate if it matches with any of the already existing objects.
            if (advertiserService.getAdvertiserList().contains(advertiser)) {
                errors.rejectValue(NAME_KEY, VALIDATION_ADVERTISER_NAME_EXISTS_KEY, "Advertiser name already exists for selected vertical.");
            }
            if (!AppUtil.validEmail(advertiser.getEmail())) {
                errors.rejectValue(EMAIL_KEY, VALIDATION_ADVERTISER_EMAIL_INVALID_KEY, "Advertiser email is not valid.");
            }
        }
    }

    private Advertiser findMatchingAdvertiser(Advertiser advertiser) {
        //This method must compare objects using key fields only.
        Advertiser matchingAdvertiser = null;
        for (Advertiser advertiserItem : advertiserService.getAdvertiserList()) {
            if (advertiserItem.getName().equalsIgnoreCase(advertiser.getName()) && advertiserItem.getVertical().equals(advertiser.getVertical())) {
                matchingAdvertiser = advertiserItem;
            }
        }
        return matchingAdvertiser;
    }
}
