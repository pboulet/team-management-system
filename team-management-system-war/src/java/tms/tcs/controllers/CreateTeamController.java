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
import tms.boundaries.ITeamManagementFacade;
import tms.models.Course;
import tms.models.Student;
import tms.models.User;
import tms.tcs.models.Team;

/**
 * Create Team controller.
 *
 * @author Maxime, Sofiane Batou, Patrice Boulet
 */
@ManagedBean(name = "createTeamController")
@ViewScoped
public class CreateTeamController {

    @EJB(beanName = "TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;

    private String teamName;
    private DualListModel<String> studentList;
    private Course course;
    private Long courseid;
    private Student currentStudent;
    private boolean submitDisabled = false;

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
        studentList = new DualListModel<>(tmsFacade.getStudentsNotInTeam(course, currentStudent), new LinkedList<String>());
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


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void submit(ActionEvent actionEvent) {
        tmsFacade.createTeam(teamName, course, currentStudent, studentList.getTarget());
    }

    public void onTransfer(TransferEvent event) {
        submitDisabled = (studentList.getTarget().size() > course.getTeamParams().getMaxNumStudents() - 1);
        if (!event.isAdd()) {
            return;
        }
        if (studentList.getTarget().size() > course.getTeamParams().getMaxNumStudents() - 1) {
            // limit of selected students is exceeded
            FacesMessage msg = new FacesMessage();
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            msg.setSummary("Exceeded number of students in the team");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public boolean isSubmitDisabled() {
        return submitDisabled;
    }

    public void setSubmitDisabled(boolean submitDisabled) {
        this.submitDisabled = submitDisabled;
    }

}
