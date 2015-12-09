/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import tms.boundaries.ITeamManagementFacade;
import tms.models.Student;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;

/**
 *
 * @author maxime, Patrice Boulet
 */
@ManagedBean(name = "acceptStudentController")
@ViewScoped
public class AcceptStudentController {

    @EJB(beanName = "TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;

    private Long teamid;
    private Team team;
    private DualListModel<String> studentList;
    private int maxStudents;
    private boolean submitDisabled = false;

    public AcceptStudentController() {
    }

    public void init() {
        if (teamid == null) {
            return;
        }
        team = tmsFacade.getTeam(teamid);
        if (team == null) {
            return;
        }
        studentList = new DualListModel<>(tmsFacade.getStudentsFromJoinRequest(team), new LinkedList<String>());
        maxStudents = tmsFacade.getMaxStudent(team);
        maxStudents -= team.getStudentList().size();
    }

    public void submit(ActionEvent actionEvent) {
        tmsFacade.acceptStudent(studentList.getTarget(), team);

    }

    public void onTransfer(TransferEvent event) {
        submitDisabled = (studentList.getTarget().size() > maxStudents);
        if (!event.isAdd()) {
            return;
        }
        if (studentList.getTarget().size() > maxStudents) {
            // limit of selected students is exceeded
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary("Exceeded number of students in the team");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Long getTeamid() {
        return teamid;
    }

    public void setTeamid(Long teamid) {
        this.teamid = teamid;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public DualListModel<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(DualListModel<String> studentList) {
        this.studentList = studentList;
    }

    public boolean isSubmitDisabled() {
        return submitDisabled;
    }

    public void setSubmitDisabled(boolean submitDisabled) {
        this.submitDisabled = submitDisabled;
    }

}
