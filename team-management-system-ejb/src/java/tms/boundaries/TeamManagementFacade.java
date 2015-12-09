package tms.boundaries;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import tms.models.Course;
import tms.models.Instructor;
import tms.models.Student;
import tms.models.User;
import tms.tcs.boundaries.ITeamCreationFacade;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;
import tms.tcs.models.TeamParameters;

/**
 *
 * @author User
 */
@Stateless
public class TeamManagementFacade implements ITeamManagementFacade {
    
    @Inject
    private StudentFacade studentFacade;
    
    @Inject
    private InstructorFacade instructorFacade;
    
    @Inject
    private UserFacade userFacade;

    @Inject
    private CourseFacade courseFacade;
    
    @EJB(beanName="TeamCreationFacade")
    private ITeamCreationFacade tcsFacade;
    
    @Override
    public User getUser(Long id){
        return userFacade.find(id);
    }
    
    @Override
    public Instructor getInstructor(String id) {
        return instructorFacade.find(id);
    }
    
    @Override
    public Student getStudent(String id){
        return studentFacade.find(id);
    }
    
    @Override
    public void editStudent(Student s){
        studentFacade.edit(s);
    }
    
    @Override
    public void createStudent(Student s){
        studentFacade.create(s);
    }
    
    @Override
    public void createInstructor(Instructor i){
        instructorFacade.create(i);
    }
    
    @Override
    public void createUser(User u){
        userFacade.create(u);
    }
    
    @Override
    public Team getTeam(Long id){
       return tcsFacade.getTeam(id);
    }
    
    @Override
    public void editTeam(Team t){
        tcsFacade.editTeam(t);
    }
    
    @Override
    public void editJoinRequest(JoinRequest j){
        tcsFacade.editJoinRequest(j);
    }
    
    @Override
    public Course getCourse(Long id){
        return courseFacade.find(id);
    }
    
    @Override
    public void editCourse(Course c){
        courseFacade.edit(c);
    }
    
    @Override
    public void createTeam(Team t){
        tcsFacade.createTeam(t);
    }
    
    @Override
    public void createJoinRequest(JoinRequest j){
        tcsFacade.createJoinRequest(j);
    }
    
    @Override
    public TeamParameters getTeamParameters(Long id){
        return tcsFacade.getTeamParameters(id);
    }
    
    @Override
    public void createTeamParameters(TeamParameters p){
        tcsFacade.createTeamParameters(p);
    }
    
    @Override
    public void editTeamParameters(TeamParameters p){
        tcsFacade.editTeamParameters(p);
    }
}
