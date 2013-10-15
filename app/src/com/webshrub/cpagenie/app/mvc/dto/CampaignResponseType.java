package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.campaign.response.CPAGenieCampaignResponseType;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public class CampaignResponseType implements Comparable<CampaignResponseType> {
    private static Map<Integer, CampaignResponseType> responseTypeMap = new HashMap<Integer, CampaignResponseType>();

    static {
        for (CPAGenieCampaignResponseType responseType : CPAGenieCampaignResponseType.values()) {
            responseTypeMap.put(responseType.getId(), new CampaignResponseType(responseType.getId(), responseType.getName()));
        }
    }

    private Integer id;
    private String name;

    public CampaignResponseType(Integer id, String name) {
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

    public static CampaignResponseType getResponseType(Integer id) {
        return responseTypeMap.get(id);
    }

    public static List<CampaignResponseType> getResponseTypeList() {
        List<CampaignResponseType> values = new ArrayList<CampaignResponseType>(responseTypeMap.values());
        Collections.sort(values);
        return values;
    }

    public int compareTo(CampaignResponseType o) {
        return this.id - o.getId();
    }
}