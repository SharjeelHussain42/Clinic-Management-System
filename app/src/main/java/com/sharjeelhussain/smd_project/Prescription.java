package com.sharjeelhussain.smd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Prescription extends AppCompatActivity {
    EditText Name,Email,Problem,Description,Medicine;
    Button Save,Message;
    String Prescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        Name=findViewById(R.id.name);
        Email=findViewById(R.id.email);
        Problem=findViewById(R.id.problem);
        Description=findViewById(R.id.description);
        Medicine=findViewById(R.id.medicine);
        Save=findViewById(R.id.save);
        Message=findViewById(R.id.message);


        ////////
        String name=getIntent().getExtras().getString("PtName");
        String pttemail=getIntent().getExtras().getString("PtEmail");
        String problem=getIntent().getExtras().getString("Problem");

        Name.setText(name);
        Email.setText(pttemail);
        Problem.setText(problem);
        ////

        String DrEmail=getIntent().getExtras().getString("DrEmail");
        DrEmail=DrEmail.replace("@","_");
        DrEmail=DrEmail.replace(".","_");


        String PtEmail=getIntent().getExtras().getString("PtEmail");
        PtEmail=PtEmail.replace("@","_");
        PtEmail=PtEmail.replace(".","_");


        String Date=getIntent().getExtras().getString("Date");
        Date=Date.replace("/","_");
        Date=Date.replace("/","_");

        String Time=getIntent().getExtras().getString("Time");
        Time=Time.replace(":","_");
        Time=Time.replace(" ","_");


        Prescriptions=DrEmail +"_"+ PtEmail +"_"+ Date +"_"+ Time;



        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String desciption=Description.getText().toString();
                String medicine=Medicine.getText().toString();

                if(desciption.length()==0 ||medicine.length()==0)
                {
                    Toast.makeText(Prescription.this, "Kindly fill all details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Firebase code
                    Patient_Dashboard_Model object=new Patient_Dashboard_Model();
                    object.setDescription(desciption);
                    object.setMedicine(medicine);

                    FirebaseDatabase.getInstance().getReference("Prescription").child(Prescriptions).setValue(object);
                    Intent i =new Intent(Prescription.this,Doctor_Dashboard.class);
                    i.putExtra("Email",getIntent().getExtras().getString("DrEmail"));
                    startActivity(i);
                }


            }
        });

        Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Chat_Box.class);
                intent.putExtra("Fkey",Prescriptions);
                intent.putExtra("Type","Doctor");
                startActivity(intent);
            }
        });


        FirebaseDatabase.getInstance().getReference("Prescription").child(Prescriptions).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patient_Dashboard_Model obj = dataSnapshot.getValue(Patient_Dashboard_Model.class);
                if(obj==null)
                {
                    Description.setText("N/A");
                    Medicine.setText("N/A");
                }
                else
                {
                    Description.setText(obj.getDescription());
                    Medicine.setText(obj.getMedicine());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}