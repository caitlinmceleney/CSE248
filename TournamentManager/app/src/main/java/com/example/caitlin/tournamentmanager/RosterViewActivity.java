package com.example.caitlin.tournamentmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RosterViewActivity extends AppCompatActivity {
    Button addPlayerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster_view);

        addPlayerBtn = findViewById(R.id.addPlayerBtn);
        addPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RosterViewActivity.this, AddPlayerActivity.class));
            }
        });
    }
}
