package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignFieldType;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 4:29:08 PM
 */
public class CampaignFieldType implements Comparable<CampaignFieldType> {
    private static final Map<Integer, CampaignFieldType> fieldTypeMap = new HashMap<Integer, CampaignFieldType>();

    static {
        for (CPAGenieCampaignFieldType fieldType : CPAGenieCampaignFieldType.values()) {
            fieldTypeMap.put(fieldType.getId(), new CampaignFieldType(fieldType.getId(), fieldType.getName()));
        }
    }

    private Integer id;
    private String name;

    public CampaignFieldType(Integer id, String name) {
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

    public static CampaignFieldType getFieldType(Integer id) {
        return fieldTypeMap.get(id);
    }

    public static List<CampaignFieldType> getFieldTypeList() {
        List<CampaignFieldType> values = new ArrayList<CampaignFieldType>(fieldTypeMap.values());
        Collections.sort(values);
        return values;
    }

    public int compareTo(CampaignFieldType o) {
        return this.id - o.getId();
    }
}
