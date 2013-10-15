package com.webshrub.cpagenie.app.db.user;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieUserStatus {
    //Never change the order of enum, they will break the ordinal system. To add a new status, just introduce a new enum at the last.
    INACTIVE(0, "Inactive"),
    ACTIVE(1, "Active");

    private static Map<Integer, CPAGenieUserStatus> statusMap = new HashMap<Integer, CPAGenieUserStatus>();

    static {
        for (CPAGenieUserStatus userStatus : CPAGenieUserStatus.values()) {
            statusMap.put(userStatus.getId(), userStatus);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieUserStatus(Integer id, String name) {
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

    public static CPAGenieUserStatus getStatus(Integer ordinal) {
        return statusMap.get(ordinal);
    }

    public static List<CPAGenieUserStatus> getStatusList() {
        List<CPAGenieUserStatus> values = new ArrayList<CPAGenieUserStatus>(statusMap.values());
        Collections.sort(values);
        return values;
    }
}
