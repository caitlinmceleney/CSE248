package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caitlin.tournamentmanager.Model.TeamInfo;
import com.example.caitlin.tournamentmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailEntered, passwordEntered,  teamName, confirmPass;
    private FirebaseAuth mAuth;
    private Button changeView, signUp;
    private Spinner teamDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEntered = findViewById(R.id.sign_up_email);
        passwordEntered = findViewById(R.id.sign_up_password);
        teamName = findViewById(R.id.teamName);
        teamDivision = findViewById(R.id.teamDivision);
        confirmPass = findViewById(R.id.confirmPass);
        mAuth = FirebaseAuth.getInstance();

        changeView = findViewById(R.id.sign_up_change_view);
        signUp = findViewById(R.id.sign_up_button);

        String[] divisions = new String[] {"Mens Division", "Womens Division", "Mixed Division"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, divisions);
        teamDivision.setAdapter(adapter);


        signUp.setOnClickListener(this);
        changeView.setOnClickListener(this);

    }

    private void registerUser(){
        final String email = emailEntered.getText().toString().trim();
        String password = passwordEntered.getText().toString();
        String confirmPassword = confirmPass.getText().toString();
        final String name = teamName.getText().toString().trim();
        final String division = teamDivision.getSelectedItem().toString();

        if(email.isEmpty()){
            emailEntered.setError("Email is required.");
            emailEntered.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEntered.setError("Enter valid email");
            emailEntered.requestFocus();
            return;
        }

        if(password.length() < 6){
            passwordEntered.setError("Minimum password length is 6 characters.");
            passwordEntered.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordEntered.setError("Password is required.");
            passwordEntered.requestFocus();
            return;
        }
        if(password.contentEquals(confirmPassword) == false ){
            passwordEntered.setError("Passwords do not match!");
            passwordEntered.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                     TeamInfo team = new TeamInfo(name, email, division);
                    FirebaseDatabase.getInstance().getReference("Teams")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(team);
                    Intent intent = new Intent(SignUpActivity.this, LoginScreenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_SHORT);
                    }else {
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_up_button:
                registerUser();
                break;
            case R.id.sign_up_change_view:
                finish();
                startActivity(new Intent(this, LoginScreenActivity.class));
                break;
        }

    }
}
