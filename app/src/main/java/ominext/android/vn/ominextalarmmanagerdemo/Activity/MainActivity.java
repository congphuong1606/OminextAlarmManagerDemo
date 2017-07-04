package ominext.android.vn.ominextalarmmanagerdemo.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ominext.android.vn.ominextalarmmanagerdemo.Adapter.AlarmAdapter;
import ominext.android.vn.ominextalarmmanagerdemo.Database.Database;
import ominext.android.vn.ominextalarmmanagerdemo.Model.Alarm;
import ominext.android.vn.ominextalarmmanagerdemo.R;

public class MainActivity extends AppCompatActivity {
    static final String DATABASE_NAME = "Alarm.sqlite";
    ImageButton imbAddAlarm;
    TextView textView;
    private SQLiteDatabase database;
    Alarm alarm;
    ArrayList<Alarm> alarms = new ArrayList<>();
    private AlarmAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button add;
    private TimePickerDialog.OnTimeSetListener callbackTime;
    private String hours;
    private Calendar calendar;
    private Date gio;
    private String strTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imbAddAlarm = (ImageButton) findViewById(R.id.imb_add_alarm);
        imbAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new AlarmAdapter(this, R.layout.row_alarm, alarms);
        recyclerView.setAdapter(adapter);


        readData();


//        calendar = Calendar.getInstance();
//        SimpleDateFormat adt = null;
//        adt = new SimpleDateFormat("hh:mm a", Locale.getDefault());
//        strTime = adt.format(calendar.getTime());
//        textView.setText(strTime);
//        adt = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        textView.setTag(adt.format(calendar.getTime()));
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callbackTime = new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
//                        String s = hour + ":" + minute;
//                        int hourTam = hour;
//                        if (hourTam > 12)
//                            hourTam = hourTam - 12;
//                        textView.setText
//                                (hourTam + ":" + minute + (hour > 12 ? " PM" : " AM"));
//
//                        textView.setTag(s);
//
//                        calendar.set(Calendar.HOUR_OF_DAY, hour);
//                        calendar.set(Calendar.MINUTE, minute);
//                        gio = calendar.getTime();
//                    }
//                };
//                String s = textView.getTag() + "";
//                String strArr[] = s.split(":");
//                int gio = Integer.parseInt(strArr[0]);
//                int phut = Integer.parseInt(strArr[1]);
//                TimePickerDialog time = new TimePickerDialog(MainActivity.this, callbackTime, gio, phut, true);
//                time.setTitle("giờ hoàn thành");
//                time.show();
//                ContentValues values=new ContentValues();
//                values.put("time",strTime);
//                SQLiteDatabase database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
//                database.insert("Alarm",null,values);
//            }
//        });


    }

    private void readData() {
        database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select * from Alarm", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String time = cursor.getString(1);
            int iUniqueId = cursor.getInt(2);
            alarms.add(new Alarm(false, id, time, iUniqueId));
        }
        adapter.notifyDataSetChanged();
    }


}
