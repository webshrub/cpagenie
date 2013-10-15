package com.webshrub.cpagenie.app.db.authority;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * CPAGenieUser: Ahsan.Javed
 * Date: Jul 28, 2010
 * Time: 12:15:16 AM
 */
@Entity
@Table(name = "CG_AUTHORITY", uniqueConstraints = @UniqueConstraint(columnNames = {"AUTHORITY_TYPE"}))
public class CPAGenieAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "AUTHORITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private CPAGenieAuthorityType authorityType;

    @Column(name = "CREATION_TIME", nullable = false)
    private Date creationTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_COMMENTS", length = 1000)
    private String updateComments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CPAGenieAuthorityType getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(CPAGenieAuthorityType authorityType) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieAuthority authority1 = (CPAGenieAuthority) o;
        return authorityType.equals(authority1.authorityType);
    }

    @Override
    public int hashCode() {
        return authorityType.hashCode();
    }
}
