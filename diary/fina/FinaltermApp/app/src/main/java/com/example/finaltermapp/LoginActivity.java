package com.example.finaltermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextpwd;
    private CheckBox checkBoxrember;
    private Button buttonlogin;
    private String password;
    private boolean remember;
    private TextView textViewtip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextpwd = findViewById(R.id.editTextTextPassword);
        checkBoxrember = findViewById(R.id.checkBoxremember);
        buttonlogin = findViewById(R.id.buttonlogin);
        textViewtip = findViewById(R.id.textViewtip);

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        password = sp.getString("password","666666");
        remember = sp.getBoolean("remember",false);

        if(password != "666666"){
            textViewtip.setText(null);
        }

        if(password == null){
            Intent intent = new Intent(LoginActivity.this,SetPActivity.class);
            startActivity(intent);
            finish();
        }

        checkBoxrember.setChecked(remember);

        if(remember){
            editTextpwd.setText(password);
        }

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = editTextpwd.getText().toString();
                if(pwd.equals(password)){
                    SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("remember",checkBoxrember.isChecked());
                    editor.commit();

                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    if(pwd.isEmpty()){
                        editTextpwd.setError("密码不能为空！");
                    }
                    else
                        Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}