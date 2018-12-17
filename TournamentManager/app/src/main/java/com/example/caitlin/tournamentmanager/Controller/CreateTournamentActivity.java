package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caitlin.tournamentmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTournamentActivity extends AppCompatActivity {
    private EditText tournamentName;
    private Button createTournamentBtn, cancelCreateTournamentBtn;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tournament);
        tournamentName = findViewById(R.id.tournamentNameField);
        createTournamentBtn = findViewById(R.id.createTournamentBtn);
        cancelCreateTournamentBtn = findViewById(R.id.cancelCreateTournamentBtn);
        mDatabase = FirebaseDatabase.getInstance();

        createTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tournyName = tournamentName.getText().toString().trim();
                if(tournyName.isEmpty()){
                    tournamentName.setError("Tournament Name Required!");
                    tournamentName.requestFocus();
                    return;
                }
                else{
                    CreateNewTournament(tournyName);
                }
            }
        });


        cancelCreateTournamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateTournamentActivity.this, MainMenuActivity.class));
            }
        });
    }

    private void CreateNewTournament(String newTournamentName) {
        mDatabase.getReference("Tournaments").child(newTournamentName).setValue("")
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        startActivity(new Intent(CreateTournamentActivity.this, MainMenuActivity.class));
                    }
                    else{
                        return;
                    }
                    }
            });
    }
}
