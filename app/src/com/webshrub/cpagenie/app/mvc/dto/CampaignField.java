package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignField;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 12:57:11 AM
 */
public class CampaignField {
    private Integer id;
    private CampaignSupportedField field;
    private String description;
    private String parameter;
    private CampaignFieldType fieldType;
    private CampaignFieldValidationType fieldValidationType;
    private Campaign campaign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CampaignSupportedField getField() {
        return field;
    }

    public void setField(CampaignSupportedField field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public CampaignFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(CampaignFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public CampaignFieldValidationType getFieldValidationType() {
        return fieldValidationType;
    }

    public void setFieldValidationType(CampaignFieldValidationType fieldValidationType) {
        this.fieldValidationType = fieldValidationType;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public CampaignField fill(CPAGenieCampaignField field) {
        setId(field.getId());
        setField(new CampaignSupportedField(field.getField().getId(), field.getField().getName(), field.getField().getDisplayName()));
        setDescription(field.getDescription());
        setParameter(field.getParameter());
        setFieldType(new CampaignFieldType(field.getFieldType().getId(), field.getFieldType().getName()));
        setFieldValidationType(new CampaignFieldValidationType(field.getFieldValidationType().getId(), field.getFieldValidationType().getName()));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CampaignField that = (CampaignField) o;
        return !(campaign != null ? !campaign.equals(that.campaign) : that.campaign != null) && !(field != null ? !field.equals(that.field) : that.field != null);
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (campaign != null ? campaign.hashCode() : 0);
        return result;
    }
}
