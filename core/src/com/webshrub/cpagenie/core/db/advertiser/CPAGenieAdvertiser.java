package com.webshrub.cpagenie.core.db.advertiser;

import com.webshrub.cpagenie.core.db.campaign.CPAGenieCampaign;
import com.webshrub.cpagenie.core.db.vertical.CPAGenieVertical;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Jul 8, 2010
 * Time: 1:26:50 PM
 */
@Entity
@Table(name = "CG_ADVERTISER", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME", "VERTICAL_ID"}))
public class CPAGenieAdvertiser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "CREATION_TIME", nullable = false)
    private Date creationTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @Column(name = "UPDATE_COMMENTS", length = 1000)
    private String updateComments;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "VERTICAL_ID")
    private CPAGenieVertical vertical;

    @OneToMany(mappedBy = "advertiser")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<CPAGenieCampaign> campaigns = new HashSet<CPAGenieCampaign>();

    public CPAGenieAdvertiser() {
    }

    //Initialize it here so that org.hibernate.PersistentSet does not throw NPE while calculating
    // hashCode due to unset field values.

    public CPAGenieAdvertiser(String name, CPAGenieVertical vertical) {
        this.name = name;
        this.vertical = vertical;
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

    public CPAGenieVertical getVertical() {
        return vertical;
    }

    public void setVertical(CPAGenieVertical vertical) {
        this.vertical = vertical;
        vertical.addAdvertiser(this);
    }

    public Set<CPAGenieCampaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Set<CPAGenieCampaign> campaigns) {
        this.campaigns = campaigns;
    }

    public void addCampaign(CPAGenieCampaign campaign) {
        this.campaigns.add(campaign);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPAGenieAdvertiser advertiser = (CPAGenieAdvertiser) o;
        return name.equals(advertiser.name) && vertical.equals(advertiser.vertical);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + vertical.hashCode();
        return result;
    }
}
