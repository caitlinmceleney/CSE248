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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    //add more fields as necessary
    EditText emailEntered;
    EditText passwordEntered;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEntered = (EditText) findViewById(R.id.sign_up_email);
        passwordEntered = (EditText) findViewById(R.id.sign_up_password);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.sign_up_button).setOnClickListener(this);
        findViewById(R.id.login_change_view).setOnClickListener(this);

    }

    private void registerUser(){
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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //whet registration completed - method called
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "User Subscription is successful!", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(SignUp.this, LoginScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //clear open activities
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
                startActivity(new Intent(this, LoginScreen.class));
                break;
        }

    }
}
