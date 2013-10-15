package com.webshrub.cpagenie.app.db.user;

import com.webshrub.cpagenie.app.db.authority.CPAGenieAuthority;
import com.webshrub.cpagenie.core.db.advertiser.CPAGenieAdvertiser;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * CPAGenieUser: Ahsan.Javed
 * Date: Jul 27, 2010
 * Time: 11:42:33 AM
 */
@Entity
@Table(name = "CG_USER", uniqueConstraints = @UniqueConstraint(columnNames = {"USERNAME"}))
public class CPAGenieUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieUserStatus status;

    @Column(name = "CREATION_TIME", nullable = false)
    private Date creationTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_COMMENTS", length = 1000)
    private String updateComments;

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "CG_USER_AUTHORITY", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "AUTHORITY_ID"))
    private Set<CPAGenieAuthority> authorities = new HashSet<CPAGenieAuthority>();

    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "CG_USER_ADVERTISER", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ADVERTISER_ID"))
    private Set<CPAGenieAdvertiser> advertisers = new HashSet<CPAGenieAdvertiser>();

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

    public CPAGenieUserStatus getStatus() {
        return status;
    }

    public void setStatus(CPAGenieUserStatus status) {
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

    public Set<CPAGenieAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<CPAGenieAuthority> authorities) {
        this.authorities = authorities;
    }

    public void addAuthority(CPAGenieAuthority authority) {
        this.authorities.add(authority);
    }

    public Set<CPAGenieAdvertiser> getAdvertisers() {
        return advertisers;
    }

    public void setAdvertisers(Set<CPAGenieAdvertiser> advertisers) {
        this.advertisers = advertisers;
    }

    public void addAdvertiser(CPAGenieAdvertiser advertiser) {
        this.advertisers.add(advertiser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieUser user = (CPAGenieUser) o;
        return id.equals(user.id) && username.equals(user.username);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }
}