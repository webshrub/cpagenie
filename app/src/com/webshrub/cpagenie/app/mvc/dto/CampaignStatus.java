package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaignStatus;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public class CampaignStatus implements Comparable<CampaignStatus> {
    private static Map<Integer, CampaignStatus> statusMap = new HashMap<Integer, CampaignStatus>();

    static {
        for (CPAGenieCampaignStatus campaignStatus : CPAGenieCampaignStatus.values()) {
            statusMap.put(campaignStatus.getId(), new CampaignStatus(campaignStatus.getId(), campaignStatus.getName()));
        }
    }

    private Integer id;
    private String name;

    public CampaignStatus(Integer id, String name) {
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

    public static CampaignStatus getStatus(Integer id) {
        return statusMap.get(id);
    }

    public static List<CampaignStatus> getStatusList() {
        List<CampaignStatus> values = new ArrayList<CampaignStatus>(statusMap.values());
        Collections.sort(values);
        return values;
    }

    public int compareTo(CampaignStatus o) {
        return this.id - o.getId();
    }
}