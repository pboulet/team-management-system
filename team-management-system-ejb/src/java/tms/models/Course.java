/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import tms.tcs.models.Team;
import tms.tcs.models.TeamParameters;

/**
 *
 * @author User
 */
@Entity
@Table(name="PNSM_Courses")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="COURSE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="COURSE_SECTION")
    private String courseSection;
    
    @Column(name="TEAM_CREATION_ALLOWED")
    private Boolean teamCreationAllowed;

    @OneToMany(mappedBy="course")
    private List<Team> teams;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TEAM_PARAMETERS_ID")
    private TeamParameters teamParams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public TeamParameters getTeamParams() {
        return teamParams;
    }

    public void setTeamParams(TeamParameters teamParams) {
        this.teamParams = teamParams;
    }
    
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
    
    public String getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(String SECTION) {
        this.courseSection = SECTION;
    }

    public Boolean getTeamCreationAllowed() {
        return teamCreationAllowed;
    }

    public void setTeamCreationAllowed(Boolean teamCreationAllowed) {
        this.teamCreationAllowed = teamCreationAllowed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Course[ id=" + id + " ]";
    }
    
}
