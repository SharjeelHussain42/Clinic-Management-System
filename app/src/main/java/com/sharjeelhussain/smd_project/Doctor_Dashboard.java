package com.sharjeelhussain.smd_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Doctor_Dashboard extends AppCompatActivity {
    List<AppointmentModel> list=new ArrayList<>();
    Button Message;
    RecyclerView Ptlist;
    RecyclerViewAdapterDoctor mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__dashboard);

        Ptlist=findViewById(R.id.ptlist);
        Message=findViewById(R.id.message);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Ptlist.setHasFixedSize(true);
        Ptlist.setLayoutManager(layoutManager);


        mAdapter = new RecyclerViewAdapterDoctor(list);
        Ptlist.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();



        FirebaseDatabase.getInstance().getReference("Appointments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                AppointmentModel cts = dataSnapshot.getValue(AppointmentModel.class);
                if(cts.getDrEmail().equals(getIntent().getExtras().getString("Email")))
                {
                    list.add(cts);
                    mAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}