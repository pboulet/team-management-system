package tms.tcs.boundaries;

import java.util.List;
import javax.ejb.Local;
import tms.models.Student;
import tms.models.User;
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

    Team getTeam(Long id);

    TeamParameters getTeamParameters(Long id);
    
    boolean joinTeams(List<Team> teams, Student s);
}
