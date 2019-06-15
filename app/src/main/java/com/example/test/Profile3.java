package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile3 extends AppCompatActivity {
    TextView profile_userName,bron_bio,born_address,bron_zone;
    //Button profile_follow_btn;
    ImageView profile_img;
    private String CurrentUserId ;
    private DatabaseReference userRef , calcRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile3);

        profile_userName = (TextView)findViewById(R.id.bron_userName);
        bron_bio = (TextView)findViewById(R.id.bron_bio);
        born_address = (TextView)findViewById(R.id.bron_address);
        bron_zone = (TextView)findViewById(R.id.bron_area);

        profile_img = findViewById(R.id.profile_img);
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();//to check user authentication loggedin or not

        CurrentUserId = mAuth.getCurrentUser().getUid();
        userRef.child(CurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_class user = dataSnapshot.getValue(user_class.class);
               // profile_email.setText(user.getEmail());
              //  profile_phone.setText(dataSnapshot.child("Phone").getValue().toString());
                profile_userName.setText(dataSnapshot.child("username").getValue().toString());
                born_address.setText(dataSnapshot.child("address").getValue().toString());
                bron_zone.setText(dataSnapshot.child("UserArea").getValue().toString());
                bron_bio.setText(dataSnapshot.child("Bio").getValue().toString());
                //   profile_followers.setText(dataSnapshot.child("followersCount").getValue().toString());
                Glide.with(getApplicationContext()).load(user.getImageUrl()).into(profile_img);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
