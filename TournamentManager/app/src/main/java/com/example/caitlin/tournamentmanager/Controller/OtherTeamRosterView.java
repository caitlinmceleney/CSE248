package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.caitlin.tournamentmanager.Model.Player;
import com.example.caitlin.tournamentmanager.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;

public class OtherTeamRosterView extends AppCompatActivity {
    TextView teamRoster, otherTeamName;
    String currentUid;
    private DatabaseReference dbRef;
    String age, fName, lName;
    Button returnToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_team_roster_view);
        returnToMain = findViewById(R.id.returnToMain);

        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherTeamRosterView.this, MainMenuActivity.class));
            }
        });
    }

    protected void onStart() {

        super.onStart();
        otherTeamName = findViewById(R.id.otherTeamName);
        teamRoster = findViewById(R.id.teamRoster);
        currentUid = getIntent().getExtras().get("teamUid").toString();
        otherTeamName.append(currentUid);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Roster " + currentUid);
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    displayTeam(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    displayTeam(dataSnapshot);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayTeam(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

        while(iterator.hasNext()){
            age = (String)((DataSnapshot)iterator.next()).getValue();
            fName = (String)((DataSnapshot)iterator.next()).getValue();
            lName = (String)((DataSnapshot)iterator.next()).getValue();

            Player player = new Player(fName, lName, age);
            teamRoster.append(player.toString() + "\n");


        }
    }
}
