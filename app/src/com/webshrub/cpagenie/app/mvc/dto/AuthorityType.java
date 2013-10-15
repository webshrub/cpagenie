package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.app.db.authority.CPAGenieAuthorityType;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 6, 2010
 * Time: 4:29:08 PM
 */
public class AuthorityType implements Comparable<AuthorityType> {
    private static final Map<Integer, AuthorityType> authorityTypeMap = new HashMap<Integer, AuthorityType>();

    static {
        for (CPAGenieAuthorityType authorityType : CPAGenieAuthorityType.values()) {
            authorityTypeMap.put(authorityType.getId(), new AuthorityType(authorityType.getId(), authorityType.getName()));
        }
    }

    private Integer id;
    private String name;

    public AuthorityType(Integer id, String name) {
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

    public static AuthorityType getAuthorityType(Integer id) {
        return authorityTypeMap.get(id);
    }

    public static List<AuthorityType> getAuthorityTypeList() {
        List<AuthorityType> values = new ArrayList<AuthorityType>(authorityTypeMap.values());
        Collections.sort(values);
        return values;
    }

    public int compareTo(AuthorityType o) {
        return this.id - o.getId();
    }
}