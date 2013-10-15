package com.webshrub.cpagenie.app.mvc.dto;

import com.webshrub.cpagenie.core.db.profane.CPAGenieProfane;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 8, 2010
 * Time: 1:26:50 PM
 */
public class ValidationRule {
    private Integer id;
    private String profane;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfane() {
        return profane;
    }

    public void setProfane(String profane) {
        this.profane = profane;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ValidationRule fill(CPAGenieProfane profane) {
        setId(profane.getId());
        setProfane(profane.getProfane());
        setDescription("");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationRule that = (ValidationRule) o;
        return !(profane != null ? !profane.equals(that.profane) : that.profane != null);
    }

    @Override
    public int hashCode() {
        return profane != null ? profane.hashCode() : 0;
    }
}
