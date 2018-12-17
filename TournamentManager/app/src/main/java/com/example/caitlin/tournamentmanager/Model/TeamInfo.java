package com.example.caitlin.tournamentmanager.Model;

import android.widget.EditText;
import android.widget.Spinner;

public class TeamInfo {
    public String teamName, email, teamDivision;

    public TeamInfo(String teamName, String email, String teamDivision) {
        this.teamName = teamName;
        this.email = email;
        this.teamDivision = teamDivision;
    }

    public TeamInfo() {
    }

    public TeamInfo(String teamName, String email){
        this.teamName = teamName;
        this.email = email;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeamDivision() {
        return teamDivision;
    }

    public void setTeamDivision(String teamDivision) {
        this.teamDivision = teamDivision;
    }
}
