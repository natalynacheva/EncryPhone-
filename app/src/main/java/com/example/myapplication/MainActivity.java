package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleButton;

     EditText email, password;
    Button btnRegister, btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //normal login
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginbtn);
        btnLogin.setOnClickListener(view -> signIn());


        //google login here
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleButton = findViewById(R.id.google_btn);
        googleButton.setOnClickListener(view -> signInGoogle());

        // registration here
        btnRegister = findViewById(R.id.btn_register_main);
        btnRegister.setOnClickListener(view -> register());



    }


    private void register() {
        finish();
        Intent intent = new Intent(MainActivity.this,RegisterFragment.class);
        startActivity(intent);
    }

    private void signInGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
        Log.d("success","singing in");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Log.d("success","logged in");
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Oops, Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        Log.d("success","navigating to 2nd activity");

        //updating current user before starting acctivity
        RegisterFragment.dataBase.currentUser(email.getText().toString());

        startActivity(intent);

    }

    private void signIn() {
        String check_email = email.getText().toString();
        String check_pass = password.getText().toString();
        if(RegisterFragment.dataBase.checkUserLogin(check_email,check_pass)){
            Log.i("logging in", "registered user from app" + RegisterFragment.dataBase.checkUserLogin(check_email,check_pass));
            navigateToSecondActivity();
        } else{
            Toast.makeText(MainActivity.this, "No such user. Try Registering", Toast.LENGTH_SHORT).show();
        }
    }

}





