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

    @EJB(beanName="TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;

    private Long teamid;
    private Team team;
    private DualListModel<String> studentList;
    private List<JoinRequest> joinRequestList;
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
        joinRequestList = team.getJoinRequests();
        LinkedList<String> studentSource = new LinkedList<>();
        LinkedList<String> studentTarget = new LinkedList<>();
        //get all join request not already accepted and add the student 
        //to the student list
        boolean toAdd = true;
        for (JoinRequest j : joinRequestList) {
            if (!j.getAccepted()) {
                Student s = j.getStudent();
                //get only the students that are not already in a team for this class
                for (Team t : s.getTeamList()) {
                    if (t.getCourse().equals(team.getCourse())) {
                        toAdd = false;
                    }
                }
                if (toAdd) {
                    studentSource.add(s.getId() + " : " + s.getUser().getFirstName() + " " + s.getUser().getLastName());
                }
            }
        }
        studentList = new DualListModel<>(studentSource, studentTarget);
        maxStudents = team.getCourse().getTeamParams().getMaxNumStudents() - team.getStudentList().size();
    }

    public void submit(ActionEvent actionEvent) {
        List<String> studentTarget = studentList.getTarget();
        for (String s : studentTarget) {
            Student student = tmsFacade.getStudent(s.split(" ")[0]);
            team.getStudentList().add(student);
            student.getTeamList().add(team);
            tmsFacade.editStudent(student);
            for (JoinRequest j : joinRequestList) {
                if (j.getStudent().equals(student)) {
                    j.setAccepted(true);
                    tmsFacade.editJoinRequest(j);
                }
            }
        }
        tmsFacade.editTeam(team);
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

    public List<JoinRequest> getJoinRequestList() {
        return joinRequestList;
    }

    public void setJoinRequestList(List<JoinRequest> joinRequestList) {
        this.joinRequestList = joinRequestList;
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
