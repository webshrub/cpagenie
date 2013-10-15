package com.webshrub.cpagenie.core.db.campaign.field;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 26, 2010
 * Time: 1:47:10 PM
 */
@Entity
@Table(name = "CG_CAMPAIGN_FIELD", uniqueConstraints = @UniqueConstraint(columnNames = {"FIELD", "CAMPAIGN_ID"}))
public class CPAGenieCampaignField {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FIELD", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieCampaignSupportedField field;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PARAMETER", nullable = false)
    private String parameter;

    @Column(name = "FIELD_TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieCampaignFieldType fieldType;

    @Column(name = "FIELD_VALIDATION_TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieCampaignFieldValidationType fieldValidationType;

    @ManyToOne(optional = false)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "CAMPAIGN_ID")
    private CPAGenieCampaign campaign;

    public CPAGenieCampaignField() {

    }

    public CPAGenieCampaignField(CPAGenieCampaign campaign) {
        this.campaign = campaign;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CPAGenieCampaignSupportedField getField() {
        return field;
    }

    public void setField(CPAGenieCampaignSupportedField field) {
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

    public CPAGenieCampaignFieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(CPAGenieCampaignFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public CPAGenieCampaignFieldValidationType getFieldValidationType() {
        return fieldValidationType;
    }

    public void setFieldValidationType(CPAGenieCampaignFieldValidationType fieldValidationType) {
        this.fieldValidationType = fieldValidationType;
    }

    public CPAGenieCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(CPAGenieCampaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieCampaignField that = (CPAGenieCampaignField) o;
        return campaign.equals(that.campaign) && field.equals(that.field);
    }

    @Override
    public int hashCode() {
        int result = field.hashCode();
        result = 31 * result + campaign.hashCode();
        return result;
    }
}
