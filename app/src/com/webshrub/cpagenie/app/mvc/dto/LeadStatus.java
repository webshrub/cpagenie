package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.lead.CPAGenieLeadStatus;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public class LeadStatus implements Comparable<LeadStatus> {
    private static Map<Integer, LeadStatus> statusMap = new HashMap<Integer, LeadStatus>();

    static {
        for (CPAGenieLeadStatus leadStatus : CPAGenieLeadStatus.values()) {
            statusMap.put(leadStatus.getId(), new LeadStatus(leadStatus.getId(), leadStatus.getName()));
        }
    }

    private Integer id;
    private String name;

    public LeadStatus(Integer id, String name) {
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

    public static LeadStatus getStatus(Integer id) {
        return statusMap.get(id);
    }

    public static List<LeadStatus> getStatusList() {
        List<LeadStatus> values = new ArrayList<LeadStatus>(statusMap.values());
        Collections.sort(values);
        return values;
    }

    public int compareTo(LeadStatus o) {
        return this.id - o.getId();
    }
}
