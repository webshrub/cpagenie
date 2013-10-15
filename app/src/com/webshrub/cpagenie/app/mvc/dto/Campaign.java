package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignField;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 23, 2010
 * Time: 6:01:39 PM
 */
public class Campaign {
    private Integer id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date completionDate;
    private String email;
    private Double costPerLead;
    private Double totalBudget;
    private CampaignStatus status;
    private CampaignResponseType responseType;
    private String successResponse;
    private String failureResponse;
    private Date creationTime;
    private Date updateTime;
    private String updateComments;
    private Advertiser advertiser;
    private List<CampaignField> fields = new ArrayList<CampaignField>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getCostPerLead() {
        return costPerLead;
    }

    public void setCostPerLead(Double costPerLead) {
        this.costPerLead = costPerLead;
    }

    public Double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public CampaignResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(CampaignResponseType responseType) {
        this.responseType = responseType;
    }

    public String getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(String successResponse) {
        this.successResponse = successResponse;
    }

    public String getFailureResponse() {
        return failureResponse;
    }

    public void setFailureResponse(String failureResponse) {
        this.failureResponse = failureResponse;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateComments() {
        return updateComments;
    }

    public void setUpdateComments(String updateComments) {
        this.updateComments = updateComments;
    }

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    public List<CampaignField> getFields() {
        return fields;
    }

    public void setFields(List<CampaignField> fields) {
        this.fields = fields;
    }

    public void addField(CampaignField field) {
        this.fields.add(field);
    }

    public Campaign fill(CPAGenieCampaign campaign) {
        setId(campaign.getId());
        setName(campaign.getName());
        setDescription(campaign.getDescription());
        setStartDate(campaign.getStartDate());
        setEndDate(campaign.getEndDate());
        setCompletionDate(campaign.getCompletionDate());
        setEmail(campaign.getEmail());
        setCostPerLead(campaign.getCostPerLead());
        setTotalBudget(campaign.getTotalBudget());
        setStatus(CampaignStatus.getStatus(campaign.getStatus().getId()));
        setResponseType(CampaignResponseType.getResponseType(campaign.getResponse().getResponseType().getId()));
        setSuccessResponse(campaign.getResponse().getSuccessResponse());
        setFailureResponse(campaign.getResponse().getFailureResponse());
        setCreationTime(campaign.getCreationTime());
        setUpdateTime(campaign.getUpdateTime());
        setUpdateComments(campaign.getUpdateComments());
        setAdvertiser(new Advertiser().fill(campaign.getAdvertiser()));
        Set<CPAGenieCampaignField> fields = campaign.getFields();
        for (CPAGenieCampaignField field : fields) {
            addField(new CampaignField().fill(field));
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campaign campaign = (Campaign) o;
        return !(name != null ? !name.equals(campaign.name) : campaign.name != null) && !(advertiser != null ? !advertiser.equals(campaign.advertiser) : campaign.advertiser != null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (advertiser != null ? advertiser.hashCode() : 0);
        return result;
    }
}
