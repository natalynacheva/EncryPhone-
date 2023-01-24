package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class SecondActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView email;
    Button settingsButton, syncDataButton;
    Button signOutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        email =  findViewById(R.id.editTextTextEmailAddress);
        signOutButton = findViewById(R.id.sign_out);
        syncDataButton = findViewById(R.id.sync_data);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
        if(acc != null) {
            //String personName = acc.getDisplayName();
            String personEmail = acc.getEmail();
            //name.setText(personName);
            email.setText(personEmail);
        }else{
            //String personEmail = MainActivity.email;
        }
        signOutButton.setOnClickListener(view -> signOut());
        syncDataButton.setOnClickListener(view -> syncData());
        }
        void signOut(){
            gsc.signOut().addOnCompleteListener(task -> {
                finish();
                startActivity(new Intent(SecondActivity.this,MainActivity.class));
            });

        }
        void syncData(){
            finish();
            Intent intent = new Intent(SecondActivity.this,SyncActivity.class);
            startActivity(intent);
        }
    }

