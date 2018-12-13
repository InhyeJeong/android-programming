package com.example.test.intenttest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn_web, btn_phone, btn_map, btn_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_web = (Button)findViewById(R.id.btn_web);
        btn_phone = (Button)findViewById(R.id.btn_phone);
        btn_map = (Button)findViewById(R.id.btn_map);
        btn_contacts = (Button)findViewById(R.id.btn_contacts);

        //  인텐트버튼 리스너 만들기
        IntentBtnListener intentBtnListener = new IntentBtnListener();

        // 온클릭 연결
        btn_web.setOnClickListener(intentBtnListener);
        btn_phone.setOnClickListener(intentBtnListener);
        btn_map.setOnClickListener(intentBtnListener);
        btn_contacts.setOnClickListener(intentBtnListener);
    }

    class IntentBtnListener implements View.OnClickListener{
        //  초기화
        Intent intent = null;

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btn_web:
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.naver.com"));
                    break;
                case R.id.btn_phone:
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("tel:01012345678"));
                    break;
                case R.id.btn_map:
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("geo:36.6349120,127.4869820"));
                    break;
                case R.id.btn_contacts:
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("content://contacts/people/"));
                    break;
            }
            //  버튼 눌린 것을 intent에 받아서 화면전환
            if(intent != null) {
                startActivity(intent);
            }
        }
    }
}
