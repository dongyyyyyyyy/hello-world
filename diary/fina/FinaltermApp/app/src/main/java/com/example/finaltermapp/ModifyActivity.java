package com.example.finaltermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyActivity extends AppCompatActivity {
    private EditText editTexttitlechange,editTextcontentchange;
    private Button buttonmodify,buttoncancle2;
    private int id;
    private String title;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        editTexttitlechange = findViewById(R.id.editTexttitlechange);
        editTextcontentchange = findViewById(R.id.editTextcontentchange);
        buttonmodify = findViewById(R.id.buttonmodify);
        buttoncancle2 = findViewById(R.id.buttoncancle2);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String time = intent.getStringExtra("time");
        editTexttitlechange.setText(title);
        editTextcontentchange.setText(content);



        buttonmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTexttitlechange.getText().toString();
                String content = editTextcontentchange.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(ModifyActivity.this,"标题不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if(content.isEmpty()){
                        Toast.makeText(ModifyActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                myDbHelper = new MyDbHelper(ModifyActivity.this);
                db = myDbHelper.getReadableDatabase();
                Cursor cursor = db.query("note",null,"title=?",new String[]{title},null,null,null);
                ContentValues values = new ContentValues();
                values.put("title",title);
                values.put("content",content);
                values.put("time",getTime());
                db.update("note",values,"_id = ?",new String[]{id+""});
                Toast.makeText(ModifyActivity.this,"事件修改成功",Toast.LENGTH_SHORT).show();
                if(db != null && db.isOpen()){
                    db.close();
                }
                finish();
            }
        });

        buttoncancle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str = sdf.format(date);
        return str;
    }
}