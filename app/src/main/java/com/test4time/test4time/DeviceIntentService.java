package com.test4time.test4time;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
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

    /*
     *
     */
    @Override
    protected void onHandleIntent(Intent intent) {
       // what the service does
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        List< ActivityManager.RunningTaskInfo > runningTaskInfo = manager.getRunningTasks(1);
        while(true) {
            ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
            if(componentInfo.getPackageName().equals("")) {

            }
        }
    }

}
