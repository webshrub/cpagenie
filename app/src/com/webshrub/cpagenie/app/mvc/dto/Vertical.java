package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.vertical.CPAGenieVertical;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 11, 2010
 * Time: 6:04:16 PM
 */
public class Vertical {
    private Integer id;
    private String name;
    private String description;
    private Date creationTime;
    private Date updateTime;
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

    public Vertical fill(CPAGenieVertical vertical) {
        setId(vertical.getId());
        setName(vertical.getName());
        setDescription(vertical.getDescription());
        setCreationTime(vertical.getCreationTime());
        setUpdateTime(vertical.getUpdateTime());
        setUpdateComments(vertical.getUpdateComments());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertical that = (Vertical) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
