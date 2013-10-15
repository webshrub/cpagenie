package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.advertiser.CPAGenieAdvertiser;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 8, 2010
 * Time: 1:26:50 PM
 */
public class Advertiser {
    private Integer id;
    private String name;
    private String description;
    private String email;
    private Date creationTime;
    private Date updateTime;
    private String updateComments;
    private Vertical vertical;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Vertical getVertical() {
        return vertical;
    }

    public void setVertical(Vertical vertical) {
        this.vertical = vertical;
    }

    public Advertiser fill(CPAGenieAdvertiser advertiser) {
        setId(advertiser.getId());
        setName(advertiser.getName());
        setDescription(advertiser.getDescription());
        setEmail(advertiser.getEmail());
        setCreationTime(advertiser.getCreationTime());
        setUpdateTime(advertiser.getUpdateTime());
        setUpdateComments(advertiser.getUpdateComments());
        setVertical(new Vertical().fill(advertiser.getVertical()));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertiser advertiser = (Advertiser) o;
        return !(name != null ? !name.equals(advertiser.name) : advertiser.name != null) && !(vertical != null ? !vertical.equals(advertiser.vertical) : advertiser.vertical != null);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (vertical != null ? vertical.hashCode() : 0);
        return result;
    }
}
