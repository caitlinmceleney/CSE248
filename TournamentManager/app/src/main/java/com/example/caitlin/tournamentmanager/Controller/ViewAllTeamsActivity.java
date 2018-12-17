package com.example.caitlin.tournamentmanager.Controller;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.caitlin.tournamentmanager.Model.TeamInfo;
import com.example.caitlin.tournamentmanager.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ViewAllTeamsActivity extends AppCompatActivity {
    //private RecyclerView findTeamsRecycler;
    private DatabaseReference teamRef;
    private ListView teamView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> teamList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_teams);
        //findTeamsRecycler = findViewById(R.id.findTeamRecycler);
        //findTeamsRecycler.setLayoutManager(new LinearLayoutManager(this));
        teamRef = FirebaseDatabase.getInstance().getReference().child("Teams");
        teamView = findViewById(R.id.teamView);

        initializeTeamList();
        retrieveAndDisplayTeams();


        teamView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String currentTeam = parent.getItemAtPosition(position).toString();
                final DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("Teams");

                newRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if(dataSnapshot.hasChildren()){

                            Iterator iterator = dataSnapshot.getChildren().iterator();
                            String key = null;
                            String teamUid = null;//i know some of this is useless, but i just got it to work and at this point i'm too afraid to change it
                            while(iterator.hasNext()){
                                key = ((DataSnapshot)iterator.next()).child("teamName").toString();
                                teamUid = iterator.toString();
                                if(key == currentTeam){
                                    break;
                                }
                            }
                            String newParent = newRef.orderByChild("teamName").equalTo(currentTeam).toString();
                            Intent teamViewIntent = new Intent(ViewAllTeamsActivity.this, OtherTeamRosterView.class);
                            teamViewIntent.putExtra("teamUid",currentTeam);
                            startActivity(teamViewIntent);

                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
        });

    }



    private void retrieveAndDisplayTeams() {
        teamRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Set<String> set = new HashSet<>();
                final Iterator iterator = dataSnapshot.getChildren().iterator();
                String uid = FirebaseAuth.getInstance().getUid();
                DatabaseReference dbNameRef = FirebaseDatabase.getInstance().getReference("Teams").child(uid);
                dbNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            TeamInfo teamInfo = new TeamInfo(dataSnapshot.getValue(TeamInfo.class).getTeamName(), dataSnapshot.getValue(TeamInfo.class).getEmail(), dataSnapshot.getValue(TeamInfo.class).getTeamDivision());
                            while(iterator.hasNext()){
                                String teamNameValue =((DataSnapshot)iterator.next()).child("teamName").getValue().toString();
                                if(teamInfo.getTeamName().contentEquals(teamNameValue) == false){
                                    set.add(teamNameValue);
                                }

                            }
                            teamList.clear();
                            teamList.addAll(set);
                            arrayAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeTeamList() {
        teamView = (ListView) findViewById(R.id.teamView);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, teamList);
        teamView.setAdapter(arrayAdapter);
    }

}
