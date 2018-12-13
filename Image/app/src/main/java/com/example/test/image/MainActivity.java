package com.example.test.image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
        if (name.equals("ImageSize")) {
            Intent intent = new Intent(getApplicationContext(), ImageSize.class );
            startActivity(intent);
        } else if (name.equals("ImageScroll")) {
            Intent intent = new Intent(getApplicationContext(),ImageScroll.class );
            startActivity(intent);
        } else if (name.equals("EditText")) {
            Intent intent = new Intent(getApplicationContext(),EditTextTest.class );
            startActivity(intent);
        } else if (name.equals("sms")) {
            Intent intent = new Intent(getApplicationContext(),SmsTextView.class );
            startActivity(intent);
        }
    }
}
