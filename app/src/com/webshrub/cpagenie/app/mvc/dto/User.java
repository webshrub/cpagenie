package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.app.db.authority.CPAGenieAuthority;
import com.webshrub.cpagenie.app.db.user.CPAGenieUser;
import com.webshrub.cpagenie.core.db.advertiser.CPAGenieAdvertiser;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 9, 2010
 * Time: 9:04:57 PM
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String firstName;
    private String lastName;
    private UserStatus status;
    private Date creationTime;
    private Date updateTime;
    private String updateComments;
    private Set<Authority> authorities = new HashSet<Authority>();
    private Set<Advertiser> advertisers = new HashSet<Advertiser>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateComments() {
        return updateComments;
    }

    public void setUpdateComments(String updateComments) {
        this.updateComments = updateComments;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public Set<Advertiser> getAdvertisers() {
        return advertisers;
    }

    public void setAdvertisers(Set<Advertiser> advertisers) {
        this.advertisers = advertisers;
    }

    public void addAdvertiser(Advertiser advertiser) {
        this.advertisers.add(advertiser);
    }

    public User fill(CPAGenieUser dbUser) {
        setId(dbUser.getId());
        setUsername(dbUser.getUsername());
        setPassword(dbUser.getPassword());
        setEmail(dbUser.getEmail());
        setFirstName(dbUser.getFirstName());
        setLastName(dbUser.getLastName());
        setStatus(UserStatus.getStatus(dbUser.getStatus().getId()));
        setCreationTime(dbUser.getCreationTime());
        setUpdateTime(dbUser.getUpdateTime());
        setUpdateComments(dbUser.getUpdateComments());
        for (CPAGenieAuthority dbAuthority : dbUser.getAuthorities()) {
            addAuthority(new Authority().fill(dbAuthority));
        }
        for (CPAGenieAdvertiser dbAdvertiser : dbUser.getAdvertisers()) {
            addAdvertiser(new Advertiser().fill(dbAdvertiser));
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return !(username != null ? !username.equals(user.username) : user.username != null);
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
