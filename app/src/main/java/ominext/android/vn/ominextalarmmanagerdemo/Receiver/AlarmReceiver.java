package ominext.android.vn.ominextalarmmanagerdemo.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ominext.android.vn.ominextalarmmanagerdemo.Activity.ShowAlarmDialogActivity;

/**
 * Created by MyPC on 03/07/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String key = intent.getExtras().getString("extra");
        Intent intenMusic = new Intent(context, MusicReceiver.class);
        intenMusic.putExtra("extra", key);
        context.startService(intenMusic);
        if (key.equals("on")) {
            Intent intentShowdialog = new Intent("android.intent.action.MAIN");
            intentShowdialog.setClass(context, ShowAlarmDialogActivity.class);
            intentShowdialog.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentShowdialog);
        }

    }
}
