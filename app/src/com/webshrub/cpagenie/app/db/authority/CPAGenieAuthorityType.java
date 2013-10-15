package com.webshrub.cpagenie.app.db.authority;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 24, 2010
 * Time: 5:11:23 PM
 */
public enum CPAGenieAuthorityType {
    //Never change the order of enum, they will break the ordinal system. To add a new status, just introduce a new enum at the last.
    ROLE_USER(0, "ROLE_USER"),
    ROLE_ADVERTISER(1, "ROLE_ADVERTISER"),
    ROLE_MANAGER(2, "ROLE_MANAGER"),
    ROLE_ADMIN(3, "ROLE_ADMIN");


    private static Map<Integer, CPAGenieAuthorityType> authorityTypeMap = new HashMap<Integer, CPAGenieAuthorityType>();

    static {
        for (CPAGenieAuthorityType authorityType : CPAGenieAuthorityType.values()) {
            authorityTypeMap.put(authorityType.getId(), authorityType);
        }
    }

    private Integer id;
    private String name;

    private CPAGenieAuthorityType(Integer id, String name) {
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

    public static CPAGenieAuthorityType getAuthorityType(Integer id) {
        return authorityTypeMap.get(id);
    }

    public static List<CPAGenieAuthorityType> getAuthorityTypeList() {
        List<CPAGenieAuthorityType> values = new ArrayList<CPAGenieAuthorityType>(authorityTypeMap.values());
        Collections.sort(values);
        return values;
    }
}
