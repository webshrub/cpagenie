package com.webshrub.cpagenie.app.mvc.validator;

import com.webshrub.cpagenie.app.mvc.dto.ValidationRule;
import com.webshrub.cpagenie.app.mvc.service.ValidationRuleService;
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
public class ValidationRuleValidator {
    private static final String PROFANE_KEY = "profane";
    private static final String VALIDATION_VALIDATION_RULE_PROFANE_EXISTS_KEY = "validation.validationRule.profane.exists";

    @Autowired
    private ValidationRuleService validationRuleService;

    public void validate(ValidationRule validationRule, Errors errors) {
        //Check if there are any errors due to binding failures. No need to move ahead then.
        if (errors.hasErrors()) {
            return;
        }
        //If object contains id field, means it is the object to be updated.
        if (validationRule.getId() != null) {
            if (validationRuleService.getValidationRuleList().contains(validationRule)) {
                //This is the case where we are sure that we already have a object with same key fields.
                //Hence find the matching object.
                ValidationRule matchingValidationRule = findMatchingValidationRule(validationRule);
                //If id fields are different, it means we got a matching object, hence update should not be allowed.
                if (!matchingValidationRule.getId().equals(validationRule.getId())) {
                    errors.rejectValue(PROFANE_KEY, VALIDATION_VALIDATION_RULE_PROFANE_EXISTS_KEY, "Validation rule profane already exists.");
                }
            }
        } else {
            //This object is newly created object. Validate if it matches with any of the already existing objects.
            if (validationRuleService.getValidationRuleList().contains(validationRule)) {
                errors.rejectValue(PROFANE_KEY, VALIDATION_VALIDATION_RULE_PROFANE_EXISTS_KEY, "Validation rule profane already exists.");
            }
        }
    }

    private ValidationRule findMatchingValidationRule(ValidationRule validationRule) {
        //This method must compare objects using key fields only.
        ValidationRule matchingValidationRule = null;
        for (ValidationRule validationRuleItem : validationRuleService.getValidationRuleList()) {
            if (validationRuleItem.getProfane().equalsIgnoreCase(validationRule.getProfane())) {
                matchingValidationRule = validationRuleItem;
            }
        }
        return matchingValidationRule;
    }
}