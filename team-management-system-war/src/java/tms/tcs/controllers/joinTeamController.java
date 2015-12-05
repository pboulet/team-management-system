/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import tms.models.Course;
import tms.tcs.boundaries.TeamFacade;
import tms.tcs.models.Team;

@Named(value = "joinTeamController")
@RequestScoped
public class joinTeamController {
    @EJB
    private TeamFacade teamFacade;

    
private List<Team> teamList;
private Team team;
private Course course;
private Long courseid;
public void init() {

}
    public joinTeamController() {
        
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }
    
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }
    
    public void submit(ActionEvent actionEvent) {  
    teamFacade.edit(team);  
    }
    
    public void add(ActionEvent actionEvent) {  
        int currentStudent=0; //need to figure how to get the current student that being added
    teamList.add(currentStudent, team); //then add the student to the 
    }
    
    
        
}
