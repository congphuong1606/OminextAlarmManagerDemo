package ominext.android.vn.ominextalarmmanagerdemo.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ominext.android.vn.ominextalarmmanagerdemo.R;
import ominext.android.vn.ominextalarmmanagerdemo.Receiver.AlarmReceiver;

public class ShowAlarmDialogActivity extends Activity {
    ImageButton imageButton;
    TextView tvRepeatOneMin;
    private PendingIntent pendingIntent;
    private Intent intent;
    MediaPlayer mediaPlayer = null;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_alarm);
        mediaPlayer = MediaPlayer.create(ShowAlarmDialogActivity.this, R.raw.ghen);
        imageButton = (ImageButton) findViewById(R.id.imb_off_music);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                finish();
            }
        });

        tvRepeatOneMin = (TextView) findViewById(R.id.tv_repeat);
        tvRepeatOneMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ShowAlarmDialogActivity.this, "hahahah", Toast.LENGTH_SHORT).show();
                finish();
                mediaPlayer.stop();
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(ShowAlarmDialogActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(ShowAlarmDialogActivity.this,
                        0, intent, 0);
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60 * 1000, pendingIntent);

            }
        });
        playSound(this, getAlarmUri());


    }

    private void playSound(final Context context, Uri alert) {
        Thread backgroud = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.start();
                } catch (Throwable e) {
                    Log.i("loi phat nhac", "" + e);
                }
            }
        });
        backgroud.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    private Uri getAlarmUri() {
        Uri uri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (uri == null) {
            uri = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (uri == null) {
                uri = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return uri;
    }
}
