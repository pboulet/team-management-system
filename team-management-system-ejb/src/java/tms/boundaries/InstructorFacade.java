/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.boundaries;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import tms.models.Instructor;

/**
 *
 * @author User
 */
@Stateless
public class InstructorFacade extends AbstractFacade<Instructor> {
    @PersistenceContext(unitName = "team-management-system-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InstructorFacade() {
        super(Instructor.class);
    }
    
}
