package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.app.db.authority.CPAGenieAuthority;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 9, 2010
 * Time: 9:10:46 PM
 */
public class Authority {
    private Integer id;
    private AuthorityType authorityType;
    private Date creationTime;
    private Date updateTime;
    private String updateComments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(AuthorityType authorityType) {
        this.authorityType = authorityType;
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

    public Authority fill(CPAGenieAuthority dbAuthority) {
        setId(dbAuthority.getId());
        setAuthorityType(AuthorityType.getAuthorityType(dbAuthority.getAuthorityType().getId()));
        setCreationTime(dbAuthority.getCreationTime());
        setUpdateTime(dbAuthority.getUpdateTime());
        setUpdateComments(dbAuthority.getUpdateComments());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority1 = (Authority) o;
        return !(authorityType != null ? !authorityType.equals(authority1.authorityType) : authority1.authorityType != null);
    }

    @Override
    public int hashCode() {
        return authorityType != null ? authorityType.hashCode() : 0;
    }
}
