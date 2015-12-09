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
import tms.models.Course;

/**
 * View Model for the Home view.
 * 
 * @author Patrice Boulet
 */
@Named(value = "homeView")
@ViewScoped
public class HomeView implements Serializable {

    private final int STUDENT_TAB_INDEX = 0;
    private final int INSTRUCTOR_TAB_INDEX = 1;
    
    private Course selectedStudentCourse;  
    private Course selectedInstructorCourse;
    
    private TabView courseListTv;
    
    private boolean showStudentMenuOptions;
    private boolean showInstructorMenuOptions;
    
    private boolean showCreateTeamOption;
    private boolean showJoinTeamOption;
    private boolean showAcceptStudentsOption;
    
    /**
     * Creates a new instance of HomeView
     */
    public HomeView() {
    }

    public boolean isShowJoinTeamOption() {
        return showJoinTeamOption;
    }

    public void setShowJoinTeamOption(boolean showJoinTeamOption) {
        this.showJoinTeamOption = showJoinTeamOption;
    }
    
    public boolean isShowAcceptStudentsOption() {
        return showAcceptStudentsOption;
    }

    public void setShowAcceptStudentsOption(boolean showAcceptStudentsOption) {
        this.showAcceptStudentsOption = showAcceptStudentsOption;
    }
    
    public boolean isShowCreateTeamOption() {
        return showCreateTeamOption;
    }

    public void setShowCreateTeamOption(boolean showCreateTeamOption) {
        this.showCreateTeamOption = showCreateTeamOption;
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
    
    public boolean isShowStudentMenuOptions() {
        return showStudentMenuOptions;
    }

    public void setShowStudentMenuOptions(boolean showStudentMenuOptions) {
        this.showStudentMenuOptions = showStudentMenuOptions;
    }

    public boolean isShowInstructorMenuOptions() {
        return showInstructorMenuOptions;
    }

    public void setShowInstructorMenuOptions(boolean showInstructorMenuOptions) {
        this.showInstructorMenuOptions = showInstructorMenuOptions;
    }

    public int getSTUDENT_TAB_INDEX() {
        return STUDENT_TAB_INDEX;
    }

    public int getINSTRUCTOR_TAB_INDEX() {
        return INSTRUCTOR_TAB_INDEX;
    }
}
