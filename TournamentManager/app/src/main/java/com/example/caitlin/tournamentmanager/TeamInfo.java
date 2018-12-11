package com.example.caitlin.tournamentmanager;

import android.widget.EditText;
import android.widget.Spinner;

public class TeamInfo {
    public String teamName, email, teamDivision;

    public TeamInfo(String teamName, String email, String teamDivision) {
        this.teamName = teamName;
        this.email = email;
        this.teamDivision = teamDivision;
    }

    public TeamInfo(String teamName, String email){
        this.teamName = teamName;
        this.email = email;
    }

}
