package com.webshrub.cpagenie.core.db.campaign.response;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 6:10:07 PM
 */

@Entity
@Table(name = "CG_CAMPAIGN_RESPONSE")
public class CPAGenieCampaignResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "RESPONSE_TYPE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieCampaignResponseType responseType;

    @Column(name = "SUCCESS_RESPONSE", length = 1000, nullable = false)
    private String successResponse;

    @Column(name = "FAILURE_RESPONSE", length = 1000, nullable = false)
    private String failureResponse;

    @OneToOne(mappedBy = "response")
    private CPAGenieCampaign campaign;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CPAGenieCampaignResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(CPAGenieCampaignResponseType responseType) {
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

    public CPAGenieCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(CPAGenieCampaign campaign) {
        this.campaign = campaign;
    }
}
