/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.boundaries;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;
import tms.models.Course;

/**
 *
 * @author User
 */
@Named(value = "homeView")
@ViewScoped
public class HomeView implements Serializable {

    private final int STUDENT_TAB_INDEX = 0;
    private final int INSTRUCTOR_TAB_INDEX = 1;
    
    private Course selectedStudentCourse;  
    
    private Course selectedInstructorCourse;
    
    private TabView courseListTv;
    
    /**
     * Creates a new instance of HomeView
     */
    public HomeView() {
    }

    public TabView getCourseListTv() {
        return courseListTv;
    }

    public void setCourseListTv(TabView courseListTv) {
        this.courseListTv = courseListTv;
    }
    
    public Course getSelectedStudentCourse() {
        return selectedStudentCourse;
    }

    public void setSelectedStudentCourse(Course selectedStudentCourse) {
        this.selectedStudentCourse = selectedStudentCourse;
    }

    public Course getSelectedInstructorCourse() {
        return selectedInstructorCourse;
    }

    public void setSelectedInstructorCourse(Course selectedInstructorCourse) {
        this.selectedInstructorCourse = selectedInstructorCourse;
    }

    public int getSTUDENT_TAB_INDEX() {
        return STUDENT_TAB_INDEX;
    }

    public int getINSTRUCTOR_TAB_INDEX() {
        return INSTRUCTOR_TAB_INDEX;
    }
    
    public void onCourseListTabChange( TabChangeEvent e) {
        courseListTv.setActiveIndex(courseListTv.getChildren().indexOf(e.getTab()));
    }
}
