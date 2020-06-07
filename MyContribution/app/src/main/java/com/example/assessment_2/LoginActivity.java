package com.example.assessment_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.assessment_2.model.AccountItem;
import com.example.assessment_2.model.AppDataFactory;

import java.util.List;

public class LoginActivity extends Activity {

    EditText et_UserName;
    EditText et_PassWord;
    Button btn_Login;

    String userName;
    String passWord;

    List<AccountItem> accountList = AppDataFactory.getAccountList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);
        btn_Login = findViewById(R.id.btn_Login);
        et_UserName = findViewById(R.id.et_UserName);
        et_PassWord = findViewById(R.id.et_PassWord);

        initLoginClickListener();
    }

    private void initLoginClickListener() {
        btn_Login.setOnClickListener(view -> {
//            String userName_input = et_UserName.getText().toString();
//            String passWord_input = et_PassWord.getText().toString();

            et_UserName.setText("admin");
            et_PassWord.setText("admin");

            String userName_input = "admin";
            String passWord_input = "admin";

            boolean isUser = false;

            for (AccountItem bean : accountList){
                String userName = bean.getUserName();
                String passWord = bean.getPassWord();

                if (userName.equals(userName_input) && passWord.equals(passWord_input)) {
                   isUser = true;
                   break;
                }
            }

            if(isUser == true) {
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                this.finish();
            }
            else
            {
                Toast.makeText(this,"User Name or Password wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
