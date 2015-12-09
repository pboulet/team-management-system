/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tms.boundaries.ITeamManagementFacade;
import tms.models.Course;
import tms.models.Student;
import tms.tcs.models.Team;
import tms.tcs.models.TeamParameters;

/**
 *
 * @author Maxime Bélair, Patrice Boulet
 */
@ManagedBean(name = "visualizeStudentsTeamsController")
@RequestScoped
public class VisualizeStudentsTeamsController {

    @EJB(beanName="TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;
    
    private List<Team> teams;
    
    private Course course;
    
    private TeamParameters tParams;
    
    private Long courseid;

    public TeamParameters gettParams() {
        return tParams;
    }

    public void settParams(TeamParameters tParams) {
        this.tParams = tParams;
    }    
    
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
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

    /**
     * Creates a new instance of visualizeStudentsTeamsController
     */
    public VisualizeStudentsTeamsController() {
    }

    public void init() {
        if (courseid == null) {
            return;
        }
        course = tmsFacade.getCourse(courseid);
        teams = tmsFacade.getCourseTeams(courseid);
    } 
/*
    public String validate(Team team) {
        int min = team.getCourse().getTeamParams().getMinNumStudents();
        int max = team.getCourse().getTeamParams().getMaxNumStudents();
        int students = team.getStudentList().size();
        if (students < min) {
            return "The team is not valid, need " + (min - students) + " more students.";
        }
        if (students == max) {
            return "The team complete.";
        }
        return "The team is valid, but not complete. " + (max - students) + " places remaining.";
    }
*/
    public String getStudentName(Student student, Team team) {
        return tmsFacade.getStudentName(student, team);
    }
}
