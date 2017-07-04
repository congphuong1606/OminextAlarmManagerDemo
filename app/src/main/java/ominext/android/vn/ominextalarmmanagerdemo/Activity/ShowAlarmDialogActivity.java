package ominext.android.vn.ominextalarmmanagerdemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import ominext.android.vn.ominextalarmmanagerdemo.R;
import ominext.android.vn.ominextalarmmanagerdemo.Receiver.AlarmReceiver;

public class ShowAlarmDialogActivity extends Activity {
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_alarm);
        imageButton = (ImageButton) findViewById(R.id.imb_off_music);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ShowAlarmDialogActivity.this, AlarmReceiver.class);
                intent.putExtra("extra", "off");
                sendBroadcast(intent);
            }
        });


    }


}
