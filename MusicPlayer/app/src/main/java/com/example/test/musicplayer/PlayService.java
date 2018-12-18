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

    MediaPlayer player;
    String filePath;

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String btn = intent.getStringExtra("btn");

            Intent intent1 = new Intent("rj.myplayerservice");
            if(btn != null) {
                if(btn.equals("play") || btn.equals("pause")) {
                    if(player.isPlaying()) {
                        player.pause();
                        intent1.putExtra("state", "pause");
                    } else {
                        player.start();
                        intent1.putExtra("state", "play");
                    }
                } else if(btn.equals("stop")) {
                    player.stop();
                    try {
                        intent1.putExtra("state", "stop");
                        player.prepare();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                sendBroadcast(intent1);
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        filePath = intent.getStringExtra("filePath");
        if(filePath != null) {
            try {
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Intent intent = new Intent("rj.myplayerservice");
                        intent.putExtra("state", "stop");
                        sendBroadcast(intent);
                        stopSelf();
                    }
                });
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
        player = new MediaPlayer();

        registerReceiver(receiver, new IntentFilter("rj.myplayerservice"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyPlayerService_log", "Service onDestroy()");
        unregisterReceiver(receiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}