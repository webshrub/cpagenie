package com.webshrub.cpagenie.core.db.source;

import com.webshrub.cpagenie.core.db.lead.CPAGenieLead;
import com.webshrub.cpagenie.core.db.tracking.CPAGenieTracking;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 8, 2010
 * Time: 1:27:07 PM
 */
@Entity
@Table(name = "CG_SOURCE", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
public class CPAGenieSource {
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

    @OneToMany(mappedBy = "source")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieLead> leads = new HashSet<CPAGenieLead>();

    @OneToMany(mappedBy = "campaign")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieTracking> trackings = new HashSet<CPAGenieTracking>();

    public CPAGenieSource() {
    }

    //Initialize it here so that org.hibernate.PersistentSet does not throw NPE while calculating
    // hashCode due to unset field values.

    public CPAGenieSource(String name) {
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

    public Set<CPAGenieLead> getLeads() {
        return leads;
    }

    public void setLeads(Set<CPAGenieLead> leads) {
        this.leads = leads;
    }

    public void addLead(CPAGenieLead lead) {
        this.leads.add(lead);
    }

    public Set<CPAGenieTracking> getTrackings() {
        return trackings;
    }

    public void setTrackings(Set<CPAGenieTracking> trackings) {
        this.trackings = trackings;
    }

    public void addTracking(CPAGenieTracking tracking) {
        this.trackings.add(tracking);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieSource source = (CPAGenieSource) o;
        return name.equals(source.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
