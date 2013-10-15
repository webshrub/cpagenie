package com.webshrub.cpagenie.core.db.lead;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.source.CPAGenieSource;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 7, 2010
 * Time: 1:55:43 PM
 */
@Entity
@Table(name = "CG_LEAD", uniqueConstraints = @UniqueConstraint(columnNames = {"CAPTURE_TIME", "IP_ADDRESS", "CAMPAIGN_ID", "SOURCE_ID"}))
public class CPAGenieLead {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieLeadStatus status;

    @Column(name = "CAPTURE_TIME", nullable = false)
    private Date captureTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_COMMENTS", length = 1000)
    private String updateComments;

    @Column(name = "IP_ADDRESS", nullable = false)
    public String ipAddress;

    @Column(name = "USER_AGENT", length = 1000)
    public String userAgent;

    @Column(name = "DAY_OF_BIRTH")
    private Integer dayOfBirth;

    @Column(name = "MONTH_OF_BIRTH")
    private Integer monthOfBirth;

    @Column(name = "YEAR_OF_BIRTH")
    private Integer yearOfBirth;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PIN_CODE")
    private String pinCode;

    @Column(name = "HOME_PHONE")
    private String homePhone;

    @Column(name = "WORK_PHONE")
    private String workPhone;

    @Column(name = "WORK_EXT")
    private String workExt;

    @Column(name = "OTHER_PHONE")
    private String otherPhone;

    @Column(name = "MOBILE_PHONE")
    private String mobilePhone;

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

    public CPAGenieLead() {
    }

    //Initialize it here so that org.hibernate.PersistentSet does not throw NPE while calculating
    // hashCode due to unset field values.

    public CPAGenieLead(Date captureTime, String ipAddress, CPAGenieCampaign campaign, CPAGenieSource source) {
        this.captureTime = captureTime;
        this.ipAddress = ipAddress;
        this.campaign = campaign;
        this.source = source;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public CPAGenieCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(CPAGenieCampaign campaign) {
        this.campaign = campaign;
        campaign.addLead(this);
    }

    public CPAGenieSource getSource() {
        return source;
    }

    public void setSource(CPAGenieSource source) {
        this.source = source;
        source.addLead(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieLead lead = (CPAGenieLead) o;
        return campaign.equals(lead.campaign) && captureTime.equals(lead.captureTime) && ipAddress.equals(lead.ipAddress) && source.equals(lead.source);
    }

    @Override
    public int hashCode() {
        int result = captureTime.hashCode();
        result = 31 * result + ipAddress.hashCode();
        result = 31 * result + campaign.hashCode();
        result = 31 * result + source.hashCode();
        return result;
    }
}
