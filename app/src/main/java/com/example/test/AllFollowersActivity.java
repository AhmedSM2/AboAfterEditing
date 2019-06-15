package com.example.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllFollowersActivity extends AppCompatActivity {
    private RecyclerView recyclerView ;

    private AllFollowersAdapter allFollowersAdapter;
    private List<DataSnapshot> mfollowers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_followers);
        recyclerView = findViewById(R.id.allfollowers_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mfollowers = new ArrayList<DataSnapshot>();
        readUsers();

    }
    private void readUsers(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(firebaseUser.getUid()).child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mfollowers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // assert user != null;
                    //assert firebaseUser !=null;

                    mfollowers.add(snapshot);

                    /*if ( user.getIsblocked().equals("false")) {
                        mComplain.add(user);
                    }*/
                    //mUsers.add(user);
                }
                allFollowersAdapter = new AllFollowersAdapter(getApplicationContext(), mfollowers);
                recyclerView.setAdapter(allFollowersAdapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}