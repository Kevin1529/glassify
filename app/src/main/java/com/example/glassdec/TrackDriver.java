package com.example.glassdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackDriver extends AppCompatActivity {

    TextView etuserloc,etdriverloc;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference databaseReference;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_driver);
        etuserloc=findViewById(R.id.userLocation);
        etdriverloc=findViewById(R.id.driverLocation);

        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.
        databaseReference = firebaseDatabase.getReference("Drivers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = user.getUid();
                name = snapshot.child(uid).child("Driver Details").child("dloc").getValue(String.class).trim();
//                Toast.makeText(getApplicationContext(), "Driver Loccation"+name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}