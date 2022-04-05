package com.example.glassdec;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class accepted_rquest extends Fragment {

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ListView driver_listView;
    DatabaseReference databaseRef;
    FirebaseDatabase firebase_database;
    Task task;
    String strAdd,driver_location;
    Double lng,lat;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_accepted_request,container,false);



        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.user_list,R.id.userInfo,arrayList);

        driver_listView = root.findViewById(R.id.driverList);
        firebase_database = FirebaseDatabase.getInstance();
        databaseRef = firebase_database.getReference("Task");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    task = ds.getValue(Task.class);
                    assert task != null;
                    arrayList.add(task.getDriverName().toString());
                }
                driver_listView.setAdapter(arrayAdapter);
                driver_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedItem = (String) adapterView.getItemAtPosition(i);
                    //    Toast.makeText(getContext(), "Driver: "+selectedItem, Toast.LENGTH_SHORT).show();
                        getDriverLocation(selectedItem);

//                        getCompleteAddressString();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }

    private void getDriverLocation(String driver) {
        firebase_database = FirebaseDatabase.getInstance();
        databaseRef = firebase_database.getReference("Drivers");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                driver_location = snapshot.child(driver).child("Driver Loc").child("dloc").getValue(String.class);
              //  Toast.makeText(getContext(), "DriverLoc "+ driver_location, Toast.LENGTH_SHORT).show();
                AlertDialog dialog;
                dialog = new AlertDialog.Builder(getContext()).setTitle("Driver Location")
                        .setMessage(driver_location)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create();
                dialog.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
