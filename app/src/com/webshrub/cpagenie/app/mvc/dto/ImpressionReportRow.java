package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.app.common.util.AppUtil;
import com.webshrub.cpagenie.core.db.report.CPAGenieImpressionReportRow;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 16, 2010
 * Time: 11:08:18 AM
 */
public class ImpressionReportRow {
    private Integer id;
    private Date runTime;
    private Campaign campaign;
    private Integer impressions;
    private Integer submitCount;
    private Integer leadCount;
    private Double costPerLead;
    private Double revenue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Integer getImpressions() {
        return impressions;
    }

    public void setImpressions(Integer impressions) {
        this.impressions = impressions;
    }

    public Integer getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(Integer submitCount) {
        this.submitCount = submitCount;
    }

    public Integer getLeadCount() {
        return leadCount;
    }

    public void setLeadCount(Integer leadCount) {
        this.leadCount = leadCount;
    }

    public Double getCostPerLead() {
        return costPerLead;
    }

    public void setCostPerLead(Double costPerLead) {
        this.costPerLead = costPerLead;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Double getConversionRate() {
        double conversionRate = (double) leadCount / impressions * 100;
        return AppUtil.isNaNOrInfinity(conversionRate) ? 0d : conversionRate;
    }

    public Double getRevenuePerImpression() {
        double revenuePerImpression = revenue / impressions;
        return AppUtil.isNaNOrInfinity(revenuePerImpression) ? 0d : revenuePerImpression;
    }

    public Double getFilterRate() {
        double filterRate = (double) (submitCount - leadCount) / leadCount * 100;
        return AppUtil.isNaNOrInfinity(filterRate) ? 0d : filterRate;
    }

    public ImpressionReportRow fill(CPAGenieImpressionReportRow dbRow) {
        setId(dbRow.getId());
        setRunTime(dbRow.getRunTime());
        setCampaign(new Campaign().fill(dbRow.getCampaign()));
        setImpressions(dbRow.getImpressions());
        setSubmitCount(dbRow.getSubmitCount());
        setLeadCount(dbRow.getLeadCount());
        setCostPerLead(dbRow.getCostPerLead());
        setRevenue(dbRow.getRevenue());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpressionReportRow that = (ImpressionReportRow) o;
        return !(campaign != null ? !campaign.equals(that.campaign) : that.campaign != null) && !(runTime != null ? !runTime.equals(that.runTime) : that.runTime != null);
    }

    @Override
    public int hashCode() {
        int result = runTime != null ? runTime.hashCode() : 0;
        result = 31 * result + (campaign != null ? campaign.hashCode() : 0);
        return result;
    }
}
