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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PROGRAM_OF_STUDY")
    private String programOfStudy;

    @OneToMany(mappedBy="liaison")
    private List<Team> liaisonOf;
    
    @ManyToMany
    @JoinColumn(name="TEAM_ID")
    private List<Team> teamList;

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
