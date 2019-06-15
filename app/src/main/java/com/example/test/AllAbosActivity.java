package com.example.test;

import android.support.annotation.NonNull;
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

public class AllAbosActivity extends AppCompatActivity {
    private RecyclerView recyclerView ;

    private AboAreasAdapter aboAreasAdapter;
    private List<DataSnapshot> mAbos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_abos);
        recyclerView = findViewById(R.id.allabos_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAbos = new ArrayList<DataSnapshot>();
        readUsers();
    }
    private void readUsers(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String userArea = dataSnapshot.child("UserArea").getValue().toString();
                DatabaseReference refAreas = FirebaseDatabase.getInstance().getReference("finalAbuEl3orifs");
                refAreas.child(userArea).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mAbos.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            mAbos.add(snapshot);
                        }
                        aboAreasAdapter = new AboAreasAdapter(getApplicationContext(), mAbos);
                        recyclerView.setAdapter(aboAreasAdapter);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

