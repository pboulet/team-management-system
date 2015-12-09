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
import tms.boundaries.ITeamManagementFacade;
import tms.models.Course;
import tms.models.User;
import tms.tcs.boundaries.HomeView;
import tms.tcs.models.Team;

/**
 * Controller for the Home view.
 * 
 * @author Patrice Boulet
 */
@ManagedBean(name = "homeController")
@ViewScoped
public class HomeController implements Serializable {
    
    @EJB(beanName="TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;
    
    @ManagedProperty("#{homeView}")
    private HomeView homeView;
    
    private User user;

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
    
    /**
     * Sets the visibility of the operational menu items.
     * 
     * @author Patrice Boulet
     */
    public void setMenuOptionsVisibility() {
        boolean hasBothRoles = user.isInstructor() && user.isStudent();
        
        setShowStudentOptions(hasBothRoles);
        setShowInstructorsOptions(hasBothRoles);    
    }
    
    /**
     * Sets the visibility of the students options in the right most menu.
     * 
     * @param hasBothRoles if the user is both a Student and an Instructor
     * @author Patrice Boulet
     */
    private void setShowStudentOptions(boolean hasBothRoles) {
        
        homeView.setShowStudentMenuOptions(
            homeView.getCourseListTv().getActiveIndex() == homeView.getSTUDENT_TAB_INDEX() ||
            (!hasBothRoles && user.isStudent()));
        
          
        Course selectedCourse = homeView.getSelectedStudentCourse();
        
        if ( selectedCourse != null ) {    
            boolean isAlreadyInAteam = isAlreadyInAteamFor(selectedCourse);
            boolean isLiaisonInTeam = isLiaisonInTeamFor(selectedCourse);
            
            homeView.setShowCreateTeamOption(
                    !isAlreadyInAteam && 
                    selectedCourse.hasTeamParams() && 
                    homeView.isShowStudentMenuOptions());
            homeView.setShowJoinTeamOption(!isAlreadyInAteam && 
                                homeView.isShowStudentMenuOptions());
            homeView.setShowAcceptStudentsOption(isLiaisonInTeam);
        } else {
            homeView.setShowCreateTeamOption(false);
            homeView.setShowJoinTeamOption(false);
        }
    }
    
    /**
     * Sets the visibility of the Instructor options in the right most menu.
     * 
     * @param hasBothRoles if the user is both a Student and an Instructor
     * @author Patrice Boulet
     */
    private void setShowInstructorsOptions(boolean hasBothRoles) {
       homeView.setShowInstructorMenuOptions(
            homeView.getCourseListTv().getActiveIndex() == homeView.getINSTRUCTOR_TAB_INDEX() ||
            (!hasBothRoles && user.isInstructor()));
    }
    
    /**
     * Checks if the user is already in a team of the
     * selected course.
     * 
     * @param selectedCourse the course selected by 
     * the user in the student course list
     * 
     * @return true if the Student User is already in a Team
     * for that course and false otherwise.
     * 
     * @author Patrice Boulet
     */
    private boolean isAlreadyInAteamFor(Course selectedCourse){
        for(Team t : user.getStudent().getTeamList()) {
            if (t.getCourse().equals(selectedCourse)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the session User Student is a 
     * liaison for a team in the selected course.
     * 
     * @param selectedCourse the selected course
     * 
     * @return true if the session User Student is
     * a liaison for an existing team in the selected,
     * false otherwise.
     * 
     * @author Patrice Boulet
     */
    private boolean isLiaisonInTeamFor(Course selectedCourse){
        for(Team t: user.getStudent().getLiaisonOf()){
            if (t.getCourse().equals(selectedCourse))
                return true;
        }
        return false;
    }
    
    /**
     * Gets the Team of the Student Session User
     * for the selected Course.
     * 
     * @return the Student Team for the selected Course.
     * 
     * @author Patrice Boulet
     */
    public Team getStudentTeamForSelectedCourse(){
        Course selectedCourse = homeView.getSelectedStudentCourse();
        if(user != null && selectedCourse != null){
            for (Team t : user.getStudent().getLiaisonOf()){
                if(t.getCourse().equals(selectedCourse))
                    return t;
            }
        }
        return null;
    }
    
    /**
     * Handler for when a user changes the tab containing
     * the course lists for an Instructor or a Student.
     * 
     * @param e the tab change event 
     * @author Patrice Boulet
     */
    public void onCourseListTabChange( TabChangeEvent e) {
        homeView.getCourseListTv()
                .setActiveIndex(homeView
                                    .getCourseListTv()
                                    .getChildren()
                                    .indexOf(e.getTab()));
        setMenuOptionsVisibility();
    }
    
    /**
     * Initialize the course lists for the tabs
     * based on the user logged in the session. Defaults the information
     * on the first record if now user is found in the session.
     * 
     * @author Patrice Boulet
     */
    @PostConstruct
    public void init() {
        //TODO: hookup everything to the user from the session
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Object sessionUser = session.getAttribute("User");
        
        if(sessionUser != null) 
            user=(User)sessionUser; 
        else
            user = tmsFacade.getUser((long) 1); // default workaround in case auth fails 
        
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
