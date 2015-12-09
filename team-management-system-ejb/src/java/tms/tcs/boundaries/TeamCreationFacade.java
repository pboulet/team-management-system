/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.boundaries;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;
import tms.tcs.models.TeamParameters;

/**
 *
 * @author User
 */
@Stateless
public class TeamCreationFacade implements ITeamCreationFacade {

    @EJB
    private TeamFacade teamFacade;
    @EJB
    private JoinRequestFacade joinRequestFacade;
    @EJB
    private TeamParametersFacade teamParametersFacade;
    
    @Override
    public Team getTeam(Long id) {
        return teamFacade.find(id);
    }
    
    @Override
    public void editTeam(Team t){
        teamFacade.edit(t);
    }
    
    @Override
    public void createTeam(Team t){
        teamFacade.create(t);
    }
    
    @Override
    public void editJoinRequest(JoinRequest j){
        joinRequestFacade.edit(j);
    }
    
    @Override
    public void createJoinRequest(JoinRequest j){
        joinRequestFacade.create(j);
    }
    
    @Override
    public void createTeamParameters(TeamParameters p){
        teamParametersFacade.create(p);
    }
    
    @Override
    public void editTeamParameters(TeamParameters p){
        teamParametersFacade.edit(p);
    }
    
    @Override
    public TeamParameters getTeamParameters(Long id){
        return teamParametersFacade.find(id);
    }
}
