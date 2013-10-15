package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.source.CPAGenieSource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Sep 14, 2010
 * Time: 1:14:34 AM
 */
public class Source {
    private Integer id;
    private String name;
    private String description;
    private Date creationTime;
    private Date updateTime;
    private String updateComments;
    private Set<Lead> leads = new HashSet<Lead>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<Lead> getLeads() {
        return leads;
    }

    public void setLeads(Set<Lead> leads) {
        this.leads = leads;
    }

    public void addLead(Lead lead) {
        this.leads.add(lead);
    }

    public Source fill(CPAGenieSource source) {
        setId(source.getId());
        setName(source.getName());
        setDescription(source.getDescription());
        setCreationTime(source.getCreationTime());
        setUpdateTime(source.getUpdateTime());
        setUpdateComments(source.getUpdateComments());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return !(name != null ? !name.equals(source.name) : source.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
