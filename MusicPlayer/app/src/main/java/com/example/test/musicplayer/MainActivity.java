package com.example.test.musicplayer;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    boolean bReadPerm = false;
    Button button_play, button_stop;
    boolean bStatePlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPermission();

        button_play = (Button) findViewById(R.id.button_play);
        button_stop = (Button) findViewById(R.id.button_stop);

        button_play.setOnClickListener(new myButtonListener());
        button_stop.setOnClickListener(new myButtonListener());

        registerReceiver(receiver, new IntentFilter("rj.myplayerservice"));

        if(bReadPerm) {
            String state = Environment.getExternalStorageState();

            if (state.equals(Environment.MEDIA_MOUNTED)) {
                try {
                    String musicPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/paulkim.mp3";
                    Intent intent = new Intent(MainActivity.this, PlayService.class);
                    intent.putExtra("filePath", musicPath);
                    startService(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MyPlayerService_log", "Main onDestroy()");
        unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra("state");

            if(state != null) {
                if (state.equals("play")) {
                    bStatePlay = true;
                    button_play.setText("Pause");
                } else if(state.equals("pause") || state.equals("stop")) {
                    bStatePlay = false;
                    button_play.setText("Play");
                }
            }
        }
    };

    class myButtonListener implements View.OnClickListener {
        Intent intent;

        @Override
        public void onClick(View view) {
            intent = new Intent("rj.myplayerservice");
            switch(view.getId()) {
                case R.id.button_play:
                    if(bStatePlay) {
                        intent.putExtra("btn", "pause");
                    } else {
                        intent.putExtra("btn", "play");
                    }
                    break;
                case R.id.button_stop:
                    intent.putExtra("btn", "stop");
                    break;
            }
            sendBroadcast(intent);
        }
    }

    private void setPermission() {
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            bReadPerm = true;
        }


        if(!bReadPerm) {
            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 200);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200 && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bReadPerm = true;
            }
        }
    }
}