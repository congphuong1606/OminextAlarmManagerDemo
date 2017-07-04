package ominext.android.vn.ominextalarmmanagerdemo.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ominext.android.vn.ominextalarmmanagerdemo.Activity.MainActivity;
import ominext.android.vn.ominextalarmmanagerdemo.Database.Database;
import ominext.android.vn.ominextalarmmanagerdemo.Model.Alarm;
import ominext.android.vn.ominextalarmmanagerdemo.R;
import ominext.android.vn.ominextalarmmanagerdemo.Receiver.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by MyPC on 03/07/2017.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    static final String DATABASE_NAME = "Alarm.sqlite";
    SQLiteDatabase database;
    int mPosition;
    MainActivity mainActivity;
    ArrayList<Alarm> alarms = new ArrayList<>();

    public AlarmAdapter(MainActivity mainActivity, int row_layout, ArrayList<Alarm> alarms) {

        this.mainActivity = mainActivity;
        this.alarms = alarms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_alarm, parent, false);
        return new ViewHolder(view);

    }

    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Alarm alarm = alarms.get(position);
        mPosition = position;
        holder.tvTime.setText(String.valueOf(alarm.getTime()));
        holder.imbOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainActivity);
                alertDialog.setIcon(R.drawable.delete);
                alertDialog.setTitle("Xác nhận xóa");
                alertDialog.setMessage("Bạn có chắc chắn muốn xóa báo thức  này không?");
                alertDialog.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAlarm();
                    }
                });
                alertDialog.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = alertDialog.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
                lp.dimAmount = 0.0f;
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.show();
            }
        });
    }

    private void deleteAlarm() {
        final Alarm alarm = alarms.get(mPosition);
        database = Database.initDatabase(mainActivity, DATABASE_NAME);
        database.delete("Alarm", "id = ? ", new String[]{alarm.getId() + ""});
        alarms.remove(mPosition);
        Integer i = Integer.valueOf(alarm.getiUniqueId());
        AlarmManager alarmManager = (AlarmManager) mainActivity.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(mainActivity, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mainActivity,
                i, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        ImageButton imbOff;


        public ViewHolder(View itemView) {
            super(itemView);
            final ArrayList<Alarm> alarms = new ArrayList<>();
            tvTime = (TextView) itemView.findViewById(R.id.tv_timer);
            imbOff = (ImageButton) itemView.findViewById(R.id.imb_off);


        }
    }

}
