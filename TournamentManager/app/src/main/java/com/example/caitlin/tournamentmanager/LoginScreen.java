package com.example.caitlin.tournamentmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        mAuth = FirebaseAuth.getInstance();

        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);

        findViewById(R.id.signUp).setOnClickListener(this);
        findViewById(R.id.log_in_button).setOnClickListener(this);
    }

    public void userLogin() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString();
        //check that field is filled
        if(email.isEmpty()){
            emailField.setError("E-mail is required!");
            emailField.requestFocus();
            return;
        }
        //Check that format is correct
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailField.setError("Please enter a valid e-mail");
            emailField.requestFocus();
            return;
        }
        //check that field is filled
        if(password.isEmpty()){
            passwordField.setError("Password is required!");
            passwordField.requestFocus();
        }
        //check that it is above requested character length
        if(password.length() < 6){
            passwordField.setError("Should be greater than 6 characters!");
            passwordField.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    finish();
                    //Intent intent = new Intent(MainActivity.this, <Page to go to)
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(intent);
                } else{
                     Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT);

                    }
                }
            });


    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null) {
            finish();
            //startActivity(new Intent(this, Menu page that I still need to make))
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.signUp:
                finish();
                startActivity(new Intent(this, SignUp.class));
                break;

            case R.id.log_in_button:
                userLogin();
                break;
            }
        }
    }
