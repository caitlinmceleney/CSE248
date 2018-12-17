package com.example.caitlin.tournamentmanager.Controller;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caitlin.tournamentmanager.Model.Message;
import com.example.caitlin.tournamentmanager.Model.Tournament;
import com.example.caitlin.tournamentmanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Identity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class TournamentChatActivity extends AppCompatActivity {
    private ImageButton sendMessageBtn;
    private EditText messageInput;
    private ScrollView scrollView;
    private TextView displayMessages;

    private String currentUserID, currentTeamName, currentDate, currentTime, currentTournamentName;
    private String messageDate, messageText, messageSender, messageTime;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef, tournamentNameRef, tournamentMessageKeyRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_chat);

        currentTournamentName = getIntent().getExtras().get("tournamentName").toString();

        sendMessageBtn = findViewById(R.id.sendMessageBtn);
        messageInput = findViewById(R.id.inputMessage);
        scrollView = findViewById(R.id.chatScrollView);
        displayMessages = findViewById(R.id.tournamentChatTextDisplay);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Teams");
        tournamentNameRef = FirebaseDatabase.getInstance().getReference().child("Tournaments").child(currentTournamentName);

        getUserInfo();

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessageInfoToDatabase();
                messageInput.setText("");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        displayMessages = findViewById(R.id.tournamentChatTextDisplay);
        currentTournamentName = getIntent().getExtras().get("tournamentName").toString();
        tournamentNameRef = FirebaseDatabase.getInstance().getReference().child("Tournaments").child(currentTournamentName);

        tournamentNameRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    displayMessage(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    displayMessage(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void displayMessage(DataSnapshot dataSnapshot) {
        Iterator iterator = dataSnapshot.getChildren().iterator();

           while(iterator.hasNext()){
               messageDate = (String)((DataSnapshot)iterator.next()).getValue();
               messageText = (String)((DataSnapshot)iterator.next()).getValue();
               messageSender = (String)((DataSnapshot)iterator.next()).getValue();
               messageTime = (String)((DataSnapshot)iterator.next()).getValue();


               //isplayMessages.append("\n");
                displayMessages.append(messageSender + ": \n          " + messageText + "\n" + messageTime + "                             " + messageDate + "\n\n\n");
                //displayMessages.append("\n");
               scrollView.fullScroll(ScrollView.FOCUS_DOWN);
           }
        }


    private void sendMessageInfoToDatabase() {
        String message = messageInput.getText().toString();
        String messageKeyRef = tournamentNameRef.push().getKey();
        if(TextUtils.isEmpty(message)){
            return;
        }
        else{
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
            currentDate = currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
            currentTime = currentTimeFormat.format(calForTime.getTime());

            HashMap<String, Object> messageKey = new HashMap<>();
            tournamentNameRef.updateChildren(messageKey);

            tournamentMessageKeyRef = tournamentNameRef.child(messageKeyRef);
            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("teamName", currentTeamName);
            messageInfoMap.put("message", message);
            messageInfoMap.put("date", currentDate);
            messageInfoMap.put("time", currentTime);
            tournamentMessageKeyRef.updateChildren(messageInfoMap);

        }
    }

    private void getUserInfo() {
        userRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    currentTeamName = dataSnapshot.child("teamName").getValue().toString();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
