package com.example.glassdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DriverHome extends AppCompatActivity {

    EditText etDriverName,etVechName,etVechNo;
    Button submit;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        etDriverName=findViewById(R.id.dName);
        etVechName=findViewById(R.id.dModelName);
        etVechNo=findViewById(R.id.dVechNo);
        submit=findViewById(R.id.submit);
        mAuth=FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uid = user.getUid();
//                String dcname = snapshot.child(uid).child("dCarName").getValue(String.class);
                reference = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Driver Details");
                Query query = reference.orderByChild("dCarName");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            etDriverName.setEnabled(false);
                            Toast.makeText(DriverHome.this, "Profile Already Added!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            etDriverName.setEnabled(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String dname=etDriverName.getText().toString().trim();
                String dvechname=etVechName.getText().toString().trim();
                String dvechno=etVechNo.getText().toString().trim();

                DriverDetails driverDetails=new DriverDetails(dname,dvechname,dvechno);
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Driver Details").setValue(driverDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
//
                            Toast.makeText(getApplicationContext(),"Data Submitted Successfully",Toast.LENGTH_SHORT).show();
//
                        }
                    }
                });

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logoutmenu:
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(DriverHome.this,Login.class));
                        return false;
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}