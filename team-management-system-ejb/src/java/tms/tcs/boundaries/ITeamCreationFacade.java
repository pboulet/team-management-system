/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.boundaries;

import javax.ejb.Local;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;
import tms.tcs.models.TeamParameters;

/**
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
    
}
