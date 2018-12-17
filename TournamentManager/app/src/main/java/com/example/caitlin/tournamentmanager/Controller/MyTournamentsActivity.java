package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.caitlin.tournamentmanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyTournamentsActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> tournamentList = new ArrayList<>();
    private DatabaseReference tournamentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tournaments);
        tournamentRef = FirebaseDatabase.getInstance().getReference("Tournaments");

        initializeTournamentList();
        retrieveAndDisplayTournaments();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentTournamentName = parent.getItemAtPosition(position).toString();

                Intent tournamentViewIntent = new Intent(MyTournamentsActivity.this, TournamentChatActivity.class);
                tournamentViewIntent.putExtra("tournamentName", currentTournamentName);
                startActivity(tournamentViewIntent);
            }
        });
    }

    private void retrieveAndDisplayTournaments() {
        tournamentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while(iterator.hasNext()){
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }

                tournamentList.clear();
                tournamentList.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeTournamentList() {
        listView = (ListView) findViewById(R.id.tournamenView);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, tournamentList);
        listView.setAdapter(arrayAdapter);
    }
}
