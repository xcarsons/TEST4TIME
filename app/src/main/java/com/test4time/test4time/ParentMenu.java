package com.test4time.test4time;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.List;

/**
 * Created by exovu_000 on 4/3/2016.
 */
public class ParentMenu extends Activity {

    private Button viewApps;
    private Button addUser;

    private RecyclerView mRecyclerView;
    private PackageManager packageManager = null;
    private List<ApplicationInfo> userlist = null;
    private ApplicationAdapter listadaptor = null;  // might need a different class for Users

    /**
     * Called when the activity is first created.
     */
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove the top title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.parentmenu);

        viewApps = (Button) findViewById(R.id.parent_viewApps);
        addUser = (Button) findViewById(R.id.parent_addUser);

        mRecyclerView = (RecyclerView) findViewById(R.id.parent_userList);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

    }

    public void viewBlockedApps(View view) {
        //TODO: launch the BlockedApps activity when the viewApps button is pressed
        System.out.println("VIEW BLOCKED APPS");
        Intent intent = new Intent(this, BlockedApps.class);
        startActivity(intent);
    }

    /****************************************************************
     Responsible for handling clicks
     **************************************************************/
    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // Send to page to view and update list of blocked apps
                case R.id.parent_viewApps:
                    System.out.println("VIEW APPS");
//                    viewBlockedApps(view);
                    break;
                // Open a view to add a new child user
                case R.id.parent_addUser:
                    System.out.println("ADD A USER");
                    break;
            }
        }
    }

}
