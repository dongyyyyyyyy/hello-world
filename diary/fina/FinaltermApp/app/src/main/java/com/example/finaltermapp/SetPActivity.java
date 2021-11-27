package com.example.finaltermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPActivity extends AppCompatActivity {
    private EditText editTextold,editTextnew;
    private Button buttonok,buttoncancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_p);
        editTextold = findViewById(R.id.editTextTextPasswordold);
        editTextnew = findViewById(R.id.editTextTextPasswordnew);
        buttonok = findViewById(R.id.buttonok);
        buttoncancel = findViewById(R.id.buttoncancle);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwdold = editTextold.getText().toString();
                String pwdnew = editTextnew.getText().toString();
                if(pwdold.length()>10 || pwdold.length()<6){
                    editTextold.setError("密码长度必须为6-10位数");
                    return;
                }
                if(!pwdnew.equals(pwdold)){
                    editTextnew.setError("两次密码输入不一致");
                    return;
                }
                else {
                    SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password",pwdold);
                    editor.putBoolean("remember",false);
                    editor.commit();
                    Toast.makeText(SetPActivity.this,"密码更改成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SetPActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}