package com.webshrub.cpagenie.core.db.campaign;

import com.webshrub.cpagenie.core.db.campaign.delivery.CPAGenieCampaignDelivery;
import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignField;
import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponse;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.advertiser.CPAGenieAdvertiser;
import com.webshrub.cpagenie.core.db.report.CPAGenieImpressionReportRow;
import com.webshrub.cpagenie.core.db.tracking.CPAGenieTracking;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 8, 2010
 * Time: 1:27:19 PM
 */
@Entity
@Table(name = "CG_CAMPAIGN", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "ADVERTISER_ID"}))
public class CPAGenieCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "COMPLETION_DATE")
    private Date completionDate;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "COST_PER_LEAD", nullable = false)
    private Double costPerLead;

    @Column(name = "TOTAL_BUDGET", nullable = false)
    private Double totalBudget;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieCampaignStatus status;

    @Column(name = "CREATION_TIME", nullable = false)
    private Date creationTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_COMMENTS", length = 1000)
    private String updateComments;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "RESPONSE_ID")
    private CPAGenieCampaignResponse response;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "ADVERTISER_ID")
    private CPAGenieAdvertiser advertiser;

    @OneToMany(mappedBy = "campaign")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieLead> leads = new HashSet<CPAGenieLead>();

    @OneToMany(mappedBy = "campaign")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieTracking> trackings = new HashSet<CPAGenieTracking>();

    @OneToMany(mappedBy = "campaign")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieCampaignField> fields = new HashSet<CPAGenieCampaignField>();

    @OneToMany(mappedBy = "campaign")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieCampaignDelivery> deliveries = new HashSet<CPAGenieCampaignDelivery>();

    @OneToMany(mappedBy = "campaign")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieImpressionReportRow> impressionReportRows = new HashSet<CPAGenieImpressionReportRow>();

    public CPAGenieCampaign() {
    }

    //Initialize it here so that org.hibernate.PersistentSet does not throw NPE while calculating
    // hashCode due to unset field values.

    public CPAGenieCampaign(String name, CPAGenieAdvertiser advertiser) {
        this.name = name;
        this.advertiser = advertiser;
    }

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

    public CPAGenieCampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CPAGenieCampaignStatus status) {
        this.status = status;
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

    public CPAGenieCampaignResponse getResponse() {
        return response;
    }

    public void setResponse(CPAGenieCampaignResponse response) {
        this.response = response;
        response.setCampaign(this);
    }

    public CPAGenieAdvertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(CPAGenieAdvertiser advertiser) {
        this.advertiser = advertiser;
    }

    public Set<CPAGenieLead> getLeads() {
        return leads;
    }

    public void setLeads(Set<CPAGenieLead> leads) {
        this.leads = leads;
    }

    public void addLead(CPAGenieLead lead) {
        this.leads.add(lead);
    }

    public Set<CPAGenieTracking> getTrackings() {
        return trackings;
    }

    public void setTrackings(Set<CPAGenieTracking> trackings) {
        this.trackings = trackings;
    }

    public void addTracking(CPAGenieTracking tracking) {
        this.trackings.add(tracking);
    }

    public Set<CPAGenieCampaignField> getFields() {
        return fields;
    }

    public void setFields(Set<CPAGenieCampaignField> fields) {
        this.fields = fields;
    }

    public void addField(CPAGenieCampaignField field) {
        this.fields.add(field);
        field.setCampaign(this);
    }

    public Set<CPAGenieCampaignDelivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(Set<CPAGenieCampaignDelivery> deliveries) {
        this.deliveries = deliveries;
    }

    public void addDelivery(CPAGenieCampaignDelivery delivery) {
        this.deliveries.add(delivery);
        delivery.setCampaign(this);
    }

    public Set<CPAGenieImpressionReportRow> getImpressionReportRows() {
        return impressionReportRows;
    }

    public void setImpressionReportRows(Set<CPAGenieImpressionReportRow> impressionReportRows) {
        this.impressionReportRows = impressionReportRows;
    }

    public void addImpressionReportRow(CPAGenieImpressionReportRow impressionReportRow) {
        this.impressionReportRows.add(impressionReportRow);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieCampaign campaign = (CPAGenieCampaign) o;
        return name.equals(campaign.name) && advertiser.equals(campaign.advertiser);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + advertiser.hashCode();
        return result;
    }
}
