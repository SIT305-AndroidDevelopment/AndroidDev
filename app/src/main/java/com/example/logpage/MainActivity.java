package com.example.logpage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
    }
    public void Check(View v){
        String mname = "hello";
        String mpassword = "1234";
        String user = name.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if (user.equals(mname) && pwd.equals(mpassword)) {
            Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show();
             }
        else {
            Toast.makeText(this, "Wrong Username or Password", Toast.LENGTH_SHORT).show();
        }

    }

}
