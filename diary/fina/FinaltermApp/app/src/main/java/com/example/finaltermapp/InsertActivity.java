package com.example.finaltermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertActivity extends AppCompatActivity {
    private EditText editTexttitleinsert,editTextcontentinsert;
    private Button buttonadd,buttoncancle2;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        editTexttitleinsert = findViewById(R.id.editTexttitlechange);
        editTextcontentinsert = findViewById(R.id.editTextcontentchange);
        buttonadd = findViewById(R.id.buttonmodify);
        buttoncancle2 = findViewById(R.id.buttoncancle2);
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTexttitleinsert.getText().toString();
                String content = editTextcontentinsert.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(InsertActivity.this,"标题不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if(content.isEmpty()){
                        Toast.makeText(InsertActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                myDbHelper = new MyDbHelper(InsertActivity.this);
                db = myDbHelper.getReadableDatabase();
                Cursor cursor = db.query("note",null,"title=?",new String[]{title},null,null,null);
                ContentValues values = new ContentValues();
                values.put("title",title);
                values.put("content",content);
                values.put("time",getTime());
                db.insert("note",null,values);
                Toast.makeText(InsertActivity.this,"事件添加成功",Toast.LENGTH_SHORT).show();
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
    }//获取当前时间
}