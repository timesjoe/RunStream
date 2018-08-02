package com.example.pichai.runstream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.VideoView;

public class ManualActivity extends Activity {

    VideoView vdo;
    Button btn1, btn2, btn3;
    int sl;
    int a = 0;
    int b,x;
    int y = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        Bundle bundle = getIntent().getExtras();
        sl = bundle.getInt("MyA");
        b = sl+1;
        Log.d("MyA", String.valueOf(sl));
        vdo = (VideoView)findViewById(R.id.videoView2);
        btn1 = (Button)findViewById(R.id.button2);
        btn2 = (Button)findViewById(R.id.button3);
        btn3 = (Button)findViewById(R.id.button4);

        run();

    }



    String[] num={"http://96.30.124.232:1935/EDGE_T1/smil:5HD_ABR.smil/playlist.m3u8","http://96.30.124.232:1935/EDGE_T3/smil:NBT_ABR.smil/playlist.m3u8","http://96.30.124.232:1935/EDGE_T1/smil:TPBS_ABR.smil/playlist.m3u8","http://96.30.124.232:1935/EDGE_T1/smil:3Family_ABR.smil/playlist.m3u8"};
    String[] name1={"5HD","NBT","Thai PBS","3Family"};


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                sl = sl-1;
                if(sl<0){
                    sl=num.length-1;
                    vdo.setVideoPath(num[sl]);
                    btn2.setText(num.length+"/4");
                    btn3.setText("Name : "+name1[sl]);}
                else{
                    int l = sl+1;
                    vdo.setVideoPath(num[sl]);
                    btn2.setText(l+"/4");
                    btn3.setText("Name : "+name1[sl]);}
                Log.d("LEFT", String.valueOf(sl));
                Log.d("OnKey", "Key_Left");
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                sl = sl+1;
                if(sl > num.length-1){
                    sl = 0;
                    vdo.setVideoPath(num[0]);
                    btn2.setText("1"+"/4");
                    btn3.setText("Name : "+name1[0]);}

                else{
                    int v = sl+1;
                    Log.d("RIGHT = ", String.valueOf(sl));
                    vdo.setVideoPath(num[sl]);
                    btn2.setText(v+"/4");
                    btn3.setText("Name : "+name1[sl]);}
                Log.d("--", String.valueOf(sl));
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                x = sl;
                Log.d("X = ", String.valueOf(x));
                Intent intent = new Intent(ManualActivity.this, MainActivity.class);
                intent.putExtra("St", x);
                intent.putExtra("BackM", y);
                startActivity(intent);
                Log.d("OnKey", "Key_Center");
                break;
            default:    Log.d("OnKey", "No Input");
                return true;
        }
        return false;
    }

    public void run() {
        Log.d("B = ", String.valueOf(b));
        String numb=Integer.toString(b);
        vdo.setVideoPath(num[sl]);
        Log.d("sl =", String.valueOf(sl));
        btn2.setText(numb+"/4");
        btn3.setText("Name : "+name1[sl]);
        vdo.start();
        a++;
        b++;
        Log.d("Run Manual : ", num[sl]);
        if(a > num.length-1){
            a = 0;
        }
        if(b == num.length+1){
            b = 1;
        }

    }

}
