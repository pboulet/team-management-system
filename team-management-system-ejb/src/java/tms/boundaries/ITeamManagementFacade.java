package tms.boundaries;

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
 * Performs CRUD operations on Instructor, Course,
 * User, Student and all the other operations handled
 * by its sub systems.
 * 
 * @author Patrice Boulet
 */
@Local
public interface ITeamManagementFacade {

    void createInstructor(Instructor i);

    void createJoinRequest(JoinRequest j);

    void createStudent(Student s);

    void createTeam(Team t);

    void createTeamParameters(TeamParameters p);

    void createUser(User u);

    void editCourse(Course c);

    void editJoinRequest(JoinRequest j);

    void editStudent(Student s);

    void editTeam(Team t);

    void editTeamParameters(TeamParameters p);

    Course getCourse(Long id);

    Instructor getInstructor(String id);

    Student getStudent(String id);

    Team getTeam(Long id);

    TeamParameters getTeamParameters(Long id);

    User getUser(Long id);
    
}
