package com.sharjeelhussain.smd_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Patient_Dashboard_CheckUp extends AppCompatActivity {
List<AppointmentModel> list=new ArrayList<>();
FloatingActionButton pat_to_appoint;
TextView Fname,Phno,Emaill;
Button logout;
    RecyclerViewAdapterPatient mAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__dashboard__check_up);
        Fname=findViewById(R.id.fname);
        Phno=findViewById(R.id.phno);
        Emaill=findViewById(R.id.email);
        pat_to_appoint=findViewById(R.id.pat_to_appoint);
        logout=findViewById(R.id.logout);
        recyclerView=findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        mAdapter = new RecyclerViewAdapterPatient(list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        pat_to_appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Appointment.class);
                intent.putExtra("Email",getIntent().getExtras().getString("Email"));
                startActivity(intent);
            }
        });
        String Email=getIntent().getExtras().getString("Email");
        Email=Email.replace("@","_");
        Email=Email.replace(".","_");

//help
        FirebaseDatabase.getInstance().getReference("Registration").child("Patient").child(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RegistrationModel obj = dataSnapshot.getValue(RegistrationModel.class);
                Fname.setText(obj.getFull_name());
                Phno.setText(obj.getPhone());
                Emaill.setText(obj.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("Appointments").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                AppointmentModel cts = dataSnapshot.getValue(AppointmentModel.class);
                if(cts.getPtEmail().equals(getIntent().getExtras().getString("Email")))
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