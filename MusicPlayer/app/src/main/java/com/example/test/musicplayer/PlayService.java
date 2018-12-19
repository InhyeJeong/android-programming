package com.example.test.musicplayer;

/**
 * Created by okooo on 2018-12-18.
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class PlayService extends Service {

    MediaPlayer player; // mp3파일을 재생하는 MediaPlayer 객체 변수
    String filePath;// mp3파일의 경로를 저장하는 변수

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 액티비티에서 브로드캐스팅 된 인텐트를 리시버가 받아서
            // 인텐트 안에 저장된 내용을 읽어본다.
            String btn = intent.getStringExtra("btn");

            // 서비스에서 액티비티로 응답하기 위한 인텐트트
            Intent intent1 = new Intent("com.example.test.musicplayer");
            // btn 안의 값이 null 값이 없음, play, stop, pause 중 1
            if(btn != null) {
                if(btn.equals("play") || btn.equals("pause")) {
                    // 현재 파일이 재생중이면
                    if(player.isPlaying()) {
                        // 일시 중지 기능을 수행
                        player.pause();

                        // 일시 중지 기능을 수행한 상태를 인텐트에 기록
                        intent1.putExtra("state", "pause");
                    } else {
                        // 재생 기능 수행
                        player.start();

                        // 재생 기능을 수행한 상태를 인텐트에 기록
                        intent1.putExtra("state", "play");
                    }
                } else if(btn.equals("stop")) {
                    // 재생을 정지 하는 기능 수행
                    player.stop();
                    try {
                        // 중지 기능을 수행한 상태를 인텐트에 기록
                        intent1.putExtra("state", "stop");
                        player.prepare();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                // 수행한 상태를 기록한 인텐드를 패키지 내에서
                // 브로드 캐스팅 한다.
                sendBroadcast(intent1);
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 처음 실행할 때 엑티비티로 부터 받은 인텐트에서
        // 음악파일의 경로를 얻어온다.

        // null 혹은 SD카드 안의 mp3 파일의 경로가 filePath 변수에 저장
        filePath = intent.getStringExtra("filePath");
        if(filePath != null) {
            // 파일의 경로가 있다면
            try {
                // mediaplayer에서 음악 재생이 완료되면
                // 호출되는 OnCompletionListener()를 활용해서
                // 음악 파일의 재생이 완료 되었다면
                // 엑티비디에 결과를 보고해야 한다.
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        // 액티비티의 리시버에게 전달할 인텐트를 만든다.
                        Intent intent = new Intent("com.example.test.musicplayer");
                        // 실행한 결과를 인텐트에 기록한다.
                        intent.putExtra("state", "stop");
                        // 인텐트를 패키지 안에서 브로드 캐스트를 한다.
                        sendBroadcast(intent);
                        // 서비스를 종료하는 코드
                        stopSelf();
                    }
                });
                // 경로에 있는 파일을 읽어와서 재생을 준비
                player.setDataSource(filePath);
                player.prepare();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        Log.d("MyPlayerService_log", "Service onStartCommend()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // mp3 재생을 위한 MediaPlayer 객체를 생성한다.
        player = new MediaPlayer();
        // 액티비티와 통신을 위한 리시버를 등록한다.
        registerReceiver(receiver, new IntentFilter("com.example.test.musicplayer"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyPlayerService_log", "Service onDestroy()");
        // 서비스가 종료될때 리시버를 등록 해제한다.
        unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}