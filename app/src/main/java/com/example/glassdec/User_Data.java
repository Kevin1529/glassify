package com.example.glassdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class User_Data extends AppCompatActivity {

    TextView userName, userAdd,userPhNo;
    ImageView imageView;
    Spinner driver;
    Button assign_driver;

    Bundle bundle;
    String user_name;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    Retrive_User_Request userRequest;
    Retrive_User user;

    ValueEventListener listener;
    ArrayList<String> list, list_of_drivers;
    ArrayAdapter<String> adapter;
    Task task_to_compare = new Task();
    String user_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        userName = findViewById(R.id.user_name);
        userAdd = findViewById(R.id.user_address);
        userPhNo = findViewById(R.id.user_phno);
        driver = findViewById(R.id.spinner);
        imageView = findViewById(R.id.image);
        assign_driver = findViewById(R.id.assignDriver);

        userRequest = new Retrive_User_Request();
        user = new Retrive_User();

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        driver.setAdapter(adapter);

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
                    userRequest = dataSnapshot.getValue(Retrive_User_Request.class);

                    if (userRequest != null ) {
                        String name = userRequest.getName().toString();
                        String address = userRequest.getAddress().toString();
                        String phone = userRequest.getPhno().toString();
                        String img = userRequest.getImgpath().toString();
                        user_location = userRequest.getuLocation().toString();
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

        assign_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                String name = userName.getText().toString();
                FirebaseDatabase.getInstance().getReference("Admin").child(name).removeValue();
            }
        });
        compare();
        fetchdata();

       // Toast.makeText(getApplicationContext(), list_of_drivers., Toast.LENGTH_SHORT).show();

    }

    private void insertData() {
        String user_name = userName.getText().toString();
        String address = userAdd.getText().toString();
        String phone = userPhNo.getText().toString();
        String driver_name = driver.getSelectedItem().toString();
        String location = user_location;
        Task driver_task = new Task(user_name,driver_name,address,phone,location);
        FirebaseDatabase.getInstance().getReference("Task").child(driver_name).setValue(driver_task).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Task assigned successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchdata() {
        databaseReference = firebaseDatabase.getReference("Users");
        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot myData: snapshot.getChildren()){
                    user = myData.getValue(Retrive_User.class);
                    assert user != null;
                    String account = user.getChooseAcc();

                    if(account.equals("Driver")){
                        String name = user.getName();
                        Toast.makeText(getApplicationContext(), "Check: "+name, Toast.LENGTH_SHORT).show();
                        if(!list_of_drivers.contains(name)){
                            list.add(user.getName().toString());
                        }

                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void compare( ) {
        DatabaseReference reference;
        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Task");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    task_to_compare = ds.getValue(Task.class);
                    assert task_to_compare!=null;
                    String driver_name = task_to_compare.getDriverName().toString();
                    list_of_drivers = new ArrayList<String>();
                    Toast.makeText(getApplicationContext(), "Add driver: "+driver_name, Toast.LENGTH_SHORT).show();
                    list_of_drivers.add(driver_name);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}