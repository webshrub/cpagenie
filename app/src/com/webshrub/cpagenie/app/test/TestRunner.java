package com.webshrub.cpagenie.app.test;

import com.webshrub.cpagenie.app.db.authority.CPAGenieAuthorityType;
import com.webshrub.cpagenie.app.mvc.dto.Authority;
import com.webshrub.cpagenie.app.mvc.dto.AuthorityType;
import com.webshrub.cpagenie.app.mvc.dto.User;
import com.webshrub.cpagenie.app.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * CPAGenieUser: Ahsan.Javed
 * Date: Aug 14, 2010
 * Time: 9:11:33 PM
 */
@Component
public class TestRunner {
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("dispatcher-servlet.xml", "dispatcher-data.xml", "dispatcher-context.xml", "security-context.xml");
        TestRunner testRunner = (TestRunner) ctx.getBean("testRunner");
        UserService userService = testRunner.getUserService();

        for (AuthorityType authorityType : AuthorityType.getAuthorityTypeList()) {
            Authority authority = new Authority();
            authority.setAuthorityType(authorityType);
            authority.setCreationTime(new Date());
            userService.saveAuthority(authority);
        }

        userService.saveUser("admin", "password", "admin@webshrub.com", "Ahsan", "Javed", true);
        User user = userService.getUser("admin");
        userService.changePassword(user, "welcome");
        userService.assignAuthority(user, userService.getAuthority(AuthorityType.getAuthorityType(CPAGenieAuthorityType.ROLE_ADMIN.getId())));
    }
}
