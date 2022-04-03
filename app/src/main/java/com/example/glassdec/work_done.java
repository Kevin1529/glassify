package com.example.glassdec;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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

public class work_done extends Fragment {
    ArrayList<String> list_completed_task;
    ArrayAdapter<String> adapter;
    ListView listView;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_work_done,container,false);

        listView = (ListView) root.findViewById(R.id.completed_work);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Completed Task");

        list_completed_task = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getContext(),R.layout.user_list,R.id.userInfo,list_completed_task);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String user_name = ds.child("user").getValue(String.class);
                    list_completed_task.add(user_name);
                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedItem = (String) adapterView.getItemAtPosition(i);
                        getcompletedInfo(selectedItem);

                    }
                });

        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    private void getcompletedInfo(String user_name) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Completed Task");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dri_name = snapshot.child(user_name).child("dname").getValue(String.class);
                String weight = snapshot.child(user_name).child("totalWeight").getValue(String.class);

                AlertDialog dialog;
                dialog = new AlertDialog.Builder(getContext()).setTitle(user_name)
                        .setMessage("Driver: "+dri_name+"\n"+"Weight: "+weight)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
