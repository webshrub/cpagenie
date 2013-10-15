package com.webshrub.cpagenie.app.mvc.authentication;

import com.webshrub.cpagenie.app.mvc.dto.User;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 18, 2010
 * Time: 1:18:42 PM
 */
@Component
public class AuthenticationUtil {

    @Autowired
    private UserService userService;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            UserDetails userDetails = (UserDetails) principal;
            return userService.getUser(userDetails.getUsername());
        }
        return null;
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }
}
