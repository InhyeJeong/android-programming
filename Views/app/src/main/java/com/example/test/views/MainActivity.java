package com.example.test.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextPage(View v) {
        Button btn = (Button) v;
        String name = btn.getText().toString(); // 적힌 이름을 스트링으로 받음
        if (name.equals("textview")) {
            Intent intent = new Intent(getApplicationContext(), textView.class );
            startActivity(intent);
        } else if (name.equals("Table")) {
            Intent intent = new Intent(getApplicationContext(),TableLayout.class );
            startActivity(intent);
        } else if (name.equals("Frame")) {
            Intent intent = new Intent(getApplicationContext(),FrameLayout.class );
            startActivity(intent);
        } else if (name.equals("ScrollView")) {
            Intent intent = new Intent(getApplicationContext(),ScrollView.class );
            startActivity(intent);
        }
    }


}
