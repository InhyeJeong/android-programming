package com.example.test.startactivitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {


    Button btn1;
    Button btn2;
    Button btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle("메인 메뉴");

        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);

    }
    void Click(View v) {
        Button btn = (Button) v;
        String ClickBtn = btn.getText().toString();
        //  메인으로 이동할때 보내는 data
        Intent intent = new Intent();
        if(ClickBtn.equals("고객 관리")) {

            intent.putExtra("msg", "고객 관리");
            setResult(RESULT_OK, intent);
            finish();
        } else if(ClickBtn.equals("매출 관리")) {

            intent.putExtra("msg", "매출 관리");
            setResult(RESULT_OK, intent);
            finish();
        } else if(ClickBtn.equals("상품 관리")) {

            intent.putExtra("msg", "상품 관리");
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
