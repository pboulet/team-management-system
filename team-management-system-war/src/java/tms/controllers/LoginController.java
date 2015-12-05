/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import tms.boundaries.InstructorFacade;
import tms.boundaries.StudentFacade;
import tms.boundaries.UserFacade;
import tms.models.Instructor;
import tms.models.Student;
import tms.models.User;

/**
 *
 * @author Nick
 */
@ManagedBean
@RequestScoped
public class LoginController {
    private String userId;
    private String password;
    private String status;
    @EJB
    private StudentFacade studentFacade;
    @EJB
    private InstructorFacade instructorFacade;
    /**
     * Creates a new instance of LoginBean
     */
    public LoginController() {
    }

    /**
     * @return the userId
     */
    public String getUsername() {
        return userId;
    }

    /**
     * @param id the userId to set
     */
    public void setUsername(String userId) {
        this.userId = userId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    public String login() {
        User account=null;
        if(userId.charAt(0)=='e'){
            Instructor instructor = instructorFacade.find(userId);
            if(instructor!=null){
                account = instructor.getUser();
            }
        }
        else{
            Student student = studentFacade.find(userId);
            if(student!=null){
                account = student.getUser();
            }
        }
         if (account != null) {
             try {
                 // check password
                 byte[] salt = account.getSalt();
                 String saltString = new String(salt, "UTF-8");
                 String checkPass = saltString+password;
                 MessageDigest digest = MessageDigest.getInstance("SHA-256");
                 byte[] checkPassHash = digest.digest(checkPass.getBytes("UTF-8"));
                 if (Arrays.equals(checkPassHash, account.getPassword())) {
                     //login ok - set user in session context
                     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                     session.setAttribute("User", account);
                     return "/protected/home";
                 } else {
                    status="Invalid Login, Please Try again"; 
                 }
             } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                 Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
             }
         } else {
             status="Invalid Login, Please Try again";
         }
         return "login";
    }
    
    public String logout() {
        // invalidate session to remove User
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        // navigate to index - see faces-config.xml for navigation rules
        return "login";
    }
}
