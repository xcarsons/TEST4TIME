package com.test4time.test4time;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @Author - Carson Schaefer
 * Class responsible for starting the DeviceIntentService when device is booted
 */
public class StartOnBoot extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,DeviceIntentService.class);
        context.startService(i);
        Log.d("start","serviceBooted");
    }
}
