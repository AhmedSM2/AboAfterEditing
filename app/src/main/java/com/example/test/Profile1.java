package com.example.test;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile1 extends AppCompatActivity {

    TextView profile_userName,profile_email,profile_phone,profile_bio;
    Button profile_follow_btn;
    CircleImageView profile_img;
    private String CurrentUserId ;
    private DatabaseReference userRef , calcRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        profile_userName = (TextView)findViewById(R.id.profile_userName);
        profile_email = (TextView)findViewById(R.id.profile_email);
        profile_phone = (TextView)findViewById(R.id.profile_phone);
        profile_bio = findViewById(R.id.sliver_bio);
//        profile_followers = (TextView)findViewById(R.id.profile_followers);
//        profile_follow_btn = (Button)findViewById(R.id.profile_follow_btn);
        profile_img = findViewById(R.id.profile_img);
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();//to check user authentication loggedin or not

        CurrentUserId = mAuth.getCurrentUser().getUid();
        userRef.child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_class user = dataSnapshot.getValue(user_class.class);
                profile_email.setText(user.getEmail());
                profile_phone.setText(dataSnapshot.child("phone").getValue().toString());
                profile_userName.setText(dataSnapshot.child("username").getValue().toString());
                profile_bio.setText(dataSnapshot.child("Bio").getValue().toString());
//                profile_followers.setText(dataSnapshot.child("followersCount").getValue().toString());
                    Glide.with(getApplicationContext()).load(user.getImageUrl()).into(profile_img);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}