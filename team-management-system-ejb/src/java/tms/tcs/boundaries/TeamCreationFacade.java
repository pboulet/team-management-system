/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tms.tcs.boundaries;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import tms.models.Course;
import tms.models.Student;
import tms.tcs.models.JoinRequest;
import tms.tcs.models.Team;
import tms.tcs.models.TeamParameters;

/**
 *
 * @author User
 */
@Stateless
public class TeamCreationFacade implements ITeamCreationFacade {

    @EJB
    private TeamFacade teamFacade;
    @EJB
    private JoinRequestFacade joinRequestFacade;
    @EJB
    private TeamParametersFacade teamParametersFacade;

    @Override
    public Team getTeam(Long id) {
        return teamFacade.find(id);
    }

    @Override
    public void editTeam(Team t) {
        teamFacade.edit(t);
    }

    @Override
    public void createTeam(Team t) {
        teamFacade.create(t);
    }

    @Override
    public void editJoinRequest(JoinRequest j) {
        joinRequestFacade.edit(j);
    }

    @Override
    public void createJoinRequest(JoinRequest j) {
        joinRequestFacade.create(j);
    }

    @Override
    public void createTeamParameters(TeamParameters p) {
        teamParametersFacade.create(p);
    }

    @Override
    public void editTeamParameters(TeamParameters p) {
        teamParametersFacade.edit(p);
    }

    @Override
    public TeamParameters getTeamParameters(Long id) {
        return teamParametersFacade.find(id);
    }

    @Override
    public boolean joinTeams(List<Team> teams, Student s) {
        try {
            for (Team t : teams) {
                JoinRequest j = new JoinRequest();
                j.setAccepted(false);
                j.setStudent(s);
                j.setTeam(t);
                createJoinRequest(j);
                s.getJoinRequests().add(j);
                t.getJoinRequests().add(j);
                editTeam(t);
            }

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Team createTeam(String teamName, Course course, Student currentStudent, List<String> selectedStudentList) {
        Team team = new Team();
        team.setName(teamName);
        team.setCourse(course);
        team.setCreationDate(new Timestamp(new Date().getTime()));
        team.setLiaison(currentStudent);
        team.setStudentList(new LinkedList<Student>());
        createTeam(team);
        return team;
    }
    public List<Team> getIncompleteTeamsToJoin(Course c, Student s) {
        List<Team> teamList = new LinkedList<>();
        boolean toAdd;
        int maxStudent = c.getTeamParams().getMaxNumStudents();
        for (Team t : c.getTeams()) {
            toAdd = true;
            for (JoinRequest j : t.getJoinRequests()) {
                if (j.getStudent().equals(s)) {
                    toAdd = false;
                }
            }       
            if (t.getStudentList().size() < maxStudent && toAdd) {
                teamList.add(t);
            }
        }
        return teamList;
    }

    @Override
    @SuppressWarnings("null")
    public Course setupTeamParameters(TeamParameters teamPara, Date deadline, Integer maxStudent, Integer minStudent, Course course) {
        boolean newTeamPara = (teamPara == null);
        if (newTeamPara) {
            teamPara = new TeamParameters();
        }
        teamPara.setCreationDeadline(new Timestamp(deadline.getTime()));
        teamPara.setMaxNumStudents(maxStudent);
        teamPara.setMinNumStudents(minStudent);
        if (newTeamPara) {
            createTeamParameters(teamPara);
            course.setTeamParams(teamPara);
        } else {
            editTeamParameters(teamPara);
        }
        return course;
    }
}
