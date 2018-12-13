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

    }

    public void nextPage(View v) {
        Button btn = (Button) v;
        String name = btn.getText().toString(); // 적힌 이름을 스트링으로 받음
        if (name.equals("Intent1")) {
            Intent intent = new Intent(getApplicationContext(), IntentFirstTest.class );
            startActivity(intent);
        } else if (name.equals("Intent2")) {
            Intent intent = new Intent(getApplicationContext(),IntentSecondTest.class );
            startActivity(intent);
        }
    }

}
