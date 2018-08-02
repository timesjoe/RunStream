package com.example.pichai.runstream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.VideoView;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {
    Timer timer;
    VideoView vdo;
    Button btn;
    int a = 0;
    int b = 1;
    int c,x;
    int y,z,p;
    long t= System.currentTimeMillis();
    long end = t+10000;
    String[] num = {"http://96.30.124.232:1935/EDGE_T1/smil:5HD_ABR.smil/playlist.m3u8", "http://96.30.124.232:1935/EDGE_T3/smil:NBT_ABR.smil/playlist.m3u8","http://96.4654630.124.232:1935/EDGE_T1/smil:TPBS_ABR.smil/playlist.m3u8","http://96.30.124.232:1935/EDGE_T1/smil:3Family_ABR.smil/playlist.m3u8"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Intent intent = this.getIntent ( );
        int ft = intent.getIntExtra ("St",x);
        int fs = intent.getIntExtra ("BackM",z);
        y = ft;
        p = fs;
        Log.d("question",String.valueOf(ft));
        Log.d("ok",String.valueOf(fs));

        vdo = (VideoView)findViewById(R.id.videoView);
        btn = (Button)findViewById(R.id.button);
        timer = new Timer();
        timer.schedule(new MyTask(), 0, 10000);


    }

    public void checkstream(){
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(num[a]).openConnection();
                conn.setConnectTimeout(10000); //timeout after 10 seconds
                conn.setReadTimeout(10000);
                Log.d("checkstream  :", "Check "+num[a]);
                conn.setRequestMethod("GET");
                conn.setInstanceFollowRedirects(true);
                conn.setUseCaches(false);

                int resultServer = conn.getResponseCode();
                if (resultServer == HttpURLConnection.HTTP_OK) {
                    Log.d("checkstream  :", "Connect");
                    //countDown();
                    Log.d("checkstream  :", String.valueOf(a));
                } else {
                    Log.d("checkstream  :", "Error Code");
                    changeAc();
                }
               /* conn.disconnect();
                Log.d("checkstream  :", "disconnect");*/
            } catch (IOException e) {
                Log.d("checkstream  :", "IOException");
                changeAc();
            }
    }

    class MyTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    checkstream();
                    if(p ==1){
                        a = y;
                        b = y+1;
                        p = 0;}
                    String numb=Integer.toString(b);
                    vdo.setVideoPath(num[a]);
                    btn.setText(numb+"/4");
                    vdo.start();
                    Log.d("Play :", num[a]);

                    a++;
                    b++;
                    c = a-1;
                    if(a > num.length-1){
                        a = 0;
                    }
                    if(b == num.length+1){
                        b = 1;
                    }
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("debug", "we are here");
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                Log.d("OnKey", "Key_Center");
                Log.d("NEW A", String.valueOf(c));
                Intent intent = new Intent(MainActivity.this, ManualActivity.class);
                intent.putExtra("MyA", c);
                startActivity(intent);
                finish();
                System.exit(0);
                Log.d("changeActivity", "Manual Mode");
                break;
            default:    Log.d("OnKey", "No Input");
            return true;
        }
        return false;
    }
    public void changeAc(){
        Intent intent = new Intent(MainActivity.this, ManualActivity.class);
        intent.putExtra("MyA", c);
        startActivity(intent);
        finish();
        System.exit(0);
        Log.d("changeActivity", "Manual Mode");
    }
    //public static Activity fa;

    protected static void closeActivity(Activity activity) {
        activity.finish();
    }



}
