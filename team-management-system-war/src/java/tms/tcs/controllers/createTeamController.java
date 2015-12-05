/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.sql.Timestamp;
import java.util.Date;
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
import tms.boundaries.CourseFacade;
import tms.models.Course;
import tms.models.Student;
import tms.tcs.boundaries.TeamFacade;
import tms.tcs.models.Team;

@ManagedBean(name = "createTeamController")
@ViewScoped
public class CreateTeamController {

    @EJB
    private CourseFacade courseFacade;
    @EJB
    private TeamFacade teamFacade;

    private String teamName;
    private DualListModel<Student> studentList;
    private Team team;
    private Course course;
    private Long courseid;

    public CreateTeamController() {
        team = new Team();
    }

    public void init() {
        if (courseid == null) {
            return;
        }
        course = courseFacade.find(courseid);
        List<Student> studentSource;
        List<Student> studentTarget;
        if (studentList == null) {
            studentSource = new LinkedList<>();
            studentTarget = new LinkedList<>();
            boolean toAdd;
            for (Student s : course.getStudentList()) {
                toAdd = true;
                //if s == currentStudent, toAdd = false;   TODO
                for (Team t : s.getTeamList()) {
                    if (t.getCourse() == course) {
                        //already in a team, don't display in list
                        toAdd = false;
                    }
                }
                if (toAdd) {
                    studentSource.add(s);
                }
            }
            studentList = new DualListModel<>(studentSource, studentTarget);
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public DualListModel<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(DualListModel<Student> studentList) {
        this.studentList = studentList;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void submit(ActionEvent actionEvent) {
        team.setName(teamName);
        team.setCourse(course);
        team.setCreationDate((Timestamp) new Date());
        //team.setLiaison(currentStudent); //todo
        List<Student> teamList = studentList.getTarget();
       // teamList.add(currentStudent); todo
        team.setStudentList(teamList);
        teamFacade.create(team);
        courseFacade.edit(course);

    }

    public void onTransfer(TransferEvent event) {
        if (!event.isAdd()) {
            return;
        }
        if (studentList.getTarget().size() > course.getTeamParams().getMaxNumStudents() - 1) {
            // limit of selected students is exceeded
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary("Exceeded number of students in the team");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            List<Student> studentSource = studentList.getSource();
            List<Student> studentTarget = studentList.getTarget();
            while (course.getTeamParams().getMaxNumStudents() - 1 < studentList.getTarget().size()) {
                studentSource.add(studentTarget.remove(studentTarget.size() - 1));
            }
            studentList = new DualListModel<>(studentSource, studentTarget);
        }
    }
}
