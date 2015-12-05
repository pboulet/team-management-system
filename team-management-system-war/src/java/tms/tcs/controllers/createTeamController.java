/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import tms.boundaries.CourseFacade;
import tms.models.Course;
import tms.models.Student;
import tms.tcs.boundaries.TeamFacade;
import tms.tcs.models.Team;

@Named(value = "createTeamController")
@RequestScoped
public class CreateTeamController {
    @EJB
    private CourseFacade courseFacade;
    @EJB
    private TeamFacade teamFacade;
    
    private String teamName;
    private List<Student> studentList;
    private Team team;
    private Course course;
    private Long courseid;

    public CreateTeamController() {
        team = new Team();
    }
    public void init() {
        if (courseid == null) {
            return;
        }
        course = courseFacade.find(courseid);
    }
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getCourseId() {
        return courseid;
    }

    public void setCourseId(Long courseId) {
        this.courseid = courseId;
    }
    
    public void add(ActionEvent actionEvent) {  
        int currentStudent=0; //need to figure how to get the current student that being added
    studentList.add(currentStudent, studentList.get(currentStudent)); //then add the student to the 
    }
    
    public void submit(ActionEvent actionEvent) {  
        team.setName(teamName);
        team.setCourse(course);
        team.setCreationDate((Timestamp) new Date());
        //team.setLiaison(currentStudent); //todo
        team.setStudentList(studentList); 
        teamFacade.create(team);
        courseFacade.edit(course);
        
    }
}
