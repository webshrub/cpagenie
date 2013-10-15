package com.webshrub.cpagenie.core.db.campaign.delivery;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 6:10:07 PM
 */

@Entity
@Table(name = "CG_CAMPAIGN_DELIVERY")
public class CPAGenieCampaignDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieCampaignDeliveryStatus status;

    @Column(name = "DELIVERY_TIME", nullable = false)
    private Date deliveryTime;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "CG_CAMPAIGN_DELIVERY_LEAD", joinColumns = @JoinColumn(name = "CAMPAIGN_DELIVERY_ID"), inverseJoinColumns = @JoinColumn(name = "LEAD_ID"))
    private Set<CPAGenieLead> leads = new HashSet<CPAGenieLead>();

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "CAMPAIGN_ID")
    private CPAGenieCampaign campaign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CPAGenieCampaignDeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(CPAGenieCampaignDeliveryStatus status) {
        this.status = status;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
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

    public CPAGenieCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(CPAGenieCampaign campaign) {
        this.campaign = campaign;
    }
}