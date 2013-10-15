package com.webshrub.cpagenie.app.mvc.dto;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 13, 2010
 * Time: 2:09:16 PM
 */
public class LeadReportFilter {
    private Advertiser advertiser;
    private Campaign campaign;
    private Date startDate;
    private Date endDate;

    public Advertiser getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Advertiser advertiser) {
        this.advertiser = advertiser;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
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

    public boolean isPopulated() {
        return advertiser != null && campaign != null && startDate != null && endDate != null;
    }
}
