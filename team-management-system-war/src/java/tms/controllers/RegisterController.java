/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.controllers;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import tms.boundaries.ITeamManagementFacade;
import tms.models.Instructor;
import tms.models.Student;
import tms.models.User;
/**
 * Controller to register a user
 * 
 * @author Nick, Patrice Boulet
 */
@ManagedBean
@ViewScoped
public class RegisterController implements Serializable {

    @EJB(beanName="TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;
    
    private String password;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private boolean student;
    private String studentId;
    private String programOfStudy;
    private boolean instructor;
    private String instructorId;
    private String status;
    
    /**
     * Creates a new instance of setupParametersView
     */
    public RegisterController() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProgramOfStudy() {
        return programOfStudy;
    }

    public void setProgramOfStudy(String programOfStudy) {
        this.programOfStudy = programOfStudy;
    }

    public boolean isInstructor() {
        return instructor;
    }

    public void setInstructor(boolean instructor) {
        this.instructor = instructor;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Called when the user submits the form
     * set's the information of the account inside the created user bean,
     * if student information was provided a student bean is created and filled,
     * student bean is associated to user bean and put on the db
     * if instructor information was provided an instructor bean is created and filled,
     * instructor bean is associated to user bean and put on the db
     * if none were provided the status is said and the register form is displayed
     * the password is encoded and salt is added to it
     * user bean is set to db
     * @return the view to be displayed
     */
    public String submit() {
                try {
            User account = new User();
            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setEmail(email);
            if(student){
                Student s = new Student();
                s.setId(studentId);
                s.setProgramOfStudy(programOfStudy);
                account.setStudent(s);
                
            }
            if(instructor){
                Instructor i = new Instructor();
                i.setId(instructorId);
                account.setInstructor(i);
            }
            if(!(student||instructor)){
                status="must be an instructor or a student";
                return "register";
            }
            // randomly generate salt value
            final Random r = new SecureRandom();
            byte[] salt = new byte[32];
            r.nextBytes(salt);
            String saltString = new String(salt, "UTF-8");
            // hash password using SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPass = saltString+password;
            byte[] passhash = digest.digest(saltedPass.getBytes("UTF-8"));
            account.setSalt(salt);
            account.setPassword(passhash);
            tmsFacade.createUser(account);
            status="New Account Created Fine";
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("User", account);
            return "/protected/home";
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | RuntimeException ex ) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            status="Error While Creating New Account";
            return "register";
        }
    }
}
