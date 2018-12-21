package com.example.test.httpsession;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    //  텍스트 뷰, 이미지 뷰 객체 생성
    TextView tv_data;
    ImageView iv_poster;

    // db 변수생성
    Realm mRealm;

    //  onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  객체 찾기
        tv_data = (TextView)findViewById(R.id.tv_data);
        iv_poster = (ImageView)findViewById(R.id.iv_poster);
        //  변수 초기화
        Realm.init(MainActivity.this);
        mRealm = Realm.getDefaultInstance();    //  like singletone


        try {
            //  data가 있으면 찾아주고 DB에 저장

            // text target 찾기
            MovieVO target = mRealm.where(MovieVO.class).equalTo("number", 1).findFirst();
            //  String result에 원하는 형식으로 저장
            String result = "title : " + target.getTitle() + //  get으로 찾기
                            "director : " +target.getDirector() +
                            "actor : " +  target.getActor() +
                            "category : " + target.getCategory() +
                            "runningTime : " + target.getRunningTime() +
                            "openDate : " + target.getOpenDate();
            //  tv data에 setText(result);해서 출력
            tv_data.setText(result);


            //  image target 찾기
            ImageVO target_image = mRealm.where(ImageVO.class).equalTo("number", 1).findFirst();
            //  Bitmap result에 원하는 형식으로 이미지 저장
            byte[] image_byte = target_image.getPoster();
            //  byte->bitmap oncrate 바깥의 byteArrayToBitmap()사용
            Bitmap image_result = byteArrayToBitmap(image_byte);

            //   iv_poster data에 image_result해서 출력
            iv_poster.setImageBitmap(image_result);
            // 사진 찾기
        } catch (Exception e) {
            // 에러나면 첫번째 이미지 보여줌

            //  글자
            String url = "http://70.12.110.66:3000";
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("number","1");

            //  onCreate함수 안에서 서버에서 가져오면 다른 것들이 동작 x
            // 따라서 asynkTask를 활용해서 data를 불러와야함
            MyHttpTask myHttpTask = new MyHttpTask(url, map);
            myHttpTask.execute();

            //  이미지
            String url_img = "http://70.12.110.66:3000/files";
            HashMap<String, String> map_img = new HashMap<String, String>();
            map_img.put("number","1");

            MyImageHttpTask myImageHttpTask = new MyImageHttpTask(url_img, map_img);
            myImageHttpTask.execute();
        }

    }   //oncreate


    //  byte값을 bitmap으로 변환해야함
    public Bitmap byteArrayToBitmap( byte[] byteArray ) {
        Bitmap bitmap = BitmapFactory.decodeByteArray( byteArray, 0, byteArray.length ) ;
        return bitmap ;
    }




    //  이미지 받아옴
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

            // bitmap(s) 복사
            Bitmap bitmap = s.copy(s.getConfig(), true);
            // bitmap -> byte
            ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
            bitmap.compress( Bitmap.CompressFormat.PNG, 100, stream) ;
            final byte[] byte_result = stream.toByteArray() ;


            //  DB에 data 저장
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ImageVO imageVO = realm.createObject(ImageVO.class);

                    imageVO.setPoster(byte_result);//()안에 byte가 와야함...
                    String number_string = map.get("number");
                    int number_int = Integer.parseInt(number_string);
                    imageVO.setNumber(number_int);
                }
            });
            this.cancel(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    //  글자 받아옴
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

                final String title = root.getString("title");
                final String runningTime = String.valueOf(root.getInt("runningTime"));
                final String openDate = root.getString("openDate");
                final String number = root.getString("number");

                JSONArray director_array = root.getJSONArray("director");
                JSONArray actor_array = root.getJSONArray("actor");
                JSONArray category_array = root.getJSONArray("category");

                //  string으로
                String director = "";
                String actor = "";
                String category = "";

                for(int i = 0; i < director_array.length(); i++) {
                    //  첫번째 array에는 ","가 출력 안되도록
                    if(i != 0) {
                        director += ", ";
                    }
                    director += director_array.getString(i);

                }

                for(int j = 0; j < actor_array.length(); j++) {
                    if(j != 0) {
                        actor += ", ";
                    }
                    actor += actor_array.getString(j);

                }
                for(int t = 0; t < category_array.length(); t++) {
                    if(t != 0) {
                        category += ", ";
                    }
                    category += category_array.getString(t);

                }
                //  아래에서 활용하기 위한 final 변수 선언
                final String director_final= director;
                final String actor_final= actor;
                final String category_final = category;

                String result = "number : " + number + "\n" +
                                "title : "  + title + "\n" +
                                "director : " + director  + "\n" +
                                "actor : " + actor  + "\n" +
                                "category : " + category  + "\n" +
                                "runningTime : " + runningTime  + "\n" + "분" +
                                "openDate : " + openDate  + "\n";
                tv_data.setText(result);

                //  DB에 data 저장
                mRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        MovieVO movieVO = realm.createObject(MovieVO.class);

                        movieVO.setTitle(title);
                        movieVO.setDirector(director_final);
                        movieVO.setActor(actor_final);
                        movieVO.setCategory(category_final);
                        movieVO.setRunningTime(runningTime);
                        movieVO.setOpenDate(openDate);
                        movieVO.setNumber(Integer.parseInt(number));

                    }
                });
            } catch(Exception e) {
                e.printStackTrace();
            }



            this.cancel(true);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
