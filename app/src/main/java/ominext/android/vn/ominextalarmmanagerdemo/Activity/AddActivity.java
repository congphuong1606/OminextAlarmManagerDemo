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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ominext.android.vn.ominextalarmmanagerdemo.Database.Database;
import ominext.android.vn.ominextalarmmanagerdemo.R;
import ominext.android.vn.ominextalarmmanagerdemo.Receiver.AlarmReceiver;

public class AddActivity extends AppCompatActivity {
    final String DATABASE_NAME = "Alarm.sqlite";
    SQLiteDatabase database;

    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @BindView(R.id.timpicker)
    TimePicker timpicker;
    @BindView(R.id.tv_repeat5min)
    TextView tvRepeat5min;
    @BindView(R.id.tv_repeat_day)
    TextView tvRepeatDay;
    @BindView(R.id.tv_repeat_week)
    TextView tvRepeatWeek;
    @BindView(R.id.tv_curent_time)
    TextView tvCurentTime;
    @BindView(R.id.imb_ok)
    ImageButton imbOk;
    private int iUniqueId = 0;
    private long interval;
    boolean ischeck = false;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        intent = new Intent(AddActivity.this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        SimpleDateFormat adt = null;
        adt = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = adt.format(calendar.getTime());
        tvCurentTime.setText(strTime);
        adt = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvCurentTime.setTag(adt.format(calendar.getTime()));



    }


    private void insertAlarm() {
        String s = tvCurentTime.getText().toString();
        ContentValues values = new ContentValues();
        values.put("time", s);
        values.put("iUniqueId", iUniqueId);
        database = Database.initDatabase(AddActivity.this, DATABASE_NAME);
        database.insert("Alarm", null, values);

    }

    @OnClick({R.id.tv_repeat5min, R.id.tv_repeat_day, R.id.tv_repeat_week, R.id.imb_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_repeat5min:
                tvRepeat5min.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.red));
                tvRepeatDay.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                tvRepeatWeek.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                interval = 5 * 60 * 1000;
                ischeck = true;
                break;
            case R.id.tv_repeat_day:
                tvRepeat5min.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                tvRepeatDay.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.red));
                tvRepeatWeek.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                interval = AlarmManager.INTERVAL_DAY;
                ischeck = true;
                break;
            case R.id.tv_repeat_week:
                tvRepeat5min.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                tvRepeatDay.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.nau));
                tvRepeatWeek.setBackgroundColor(AddActivity.this.getResources().getColor(R.color.red));
                interval = 7 * 24 * 60 * 60 * 1000;
                ischeck = true;
                break;
            case R.id.imb_ok:
                iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

                calendar.set(Calendar.HOUR_OF_DAY, timpicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timpicker.getCurrentMinute());
                calendar.set(Calendar.SECOND, 0);
                String hour = String.valueOf(timpicker.getCurrentHour());
                String minute = String.valueOf(timpicker.getCurrentMinute());
                if (timpicker.getCurrentHour() > 12) {
                    hour = String.valueOf(timpicker.getCurrentHour() - 12);
                }
                if (timpicker.getCurrentMinute() < 10) {
                    minute = "0" + String.valueOf(timpicker.getCurrentMinute());
                }

                intent.putExtra("extra", "on");
                pendingIntent = PendingIntent.getBroadcast(
                        AddActivity.this, iUniqueId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (ischeck) {
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);

                } else if (!ischeck) {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                }
                tvCurentTime.setText(hour + ":" + minute);
                insertAlarm();
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }
}