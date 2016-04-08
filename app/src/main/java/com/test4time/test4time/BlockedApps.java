package com.test4time.test4time;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author - Carson Schaefer
 * Class is the Activity where blocked applications
 * are specified and selected
 */
public class BlockedApps extends Activity {
    private RecyclerView mRecyclerView;
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    private MenuItem checkall = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = new Intent(this, DeviceIntentService.class);
        startService(i);

        setContentView(R.layout.appslist);
        mRecyclerView = (RecyclerView) findViewById(R.id.myList);


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        packageManager = getPackageManager();

        // show the back button in the top-left corner
        getActionBar().setDisplayHomeAsUpEnabled(true);

        new LoadApplications().execute(); // start thread to generate List of apps
    }

    /*
     * Create ActionBar
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.applistmenu, menu);
        checkall = menu.findItem(R.id.action_checkall);
        return true;
    }

    /*
     * Button in ActionBar is selected
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.action_save: { // Saved button is selected
                Toast.makeText(BlockedApps.this, "List Saved", Toast.LENGTH_SHORT).show();
                listadaptor.saveApplications();
                Intent i = new Intent(this, DeviceIntentService.class);
                startService(i); // start intent service so it can update the list of blocked apps
                break;
            }
            // Respond to the action bar's Up/Home button (the top-left back button)
            case android.R.id.home: {
                NavUtils.navigateUpFromSameTask(this);
                return true;
	        }
            case R.id.action_checkall: {
                if (checkall.getTitle().toString().equalsIgnoreCase("check all")) {
                    checkall.setTitle("UnCheck All");
                    listadaptor.checkAll(true, this);
                } else {
                    checkall.setTitle("Check All");
                    listadaptor.checkAll(false, this);
                }
            }
            default: {
                result = super.onOptionsItemSelected(item);

                break;
            }
        }

        return result;
    }

    /*
     * generate list of installed apps
     */
    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }


    /*
         * inner class creates a thread to populated the RecyclerView
         * Data retrieved (applications installed on device, applications in BLOCKAPPS table)
         */
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;


        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(BlockedApps.this, applist);

            Database db = new Database(getApplicationContext(), null, null, 0, null);
            Cursor data = db.getBlockedApps();
            data = db.getBlockedApps();
            // get list of blocked apps from BLOCKAPPS table, add them to the list of selected (checked) apps
            while (data.moveToNext()) {
                Application app = new Application(data.getString(1), data.getString(2), data.getString(3));
                listadaptor.addApp(data.getString(1), app);
            }
            data.close();
            db.close();
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            mRecyclerView.setAdapter(listadaptor);
            if (listadaptor.getCheckedItemCount() == listadaptor.getItemCount()) {
                checkall.setTitle("UnCheck All");
            }
            listadaptor.notifyDataSetChanged(); // new
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(BlockedApps.this, null,
                    "Loading Applications");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
