package tms.tcs.controllers;


import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import tms.boundaries.ITeamManagementFacade;
import tms.models.Course;
import tms.models.Student;
import tms.models.User;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;

@ManagedBean(name = "joinTeamController")
@ViewScoped
public class JoinTeamController {

    @EJB(beanName="TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;

    private String teamName;
    private List<Team> teamList;
    private List<Team> selectedTeam;
    private Course course;
    private Long courseid;
    private Student currentStudent;

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public List<Team> getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(List<Team> selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public void init() {
        if (courseid == null) {
            return;
        }
        course = tmsFacade.getCourse(courseid);

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Object sessionUser = session.getAttribute("User");

        if (sessionUser != null) {
            currentStudent = ((User) sessionUser).getStudent();
        }
        teamList = new LinkedList<>();
        selectedTeam = new LinkedList<>();
        boolean toAdd;
        int maxStudent = course.getTeamParams().getMaxNumStudents();
        for (Team t : course.getTeams()) {
            toAdd = true;
            for (JoinRequest j : t.getJoinRequests()) {
                if (j.getStudent().equals(currentStudent)) {
                    toAdd = false;
                }
            }
            if (t.getStudentList().size() < maxStudent && toAdd) {
                teamList.add(t);
            }
        }
    }

    public void submit(ActionEvent actionEvent) {
        for (Team t : selectedTeam) {
            JoinRequest j = new JoinRequest();
            j.setAccepted(false);
            j.setStudent(currentStudent);
            j.setTeam(t);
            tmsFacade.createJoinRequest(j);
            currentStudent.getJoinRequests().add(j);
            t.getJoinRequests().add(j);
            tmsFacade.editTeam(t);
        }
        tmsFacade.editStudent(currentStudent);
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

}
