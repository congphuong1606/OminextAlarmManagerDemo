package ominext.android.vn.ominextalarmmanagerdemo.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ominext.android.vn.ominextalarmmanagerdemo.Database.Database;
import ominext.android.vn.ominextalarmmanagerdemo.R;
import ominext.android.vn.ominextalarmmanagerdemo.Receiver.AlarmReceiver;

public class AddActivity extends AppCompatActivity {
    final String DATABASE_NAME = "Alarm.sqlite";
    SQLiteDatabase database;
    TextView tvOk, tvTimeCurrent;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvTimeCurrent = (TextView) findViewById(R.id.tv_curent_time);
        timePicker = (TimePicker) findViewById(R.id.timpicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(AddActivity.this, AlarmReceiver.class);
        calendar = Calendar.getInstance();
        SimpleDateFormat adt = null;
        adt = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = adt.format(calendar.getTime());
        tvTimeCurrent.setText(strTime);
        adt = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvTimeCurrent.setTag(adt.format(calendar.getTime()));

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();
                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);
                if (gio > 12) {
                    string_gio = String.valueOf(gio - 12);
                }
                if (phut < 10) {
                    string_phut = "0" + String.valueOf(phut);
                }
                pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                tvTimeCurrent.setText(string_gio + ":" + string_phut);
                insertAlarm();
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void insertAlarm() {
        String s = tvTimeCurrent.getText().toString();
        ContentValues values = new ContentValues();
        values.put("time", s);
        database = Database.initDatabase(AddActivity.this, DATABASE_NAME);
        database.insert("Alarm", null, values);

    }
}
