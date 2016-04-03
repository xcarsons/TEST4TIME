package com.test4time.test4time;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.util.Log;

import java.util.List;

/**
 * @Author - Carson Schaefer
 * Class responsible for monitoring which applications can run
 * and if enough time has been build to use blocked applications
 * Runs on a seperate thread in the background
 */
public class DeviceIntentService extends IntentService {
    private static final String TAG = "com.test4time.test4time";

    public DeviceIntentService() {
        super("DeviceIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    /*
         *
         */
    @Override
    protected void onHandleIntent(Intent intent) {
       // what the service does
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);

        while(true) {
            List<ActivityManager.RunningAppProcessInfo> tasks = manager.getRunningAppProcesses();
            Log.d("FOREGROUND",tasks.get(0).processName); //
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(tasks.get(0).processName.equals("com.pandora.android")) { // get foreground activity
                Intent test4Time = new Intent(getApplicationContext(), BlockedApps.class);
                test4Time.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(test4Time);
            }
        }
    }

}
