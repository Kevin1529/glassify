package com.example.glassdec;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class accepted_rquest extends Fragment {

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ListView driver_listView;
    DatabaseReference databaseRef;
    FirebaseDatabase firebase_database;
    Task task;

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }
}
