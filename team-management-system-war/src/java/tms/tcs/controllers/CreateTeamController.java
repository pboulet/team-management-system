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
import javax.servlet.http.HttpSession;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import tms.boundaries.CourseFacade;
import tms.boundaries.StudentFacade;
import tms.models.Course;
import tms.models.Student;
import tms.models.User;
import tms.tcs.boundaries.TeamFacade;
import tms.tcs.models.Team;

@ManagedBean(name = "createTeamController")
@ViewScoped
public class CreateTeamController {

    @EJB
    private StudentFacade studentFacade;
    @EJB
    private CourseFacade courseFacade;
    @EJB
    private TeamFacade teamFacade;

    private String teamName;
    private DualListModel<String> studentList;
    private Team team;
    private Course course;
    private Long courseid;
    private Student currentStudent;

    public CreateTeamController() {
        team = new Team();
    }

    public void init() {
        if (courseid == null) {
            return;
        }
        course = courseFacade.find(courseid);

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Object sessionUser = session.getAttribute("User");

        if (sessionUser != null) {
            currentStudent = ((User) sessionUser).getStudent();
        }
        List<String> studentSource;
        List<String> studentTarget;
        studentSource = new LinkedList<>();
        studentTarget = new LinkedList<>();
        boolean toAdd;
        for (Student s : course.getStudentList()) {
            toAdd = true;
            if (s.getId().equals(currentStudent.getId())) {
                toAdd = false;
            } else {
                for (Team t : s.getTeamList()) {
                    if (t.getCourse() == course) {
                        //already in a team, don't display in list
                        toAdd = false;
                    }
                }
            }
            if (toAdd) {
                studentSource.add(s.getId() + " : " + s.getUser().getFirstName() + " " + s.getUser().getLastName());
            }
        }
        studentList = new DualListModel<>(studentSource, studentTarget);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public DualListModel<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(DualListModel<String> studentList) {
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
        team.setCreationDate(new Timestamp(new Date().getTime()));
        team.setLiaison(currentStudent);
        List<Student> teamList = new LinkedList<Student>();
        teamList.add(currentStudent);
        for (String s : studentList.getTarget()) {
            Student t = studentFacade.find(s.split(" ")[0]);
            teamList.add(t);
            t.getTeamList().add(team);
            studentFacade.edit(t);
        }
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
            List<String> studentSource = studentList.getSource();
            List<String> studentTarget = studentList.getTarget();
            while (course.getTeamParams().getMaxNumStudents() - 1 < studentList.getTarget().size()) {
                studentSource.add(studentTarget.remove(studentTarget.size() - 1));
            }
            studentList = new DualListModel<>(studentSource, studentTarget);
        }
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

}
