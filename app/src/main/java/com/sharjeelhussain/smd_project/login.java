package com.sharjeelhussain.smd_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class login extends AppCompatActivity {
    Button Signin;
    EditText Email, Password;
    TextView mov_to_reg;
    ArrayList<RegistrationModel> List;
    //offline mode
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    //

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Signin=findViewById(R.id.signin);
        Email=findViewById(R.id.email);
        Password=findViewById(R.id.password);
        mov_to_reg=findViewById(R.id.mov_to_reg);

        List = new ArrayList<RegistrationModel>();

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString();
                String password=Password.getText().toString();

                boolean Found = false;
                String passwordoff="";
                for (int i=0;i<List.size();i++)
                {
                    if (List.get(i).getEmail().equals(email))
                    {
                        Found = true;
                        passwordoff=List.get(i).getPassword();
                        break;
                    }
                }


                if(email.length()==0 || password.length()==0)
                {
                    Toast.makeText(login.this, "Kindly enter username and password.", Toast.LENGTH_LONG).show();
                }
                else if(!Found)
                {
                    Toast.makeText(login.this, "Email not registered in "+getIntent().getExtras().getString("Type")+".", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (isNetworkConnected())
                    {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(login.this, "Login Successful.",
                                                    Toast.LENGTH_LONG).show();
                                            if(getIntent().getExtras().getString("Type").equals("Patient"))
                                            {
                                                Intent i =new Intent(login.this,Patient_Dashboard_CheckUp.class);
                                                i.putExtra("Email",email);
                                                startActivity(i);
                                            }
                                            else
                                            {
                                                Intent i =new Intent(login.this,Doctor_Dashboard.class);
                                                i.putExtra("Email",email);
                                                startActivity(i);
                                            }


                                        } else {
                                            // If sign in fails, display a message to the user.
                                            task.addOnFailureListener(login.this, new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    if(e.getMessage().contains("password is invalid")) {
                                                        Toast.makeText(login.this, "Password does not match with user account.",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                    else if (e.getMessage().contains("There is no user record")) {
                                                        Toast.makeText(login.this, "User account not registered.",
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                    else {
                                                        Toast.makeText(login.this, e.getMessage(),
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });
                    }
                    else
                    {
                        if (encryptPassword(password).equals(passwordoff))
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(login.this, "Login Successful.",
                                    Toast.LENGTH_LONG).show();
                            if(getIntent().getExtras().getString("Type").equals("Patient"))
                            {
                                Intent i =new Intent(login.this,Patient_Dashboard_CheckUp.class);
                                i.putExtra("Email",email);
                                startActivity(i);
                            }
                            else
                            {
                                Intent i =new Intent(login.this,Doctor_Dashboard.class);
                                i.putExtra("Email",email);
                                startActivity(i);
                            }

                        }
                        else
                        {
                            Toast.makeText(login.this, "Password does not match with user account.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                }

            }

        });

        mov_to_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected())
                {
                    Intent intent=new Intent(getApplicationContext(),Registration.class);
                    intent.putExtra("Type",getIntent().getExtras().getString("Type"));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(login.this, "No Internet Access.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        FirebaseDatabase.getInstance().getReference("Registration").child(getIntent().getExtras().getString("Type")).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @Nullable String s) {
                        RegistrationModel cts = dataSnapshot.getValue(RegistrationModel.class);
                        List.add(cts);
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