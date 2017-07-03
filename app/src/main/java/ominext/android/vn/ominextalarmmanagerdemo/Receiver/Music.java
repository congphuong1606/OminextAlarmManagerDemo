package ominext.android.vn.ominextalarmmanagerdemo.Receiver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import ominext.android.vn.ominextalarmmanagerdemo.R;

/**
 * Created by MyPC on 03/07/2017.
 */

public class Music extends Service {
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(this, R.raw.pop);
        mediaPlayer.start();
        mediaPlayer.start();
        mediaPlayer.start();
        Log.e("aaaaa", "tôi trong music");
        return START_NOT_STICKY;
    }
}
