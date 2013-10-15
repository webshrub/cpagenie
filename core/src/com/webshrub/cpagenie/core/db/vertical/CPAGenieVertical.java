package com.webshrub.cpagenie.core.db.vertical;

import com.webshrub.cpagenie.core.db.advertiser.CPAGenieAdvertiser;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 11, 2010
 * Time: 6:04:16 PM
 */
@Entity
@Table(name = "CG_VERTICAL", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
public class CPAGenieVertical {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "CREATION_TIME", nullable = false)
    private Date creationTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_COMMENTS", length = 1000)
    private String updateComments;

    @OneToMany(mappedBy = "vertical")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieAdvertiser> advertisers = new HashSet<CPAGenieAdvertiser>();

    public CPAGenieVertical() {
    }

    //Initialize it here so that org.hibernate.PersistentSet does not throw NPE while calculating
    // hashCode due to unset field values.

    public CPAGenieVertical(String name) {
        this.name = name;
    }

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
        CPAGenieVertical vertical = (CPAGenieVertical) o;
        return name.equals(vertical.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
