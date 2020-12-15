package com.sharjeelhussain.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class Start extends AppCompatActivity {

    @Override
    //offline mode
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Intent i =new Intent(Start.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}