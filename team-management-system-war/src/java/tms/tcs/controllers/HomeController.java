/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.controllers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.event.TabChangeEvent;
import tms.boundaries.CourseFacade;
import tms.boundaries.UserFacade;
import tms.models.Course;
import tms.models.User;
import tms.tcs.boundaries.HomeView;

/**
 * Controller for the Home view.
 * 
 * @author Patrice Boulet
 */
@ManagedBean(name = "homeController")
@ViewScoped
public class HomeController implements Serializable {
    
    @EJB
    private CourseFacade courseFacade;
    
    @EJB
    private UserFacade userFacade;
    
    @ManagedProperty("#{homeView}")
    private HomeView homeView;
    
    private User user;

    // mock until I know how to get the session user
    private List<Course> studentCourseList; 
    
    private List<Course> instructorCourseList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public List<Course> getStudentCourseList() {
        return studentCourseList;
    }

    public void setStudentCourseList(List<Course> courseList) {
        this.studentCourseList = courseList;
    }

    public List<Course> getInstructorCourseList() {
        return instructorCourseList;
    }

    public void setInstructorCourseList(List<Course> instructorCourseList) {
        this.instructorCourseList = instructorCourseList;
    }

    public HomeView getHomeView() {
        return homeView;
    }

    public void setHomeView(HomeView homeView) {
        this.homeView = homeView;
    }
    
    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
        
    }
    
    public void setMenuOptionsVisibility() {
        boolean hasBothRoles = 
                user.isInstructor() && 
                user.isStudent();
        
        homeView.setShowStudentMenuOptions(
            hasBothRoles &&
            homeView.getCourseListTv().getActiveIndex() == homeView.getSTUDENT_TAB_INDEX() ||
            user.isStudent());
        
        homeView.setShowInstructorMenuOptions(
            hasBothRoles &&
            homeView.getCourseListTv().getActiveIndex() == homeView.getINSTRUCTOR_TAB_INDEX() ||
            user.isInstructor());
    }

    public void onCourseListTabChange( TabChangeEvent e) {
        homeView.getCourseListTv()
                .setActiveIndex(homeView
                                    .getCourseListTv()
                                    .getChildren()
                                    .indexOf(e.getTab()));
        setMenuOptionsVisibility();
    }
    
    @PostConstruct
    public void init() {
        //TODO: hookup everything to the user from the session
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Object sessionUser = session.getAttribute("User");
        
        if(sessionUser != null) 
            user=(User)sessionUser; 
        else
            user = userFacade.find((long) 1); // default workaround in case auth fails 
        
        if (user.isStudent()) 
            studentCourseList = user
                                .getStudent()
                                .getCourseList();
        
        if (user.isInstructor()){
            instructorCourseList = user
                                    .getInstructor()
                                    .getCourseList();
            

            homeView.getCourseListTv().getChildren().get(1);
        }
        
        setMenuOptionsVisibility();
    }
}
