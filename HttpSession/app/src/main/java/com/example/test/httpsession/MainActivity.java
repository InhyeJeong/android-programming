package com.example.test.httpsession;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.spec.ECField;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    //  텍스트 뷰, 이미지 뷰 객체 생성
    TextView tv_data;
    ImageView iv_poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  객체 찾기
        tv_data = (TextView)findViewById(R.id.tv_data);
        iv_poster = (ImageView)findViewById(R.id.iv_poster);

        String url = "http://70.12.110.50:3000";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("number","1");

        //  onCreate함수 안에서 서버에서 가져오면 다른 것들이 동작 x
        // 따라서 asynkTask를 활용해서 data를 불러와야함
        MyHttpTask myHttpTask = new MyHttpTask(url, map);
        myHttpTask.execute();

        String url_img = "http://70.12.110.50:3000/files";
        HashMap<String, String> map_img = new HashMap<String, String>();
        map_img.put("number","1");

        MyImageHttpTask myImageHttpTask = new MyImageHttpTask(url_img, map_img);
        myImageHttpTask.execute();

    }

    class MyImageHttpTask extends AsyncTask<Void, Void, Bitmap> {

        String url_str; //  요청하려면 서버의 주소르 알아야함 //  결과물을 문자열로 받기 위한 변수
        HashMap<String, String> map;    //  쿼리 주소를 위한 키 (name, value)


        public MyImageHttpTask(String url_str, HashMap<String, String> map) {  //  쿼리 주소를 위한 키 (name, value)
            super();

            this.url_str = url_str;
            this.map = map;
        }

        //  실제 작업을 하는 함수
        //  이미지 받음 비트맵
        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap result = null;
            String post_query = "";
            PrintWriter printWriter = null;

            try {
                //  인터넷 웹서버 접속을 위한 설정
                URL text = new URL(url_str);    //  앞서 받은주소

                //  연결함
                HttpURLConnection http = (HttpURLConnection)text.openConnection();

                //  보내는 data의 header내용(보내는 data의 형태, 표시는 어떻게 할것인지 )
                http.setRequestProperty("Content-type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                http.setConnectTimeout(10000);  // 연결 끊길시 10초 기다림
                http.setReadTimeout(10000); //  읽다가 끊길시 10초 기다림
                http.setRequestMethod("POST");  //  포스트
                //  쓰기와 읽기를 동시에 쓴다.
                http.setDoInput(true);
                http.setDoOutput(true);

                //  실제 값을 보냄
                //  hashmap의 값이 있어야 보내짐(효율적으로)  (onCreate함수에 hashmap객체가 있음)
                if(map != null && map.size() > 0) {

                    Iterator<String> keys = map.keySet().iterator();

                    boolean first_query_part = true;
                    //  hashmap에서 1개 꺼내서
                    while(keys.hasNext()) {

                        if(!first_query_part) {
                            post_query += "&";
                        }

                        String key = keys.next();   // 키 값 넣고
                        // 맞는 value 꺼냄 > utf-8형태로 바꾸어서 붙혀줌
                        post_query += (key + "=" + URLEncoder.encode(map.get(key), "UTF-8"));

                        first_query_part = false;
                    }

                    // sending to server
                    printWriter = new PrintWriter(new OutputStreamWriter(   //객체생성
                            http.getOutputStream(), "UTF-8"));
                    printWriter.write(post_query);  // 쿼리스트링 보내기
                    printWriter.flush();    //  프린터 객체 비우기(다음 전송을 위해)

                    // receive from server
                    // 이미지를 읽어옴(서버가 제공하는)
                    //  비트맵으로 바로 만들어주는 함수 decodeStream() 넘나간단한것
                    result = BitmapFactory.decodeStream(http.getInputStream());

                }
            } catch(Exception e) {
                e.printStackTrace();
                result = null;
            } finally {
                try{
                    if(printWriter != null) printWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            // do something
            iv_poster.setImageBitmap(s);    //  이미지 뷰에 비트맵 이미지 표시
            this.cancel(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    class MyHttpTask extends AsyncTask<Void, Void, String> {

        String url_str;
        HashMap<String, String> map;

        public MyHttpTask(String url_str, HashMap<String, String> map) {
            super();

            this.url_str = url_str;
            this.map = map;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            String post_query = "";
            PrintWriter printWriter = null;
            BufferedReader bufferedReader = null;

            try {
                URL text = new URL(url_str);
                HttpURLConnection http = (HttpURLConnection)text.openConnection();
                http.setRequestProperty("Content-type",
                        "application/x-www-form-urlencoded;charset=UTF-8");
                http.setConnectTimeout(10000);
                http.setReadTimeout(10000);
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);

                if(map != null && map.size() > 0) {

                    Iterator<String> keys = map.keySet().iterator();

                    boolean first_query_part = true;
                    while(keys.hasNext()) {

                        if(!first_query_part) {
                            post_query += "&";
                        }

                        String key = keys.next();
                        post_query += (key + "=" + URLEncoder.encode(map.get(key), "UTF-8"));

                        first_query_part = false;
                    }

                    // sending to server
                    printWriter = new PrintWriter(new OutputStreamWriter(
                            http.getOutputStream(), "UTF-8"));
                    printWriter.write(post_query);
                    printWriter.flush();

                    // receive from server
                    //  데이터의 수신
                    //  버퍼에 저장
                    bufferedReader = new BufferedReader(new InputStreamReader(
                            http.getInputStream(), "UTF-8"));
                    //  1줄씩 읽음
                    StringBuffer stringBuffer = new StringBuffer(); //  대용량일때는 stringbuffer를 자주쓴다.
                    String line;

                    while((line = bufferedReader.readLine()) != null) { // buffer에 있는 내용을 1개 씩 불러옴.
                        stringBuffer.append(line);
                    }
                    //  result에 senddate라는 값이 저장되어 보내짐
                    result = stringBuffer.toString();
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally { // finally로 묶어서 예왜처리
                try{
                    if(printWriter != null) printWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if(bufferedReader != null) bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            // do something


            try {
                JSONObject root = new JSONObject(s);
                Log.d("HttpConnectionLog", root.getString("title"));
                Log.d("HttpConnectionLog", ""+(root.getInt("runningTime")));
                Log.d("HttpConnectionLog", root.getString("openDate"));

                JSONArray director = root.getJSONArray("director");
                JSONArray actor = root.getJSONArray("actor");
                JSONArray category = root.getJSONArray("category");

                for(int i = 0; i < director.length(); i++) {
                    Log.d("HttpConnectionLog", director.getString(i));
                }

                for(int j = 0; j < actor.length(); j++) {
                    Log.d("HttpConnectionLog", actor.getString(j));
                }
                for(int t = 0; t < category.length(); t++) {
                    Log.d("HttpConnectionLog", category.getString(t));
                }

            } catch(Exception e) {
                e.printStackTrace();
            }

            tv_data.setText(s);

            this.cancel(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
