package com.webshrub.cpagenie.core.db.campaign.field;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieCampaignFieldValidationType {
    //Never change the order of enum, they will break the ordinal system. To add a new status, just introduce a new enum at the last.
    REQUIRED(0, "Required"),
    NOT_REQUIRED(1, "Not Required");

    private static Map<Integer, CPAGenieCampaignFieldValidationType> campaignValidationTypeMap = new HashMap<Integer, CPAGenieCampaignFieldValidationType>();

    static {
        for (CPAGenieCampaignFieldValidationType fieldValidationType : CPAGenieCampaignFieldValidationType.values()) {
            campaignValidationTypeMap.put(fieldValidationType.getId(), fieldValidationType);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieCampaignFieldValidationType(Integer id, String name) {
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

    public static CPAGenieCampaignFieldValidationType getValidationType(Integer ordinal) {
        return campaignValidationTypeMap.get(ordinal);
    }

    public static List<CPAGenieCampaignFieldValidationType> getValidationTypeList() {
        List<CPAGenieCampaignFieldValidationType> values = new ArrayList<CPAGenieCampaignFieldValidationType>(campaignValidationTypeMap.values());
        Collections.sort(values);
        return values;
    }
}
