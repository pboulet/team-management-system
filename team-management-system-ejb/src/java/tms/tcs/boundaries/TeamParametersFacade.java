/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.boundaries;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tms.boundaries.AbstractFacade;
import tms.tcs.models.TeamParameters;

/**
 *
 * @author User
 */
@Stateless
public class TeamParametersFacade extends AbstractFacade<TeamParameters> {
    @PersistenceContext(unitName = "team-management-system-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeamParametersFacade() {
        super(TeamParameters.class);
    }
    
}
