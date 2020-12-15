package com.sharjeelhussain.smd_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Appointment extends AppCompatActivity {

    AutoCompleteTextView Doctor;
    EditText Fname,Phno,Problem,Date;
    Spinner Timing;
    Button Save;
    Calendar calender;
    DatePickerDialog.OnDateSetListener date;
    ArrayList<String> dataArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Doctor=findViewById(R.id.doctor);
        Fname=findViewById(R.id.fname);
        Phno=findViewById(R.id.phno);
        Timing=findViewById(R.id.timing);
        Problem=findViewById(R.id.problem);
        Save=findViewById(R.id.save);
        Date=findViewById(R.id.date);



        CustomArrayAdapter adapter = new CustomArrayAdapter(this,
                R.layout.itemlayout, dataArrayList);
        Doctor.setAdapter(adapter);
        Doctor.setThreshold(1);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String doctor=Doctor.getText().toString();
                String fname=Fname.getText().toString();
                String phno=Phno.getText().toString();
                String problem=Problem.getText().toString();
                String date=Date.getText().toString();
                String time=Timing.getSelectedItem().toString();


                if (doctor.length()==0 || fname.length()==0 || phno.length()==0  || problem.length()==0 || date.length()==0 ||time.length()==0)
                {
                    Toast.makeText(Appointment.this, "Kindly fill all the required fields.", Toast.LENGTH_LONG).show();
                }
                else if(phno.length()!=11 & (phno.startsWith("03")))
                {
                    Toast.makeText(Appointment.this, "The Phone Number is in incorrect format.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Firebase Code
                    AppointmentModel object=new AppointmentModel();
                    Bundle b=getIntent().getExtras();
                    object.setPtEmail(b.getString("Email"));
                    object.setPtName(fname);
                    object.setPhone(phno);
                    object.setTiming(time);
                    object.setDate(date);
                    object.setProblem(problem);
                    object.setDrName(doctor.split("-")[0]);
                    object.setDrEmail(doctor.split("-")[1]);

                    FirebaseDatabase.getInstance().getReference("Appointments").push().setValue(object);
                    Intent i =new Intent(Appointment.this,Patient_Dashboard_CheckUp.class);
                    i.putExtra("Email",b.getString("Email"));
                    startActivity(i);
                    
                }

            }
        });

        calender = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Date.setText(day+"/"+month+"/"+year);
            }
        };

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Appointment.this,date,calender.get(Calendar.YEAR),calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String Email=getIntent().getExtras().getString("Email");
        Email=Email.replace("@","_");
        Email=Email.replace(".","_");


        FirebaseDatabase.getInstance().getReference("Registration").child("Patient").child(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RegistrationModel obj = dataSnapshot.getValue(RegistrationModel.class);
                Fname.setText(obj.getFull_name());
                Phno.setText(obj.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Registration").child("Doctor").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                RegistrationModel cts = dataSnapshot.getValue(RegistrationModel.class);

                dataArrayList.add(cts.getFull_name()+"-"+cts.getEmail());

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



        //store in array list for autotextview
    }
}