package com.webshrub.cpagenie.app.db.user.adapter;

import com.webshrub.cpagenie.app.mvc.dto.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;

public class UserDetailsAdapter extends org.springframework.security.core.userdetails.User {

    public UserDetailsAdapter(User user) {
        super(user.getUsername(), user.getPassword(), user.getStatus().getId() != 0, true, true, true, new HashSet<GrantedAuthority>());
    }
}