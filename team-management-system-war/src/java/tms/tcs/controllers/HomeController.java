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
import javax.faces.view.ViewScoped;
import tms.boundaries.CourseFacade;
import tms.boundaries.UserFacade;
import tms.models.Course;
import tms.models.User;

/**
 *
 * @author User
 */
@ManagedBean(name = "homeController")
@ViewScoped
public class HomeController implements Serializable {
    
    @EJB
    private CourseFacade courseFacade;
    
    @EJB
    private UserFacade userFacade;
    
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
    
    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
        
    }

    @PostConstruct
    public void init() {
        //TODO: hookup everything to the user from the session
        user = userFacade.find((long) 1);        
        
        if (user.isStudent())
            studentCourseList = user
                                .getStudent()
                                .getCourseList();
        
        if (user.isInstructor())
            instructorCourseList = user
                                    .getInstructor()
                                    .getCourseList();
    }
}
