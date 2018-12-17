package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.caitlin.tournamentmanager.Model.TeamInfo;
import com.example.caitlin.tournamentmanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RemovePlayerActivity extends AppCompatActivity {
    EditText lastName;
    Button cancel, remove;
    DatabaseReference dbRef;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_player);
        lastName = findViewById(R.id.enterLastName);
        cancel = findViewById(R.id.cancelBtn);
        remove = findViewById(R.id.removePlayerBtn);
        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getUid();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RemovePlayerActivity.this, RosterViewActivity.class));
            }
        });


        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference("Teams");
                final DatabaseReference userRef = dbRef.child(uid);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            TeamInfo teamInfo = new TeamInfo(dataSnapshot.getValue(TeamInfo.class).getTeamName(), dataSnapshot.getValue(TeamInfo.class).getEmail(), dataSnapshot.getValue(TeamInfo.class).getTeamDivision());
                            final String teamName = teamInfo.getTeamName();

                            FirebaseDatabase.getInstance().getReference("Roster " + teamName).child(lastName.getText().toString()).removeValue();
                            startActivity(new Intent(RemovePlayerActivity.this, RosterViewActivity.class));
                        }
                        else{
                            lastName.setError("Player does not exist");
                            lastName.requestFocus();
                            return;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
});}}
