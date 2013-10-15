package com.webshrub.cpagenie.app.mvc.service;

import com.webshrub.cpagenie.app.mvc.dto.Authority;
import com.webshrub.cpagenie.app.mvc.dto.AuthorityType;
import com.webshrub.cpagenie.app.mvc.dto.Advertiser;
import com.webshrub.cpagenie.app.mvc.dto.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 26, 2010
 * Time: 4:54:11 PM
 */
public interface UserService {
    public List<User> getUserList();

    public User getUser(Integer id);

    public User getUser(String userName);

    public void saveUser(User user);

    public void saveUser(String userName, String password, String email, String firstName, String lastName, boolean status);

    public void updateUser(User user);

    public void deleteUser(User user);

    public void changePassword(User user, String newPassword);

    public String getEncodedPassword(User user, String newPassword);

    public List<Authority> getAuthorityList();

    public Authority getAuthority(Integer id);

    public Authority getAuthority(AuthorityType authorityType);

    public void saveAuthority(Authority authority);

    public void assignAuthority(User user, Authority authority);

    public void assignAdvertiser(User user, Advertiser advertiser);
}
