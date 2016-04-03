package com.test4time.test4time;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
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
     * Start service even if Test4Time application is killed
     */
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);
        super.onTaskRemoved(rootIntent);
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
