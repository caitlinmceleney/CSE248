package com.example.caitlin.tournamentmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlayerActivity extends AppCompatActivity {
    Button addToRoster;

    EditText playerAge, playerFirstName, playerLastName;

    FirebaseAuth mAuth;

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

                Player player = new Player(firstName, lastName, age);
                FirebaseDatabase.getInstance().getReference("Rosters")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .push()
                        .setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(AddPlayerActivity.this, RosterViewActivity.class));

                        }
                    }
                });

            }
        });


    }
}
