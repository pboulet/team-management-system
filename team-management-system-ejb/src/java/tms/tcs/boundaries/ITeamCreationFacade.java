package tms.tcs.boundaries;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import tms.models.Course;
import tms.models.Student;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;
import tms.tcs.models.TeamParameters;

/**
 *
 * Team Creation Sub-System Facade Interface
 * 
 * Performs CRUD operations on JoinRequests, Teams and TeamParameters.
 * 
 * @author User
 */
@Local
public interface ITeamCreationFacade {

    void createJoinRequest(JoinRequest j);

    void createTeam(Team t);

    void createTeamParameters(TeamParameters p);

    void editJoinRequest(JoinRequest j);

    void editTeam(Team t);

    void editTeamParameters(TeamParameters p);
    
    Course setupTeamParameters(TeamParameters teamPara, Date deadline, Integer maxStudent, Integer minStudent, Course course); 

    Team getTeam(Long id);

    TeamParameters getTeamParameters(Long id);
    
    List<JoinRequest> joinTeams(List<Team> teams, Student s);

    Team createTeam(String teamName, Course course, Student currentStudent, List<String> selectedStudentList);
    
    List<Team> getIncompleteTeamsToJoin(Course c, Student s);
}
