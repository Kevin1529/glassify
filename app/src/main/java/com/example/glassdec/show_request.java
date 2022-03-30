package com.example.glassdec;

import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class show_request extends Fragment {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView listView;
    DatabaseReference mRef;
    FirebaseDatabase database;
    Retrive_User_Request user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_show_request,container,false);

        user = new Retrive_User_Request();
        listView = (ListView) rootView.findViewById(R.id.user_list);
        database = FirebaseDatabase.getInstance();
        mRef = database.getReference("Admin");

        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getContext(),R.layout.user_list,R.id.userInfo,list);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){

                    user = ds.getValue(Retrive_User_Request.class);
                    list.add(user.getName().toString());
                }
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedItem = (String) adapterView.getItemAtPosition(i);
                        Toast.makeText(getContext(), selectedItem, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),User_Data.class);
                        intent.putExtra("name", selectedItem);
                        startActivity(intent);
                        list.clear();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter.notifyDataSetChanged();
        return rootView;
    }

}
