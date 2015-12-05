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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;

/**
 *
 * @author User
 */
@Entity
@Table(name = "PNSM_Students")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "STUDENT_ID")
    private String id;

    @OneToOne(optional = false, mappedBy = "student")
    private User user;

    @Column(name = "PROGRAM_OF_STUDY")
    private String programOfStudy;

    @OneToMany(mappedBy = "liaison")
    private List<Team> liaisonOf;

    @ManyToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Team> teamList;

    @OneToMany(mappedBy = "student")
    private List<JoinRequest> joinRequests;

    public List<JoinRequest> getJoinRequests() {
        return joinRequests;
    }

    public void setJoinRequests(List<JoinRequest> joinRequests) {
        this.joinRequests = joinRequests;
    }    
    
    public List<Team> getMemberOf() {
        return teamList;
    }

    public void setMemberOf(List<Team> memberOf) {
        this.teamList = memberOf;
    }

    public List<Team> getLiaisonOf() {
        return liaisonOf;
    }

    public void setLiaisonOf(List<Team> liaisonOf) {
        this.liaisonOf = liaisonOf;
    }

    public String getProgramOfStudy() {
        return programOfStudy;
    }

    public void setProgramOfStudy(String programOfStudy) {
        this.programOfStudy = programOfStudy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
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
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Student[ id=" + id + " ]";
    }

}
