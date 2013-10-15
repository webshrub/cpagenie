package com.webshrub.cpagenie.core.db.lead;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieLeadStatus {
    //Never change the order of enum, they will break the ordinal system. To add a new status, just introduce a new enum at the last.
    VALID(0, "Valid"),
    INVALID_MISSING_FIELD(1, "Missing Field"),
    INVALID_VALIDATION_FAILED(2, "Validation Failed");

    private static Map<Integer, CPAGenieLeadStatus> statusMap = new HashMap<Integer, CPAGenieLeadStatus>();

    static {
        for (CPAGenieLeadStatus leadStatus : CPAGenieLeadStatus.values()) {
            statusMap.put(leadStatus.getId(), leadStatus);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieLeadStatus(Integer id, String name) {
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

    public static CPAGenieLeadStatus getStatus(Integer ordinal) {
        return statusMap.get(ordinal);
    }

    public static List<CPAGenieLeadStatus> getStatusList() {
        List<CPAGenieLeadStatus> values = new ArrayList<CPAGenieLeadStatus>(statusMap.values());
        Collections.sort(values);
        return values;
    }
}
