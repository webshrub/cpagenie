package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.campaign.field.CPAGenieCampaignSupportedField;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public class CampaignSupportedField implements Comparable<CampaignSupportedField> {
    private static Map<Integer, CampaignSupportedField> supportedFieldMap = new HashMap<Integer, CampaignSupportedField>();

    static {
        for (CPAGenieCampaignSupportedField supportedField : CPAGenieCampaignSupportedField.values()) {
            supportedFieldMap.put(supportedField.getId(), new CampaignSupportedField(supportedField.getId(), supportedField.getName(), supportedField.getDisplayName()));
        }
    }

    private Integer id;
    private String name;
    private String displayName;

    public CampaignSupportedField(Integer id, String name, String displayName) {
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

    public static CampaignSupportedField getField(Integer id) {
        return supportedFieldMap.get(id);
    }

    public static List<CampaignSupportedField> getSupportedFieldList() {
        List<CampaignSupportedField> values = new ArrayList<CampaignSupportedField>(supportedFieldMap.values());
        Collections.sort(values);
        return values;
    }

    public int compareTo(CampaignSupportedField o) {
        return this.id - o.getId();
    }
}
