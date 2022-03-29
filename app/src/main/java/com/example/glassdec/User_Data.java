package com.example.glassdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class User_Data extends AppCompatActivity {

    TextView userName, userAdd,userPhNo;
    ImageView imageView;
    Spinner driver;
    Bundle bundle;
    String user_name;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Retrive_User_Request user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        userName = findViewById(R.id.user_name);
        userAdd = findViewById(R.id.user_address);
        userPhNo = findViewById(R.id.user_phno);
        driver = findViewById(R.id.spinner);
        imageView = findViewById(R.id.image);
        user = new Retrive_User_Request();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            user_name = bundle.getString("name");
            userName.setText(user_name);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Admin");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    user = dataSnapshot.getValue(Retrive_User_Request.class);

                    if (user != null ) {
                        String name = user.getName().toString();
                        String address = user.getAddress().toString();
                        String phone = user.getPhno().toString();
                        String img = user.getImgpath().toString();
                        if(name.equals(user_name)){
                            userAdd.setText(address);
                            userPhNo.setText(phone);
                            Picasso.get().load(img).into(imageView);
                        }



                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}