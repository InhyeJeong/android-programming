package com.example.test.startactivitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //  request code
    public static final int REQUEST_CONE_SECOND = 101;
    Button btn_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("로그인하기");

        btn_1 = (Button)findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new MyButtonListener());
    }

    class MyButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent;

            switch(view.getId()) {
                case R.id.btn_1 :
                    intent = new Intent(MainActivity.this, SecondActivity.class);
                    //  for result로 전달받기
                    startActivityForResult(intent, REQUEST_CONE_SECOND);
                    break;
            }
        }
    }

    //  response 받아오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CONE_SECOND) {
            if(resultCode == RESULT_OK) {
                String msg = data.getStringExtra("msg");
                Toast.makeText(MainActivity.this, "선택된메뉴 : "+msg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
