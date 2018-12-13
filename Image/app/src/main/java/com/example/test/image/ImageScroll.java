package com.example.test.image;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageScroll extends AppCompatActivity {

    ImageView brother;
    ImageView brother2;
    Button btnUp;
    Button btnDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scroll);

        brother = (ImageView) findViewById(R.id.brother);
        brother2 = (ImageView) findViewById(R.id.brother2);
//        btnUp = (Button)findViewById(R.id.btnUp);
//        btnDown = (Button)findViewById(R.id.btnDown);
//
//        //  up클릭시 사진 위로
//        btnUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                brother.setVisibility(View.VISIBLE);
//                brother2.setVisibility(View.INVISIBLE);
//            }
//        });
//        //  down클릭시 사진 아래로
//        btnDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                brother2.setVisibility(View.VISIBLE);
//                brother.setVisibility(View.INVISIBLE);
//            }
//        });
    }


    public void Up(View v) {
        Button btn = (Button) v;
        String name = btn.getText().toString(); // 적힌 이름을 스트링으로 받음
        if (name.equals("up")) {
            Toast.makeText(this, "눌렸음", Toast.LENGTH_SHORT).show();
            brother.setVisibility(View.VISIBLE);
            brother2.setVisibility(View.INVISIBLE);

        } else if (name.equals("down")) {
            Toast.makeText(this, "눌렸음", Toast.LENGTH_SHORT).show();
            brother2.setVisibility(View.VISIBLE);
            brother.setVisibility(View.INVISIBLE);
        }
    }
}

