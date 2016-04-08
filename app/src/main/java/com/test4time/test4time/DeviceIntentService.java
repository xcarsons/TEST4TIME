package com.test4time.test4time;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
     * Logic of the intent service
     * Current Foreground activity is monitored, if it's in the block apps list
     * Redirect to Test4Time app
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        // open settings to let user grant Test4Time access to usage data
        if (needPermissionForBlocking(this)) {
                Intent settings = new Intent("android.settings.USAGE_ACCESS_SETTINGS");//Settings.ACTION_USAGE_ACCESS_SETTINGS);
                settings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(settings);
        }
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);

        while(true) {
            List<ActivityManager.RunningAppProcessInfo> tasks = manager.getRunningAppProcesses(); // used for android 5.0
            List<ActivityManager.RunningTaskInfo> taskInfo = manager.getRunningTasks(1); // used for older versions of android

            String p = getTopPackage(); // used for android 5.1.1 and above

            if(blockApps.contains(tasks.get(0).processName) || blockApps.contains(taskInfo.get(0).topActivity.getPackageName()) || blockApps.contains(p)) {// get foreground activity
                Intent test4Time = new Intent(getApplicationContext(), EnterPin.class);
                test4Time.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String appLaunch = blockApps.contains(tasks.get(0).processName) ? tasks.get(0).processName :
                        blockApps.contains(taskInfo.get(0).topActivity.getPackageName()) ? taskInfo.get(0).topActivity.getPackageName() :p;

                test4Time.putExtra("appLaunched", appLaunch);
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
        data.close();
        db.close();
        blockApps.remove(TAG); // remove the test4time process if it exists
    }

    /*
     * get the package of the application running in the foreground
     */
    private String getTopPackage(){
        long ts = System.currentTimeMillis();
        //noinspection ResourceType
        UsageStatsManager mUsageStatsManager = (UsageStatsManager)this.getSystemService("usagestats");
        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts-1000, ts);
        if (usageStats == null || usageStats.size() == 0) { // why is size 0?
            return "NONE_PKG";
        }
        Collections.sort(usageStats, new RecentUseComparator());
        return usageStats.get(0).getPackageName();
    }

    /*
     * Compares the time that an application was used.
     * This sorts the order that packages have been "opened"
     * The front will be the Foreground activity
     */
    static class RecentUseComparator implements Comparator<UsageStats> {

        @Override
        public int compare(UsageStats lhs, UsageStats rhs) {
            return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs.getLastTimeUsed() == rhs.getLastTimeUsed()) ? 0 : 1;
        }
    }


    /*
     * Check if Test4Time has access to usage Data
     */
    private static boolean needPermissionForBlocking(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return  (mode != AppOpsManager.MODE_ALLOWED);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

}
