package com.example.caitlin.tournamentmanager.Teams;

import java.util.ArrayList;

public class Team {
    private String teamName;
    private String division;
    private String region;
    private com.example.caitlin.tournamentmanager.PlayerInfo.State state;
    private ArrayList<com.example.caitlin.tournamentmanager.PlayerInfo.Player> players;

    public Team(String name) {
        this.teamName = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void changeTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public com.example.caitlin.tournamentmanager.PlayerInfo.State getState() {
        return state;
    }

    public void setState(com.example.caitlin.tournamentmanager.PlayerInfo.State state) {
        this.state = state;
    }

    public ArrayList<com.example.caitlin.tournamentmanager.PlayerInfo.Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<com.example.caitlin.tournamentmanager.PlayerInfo.Player> players) {
        this.players = players;
    }
}