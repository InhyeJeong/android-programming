package com.example.test.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;
//  JSO Parsing
public class JsonActivity extends AppCompatActivity {

    TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_show = (TextView)findViewById(R.id.tv_show);

        //  string에 넣는다.
        String weatherJson =
                "{" +
                        "\"weather\" : [{" +
                        "\"id\" : 721, " +
                        "\"main\" : \"Haze\", " +
                        "\"description\" : \"haze\", " +
                        "\"icon\" : \"50n\"" +
                        "}]," +
                        "\"main\" : {" +
                        "\"temp\" : 10.14, " +
                        "\"pressure\" : 1020, " +
                        "\"humidity\" : 37, " +
                        "\"temp_min\" : 6, " +
                        "\"temp_max\" : 13 " +

                        "}, " +
                        "\"id\" : 18392, " +
                        "\"name\" : \"Seoul\", " +
                        "\"cod\" : 200" +
                        "}";

        try {
            JSONObject root = new JSONObject(weatherJson);

            //  root 바로 아래니까 getString

            //weather
            JSONArray weather_array = root.getJSONArray("weather");
            JSONObject weather_object = weather_array.getJSONObject(0);

            String weather_id = weather_object.getString("id");
            String weather_main = weather_object.getString("main");
            String weather_description = weather_object.getString("description");
            String weather_icon = weather_object.getString("icon");


            //main
            JSONObject main = root.getJSONObject("main");

            String main_temp = main.getString("temp");
            String main_pressure = main.getString("pressure");
            String main_humidity = main.getString("humidity");
            String main_temp_min = main.getString("temp_min");
            String main_temp_max = main.getString("temp_max");

            //id
            String id = root.getString("id");
            String name = root.getString("name");
            String cod = root.getString("cod");

            String quiz_result = "weather " + "\n" +
                    "id : " + weather_id +"\n" +
                    "main : " + weather_main +"\n" +
                    "description" + weather_description +"\n" +
                    "icon : " + weather_icon +"\n" +
                    "main : " +"\n" +
                    "temp : " + main_temp +"\n" +
                    "pressure : " + main_pressure +"\n" +
                    "humidity : "+ main_humidity +"\n" +
                    "temp_min : " + main_temp_min +"\n" +
                    "temp_max : " + main_temp_max +"\n" +
                    "id : " + id +"\n" +
                    "name : " + name + "\n" +
                    "cod : " + cod +"\n";
            tv_show.setText(quiz_result);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
