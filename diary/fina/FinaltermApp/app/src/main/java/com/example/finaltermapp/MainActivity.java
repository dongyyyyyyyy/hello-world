package com.example.finaltermapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton buttonadd;
    private Button buttonrevise;
    private SearchView searchView;
    private ListView listView;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonadd = findViewById(R.id.imageButtonadd);
        buttonrevise = findViewById(R.id.buttonrevise);
        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.lv);
        myDbHelper = new MyDbHelper(MainActivity.this);
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query("note",null,null,null,null,null,null);
        adapter = new SimpleCursorAdapter(MainActivity.this,R.layout.layout_lv,cursor,
                new String[]{"title","content","time"},//字段名
                new int[]{R.id.textViewtitle,R.id.textViewcontent,R.id.textViewtime},//与字段名对应的控件id
                1);
        listView.setAdapter(adapter);


        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivity(intent);
            }
        });
        buttonrevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SetPActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor1 = adapter.getCursor();
                cursor1.moveToPosition(position);
                final int noteid = cursor1.getInt(cursor1.getColumnIndex("_id"));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除笔记");
                builder.setMessage("你确定要删除选中的笔记吗？");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("note","_id = ?",new String[]{noteid+""});
                        Toast.makeText(MainActivity.this,"事件删除成功",Toast.LENGTH_SHORT).show();
                        refresh();
                    }
                });
                builder.show();

                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor2 = adapter.getCursor();
                cursor2.moveToPosition(position);
                int noteid = cursor2.getInt(cursor2.getColumnIndex("_id"));
                String title = cursor2.getString(cursor2.getColumnIndex("title"));
                String content = cursor2.getString(cursor2.getColumnIndex("content"));
                String time = cursor2.getString(cursor2.getColumnIndex("time"));
                Intent intent = new Intent(MainActivity.this,ModifyActivity.class);
                intent.putExtra("id",noteid);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("time",time);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = db.query("note",null,"title like ?",new String[]{"%"+newText+"%"},null,null,null,null);
                adapter.changeCursor(cursor);

                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    private void refresh(){
        Cursor cursor = db.query("note",null,null,null,null,null,null);
        adapter.changeCursor(cursor);
    }//刷新

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}