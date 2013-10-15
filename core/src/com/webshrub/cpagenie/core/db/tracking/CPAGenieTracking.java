package com.webshrub.cpagenie.core.db.tracking;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.source.CPAGenieSource;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 13, 2010
 * Time: 2:50:57 PM
 */
@Entity
@Table(name = "CG_TRACKING", uniqueConstraints = @UniqueConstraint(columnNames = {"IP_ADDRESS", "CAMPAIGN_ID", "SOURCE_ID", "CAPTURE_TIME"}))
public class CPAGenieTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IP_ADDRESS", nullable = false)
    private String ipAddress;

    @Column(name = "USER_AGENT", length = 1000)
    private String userAgent;

    @Column(name = "CAPTURE_TIME", nullable = false)
    private Date captureTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_COMMENTS", length = 1000)
    private String updateComments;

    @Column(name = "CUSTOM1", length = 1000)
    private String custom1;

    @Column(name = "CUSTOM2", length = 1000)
    private String custom2;

    @Column(name = "CUSTOM3", length = 1000)
    private String custom3;

    @Column(name = "CUSTOM4", length = 1000)
    private String custom4;

    @Column(name = "CUSTOM5", length = 1000)
    private String custom5;

    @Column(name = "CUSTOM6", length = 1000)
    private String custom6;

    @Column(name = "CUSTOM7", length = 1000)
    private String custom7;

    @Column(name = "CUSTOM8", length = 1000)
    private String custom8;

    @Column(name = "CUSTOM9", length = 1000)
    private String custom9;

    @Column(name = "CUSTOM10", length = 1000)
    private String custom10;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "CAMPAIGN_ID")
    private CPAGenieCampaign campaign;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "SOURCE_ID")
    private CPAGenieSource source;

    public CPAGenieTracking() {
    }

    //Initialize it here so that org.hibernate.PersistentSet does not throw NPE while calculating
    // hashCode due to unset field values.

    public CPAGenieTracking(String ipAddress, Date captureTime, CPAGenieCampaign campaign, CPAGenieSource source) {
        this.ipAddress = ipAddress;
        this.captureTime = captureTime;
        this.campaign = campaign;
        this.source = source;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Date getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
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

    public String getCustom1() {
        return custom1;
    }

    public void setCustom1(String custom1) {
        this.custom1 = custom1;
    }

    public String getCustom2() {
        return custom2;
    }

    public void setCustom2(String custom2) {
        this.custom2 = custom2;
    }

    public String getCustom3() {
        return custom3;
    }

    public void setCustom3(String custom3) {
        this.custom3 = custom3;
    }

    public String getCustom4() {
        return custom4;
    }

    public void setCustom4(String custom4) {
        this.custom4 = custom4;
    }

    public String getCustom5() {
        return custom5;
    }

    public void setCustom5(String custom5) {
        this.custom5 = custom5;
    }

    public String getCustom6() {
        return custom6;
    }

    public void setCustom6(String custom6) {
        this.custom6 = custom6;
    }

    public String getCustom7() {
        return custom7;
    }

    public void setCustom7(String custom7) {
        this.custom7 = custom7;
    }

    public String getCustom8() {
        return custom8;
    }

    public void setCustom8(String custom8) {
        this.custom8 = custom8;
    }

    public String getCustom9() {
        return custom9;
    }

    public void setCustom9(String custom9) {
        this.custom9 = custom9;
    }

    public String getCustom10() {
        return custom10;
    }

    public void setCustom10(String custom10) {
        this.custom10 = custom10;
    }

    public CPAGenieCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(CPAGenieCampaign campaign) {
        this.campaign = campaign;
        campaign.addTracking(this);
    }

    public CPAGenieSource getSource() {
        return source;
    }

    public void setSource(CPAGenieSource source) {
        this.source = source;
        source.addTracking(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieTracking tracking = (CPAGenieTracking) o;
        return campaign.equals(tracking.campaign) && captureTime.equals(tracking.captureTime) && ipAddress.equals(tracking.ipAddress) && source.equals(tracking.source);
    }

    @Override
    public int hashCode() {
        int result = ipAddress.hashCode();
        result = 31 * result + campaign.hashCode();
        result = 31 * result + source.hashCode();
        result = 31 * result + captureTime.hashCode();
        return result;
    }
}
