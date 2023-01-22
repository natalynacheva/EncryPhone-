package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterFragment extends AppCompatActivity {
    Button btnRegister;
    EditText name, email, password, repassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);
        btnRegister = findViewById(R.id.btn_register);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        repassword = findViewById(R.id.et_repassword);

        btnRegister.setOnClickListener(view -> {
            AccountModel accountModel;

            //if(password==repassword) {
                try {
                     accountModel = new AccountModel(1, name.getText().toString(), email.getText().toString(), password.getText().toString());
                    Log.i("success","acc is ok");
                    Toast.makeText(RegisterFragment.this, "registered successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(RegisterFragment.this, "Error with credentials", Toast.LENGTH_SHORT).show();
                    accountModel = new AccountModel(-1, "error", "error", "error");
                    Log.i("eror", "acc is not ok");
                }
               // }

                DataBase dataBase = new DataBase(RegisterFragment.this);
                boolean success = dataBase.addOne(accountModel);
                Toast.makeText(RegisterFragment.this,"success="+success,Toast.LENGTH_SHORT).show();
                Log.i("success","acc is in db"+ accountModel);
        });


    }
}

