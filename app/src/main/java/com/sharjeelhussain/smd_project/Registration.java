package com.sharjeelhussain.smd_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.nio.file.Files;

public class Registration extends AppCompatActivity {
    TextView reg_to_log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reg_to_log= findViewById(R.id.reg_to_log);

        reg_to_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });
    }
}