package com.example.glassdec;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;


import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button admin,patient;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();



    }
    @Override

    protected void onStart() {
        super.onStart();


        FirebaseUser currentUser=mAuth.getCurrentUser();
        if (currentUser!=null)
        {
//
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                String uid = user.getUid();
                name = dataSnapshot.child(uid).child("chooseAcc").getValue(String.class).trim();
//                Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
             //   Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                if (name.equals("User")) {
                Intent driverintent = new Intent(getApplicationContext(), UserHome.class);
                startActivity(driverintent);
                finish();
            }
           else{
                Intent userintent = new Intent(getApplicationContext(), DriverHome.class);
                startActivity(userintent);
                finish();
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }
        else
        {
            Intent mainintent=new Intent(getApplicationContext(),Login.class);
            startActivity(mainintent);
            finish();
        }
    }


}