package com.webshrub.cpagenie.app.mvc.service.impl;

import com.webshrub.cpagenie.app.mvc.dto.ValidationRule;
import com.webshrub.cpagenie.app.mvc.service.ValidationRuleService;
import com.webshrub.cpagenie.core.db.profane.CPAGenieProfane;
import com.webshrub.cpagenie.core.db.util.DbDataUtil;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 7, 2010
 * Time: 1:41:54 PM
 */
@Service
public class ValidationRuleServiceImpl implements ValidationRuleService {
    private static final Logger LOGGER = Logger.getLogger(ValidationRuleServiceImpl.class);
    private DbDataUtil dataUtil;

    @Autowired
    public ValidationRuleServiceImpl(DbDataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @SuppressWarnings("unchecked")
    public List<ValidationRule> getValidationRuleList() {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            List<ValidationRule> verticalList = new ArrayList<ValidationRule>();
            List<CPAGenieProfane> profanes = sessionHolder.getSession().createCriteria(CPAGenieProfane.class).list();
            for (CPAGenieProfane dbProfane : profanes) {
                verticalList.add(new ValidationRule().fill(dbProfane));
            }
            sessionHolder.commitTransaction();
            return verticalList;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getValidationRuleList()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getValidationRuleList()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public ValidationRule getValidationRule(Integer id) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieProfane dbProfane = (CPAGenieProfane) sessionHolder.getSession().get(CPAGenieProfane.class, id);
            sessionHolder.commitTransaction();
            return new ValidationRule().fill(dbProfane);
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in getValidationRule()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in getValidationRule()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void saveValidationRule(ValidationRule validationRule) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieProfane dbProfane = new CPAGenieProfane();
            dbProfane.setProfane(validationRule.getProfane());
            sessionHolder.getSession().saveOrUpdate(dbProfane);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in saveValidationRule()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in saveValidationRule()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void updateValidationRule(ValidationRule validationRule) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieProfane dbProfane = (CPAGenieProfane) sessionHolder.getSession().get(CPAGenieProfane.class, validationRule.getId());
            dbProfane.setProfane(validationRule.getProfane());
            sessionHolder.getSession().saveOrUpdate(dbProfane);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in updateValidationRule()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in updateValidationRule()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    public void deleteValidationRule(ValidationRule validationRule) {
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieProfane dbProfane = (CPAGenieProfane) sessionHolder.getSession().get(CPAGenieProfane.class, validationRule.getId());
            sessionHolder.getSession().delete(dbProfane);
            sessionHolder.commitTransaction();
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in deleteValidationRule()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in deleteValidationRule()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }
}