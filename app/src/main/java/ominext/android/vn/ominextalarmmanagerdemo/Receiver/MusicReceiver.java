package ominext.android.vn.ominextalarmmanagerdemo.Receiver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import ominext.android.vn.ominextalarmmanagerdemo.R;

/**
 * Created by MyPC on 03/07/2017.
 */

public class MusicReceiver extends Service {
    MediaPlayer mediaPlayer;
    int id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String key = intent.getExtras().getString("extra");

        if (key.equals("on")) {
            id = 1;
        } else if (key.equals("off")) {
            id = 0;
        }
        if (id == 1) {
            mediaPlayer = MediaPlayer.create(this, R.raw.ghen);
            mediaPlayer.start();
        } else {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        return START_NOT_STICKY;
    }
}
