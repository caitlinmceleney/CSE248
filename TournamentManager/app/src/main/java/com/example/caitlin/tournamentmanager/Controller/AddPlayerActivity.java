package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.caitlin.tournamentmanager.Model.Player;
import com.example.caitlin.tournamentmanager.Model.TeamInfo;
import com.example.caitlin.tournamentmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPlayerActivity extends AppCompatActivity {
    private Button addToRoster;
    private EditText playerAge, playerFirstName, playerLastName;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        addToRoster = findViewById(R.id.addToRoster);
        playerAge = findViewById(R.id.playerAge);
        playerFirstName = findViewById(R.id.playerFirstName);
        playerLastName = findViewById(R.id.playerLastName);

        mAuth = FirebaseAuth.getInstance();

        addToRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = playerAge.getText().toString();
                String testAge = playerAge.getText().toString();
                String firstName = playerFirstName.getText().toString().trim();
                String lastName = playerLastName.getText().toString().trim();
                if(firstName.isEmpty()){
                    playerFirstName.setError("First name required!");
                    playerFirstName.requestFocus();
                    return;
                }
                if(lastName.isEmpty()){
                    playerLastName.setError("Last name is required!");
                    playerLastName.requestFocus();
                    return;
                }
                if(testAge.isEmpty()){
                    playerAge.setError("Age is required!");
                    playerAge.requestFocus();
                    return;
                }

                final Player player = new Player(firstName, lastName, age);
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Teams");
                DatabaseReference userRef = dbRef.child(uid);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            TeamInfo teamInfo = new TeamInfo(dataSnapshot.getValue(TeamInfo.class).getTeamName(), dataSnapshot.getValue(TeamInfo.class).getEmail(), dataSnapshot.getValue(TeamInfo.class).getTeamDivision());
                            final String teamName = teamInfo.getTeamName();
                            FirebaseDatabase.getInstance().getReference("Roster " + teamName)
                                    .child(player.getLastName())
                                    .setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(AddPlayerActivity.this, RosterViewActivity.class));

                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        return;
                    }
                });
            }
        });
    }
}
