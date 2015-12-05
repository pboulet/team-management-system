/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import tms.models.Student;
import tms.tcs.boundaries.JoinRequestFacade;
import tms.tcs.boundaries.TeamFacade;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;

/**
 *
 * @author maxime
 */
@ManagedBean
@ViewScoped
public class acceptStudentController {

    @EJB
    private TeamFacade teamFacade;
    @EJB
    private JoinRequestFacade joinRequestFacade;

    private Long teamid;
    private Team team;
    private DualListModel<Student> studentList;
    private List<JoinRequest> joinRequestList;
    private int maxStudents;

    public acceptStudentController() {
    }

    public void init() {
        if (teamid == null) {
            return;
        }
        team = teamFacade.find(teamid);
        if (team == null) {
            return;
        }
        joinRequestList = team.getJoinRequests();
        LinkedList<Student> studentSource = new LinkedList<Student>();
        LinkedList<Student> studentTarget = new LinkedList<Student>();
        for (JoinRequest j : joinRequestList) {
            studentSource.add(j.getStudent());
        }
        studentList = new DualListModel<>(studentSource, studentTarget);
        maxStudents = team.getCourse().getTeamParams().getMaxNumStudents() - team.getStudentList().size();
    }

    public void submit(ActionEvent actionEvent) {
        List<Student> studentTarget = studentList.getTarget();
        for (Student s : studentTarget) {
            team.getStudentList().add(s);
            for (JoinRequest j : joinRequestList) {
                if (j.getStudent().equals(s)) {
                    j.setAccepted(true);
                    joinRequestFacade.edit(j);
                }
            }
        }
        teamFacade.edit(team);
    }

    public void onTransfer(TransferEvent event) {
        if (!event.isAdd()) {
            return;
        }
        int totalItemsNumber = 0;

        totalItemsNumber = studentList.getTarget().size();

        List<?> items = event.getItems();
        if (items != null) {
            totalItemsNumber = totalItemsNumber + items.size();
        }

        if (totalItemsNumber > maxStudents) {
            // limit of selected students is exceeded
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary("Exceeded number of students in the team");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            List<Student> studentSource = studentList.getSource();
            List<Student> studentTarget = studentList.getTarget();
            for (int i = maxStudents; i < studentList.getTarget().size(); i++) {
                studentSource.add(studentTarget.remove(i));
                i--;
            }
            studentList = new DualListModel<>(studentSource, studentTarget);
        
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

    public DualListModel<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(DualListModel<Student> studentList) {
        this.studentList = studentList;
    }

}
