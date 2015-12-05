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
import tms.models.Course;

/**
 *
 * @author User
 */
@ManagedBean(name = "homeController")
@ViewScoped
public class HomeController implements Serializable {
    
    @EJB
    private CourseFacade courseFacade;

    // mock until I know how to get the session user
    private List<Course> studentCourseList; 
    
    private List<Course> instructorCourseList;
    
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
        studentCourseList = courseFacade.findAll();
    }

    
//    public void onCoureSelect(SelectEvent event) {
//        Long selectedCourseId = ((Course) event.getObject()).getId();
//        
//        Course tmpCourse = new Course();
//        tmpCourse.setId(selectedCourseId);
//        
//        selectedCourse = studentCourseList.stream().filter(
//            course -> course.equals(tmpCourse)
//        ).findFirst().get();
//    }
}
