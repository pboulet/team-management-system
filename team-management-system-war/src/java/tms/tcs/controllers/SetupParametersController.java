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
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;
import tms.boundaries.CourseFacade;
import tms.models.Course;
import tms.tcs.boundaries.TeamParametersFacade;
import tms.tcs.models.TeamParameters;

/**
 * Controller for the Setup Parameters View
 *
 * @author Maxime Bélair, Patrice Boulet
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
    /*
     Get the first initialisation of the page, get the course and general information about teams
     */

    public void init() {
        if (courseid == null) {
            return;
        }
        //get the team from courseId
        course = courseFacade.find(courseid);
        if (course == null) {
            return;
        }
        courseSection = course.getCourseSection();

        // initialize fields with model properties
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
            //TODO : try to display the msg?!
            /*
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary("Maximum students in team must be higher than minimum students");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("msg");*/
          //  FacesContext.getCurrentInstance().addMessage("form:maxStudent", new FacesMessage("Maximum students in team must be higher than minimum students"));
          //  RequestContext.getCurrentInstance().update("form:maxStudent");
            return null;
        }
        boolean newTeamPara = (teamParameters == null);
        if (newTeamPara) {
            teamParameters = new TeamParameters();
        }
        teamParameters.setCreationDeadline(new Timestamp(deadline.getTime()));
        teamParameters.setMaxNumStudents(maxStudent);
        teamParameters.setMinNumStudents(minStudent);
        course.setTeamParams(teamParameters);
        if (newTeamPara) {
            teamParametersFacade.create(teamParameters);
        } else {
            teamParametersFacade.edit(teamParameters);
        }
        courseFacade.edit(course);
        return "home?faces-redirect=true";
    }
}
