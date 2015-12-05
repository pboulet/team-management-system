/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.models;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for Team Parameters Models
 * 
 * @author Patrice Boulet
 */
@Entity
@Table(name="PNSM_TeamParameters")
public class TeamParameters implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="TEAM_PARAMETERS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="MAX_NUM_STUDENTS")
    private Integer maxNumStudents;
    
    @Column(name="MIN_NUM_STUDENTS")
    private Integer minNumStudents;
    
    @Column(name="CREATION_DEADLINE")
    private Timestamp creationDeadline;

        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getMaxNumStudents() {
        return maxNumStudents;
    }

    public void setMaxNumStudents(Integer maxNumStudents) {
        this.maxNumStudents = maxNumStudents;
    }

    public Integer getMinNumStudents() {
        return minNumStudents;
    }

    public void setMinNumStudents(Integer minNumStudents) {
        this.minNumStudents = minNumStudents;
    }

    public Timestamp getCreationDeadline() {
        return creationDeadline;
    }

    public void setCreationDeadline(Timestamp creationDeadline) {
        this.creationDeadline = creationDeadline;
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
        if (!(object instanceof TeamParameters)) {
            return false;
        }
        TeamParameters other = (TeamParameters) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.TeamParameters[ id=" + id + " ]";
    }
    
}
