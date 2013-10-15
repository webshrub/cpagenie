package com.webshrub.cpagenie.app.mvc.service;

import com.webshrub.cpagenie.app.mvc.dto.ValidationRule;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 7, 2010
 * Time: 1:38:33 PM
 */
public interface ValidationRuleService {

    public List<ValidationRule> getValidationRuleList();

    public ValidationRule getValidationRule(Integer id);

    public void saveValidationRule(ValidationRule validationRule);

    public void updateValidationRule(ValidationRule validationRule);

    public void deleteValidationRule(ValidationRule validationRule);
}