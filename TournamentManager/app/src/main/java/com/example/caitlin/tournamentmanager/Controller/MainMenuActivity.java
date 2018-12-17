package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.caitlin.tournamentmanager.Model.TeamInfo;
import com.example.caitlin.tournamentmanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MainMenuActivity extends AppCompatActivity {
    private Button ruleBookBtn, logoutBtn, teamViewBtn, createTournamentBtn, myTournamentsBtn, editTeamInfoBtn, viewTeamsBtn;
    private TextView teamName, teamDivision;
    private DatabaseReference dbRef;
    private String uid;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ruleBookBtn = findViewById(R.id.rulebookBtn);
        ruleBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, RuleBookViewActivity.class));
            }
        });

        logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(MainMenuActivity.this, LoginScreenActivity.class) );
            }
        });

        teamViewBtn = findViewById(R.id.teamViewBtn);
        teamViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, RosterViewActivity.class));
            }
        });

        createTournamentBtn = findViewById(R.id.createTournamentBtn);
        createTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, CreateTournamentActivity.class));
            }
        });

        myTournamentsBtn = findViewById(R.id.myTournamentsBtn);
        myTournamentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, MyTournamentsActivity.class));
            }
        });

        editTeamInfoBtn = findViewById(R.id.editTeamInfoBtn);
        editTeamInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, EditMyTeamActivity.class));
            }
        });

        viewTeamsBtn = findViewById(R.id.viewTeamsBtn);
        viewTeamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, ViewAllTeamsActivity.class));
            }
        });

        teamName = findViewById(R.id.teamNameView);
        teamDivision = findViewById(R.id.teamDivisionView);
        uid = FirebaseAuth.getInstance().getUid();

        dbRef = FirebaseDatabase.getInstance().getReference("Teams");
        DatabaseReference userRef = dbRef.child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    TeamInfo teamInfo = new TeamInfo(dataSnapshot.getValue(TeamInfo.class).getTeamName(), dataSnapshot.getValue(TeamInfo.class).getEmail(), dataSnapshot.getValue(TeamInfo.class).getTeamDivision());
                    teamName.append(teamInfo.getTeamName());
                    teamDivision.append(teamInfo.getTeamDivision());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
