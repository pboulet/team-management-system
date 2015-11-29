/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.models;

import tms.models.Course;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import tms.models.Student;

/**
 *
 * @author User
 */
@Entity
@Table(name="PNSM_Teams")
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="TEAM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="CREATION_DATE")
    private Timestamp creationDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COURSE_ID")
    private Course course;

    @OneToMany(mappedBy="team")
    private List<JoinRequest> joinRequests;
  
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="STUDENT_ID" )
    private Student liaison;
    
    @ManyToMany(mappedBy ="teamList")
    private List<Student> studentList;

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
    
    public Student getLiaison() {
        return liaison;    
    }
    public void setLiaison(Student liaison) {
        this.liaison = liaison;
    }

    public List<JoinRequest> getJoinRequests() {
        return joinRequests;
    }

    public void setJoinRequests(List<JoinRequest> joinRequests) {
        this.joinRequests = joinRequests;
    }
    
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getCOMPLETE() {
        return COMPLETE;
    }

    public void setCOMPLETE(Boolean COMPLETE) {
        this.COMPLETE = COMPLETE;
    }

    public Integer getALLOW_MORE_STUDENTS() {
        return ALLOW_MORE_STUDENTS;
    }

    public void setALLOW_MORE_STUDENTS(Integer ALLOW_MORE_STUDENTS) {
        this.ALLOW_MORE_STUDENTS = ALLOW_MORE_STUDENTS;
    }

    public Integer getALLOW_LESS_STUDENTS() {
        return ALLOW_LESS_STUDENTS;
    }

    public void setALLOW_LESS_STUDENTS(Integer ALLOW_LESS_STUDENTS) {
        this.ALLOW_LESS_STUDENTS = ALLOW_LESS_STUDENTS;
    }
    private Boolean COMPLETE;
    private Integer ALLOW_MORE_STUDENTS;
    private Integer ALLOW_LESS_STUDENTS;

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
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Team[ id=" + id + " ]";
    }
    
}
