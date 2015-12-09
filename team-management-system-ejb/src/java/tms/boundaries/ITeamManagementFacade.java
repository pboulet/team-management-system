/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author User
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
