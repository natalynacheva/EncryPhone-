package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends AppCompatActivity {
    Button btnRegister, btnBackToMain;
    EditText name, email, password, repassword;
    static DataBase dataBase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        btnRegister = findViewById(R.id.btn_register);
        btnBackToMain = findViewById(R.id.backToLogin);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        repassword = findViewById(R.id.et_repassword);

        btnRegister.setOnClickListener(view -> {
            AccountModel accountModel;
            // validations

            if(TextUtils.isEmpty(name.getText().toString())){
                //password is empty
                Toast.makeText(this, "Please enter name",Toast.LENGTH_SHORT).show();
                //stopping execution further
                return;
            }
            if (!(eMailValidation(email.getText().toString()))) {
                //email is invalid
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                //stopping execution further
                return;
            }

            if(TextUtils.isEmpty(password.getText().toString())){
                //password is empty
                Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show();
                //stopping execution further
                return;
            }
            if(password.getText().toString().equals(repassword.getText().toString())) {
                try {
                    accountModel = new AccountModel(1, name.getText().toString(), email.getText().toString(), password.getText().toString());
                    Log.i("success", "acc is ok");
                    Toast.makeText(RegisterFragment.this, "registered successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(RegisterFragment.this, "Error with credentials", Toast.LENGTH_SHORT).show();
                    accountModel = new AccountModel(-1, "error", "error", "error");
                    Log.i("error", "acc is not ok");
                }
                dataBase = new DataBase(RegisterFragment.this);
                boolean success = dataBase.addOne(accountModel);
                Toast.makeText(RegisterFragment.this,"success="+success,Toast.LENGTH_SHORT).show();
                Log.i("success","acc is in db"+ accountModel);
            }
            else {
                Toast.makeText(RegisterFragment.this, "passwords don't match", Toast.LENGTH_SHORT).show();
            }

        });
        btnBackToMain.setOnClickListener(View ->{
            finish();
            Intent intent = new Intent(RegisterFragment.this,MainActivity.class);
            startActivity(intent);
        });
    }

    // email validity checker with regex
    public static boolean eMailValidation(String emailString) {
        if (null == emailString || emailString.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^(.+)@(.+)$");
        Matcher emailMatcher = emailPattern.matcher(emailString);
        return emailMatcher.matches();
    }
}

