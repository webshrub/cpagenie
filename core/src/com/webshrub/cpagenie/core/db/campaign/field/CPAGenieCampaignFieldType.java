package com.webshrub.cpagenie.core.db.campaign.field;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieCampaignFieldType {
    //Never change the order of enum, they will break the ordinal system. To add a new status, just introduce a new enum at the last.
    REQUIRED(0, "Required"),
    NOT_REQUIRED(1, "Not Required");

    private static Map<Integer, CPAGenieCampaignFieldType> campaignFieldTypeMap = new HashMap<Integer, CPAGenieCampaignFieldType>();

    static {
        for (CPAGenieCampaignFieldType campaignFieldType : CPAGenieCampaignFieldType.values()) {
            campaignFieldTypeMap.put(campaignFieldType.getId(), campaignFieldType);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieCampaignFieldType(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public static CPAGenieCampaignFieldType getFieldType(Integer ordinal) {
        return campaignFieldTypeMap.get(ordinal);
    }

    public static List<CPAGenieCampaignFieldType> getFieldTypeList() {
        List<CPAGenieCampaignFieldType> values = new ArrayList<CPAGenieCampaignFieldType>(campaignFieldTypeMap.values());
        Collections.sort(values);
        return values;
    }
}
