package com.example.caitlin.tournamentmanager.Model;

import java.util.ArrayList;
import java.util.Date;

public class Tournament {
    private String city;
    private String state;
    private ArrayList<TeamInfo> teams;
    private Date date;

    public Tournament(String city, String state, ArrayList<TeamInfo> teams, Date date) {
        this.city = city;
        this.state = state;
        this.teams = teams;
        this.date = date;
    }

    public Tournament() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<TeamInfo> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<TeamInfo> teams) {
        this.teams = teams;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
