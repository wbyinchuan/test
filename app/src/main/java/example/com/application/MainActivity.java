package example.com.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ScoreBoardView scoreBoardView;
    AudioManager audioManager;
    MyVolumeReceiver mVolumeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(this));

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE) ;
        myRegisterReceiver();
//        scoreBoardView = (ScoreBoardView) findViewById(R.id.text);
//        final Random random=new Random();
//        scoreBoardView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                scoreBoardView.setColor(Color.BLUE);
//                scoreBoardView.setScore(random.nextInt(28));
//                scoreBoardView.setWinDrawLose(random.nextInt(12),random.nextInt(15),random.nextInt(26));
//            }
//        });
    }

    /**
     * 注册当音量发生变化时接收的广播
     */
    private void myRegisterReceiver(){
        mVolumeReceiver = new MyVolumeReceiver();
        IntentFilter filter = new IntentFilter() ;
        filter.addAction("android.media.VOLUME_CHANGED_ACTION") ;

        registerReceiver(mVolumeReceiver, filter) ;
    }

    /**
     * 处理音量变化时的界面显示
     * @author long
     */
    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果音量发生变化则更改seekbar的位置
            if(intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")){
                int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前的媒体音量
                Log.e("音量键", "当前音量" + currVolume);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mVolumeReceiver);

    }
}
