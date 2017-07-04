package ominext.android.vn.ominextalarmmanagerdemo.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
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
    TextView tvTimeCurrent, tvRepeatFiveMin, tvRepeatDay, tvRepeatWeek;
    ImageButton imbOk;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private int iUniqueId = 0;
    private long interval;
    boolean ischeck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        imbOk = (ImageButton) findViewById(R.id.imb_ok);
        tvTimeCurrent = (TextView) findViewById(R.id.tv_curent_time);
        tvRepeatFiveMin = (TextView) findViewById(R.id.tv_repeat5min);
        tvRepeatDay = (TextView) findViewById(R.id.tv_repeat_day);
        tvRepeatWeek = (TextView) findViewById(R.id.tv_repeat_week);
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


        tvRepeatFiveMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRepeatFiveMin.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.red));
                tvRepeatDay.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                tvRepeatWeek.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                interval = 5 * 60 * 1000;
                ischeck = true;
            }
        });
        tvRepeatDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRepeatFiveMin.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                tvRepeatDay.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.red));
                tvRepeatWeek.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                interval = AlarmManager.INTERVAL_DAY;
                ischeck = true;
            }
        });
        tvRepeatWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvRepeatFiveMin.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                tvRepeatDay.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                tvRepeatWeek.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.red));
                interval = 7 * 24 * 60 * 60 * 1000;
                ischeck = true;
            }
        });


        imbOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                calendar.set(Calendar.SECOND, 0);
                String hour = String.valueOf(timePicker.getCurrentHour());
                String minute = String.valueOf(timePicker.getCurrentMinute());
                if (timePicker.getCurrentHour() > 12) {
                    hour = String.valueOf(timePicker.getCurrentHour() - 12);
                }
                if (timePicker.getCurrentMinute() < 10) {
                    minute = "0" + String.valueOf(timePicker.getCurrentMinute());
                }
                intent.putExtra("extra", "on");
                pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, iUniqueId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (ischeck) {
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);

                } else if (!ischeck) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                }
                tvTimeCurrent.setText(hour + ":" + minute);
                insertAlarm();
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }


    private void insertAlarm() {
        String s = tvTimeCurrent.getText().toString();
        ContentValues values = new ContentValues();
        values.put("time", s);
        values.put("iUniqueId", iUniqueId);
        database = Database.initDatabase(AddActivity.this, DATABASE_NAME);
        database.insert("Alarm", null, values);

    }
}