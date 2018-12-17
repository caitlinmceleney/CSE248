package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.caitlin.tournamentmanager.Model.Player;
import com.example.caitlin.tournamentmanager.Model.TeamInfo;
import com.example.caitlin.tournamentmanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RosterViewActivity extends AppCompatActivity {
    private Button addPlayerBtn, returnToMainBtn, removePlayerBtn;
    private FirebaseAuth mAuth;
    private TextView playerName;
    private DatabaseReference databaseReference;
    private String uid;
    private ArrayList<Player> playerList = new ArrayList<Player>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_view);

        mAuth = FirebaseAuth.getInstance();
        playerName = findViewById(R.id.playerNameRosterView);
        uid = FirebaseAuth.getInstance().getUid();
        addPlayerBtn = findViewById(R.id.addPlayerBtn);
        returnToMainBtn = findViewById(R.id.returnToMain);
        removePlayerBtn = findViewById(R.id.removePlayerBtn);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Teams");
        DatabaseReference userRef = dbRef.child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    TeamInfo teamInfo = new TeamInfo(dataSnapshot.getValue(TeamInfo.class).getTeamName(), dataSnapshot.getValue(TeamInfo.class).getEmail(), dataSnapshot.getValue(TeamInfo.class).getTeamDivision());
                    String teamName = teamInfo.getTeamName();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Roster " + teamName);
                    databaseReference.addListenerForSingleValueEvent(valueEventListener);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        returnToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RosterViewActivity.this, MainMenuActivity.class));
            }
        });

        removePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RosterViewActivity.this, RemovePlayerActivity.class));
            }
        });


        addPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RosterViewActivity.this, AddPlayerActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() == null) {//user not logged in
            finish();
            startActivity(new Intent(this, LoginScreenActivity.class));

        }
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Player player = new Player(snapshot.getValue(Player.class).getFirstName(), snapshot.getValue(Player.class).getLastName(), snapshot.getValue(Player.class).getAge());
                    playerList.add(player);
                    playerName.append(player.toString() + "\n");
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


}
