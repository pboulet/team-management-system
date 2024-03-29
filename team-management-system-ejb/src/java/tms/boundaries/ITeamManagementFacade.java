package tms.boundaries;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import tms.models.Course;
import tms.models.Instructor;
import tms.models.Student;
import tms.models.User;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;
import tms.tcs.models.TeamParameters;

/**
 * Team Management System Main Facade Interface
 *
 * Performs CRUD operations on Instructor, Course, User, Student and all the
 * other operations handled by its sub systems.
 *
 * @author Patrice Boulet
 */
@Local
public interface ITeamManagementFacade {

    void createJoinRequest(JoinRequest j);

    void createTeam(Team t);

    void createTeamParameters(TeamParameters p);

    void createUser(User u);

    Course getCourse(Long id);

    Student getStudent(String id);

    Team getTeam(Long id);

    User getUser(Long id);

    List<Team> getIncompleteTeamsToJoin(Long courseid, Student s);

    boolean joinTeams(List<Team> teams, Student s);

    String getStudentName(Student s, Team t);

    User login(String userId, String password);
    
    boolean setupParameters(TeamParameters teamPara, Date deadline, Integer maxStudent, Integer minStudent, Course course);

    List<String> getStudentsFromJoinRequest(Team team);

    int getMaxStudent(Team team);

    void acceptStudent(List<String> selectedStudentList, Team team);

    List<String> getStudentsNotInTeam(Course course, Student currentStudent);

    void createTeam(String teamName, Course course, Student currentStudent, List<String> selectedStudentList);
}
