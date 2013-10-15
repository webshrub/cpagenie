package com.webshrub.cpagenie.app.mvc.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 10, 2010
 * Time: 3:45:00 PM
 */
public class UserAuthorityMapping {
    private User user;
    private List<Authority> authorities = new ArrayList<Authority>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
