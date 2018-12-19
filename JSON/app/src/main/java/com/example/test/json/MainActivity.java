package com.example.test.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;
//  JSO Parsing
public class MainActivity extends AppCompatActivity {

    TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView)findViewById(R.id.tv_show);

        //  string에 넣는다.
        String json =
                "{" +
                        "\"user\": \"gildong\"," +
                        "\"color\": [\"red\", \"green\", \"blue\"]" +
                        "}";




        try {
            JSONObject root = new JSONObject(json);

            //  root 바로 아래니까 getString 하면 user and color 가져올수있음
            String user_name = root.getString("user");
            JSONArray colors = root.getJSONArray("color");



            //  배열의 1,2,3의 값(red,green,blue)
            //  getString으로 가져옴
            String first = colors.getString(0);
            String second = colors.getString(1);
            String third = colors.getString(2);

            //  array는 for문
            for(int i = 0; i < colors.length(); i++) {
                Log.d("show colors", colors.getString(i));
            }

            String result = "user : " + user_name + "\ncolor1 : " + first +
                    "\ncolor2 : " + second + "\ncolor3 : " + third;

            //  textview에 set
            tv_show.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
