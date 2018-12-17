package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.caitlin.tournamentmanager.Model.TeamInfo;
import com.example.caitlin.tournamentmanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditMyTeamActivity extends AppCompatActivity {
    private Button update, cancel;
    private EditText teamNameUpdate;
    private Spinner divisionUpdate;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_team);
        update = findViewById(R.id.updateInfoBtn);
        cancel = findViewById(R.id.cancelBtn);

        teamNameUpdate = findViewById(R.id.updateTeamName);
        divisionUpdate = findViewById(R.id.updateTeamDivision);
        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getUid();

        String[] divisions = new String[] {"Mens Division", "Womens Division", "Mixed Division"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, divisions);
        divisionUpdate.setAdapter(adapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditMyTeamActivity.this, MainMenuActivity.class));
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference("Teams");
                final DatabaseReference userRef = dbRef.child(uid);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            TeamInfo teamInfo = new TeamInfo(dataSnapshot.getValue(TeamInfo.class).getTeamName(), dataSnapshot.getValue(TeamInfo.class).getEmail(), dataSnapshot.getValue(TeamInfo.class).getTeamDivision());
                            TeamInfo newTeamInfo = new TeamInfo(teamNameUpdate.getText().toString(), teamInfo.getEmail(), divisionUpdate.getSelectedItem().toString());
                            userRef.setValue(newTeamInfo);
                            startActivity(new Intent(EditMyTeamActivity.this, MainMenuActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
