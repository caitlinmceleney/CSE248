package com.example.caitlin.tournamentmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText emailField, passwordField;
    //ProgressBar progressBar;
    Button changeViewBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.login_email);
        passwordField = findViewById(R.id.login_password);
        //Button changeView = (Button)findViewById(R.id.login_change_view);
        //changeView.setOnClickListener(this);

        changeViewBtn = findViewById(R.id.login_change_view);
        loginBtn = findViewById(R.id.login_button);
        changeViewBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
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
                    Intent intent = new Intent(LoginScreen.this, MainMenu.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
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
            startActivity(new Intent(this, MainMenu.class));
        }
    }


    //controls when things are clicked on the login screen
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.login_change_view:
                //finish();
                startActivity(new Intent(LoginScreen.this, SignUp.class));
                break;

            case R.id.login_button:
                userLogin();
                break;
            }
        }
    }
