package com.webshrub.cpagenie.core.db.campaign.field;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieCampaignSupportedField {
    //Never change the order of enum, they will break the ordinal system. To add a new field, just introduce a new enum at the last.
    DAY_OF_BIRTH(0, "dayOfBirth", "Day Of Birth"),
    MONTH_OF_BIRTH(1, "monthOfBirth", "Month Of Birth"),
    YEAR_OF_BIRTH(2, "yearOfBirth", "Year Of Birth"),
    FIRST_NAME(3, "firstName", "First Name"),
    LAST_NAME(4, "lastName", "Last Name"),
    EMAIL(5, "email", "Email"),
    ADDRESS1(6, "address1", "Address1"),
    ADDRESS2(7, "address2", "Address2"),
    CITY(8, "city", "City"),
    STATE(9, "state", "State"),
    COUNTRY(10, "country", "Country"),
    PIN_CODE(11, "pinCode", "Pin Code"),
    HOME_PHONE(12, "homePhone", "Home Phone"),
    WORK_PHONE(13, "workPhone", "Work Phone"),
    WORK_EXT(14, "workExt", "Work Ext"),
    OTHER_PHONE(15, "otherPhone", "Other Phone"),
    MOBILE_PHONE(16, "mobilePhone", "Mobile Phone"),
    CUSTOM1(17, "custom1", "Custom1"),
    CUSTOM2(18, "custom2", "Custom2"),
    CUSTOM3(19, "custom3", "Custom3"),
    CUSTOM4(20, "custom4", "Custom4"),
    CUSTOM5(21, "custom5", "Custom5"),
    CUSTOM6(22, "custom6", "Custom6"),
    CUSTOM7(23, "custom7", "Custom7"),
    CUSTOM8(24, "custom8", "Custom8"),
    CUSTOM9(25, "custom9", "Custom9"),
    CUSTOM10(26, "custom10", "Custom10");

    private static Map<Integer, CPAGenieCampaignSupportedField> supportedFieldMap = new HashMap<Integer, CPAGenieCampaignSupportedField>();

    static {
        for (CPAGenieCampaignSupportedField supportedField : CPAGenieCampaignSupportedField.values()) {
            supportedFieldMap.put(supportedField.getId(), supportedField);
        }
    }

    private Integer id;
    private String name;
    private String displayName;

    private CPAGenieCampaignSupportedField(Integer id, String name, String displayName) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public static CPAGenieCampaignSupportedField getField(Integer ordinal) {
        return supportedFieldMap.get(ordinal);
    }

    public static List<CPAGenieCampaignSupportedField> getFieldList() {
        List<CPAGenieCampaignSupportedField> values = new ArrayList<CPAGenieCampaignSupportedField>(supportedFieldMap.values());
        Collections.sort(values);
        return values;
    }
}
