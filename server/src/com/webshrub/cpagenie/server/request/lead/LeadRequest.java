package com.webshrub.cpagenie.server.request.lead;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.lead.CPAGenieLeadStatus;
import com.webshrub.cpagenie.core.db.source.CPAGenieSource;
import com.webshrub.cpagenie.server.request.CPAGenieRequest;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 13, 2010
 * Time: 6:25:52 PM
 */
public class LeadRequest extends CPAGenieRequest {
    private CPAGenieLeadStatus status;
    private Date captureTime;
    public String ipAddress;
    public String userAgent;
    private Integer dayOfBirth;
    private Integer monthOfBirth;
    private Integer yearOfBirth;
    private String firstName;
    private String lastName;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String homePhone;
    private String workPhone;
    private String workExt;
    private String otherPhone;
    private String mobilePhone;
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

    public LeadRequest() {
        super();
    }

    public CPAGenieLeadStatus getStatus() {
        return status;
    }

    public void setStatus(CPAGenieLeadStatus status) {
        this.status = status;
    }

    public Date getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
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

    public Integer getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Integer dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public Integer getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(Integer monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getWorkExt() {
        return workExt;
    }

    public void setWorkExt(String workExt) {
        this.workExt = workExt;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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

    public CPAGenieLead populateLead() {
        CPAGenieLead lead = new CPAGenieLead(getCaptureTime(), getIpAddress(), getCampaign(), getSource());
        lead.setStatus(getStatus());
        lead.setUserAgent(getUserAgent());
        lead.setDayOfBirth(getDayOfBirth());
        lead.setMonthOfBirth(getMonthOfBirth());
        lead.setYearOfBirth(getYearOfBirth());
        lead.setFirstName(getFirstName());
        lead.setLastName(getLastName());
        lead.setEmail(getEmail());
        lead.setAddress1(getAddress1());
        lead.setAddress2(getAddress2());
        lead.setCity(getCity());
        lead.setState(getState());
        lead.setCountry(getCountry());
        lead.setPinCode(getPinCode());
        lead.setHomePhone(getHomePhone());
        lead.setWorkPhone(getWorkPhone());
        lead.setWorkExt(getWorkExt());
        lead.setOtherPhone(getOtherPhone());
        lead.setMobilePhone(getMobilePhone());
        lead.setCustom1(getCustom1());
        lead.setCustom2(getCustom2());
        lead.setCustom3(getCustom3());
        lead.setCustom4(getCustom4());
        lead.setCustom5(getCustom5());
        lead.setCustom6(getCustom6());
        lead.setCustom7(getCustom7());
        lead.setCustom8(getCustom8());
        lead.setCustom9(getCustom9());
        lead.setCustom10(getCustom10());
        return lead;
    }
}
