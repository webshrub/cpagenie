package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignFieldValidationType;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 4:29:08 PM
 */
public class CampaignFieldValidationType implements Comparable<CampaignFieldValidationType> {
    private static final Map<Integer, CampaignFieldValidationType> validationTypeMap = new HashMap<Integer, CampaignFieldValidationType>();

    static {
        for (CPAGenieCampaignFieldValidationType fieldValidationType : CPAGenieCampaignFieldValidationType.values()) {
            validationTypeMap.put(fieldValidationType.getId(), new CampaignFieldValidationType(fieldValidationType.getId(), fieldValidationType.getName()));
        }
    }

    private Integer id;
    private String name;

    public CampaignFieldValidationType(Integer id, String name) {
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

    public static CampaignFieldValidationType getValidationType(Integer id) {
        return validationTypeMap.get(id);
    }

    public static List<CampaignFieldValidationType> getValidationTypeList() {
        List<CampaignFieldValidationType> values = new ArrayList<CampaignFieldValidationType>(validationTypeMap.values());
        Collections.sort(values);
        return values;
    }

    public int compareTo(CampaignFieldValidationType o) {
        return this.id - o.getId();
    }
}