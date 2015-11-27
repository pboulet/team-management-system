/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import tms.boundaries.CourseFacade;

/**
 *
 * @author User
 */
@ManagedBean
@RequestScoped
public class HomeController {
    @EJB
    private CourseFacade courseFacade;

    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
    }

}
