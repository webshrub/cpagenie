package com.webshrub.cpagenie.server.request.lead;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaignStatus;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignField;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignFieldType;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignFieldValidationType;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignSupportedField;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLeadStatus;
import com.webshrub.cpagenie.core.db.profane.CPAGenieProfane;
import com.webshrub.cpagenie.core.db.source.CPAGenieSource;
import com.webshrub.cpagenie.core.db.util.SessionHolder;
import com.webshrub.cpagenie.server.request.CPAGenieRequest;
import com.webshrub.cpagenie.server.request.CPAGenieRequestBuilder;
import com.webshrub.cpagenie.server.request.CPAGenieRequestConstants;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 27, 2010
 * Time: 4:47:11 PM
 */
public class LeadRequestBuilder extends CPAGenieRequestBuilder {
    public static final Logger LOGGER = Logger.getLogger(LeadRequestBuilder.class);
    private static final LeadRequestBuilder instance = new LeadRequestBuilder();

    private LeadRequestBuilder() {
        super();
    }

    public static LeadRequestBuilder getInstance() {
        return instance;
    }

    public CPAGenieRequest buildRequest(HttpServletRequest servletRequest) {
        Integer campaignId = Integer.parseInt(servletRequest.getParameter(CPAGenieRequestConstants.CAMPAIGN_ID_PARAM_STR));
        SessionHolder sessionHolder = dataUtil.getOrCreateSessionHolder();
        try {
            sessionHolder.beginTransaction();
            CPAGenieCampaign campaign = (CPAGenieCampaign) sessionHolder.getSession().get(CPAGenieCampaign.class, campaignId);
            if (campaign == null) {
                LOGGER.info("Returning null lead request due to unknown campaign with campaignId = " + campaignId);
                return null;
            }
            if (!campaign.getStatus().equals(CPAGenieCampaignStatus.STARTED)) {
                LOGGER.info("Returning null lead request due to inactive campaign with campaignId = " + campaignId);
                return null;
            }
            LeadRequest leadRequest = new LeadRequest();
            leadRequest.setCaptureTime(new Date());
            leadRequest.setIpAddress(servletRequest.getRemoteAddr());
            leadRequest.setUserAgent(servletRequest.getHeader(CPAGenieRequestConstants.USER_AGENT_HEADER_STR));
            Set<CPAGenieCampaignField> campaignFields = campaign.getFields();
            CPAGenieLeadStatus status = CPAGenieLeadStatus.VALID;
            for (CPAGenieCampaignField campaignField : campaignFields) {
                CPAGenieCampaignSupportedField field = campaignField.getField();
                String parameter = field.getName();
                if (servletRequest.getParameter(campaignField.getParameter()) != null && !servletRequest.getParameter(campaignField.getParameter()).equals("")) {
                    parameter = campaignField.getParameter();
                }
                CPAGenieCampaignFieldType fieldType = campaignField.getFieldType();
                CPAGenieCampaignFieldValidationType validationType = campaignField.getFieldValidationType();
                String parameterValue = servletRequest.getParameter(parameter);
                if (fieldType.equals(CPAGenieCampaignFieldType.REQUIRED) && (parameterValue == null || parameterValue.equalsIgnoreCase(""))) {
                    status = CPAGenieLeadStatus.INVALID_MISSING_FIELD;
                    continue;
                }
                if (validationType.equals(CPAGenieCampaignFieldValidationType.REQUIRED) && !validationPassed(sessionHolder, parameterValue)) {
                    status = CPAGenieLeadStatus.INVALID_VALIDATION_FAILED;
                }
                parseAndSetFieldValue(leadRequest, field, parameterValue);
            }
            leadRequest.setStatus(status);
            leadRequest.setCampaign(campaign);
            CPAGenieSource source = (CPAGenieSource) sessionHolder.getSession().get(CPAGenieSource.class, Integer.parseInt(servletRequest.getParameter(CPAGenieRequestConstants.SOURCE_ID_PARAM_STR)));
            leadRequest.setSource(source);
            return leadRequest;
        } catch (HibernateException e) {
            LOGGER.error("Exception occurred in buildRequest()", e);
            sessionHolder.rollbackTransaction();
            throw new RuntimeException("Exception occurred in buildRequest()", e);
        } finally {
            sessionHolder.closeSession();
        }
    }

    private static void parseAndSetFieldValue(LeadRequest leadRequest, CPAGenieCampaignSupportedField field, String parameterValue) {
        if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.DAY_OF_BIRTH_PARAM_STR)) {
            leadRequest.setDayOfBirth(Integer.parseInt(parameterValue));
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.MONTH_OF_BIRTH_PARAM_STR)) {
            leadRequest.setMonthOfBirth(Integer.parseInt(parameterValue));
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.YEAR_OF_BIRTH_PARAM_STR)) {
            leadRequest.setYearOfBirth(Integer.parseInt(parameterValue));
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.FIRST_NAME_PARAM_STR)) {
            leadRequest.setFirstName(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.LAST_NAME_PARAM_STR)) {
            leadRequest.setLastName(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.EMAIL_PARAM_STR)) {
            leadRequest.setEmail(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.ADDRESS1_PARAM_STR)) {
            leadRequest.setAddress1(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.ADDRESS2_PARAM_STR)) {
            leadRequest.setAddress2(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CITY_PARAM_STR)) {
            leadRequest.setCity(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.STATE_PARAM_STR)) {
            leadRequest.setState(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.COUNTRY_PARAM_STR)) {
            leadRequest.setCountry(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.PIN_CODE_PARAM_STR)) {
            leadRequest.setPinCode(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.HOME_PHONE_PARAM_STR)) {
            leadRequest.setHomePhone(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.WORK_PHONE_PARAM_STR)) {
            leadRequest.setWorkPhone(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.WORK_EXT_PARAM_STR)) {
            leadRequest.setWorkExt(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.OTHER_PHONE_PARAM_STR)) {
            leadRequest.setOtherPhone(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.MOBILE_PHONE_PARAM_STR)) {
            leadRequest.setMobilePhone(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM1_PARAM_STR)) {
            leadRequest.setCustom1(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM2_PARAM_STR)) {
            leadRequest.setCustom2(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM3_PARAM_STR)) {
            leadRequest.setCustom3(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM4_PARAM_STR)) {
            leadRequest.setCustom4(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM5_PARAM_STR)) {
            leadRequest.setCustom5(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM6_PARAM_STR)) {
            leadRequest.setCustom6(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM7_PARAM_STR)) {
            leadRequest.setCustom7(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM8_PARAM_STR)) {
            leadRequest.setCustom8(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM9_PARAM_STR)) {
            leadRequest.setCustom9(parameterValue);
        } else if (field.getName().equalsIgnoreCase(CPAGenieRequestConstants.CUSTOM10_PARAM_STR)) {
            leadRequest.setCustom10(parameterValue);
        }
    }

    @SuppressWarnings({"unchecked"})
    private boolean validationPassed(SessionHolder sessionHolder, String parameterValue) {
        Criteria criteria = sessionHolder.getSession().createCriteria(CPAGenieProfane.class);
        List<CPAGenieProfane> profanes = criteria.list();
        for (CPAGenieProfane profane : profanes) {
            if (Pattern.compile(Pattern.quote(profane.getProfane()), Pattern.CASE_INSENSITIVE).matcher(parameterValue).find()) {
                return false;
            }
        }
        return true;
    }
}
