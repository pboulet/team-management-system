/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.sql.Timestamp;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import tms.boundaries.CourseFacade;
import tms.models.Course;
import tms.tcs.boundaries.TeamParametersFacade;
import tms.tcs.models.TeamParameters;

/**
 *
 * @author maxime
 */
@ManagedBean(name = "setupParametersController")
@ViewScoped
public class SetupParametersController {

    @EJB
    private CourseFacade courseFacade;
    @EJB
    private TeamParametersFacade teamParametersFacade;

    private TeamParameters teamParameters;
    private Course course;
    private String courseSection;
    private Integer minStudent;
    private Integer maxStudent;
    private Date deadline;
    private Long courseid;

  
    public SetupParametersController() {
    }

    public void init() {
        if (courseid == null) {
            return;
        }
        course = courseFacade.find(courseid);
        if (course == null) {
            return;
        }
        courseSection = course.getCourseSection();
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public void setCourseSection(String courseSection) {
        this.courseSection = courseSection;
    }

    public Integer getMinStudent() {
        return minStudent;
    }

    public void setMinStudent(Integer minStudent) {
        this.minStudent = minStudent;
    }

    public Integer getMaxStudent() {
        return maxStudent;
    }

    public void setMaxStudent(Integer maxStudent) {
        this.maxStudent = maxStudent;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public TeamParameters getTeamParameters() {
        return teamParameters;
    }

    public void setTeamParameters(TeamParameters teamParameters) {
        this.teamParameters = teamParameters;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void submit(ActionEvent actionEvent) {  
        teamParameters = new TeamParameters();
        teamParameters.setCreationDeadline(new Timestamp(deadline.getTime()));
        teamParameters.setMaxNumStudents(maxStudent);
        teamParameters.setMinNumStudents(minStudent);
        course.setTeamCreationAllowed(true);
        course.setTeamParams(teamParameters);
        teamParametersFacade.create(teamParameters);
        courseFacade.edit(course);
    }    
}
