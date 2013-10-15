package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 14, 2010
 * Time: 1:11:09 AM
 */
public class Lead {
    private Integer id;
    private LeadStatus status;
    private Date captureTime;
    private Date updateTime;
    private String updateComments;
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
    private Campaign campaign;
    private Source source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LeadStatus getStatus() {
        return status;
    }

    public void setStatus(LeadStatus status) {
        this.status = status;
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

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Lead fill(CPAGenieLead lead) {
        setId(lead.getId());
        setStatus(LeadStatus.getStatus(lead.getStatus().getId()));
        setCaptureTime(lead.getCaptureTime());
        setUpdateTime(lead.getUpdateTime());
        setUpdateComments(lead.getUpdateComments());
        setIpAddress(lead.getIpAddress());
        setUserAgent(lead.getUserAgent());
        setDayOfBirth(lead.getDayOfBirth());
        setMonthOfBirth(lead.getMonthOfBirth());
        setYearOfBirth(lead.getYearOfBirth());
        setFirstName(lead.getFirstName());
        setLastName(lead.getLastName());
        setEmail(lead.getEmail());
        setAddress1(lead.getAddress1());
        setAddress2(lead.getAddress2());
        setCity(lead.getCity());
        setState(lead.getState());
        setCountry(lead.getCountry());
        setPinCode(lead.getPinCode());
        setHomePhone(lead.getHomePhone());
        setWorkPhone(lead.getWorkPhone());
        setWorkExt(lead.getWorkExt());
        setOtherPhone(lead.getOtherPhone());
        setMobilePhone(lead.getMobilePhone());
        setCustom1(lead.getCustom1());
        setCustom2(lead.getCustom2());
        setCustom3(lead.getCustom3());
        setCustom4(lead.getCustom4());
        setCustom5(lead.getCustom5());
        setCustom6(lead.getCustom6());
        setCustom7(lead.getCustom7());
        setCustom8(lead.getCustom8());
        setCustom9(lead.getCustom9());
        setCustom10(lead.getCustom10());
        setCampaign(new Campaign().fill(lead.getCampaign()));
        setSource(new Source().fill(lead.getSource()));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lead lead = (Lead) o;
        return !(campaign != null ? !campaign.equals(lead.campaign) : lead.campaign != null) && !(captureTime != null ? !captureTime.equals(lead.captureTime) : lead.captureTime != null) && !(ipAddress != null ? !ipAddress.equals(lead.ipAddress) : lead.ipAddress != null) && !(source != null ? !source.equals(lead.source) : lead.source != null);
    }

    @Override
    public int hashCode() {
        int result = captureTime != null ? captureTime.hashCode() : 0;
        result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
        result = 31 * result + (campaign != null ? campaign.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }
}
