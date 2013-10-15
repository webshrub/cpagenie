package com.webshrub.cpagenie.server.db.job;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CG_JOB", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
public class CPAGenieJob {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LAST_RUN_TIME", nullable = false)
    private Date lastRunTime;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private CPAGenieJobStatus status;

    @Column(name = "UPDATE_COMMENTS", length = 1000)
    private String updateComments;

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

    public Date getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(Date lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public CPAGenieJobStatus getStatus() {
        return status;
    }

    public void setStatus(CPAGenieJobStatus status) {
        this.status = status;
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
        CPAGenieJob that = (CPAGenieJob) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}