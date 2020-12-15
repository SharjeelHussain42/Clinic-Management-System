package com.sharjeelhussain.smd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Chat_Box extends AppCompatActivity {
    AutoCompleteTextView Doctor, Patient;
    EditText Message;
    Button Back,Send;
    Boolean Pchat = true;
    Boolean Dchat = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__box);
        Doctor=findViewById(R.id.doctor);
        Patient=findViewById(R.id.patient);
        Back=findViewById(R.id.back);
        Send=findViewById(R.id.send);
        Message=findViewById(R.id.message);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=Message.getText().toString();

                if (message.length()==0)
                {
                    Toast.makeText(Chat_Box.this, "Kindly enter message.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //firebase code
                    if (getIntent().getExtras().getString("Type").equals("Doctor"))
                    { if (Dchat){ message = Doctor.getText().toString()+"\n"+message; } }
                    else { if (Pchat){ message = Patient.getText().toString()+"\n"+message; } }

                    FirebaseDatabase.getInstance().getReference(getIntent().getExtras().getString("Fkey")+"_Chat")
                            .child(getIntent().getExtras().getString("Type")).setValue(message);

                    Intent reload = new Intent(Chat_Box.this,Chat_Box.class);
                    reload.putExtra("Fkey",getIntent().getExtras().getString("Fkey"));
                    reload.putExtra("Type",getIntent().getExtras().getString("Type"));
                    startActivity(reload);
                    finish();
                }
            }
        });

        FirebaseDatabase.getInstance().getReference(getIntent().getExtras().getString("Fkey")+"_Chat").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Patient"))
                {
                    Patient.setText(dataSnapshot.child("Patient").getValue(String.class));
                }
                else
                {
                    Pchat = false;
                    Patient.setText("N/A");
                }
                if (dataSnapshot.hasChild("Doctor"))
                {
                    Patient.setText(dataSnapshot.child("Doctor").getValue(String.class));
                }
                else
                {
                    Dchat = false;
                    Patient.setText("N/A");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}







