package cn.itcast.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    private int[] images=new int[]{R.drawable.cat, R.drawable.dog,R.drawable.elephant,R.drawable.lion,
    R.drawable.monkey,R.drawable.tiger};
    private int currentImg=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建一个线性布局管理器
        LinearLayout main=findViewById(R.id.wrap);
        ImageView image=new ImageView(this);
        main.addView(image);

        image.setImageResource(images[0]);
        image.setOnClickListener(view->
        {
            image.setImageResource(images[++currentImg%images.length]);
        });
    }
}