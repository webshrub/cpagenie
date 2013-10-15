package com.webshrub.cpagenie.core.db.profane;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 11, 2010
 * Time: 6:04:16 PM
 */
@Entity
@Table(name = "CG_PROFANE", uniqueConstraints = @UniqueConstraint(columnNames = {"PROFANE"}))
public class CPAGenieProfane {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PROFANE", nullable = false)
    private String profane;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieProfane that = (CPAGenieProfane) o;
        return profane.equals(that.profane);
    }

    @Override
    public int hashCode() {
        return profane.hashCode();
    }
}
