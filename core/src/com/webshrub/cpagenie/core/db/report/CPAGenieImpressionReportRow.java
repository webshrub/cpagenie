package com.webshrub.cpagenie.core.db.report;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 11, 2010
 * Time: 6:04:16 PM
 */
@Entity
@Table(name = "CG_IMPRESSION_REPORT", uniqueConstraints = @UniqueConstraint(columnNames = {"RUN_TIME", "CAMPAIGN_ID"}))
public class CPAGenieImpressionReportRow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "RUN_TIME", nullable = false)
    private Date runTime;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "CAMPAIGN_ID")
    private CPAGenieCampaign campaign;

    @Column(name = "IMPRESSIONS", nullable = false)
    private Integer impressions;

    @Column(name = "SUBMIT_COUNT", nullable = false)
    private Integer submitCount;

    @Column(name = "LEAD_COUNT", nullable = false)
    private Integer leadCount;

    @Column(name = "COST_PER_LEAD", nullable = false)
    private Double costPerLead;

    @Column(name = "REVENUE", nullable = false)
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

    public CPAGenieCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(CPAGenieCampaign campaign) {
        this.campaign = campaign;
        campaign.addImpressionReportRow(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieImpressionReportRow that = (CPAGenieImpressionReportRow) o;
        return campaign.equals(that.campaign) && runTime.equals(that.runTime);
    }

    @Override
    public int hashCode() {
        int result = runTime.hashCode();
        result = 31 * result + campaign.hashCode();
        return result;
    }
}
