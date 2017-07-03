package ominext.android.vn.ominextalarmmanagerdemo.Adapter;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ominext.android.vn.ominextalarmmanagerdemo.Database.Database;
import ominext.android.vn.ominextalarmmanagerdemo.Activity.MainActivity;
import ominext.android.vn.ominextalarmmanagerdemo.Model.Alarm;
import ominext.android.vn.ominextalarmmanagerdemo.R;

/**
 * Created by MyPC on 03/07/2017.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {
    static final String DATABASE_NAME = "Alarm.sqlite";
    SQLiteDatabase database;

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
        holder.tvTime.setText(String.valueOf(alarm.getTime()));
        holder.btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = Database.initDatabase(mainActivity, DATABASE_NAME);
                database.delete("Alarm", "id = ? ", new String[]{alarm.getId() + ""});
                alarms.remove(position);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        Button btnOff;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tv_timer);
            btnOff = (Button) itemView.findViewById(R.id.btn_off);

        }
    }

}
