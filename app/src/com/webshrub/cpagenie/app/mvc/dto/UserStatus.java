package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.app.db.user.CPAGenieUserStatus;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 4:29:08 PM
 */
public class UserStatus implements Comparable<UserStatus> {
    private static final Map<Integer, UserStatus> statusMap = new HashMap<Integer, UserStatus>();

    static {
        for (CPAGenieUserStatus status : CPAGenieUserStatus.values()) {
            statusMap.put(status.getId(), new UserStatus(status.getId(), status.getName()));
        }
    }

    private Integer id;
    private String name;

    public UserStatus(Integer id, String name) {
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

    public static UserStatus getStatus(Integer id) {
        return statusMap.get(id);
    }

    public static List<UserStatus> getUserStatusList() {
        List<UserStatus> values = new ArrayList<UserStatus>(statusMap.values());
        Collections.sort(values);
        return values;
    }

    public int compareTo(UserStatus o) {
        return this.id - o.getId();
    }
}
