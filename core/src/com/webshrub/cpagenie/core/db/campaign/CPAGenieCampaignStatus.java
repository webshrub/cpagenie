package com.webshrub.cpagenie.core.db.campaign;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieCampaignStatus {
    //Never change the order of enum, they will break the ordinal system. To add a new status, just introduce a new enum at the last.
    PAUSED_BEFORE_START(0, "Paused Due to Start Date"),
    PAUSED_USER(1, "Paused By User"),
    STARTED(2, "Started By User"),
    STOPPED_MAX_LEAD(3, "Stopped Due to Max Lead"),
    STOPPED_AFTER_END(4, "Stopped Due to End Date");

    private static Map<Integer, CPAGenieCampaignStatus> statusMap = new HashMap<Integer, CPAGenieCampaignStatus>();

    static {
        for (CPAGenieCampaignStatus campaignStatus : CPAGenieCampaignStatus.values()) {
            statusMap.put(campaignStatus.getId(), campaignStatus);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieCampaignStatus(Integer id, String name) {
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

    public static CPAGenieCampaignStatus getStatus(Integer ordinal) {
        return statusMap.get(ordinal);
    }

    public static List<CPAGenieCampaignStatus> getStatusList() {
        List<CPAGenieCampaignStatus> values = new ArrayList<CPAGenieCampaignStatus>(statusMap.values());
        Collections.sort(values);
        return values;
    }
}
