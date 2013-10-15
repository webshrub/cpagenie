package com.webshrub.cpagenie.core.db.campaign.response;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 6:18:39 PM
 */
public enum CPAGenieCampaignResponseType {
    //Never change the order of enum, they will break the ordinal system. To add a new responseType, just introduce a new enum at the last.
    NORMAL(0, "Normal"),
    REDIRECT(1, "Redirect"),
    FORWARD(2, "Forward");

    private static Map<Integer, CPAGenieCampaignResponseType> responseTypeMap = new HashMap<Integer, CPAGenieCampaignResponseType>();

    static {
        for (CPAGenieCampaignResponseType campaignStatus : CPAGenieCampaignResponseType.values()) {
            responseTypeMap.put(campaignStatus.getId(), campaignStatus);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieCampaignResponseType(Integer id, String name) {
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

    public static CPAGenieCampaignResponseType getResponseType(Integer ordinal) {
        return responseTypeMap.get(ordinal);
    }

    public static List<CPAGenieCampaignResponseType> getResponseTypeList() {
        List<CPAGenieCampaignResponseType> values = new ArrayList<CPAGenieCampaignResponseType>(responseTypeMap.values());
        Collections.sort(values);
        return values;
    }
}
