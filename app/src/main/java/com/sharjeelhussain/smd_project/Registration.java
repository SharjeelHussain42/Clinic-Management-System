package com.sharjeelhussain.smd_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Registration extends AppCompatActivity {
    TextView reg_to_log;
    EditText Fname,Email,Phno,Password;
    Button Signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Fname=findViewById(R.id.fname);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        Phno=findViewById(R.id.phno);
        Signup=findViewById(R.id.signup);


        reg_to_log= findViewById(R.id.reg_to_log);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname=Fname.getText().toString();
                String email=Email.getText().toString();
                String password=Password.getText().toString();
                String phno=Phno.getText().toString();
                if(fname.length()==0 || email.length()==0 || password.length()==0 || phno.length()==0)
                {
                    Toast.makeText(Registration.this, "Kindly fill all the fields.", Toast.LENGTH_LONG).show();
                }
                else if(Phno.getText().toString().length()!=11 & !(Phno.getText().toString().startsWith("03")))
                {
                    Toast.makeText(Registration.this, "The phone number is in incorrect format.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Firebase Code

                   FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Toast.makeText(Registration.this, "User "+email+" registered successfully.",
                                                Toast.LENGTH_LONG).show();

                                        RegistrationModel object=new RegistrationModel(fname,phno,email,encryptPassword(password));

                                        String email_2 = email.replace("@","_");
                                        email_2 = email_2.replace(".","_");

                                     //help   FirebaseDatabase.getInstance().getReference("Appointments").push().setValue(object);
                                        FirebaseDatabase.getInstance().getReference("Registration").child(getIntent().getExtras().getString("Type")).child(email_2).setValue(object);
                                        if(getIntent().getExtras().getString("Type").equals("Patient"))
                                        {
                                            Intent i =new Intent(Registration.this,Patient_Dashboard_CheckUp.class);
                                            i.putExtra("Email",email);
                                            startActivity(i);
                                        }
                                        else
                                        {
                                            Intent i =new Intent(Registration.this,Doctor_Dashboard.class);
                                            i.putExtra("Email",email);
                                            startActivity(i);
                                        }

                                    }
                                    else {
                                        // If sign in fails, display a message to the user.
                                        task.addOnFailureListener(Registration.this, new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Registration.this, e.getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                }
                            });

                }
            }
        });

        reg_to_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });
    }

    public static String encryptPassword(String input) {
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}