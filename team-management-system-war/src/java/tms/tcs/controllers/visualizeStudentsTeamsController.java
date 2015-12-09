/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tms.boundaries.ITeamManagementFacade;
import tms.models.Course;
import tms.models.Student;
import tms.models.User;
import tms.tcs.models.Team;

/**
 *
 * @author Maxime Bélair, Patrice Boulet
 */
@ManagedBean(name = "visualizeStudentsTeamsController")
@RequestScoped
public class VisualizeStudentsTeamsController {

    @EJB(beanName="TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;
    
    private Long courseid;
    private Course course;

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
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
        if (student.equals(team.getLiaison())) {
            return "";
        }   
        User u = student.getUser();
        return u.getFirstName() + " " + u.getLastName();
    }
}
