package com.webshrub.cpagenie.app.mvc.validator;

import com.webshrub.cpagenie.app.mvc.dto.Lead;
import com.webshrub.cpagenie.app.common.util.AppUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 11, 2010
 * Time: 3:06:00 PM
 */
@Component
public class LeadValidator {
    private static final String EMAIL_KEY = "email";
    private static final String HOME_PHONE_KEY = "homePhone";
    private static final String WORK_PHONE_KEY = "workPhone";
    private static final String MOBILE_PHONE_KEY = "mobilePhone";
    private static final String VALIDATION_LEAD_EMAIL_INVALID_KEY = "validation.lead.email.invalid";
    private static final String VALIDATION_LEAD_HOME_PHONE_INVALID_KEY = "validation.lead.homePhone.invalid";
    private static final String VALIDATION_LEAD_WORK_PHONE_INVALID_KEY = "validation.lead.workPhone.invalid";
    private static final String VALIDATION_LEAD_MOBILE_PHONE_INVALID_KEY = "validation.lead.mobilePhone.invalid";


    public void validate(Lead lead, Errors errors) {
        //Check if there are any errors due to binding failures. No need to move ahead then.
        if (errors.hasErrors()) {
            return;
        }
        if (lead.getEmail() != null && !lead.getEmail().equals("") && !AppUtil.validEmail(lead.getEmail())) {
            errors.rejectValue(EMAIL_KEY, VALIDATION_LEAD_EMAIL_INVALID_KEY, "Lead email is not valid.");
        }
        if (lead.getHomePhone() != null && !lead.getHomePhone().equals("") && !AppUtil.validPhone(lead.getHomePhone())) {
            errors.rejectValue(HOME_PHONE_KEY, VALIDATION_LEAD_HOME_PHONE_INVALID_KEY, "Home phone should contain digits only.");
        }
        if (lead.getWorkPhone() != null && !lead.getWorkPhone().equals("") && !AppUtil.validPhone(lead.getWorkPhone())) {
            errors.rejectValue(WORK_PHONE_KEY, VALIDATION_LEAD_WORK_PHONE_INVALID_KEY, "Work phone should contain digits only.");
        }
        if (lead.getMobilePhone() != null && !lead.getMobilePhone().equals("") && !AppUtil.validPhone(lead.getMobilePhone())) {
            errors.rejectValue(MOBILE_PHONE_KEY, VALIDATION_LEAD_MOBILE_PHONE_INVALID_KEY, "Mobile phone should contain digits only.");
        }
    }
}