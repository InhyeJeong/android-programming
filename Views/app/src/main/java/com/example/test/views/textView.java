package com.example.test.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class textView extends AppCompatActivity {
    //  텍스트 뷰의 객체를 만든다.
    TextView tv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        //  레이아웃에 뷰 중에서 tv를 객체로 만든다.
        tv = (TextView)findViewById(R.id.tv);


        String message = tv.getText().toString();

        tv.getText();   // 텍스트 뷰에 적힌 글자 가져옴
        tv.setText("bye world~");   // 글자 변경

        Toast.makeText(textView.this, message, Toast.LENGTH_SHORT).show();
    }
}
