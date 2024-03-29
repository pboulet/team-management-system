/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.sql.Timestamp;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import tms.boundaries.ITeamManagementFacade;
import tms.models.Course;
import tms.tcs.models.TeamParameters;

/**
 * Controller for the Setup Parameters View
 *
 * @author Maxime Bélair, Patrice Boulet
 */
@ManagedBean(name = "setupParametersController")
@ViewScoped
public class SetupParametersController {

    @EJB(beanName = "TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;

    private TeamParameters teamParameters;
    private Course course;
    private String courseSection;
    private Integer minStudent;
    private Integer maxStudent;
    private Date deadline;
    private Long courseid;

    public SetupParametersController() {
    }
    /*
     Get the first initialisation of the page, get the course and general information about teams
     */

    public void init() {
        if (courseid == null) {
            return;
        }
        //get the team from courseId
        course = tmsFacade.getCourse(courseid);
        if (course == null) {
            return;
        }
        //to display on the page
        courseSection = course.getCourseSection();

        // initialize fields with model properties, this is for use case Modify Team Parameters
        if (course.hasTeamParams() && teamParameters == null) {
            teamParameters = course.getTeamParams();
            minStudent = teamParameters.getMinNumStudents();
            maxStudent = teamParameters.getMaxNumStudents();
            deadline = teamParameters.getCreationDeadline();
        }
    }

    public void validateMax(FacesContext context, UIComponent component, Object value) {

        if ((Integer) value < minStudent) {
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary("Maximum students in team must be higher than minimum students");
            // FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new ValidatorException(msg);
        }
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

    public String submit() {
        if (maxStudent < minStudent) {
            FacesContext.getCurrentInstance().addMessage("form:maxStudent", new FacesMessage("Maximum students in team must be higher than minimum students"));
            return null;
        }       
        if (tmsFacade.setupParameters(teamParameters, deadline, maxStudent, minStudent, course)) {
            return "/faces/protected/home?faces-redirect=true";
        } else {
            return null;
        }
    }
}
