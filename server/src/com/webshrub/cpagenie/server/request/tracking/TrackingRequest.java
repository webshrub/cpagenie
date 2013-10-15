package com.webshrub.cpagenie.server.request.tracking;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.source.CPAGenieSource;
import com.webshrub.cpagenie.core.db.tracking.CPAGenieTracking;
import com.webshrub.cpagenie.server.request.CPAGenieRequest;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 13, 2010
 * Time: 6:25:41 PM
 */
public class TrackingRequest extends CPAGenieRequest {
    private String ipAddress;
    private String userAgent;
    private Date captureTime;
    private String custom1;
    private String custom2;
    private String custom3;
    private String custom4;
    private String custom5;
    private String custom6;
    private String custom7;
    private String custom8;
    private String custom9;
    private String custom10;
    private CPAGenieCampaign campaign;
    private CPAGenieSource source;

    public TrackingRequest() {
        super();
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
    }

    public CPAGenieSource getSource() {
        return source;
    }

    public void setSource(CPAGenieSource source) {
        this.source = source;
    }

    public CPAGenieTracking populateTracking() {
        CPAGenieTracking tracking = new CPAGenieTracking(getIpAddress(), getCaptureTime(), getCampaign(), getSource());
        tracking.setUserAgent(getUserAgent());
        tracking.setCustom1(getCustom1());
        tracking.setCustom2(getCustom2());
        tracking.setCustom3(getCustom3());
        tracking.setCustom4(getCustom4());
        tracking.setCustom5(getCustom5());
        tracking.setCustom6(getCustom6());
        tracking.setCustom7(getCustom7());
        tracking.setCustom8(getCustom8());
        tracking.setCustom9(getCustom9());
        tracking.setCustom10(getCustom10());
        return tracking;
    }
}
