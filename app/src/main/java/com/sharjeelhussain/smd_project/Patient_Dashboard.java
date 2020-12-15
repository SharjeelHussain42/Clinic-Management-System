package com.sharjeelhussain.smd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Patient_Dashboard extends AppCompatActivity {

    Button chat_pat,Back;
    TextView Drname,Phno,Problem,Desciption,Medicine;
    String Prescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__dashboard);
        Back=findViewById(R.id.back);
        Drname=findViewById(R.id.drname);
        Phno=findViewById(R.id.phno);
        Problem=findViewById(R.id.problem);
        Desciption=findViewById(R.id.description);
        Medicine=findViewById(R.id.medicine);
        chat_pat=findViewById(R.id.chat_pat);


        Problem.setText(getIntent().getExtras().getString("Problem"));
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Patient_Dashboard.this,Patient_Dashboard_CheckUp.class);
                i.putExtra("Email",getIntent().getExtras().getString("PtEmail"));
                startActivity(i);
            }
        });

        chat_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent =new Intent(getApplicationContext(),Chat_Box.class);
                    intent.putExtra("Fkey",Prescriptions);
                    intent.putExtra("Type","Patient");
                    startActivity(intent);
                }


        });


        String Email=getIntent().getExtras().getString("DrEmail");
        Email=Email.replace("@","_");
        Email=Email.replace(".","_");

        FirebaseDatabase.getInstance().getReference("Registration").child("Doctor").child(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RegistrationModel obj = dataSnapshot.getValue(RegistrationModel.class);
                Drname.setText(obj.getFull_name());
                Phno.setText(obj.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //help also in saving doctor prescription
        String PtEmail=getIntent().getExtras().getString("PtEmail");
        PtEmail=PtEmail.replace("@","_");
        PtEmail=PtEmail.replace(".","_");


        String Date=getIntent().getExtras().getString("Date");
        Date=Date.replace("/","_");
        Date=Date.replace("/","_");

        String Time=getIntent().getExtras().getString("Time");
        Time=Time.replace(":","_");
        Time=Time.replace(" ","_");
        Prescriptions=Email +"_"+ PtEmail +"_"+ Date +"_"+ Time;




        FirebaseDatabase.getInstance().getReference("Prescription").child(Prescriptions).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient_Dashboard_Model obj = dataSnapshot.getValue(Patient_Dashboard_Model.class);
                if(obj==null)
                {
                    Desciption.setText("N/A");
                    Medicine.setText("N/A");
                }
                else
                {
                    Desciption.setText(obj.getDescription());
                    Medicine.setText(obj.getMedicine());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}