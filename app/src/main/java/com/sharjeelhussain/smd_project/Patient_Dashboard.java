package com.sharjeelhussain.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Patient_Dashboard extends AppCompatActivity {
Button chat_pat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__dashboard);

        chat_pat=findViewById(R.id.chat_pat);

        chat_pat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),Chat_Box.class);
                startActivity(intent);
            }
        });
    }
}