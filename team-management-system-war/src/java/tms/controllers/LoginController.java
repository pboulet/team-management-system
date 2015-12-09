package tms.controllers;


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
import tms.boundaries.ITeamManagementFacade;
import tms.models.Instructor;
import tms.models.Student;
import tms.models.User;

/**
 *
 * Controller to take care of the login page and the logout function
 * 
 * @author Nick, Patrice Boulet
 */
@ManagedBean
@RequestScoped
public class LoginController {
    private String userId;
    private String password;
    private String status;
    
    @EJB(beanName="TeamManagementFacade")
    private ITeamManagementFacade tmsFacade;
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
    /**
     * Goes to find the account associated to the id and password entered
     * if it's an employee id performs a query in the instructor db
     * if it's a student id it performs a query in the student db
     * Then it get's the associated user, confirms the password 
     * and put's the user in the session.
     * @return Page to navigate to.
     */
    public String login() {
        User account=null;
        if(userId.charAt(0)=='e'){
            Instructor instructor = tmsFacade.getInstructor(userId);
            if(instructor!=null){
                account = instructor.getUser();
            }
        }
        else{
            Student student = tmsFacade.getStudent(userId);
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
                     return "/protected/home?faces-redirect=true";
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
    /**
     * Called by the logout button
     * Clears the session to remove current user
     * @return navigate to login page 
     */
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "/faces/login.xhtml?faces-redirect=true";
    }
}
