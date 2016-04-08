package com.test4time.test4time;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.EventListener;

/**
 * Created by carsonschaefer on 4/7/16.
 */
public class EnterPin extends Activity {
    private EditText pinTb;
    private TextView enterPinMsg;
    private Button openAppBtn;
    private Button t4tButton;
    private String appLaunch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Intent i = new Intent(this, DeviceIntentService.class);
//        startService(i);

        setContentView(R.layout.enterpin);

        pinTb = (EditText) findViewById(R.id.pinTb);
        enterPinMsg = (TextView) findViewById(R.id.enterPinMsg);
        openAppBtn = (Button) findViewById(R.id.openAppBtn);
        openAppBtn.setOnClickListener(new ClickListener());
        t4tButton = (Button) findViewById(R.id.t4tBtn);
        t4tButton.setOnClickListener(new ClickListener());

        Intent recIntent = getIntent();
        appLaunch = recIntent.getStringExtra("appLaunched");

    }

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.t4tBtn:
                    Intent test4Time = new Intent(getApplicationContext(), Users.class);
                    test4Time.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(test4Time);
                    break;

                case R.id.openAppBtn:
                    PackageManager manager = getPackageManager();
                    Intent appLaunched = manager.getLaunchIntentForPackage(appLaunch);
                    appLaunched.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(appLaunched);
                    break;

                default:
                    break;
            }
        }
    }
}
