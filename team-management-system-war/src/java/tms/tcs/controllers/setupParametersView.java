/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import javax.faces.event.ActionEvent;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import tms.tcs.boundaries.TeamParametersFacade;
import tms.tcs.models.TeamParameters;

/**
 *
 * @author maxime
 */
@ManagedBean
@RequestScoped
public class setupParametersView {

    @EJB
    private TeamParametersFacade teamParametersFacade;

    private TeamParameters teamParameters;
    private String courseCode;
    private Integer minStudent;
    private Integer maxStudent;
    private Date deadline;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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

    /**
     * Creates a new instance of setupParametersView
     */
    public setupParametersView() {
        teamParameters = new TeamParameters();
    }

    public void submit(ActionEvent actionEvent) {
        teamParameters.setCreationDeadline(new Timestamp(deadline.getTime()));
        teamParameters.setMaxNumStudents(maxStudent);
        teamParameters.setMinNumStudents(minStudent);
        teamParametersFacade.create(teamParameters);
    }
 
}
