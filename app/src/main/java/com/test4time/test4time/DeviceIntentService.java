package com.test4time.test4time;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.*;
import android.os.Process;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author - Carson Schaefer
 * Class responsible for monitoring which applications can run
 * and if enough time has been build to use blocked applications
 * Runs on a seperate thread in the background
 */
public class DeviceIntentService extends IntentService {
    private static final String TAG = "com.test4time.test4time";
    private ArrayList<String> blockApps;

    public DeviceIntentService() {
        super("DeviceIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        populateBlockApps();
        return START_STICKY;
    }

    /*
     * Start service if Test4Time application is killed
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

        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);

        while(true) {
            List<ActivityManager.RunningAppProcessInfo> tasks = manager.getRunningAppProcesses();

            if(blockApps.contains(tasks.get(0).processName)) {// get foreground activity
                Intent test4Time = new Intent(getApplicationContext(), BlockedApps.class);
                test4Time.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(test4Time);
            }
        }
    }

    /*
     * Populate ArrayList<String> of blocked apps
     * Stores the processName of the application
     */
    private void populateBlockApps() {
        blockApps = new ArrayList<String>();
        Database db = new Database(getApplicationContext(), null, null, 0, null);
        Cursor data = db.getBlockedApps();
        data = db.getBlockedApps();
        // get list of blocked apps from BLOCKAPPS table, add them to the list of selected (checked) apps
        while (data.moveToNext()) {
            Application app = new Application(data.getString(1), data.getString(2), data.getString(3));
            blockApps.add(data.getString(3));
        }
        db.close();
        blockApps.remove(TAG); // remove the test4time process if it exists
    }

}
