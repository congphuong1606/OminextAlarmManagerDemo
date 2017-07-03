package ominext.android.vn.ominextalarmmanagerdemo.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by MyPC on 03/07/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("aaaaa", "tôi trong music");
        Intent intenMusic = new Intent(context, Music.class);

        context.startService(intenMusic);
    }
}
