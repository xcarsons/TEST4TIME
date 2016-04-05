package com.test4time.test4time;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.validation.Validator;

public class ParentMenu extends Activity
        implements AddUserDialogFragment.AddUserDialogListener {

    private Button viewApps;
    private Button addUser;

    private EditText userName;

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

        userName = (EditText)findViewById(R.id.parent_add_name);

//        addUser.setOn
        ClickListener clickListener = new ClickListener();
        viewApps.setOnClickListener(clickListener);
        addUser.setOnClickListener(clickListener);

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

    private void onAddUserAction() {
        DialogFragment fragment = new AddUserDialogFragment();
        fragment.show(getFragmentManager(), "add_user");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // user pressed the "submit" button
        System.out.println("test submit");
        //if the name is not empty, then we can dismiss the dialog and update the user list
        //userName = (EditText)findViewById(R.id.parent_add_name);
        //if(!userName.getText().toString().equals("")) {
        String name = ((AddUserDialogFragment)dialog).getUserName();
        System.out.println("name: " + name);
        if(!name.equals("")) {
            System.out.println("dismiss");
            dialog.dismiss();
        } else {
            System.out.println("don't dismiss");
            dialog.show(getFragmentManager(), "add_user");
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // user pressed the "cancel" button
        System.out.println("test cancel");
        dialog.dismiss();
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
                    viewBlockedApps(view);
                    break;
                // Open a view to add a new child user
                case R.id.parent_addUser:
                    System.out.println("ADD A USER");
                    onAddUserAction();
                    break;
            }
        }
    }

    /*
     * generate list of users
     */
    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> userlist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    userlist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userlist;
    }



}
