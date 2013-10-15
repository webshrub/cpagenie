package com.webshrub.cpagenie.server.db.job;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieJobStatus {
    //Never change the order of enum, they will break the ordinal system. To add a new status, just introduce a new enum at the last.
    FAILURE(0, "Failure"),
    SUCCESS(1, "Success");

    private static Map<Integer, CPAGenieJobStatus> statusMap = new HashMap<Integer, CPAGenieJobStatus>();

    static {
        for (CPAGenieJobStatus jobStatus : CPAGenieJobStatus.values()) {
            statusMap.put(jobStatus.getId(), jobStatus);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieJobStatus(Integer id, String name) {
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

    public static CPAGenieJobStatus getStatus(Integer ordinal) {
        return statusMap.get(ordinal);
    }

    public static List<CPAGenieJobStatus> getStatusList() {
        List<CPAGenieJobStatus> values = new ArrayList<CPAGenieJobStatus>(statusMap.values());
        Collections.sort(values);
        return values;
    }
}
