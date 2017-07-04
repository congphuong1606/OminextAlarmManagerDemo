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

            Intent intentShow = new Intent("android.intent.action.MAIN");
            intentShow.setClass(context, ShowAlarmDialogActivity.class);
            intentShow.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentShow);



    }
}
