package com.example.caitlin.tournamentmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    //add more fields as necessary
    EditText emailEntered;
    EditText passwordEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEntered = (EditText) findViewById(R.id.sign_up_email);
        passwordEntered = (EditText) findViewById(R.id.sign_up_password);

    }

    private void regiterUser(){
        String email = emailEntered.getText().toString().trim();
        String password = passwordEntered.getText().toString();

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

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_up_button:
                registerUser();
                break;
            case R.id.sign_up_change_view:
                startActivity(new Intent(this, LoginScreen.class));
        }

    }
}
