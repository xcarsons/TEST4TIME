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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ParentMenu extends Activity
        implements AddUserDialogFragment.AddUserDialogListener {

    private Button viewApps;
    private Button addUser;

    private EditText userName;

    private RecyclerView mRecyclerView;
    private PackageManager packageManager = null;
    private List<UserData> userlist = null;
    //private ApplicationAdapter listadaptor = null;  // might need a different class for Users
    private UserAdapter listadaptor = null;

    private TextView editNameTextView;
    private TextView editTimeTextView;
    private RadioGroup editRadioGroup;
    private Button editTime5, editTime10;

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

        editTime5 = (Button)findViewById(R.id.parent_edit_time5);
        editTime10= (Button)findViewById(R.id.parent_edit_time10);

//        editRadioGroup = (RadioGroup)findViewById(R.id.radiogroup_edit);

//        addUser.setOn
        ClickListener clickListener = new ClickListener();
        viewApps.setOnClickListener(clickListener);
        addUser.setOnClickListener(clickListener);

        //editTime5.setOnClickListener(clickListener);
        //editTime10.setOnClickListener(clickListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.parent_userList);
//        mRecyclerView.setOnClickListener();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        new LoadUsers().execute(); // start thread to generate list of users
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

    private void addTime(int time) {
        int currTime = Integer.parseInt(editTimeTextView.getText().toString());
        currTime += time;
        editTimeTextView.setText(Integer.toString(currTime));
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
        String grade = ((AddUserDialogFragment)dialog).getNewGradeLevel();
        System.out.println("grade: " + grade);
        if(!name.equals("")) {
            System.out.println("dismiss");
            /*RadioGroup radioGroup = (RadioGroup) dialog.getActivity().findViewById(R.id.radiogroup_grade);
            int id = radioGroup.getCheckedRadioButtonId();
            if(id == -1) {
                // no item selected, don't dismiss (but shouldn't be possible either
            } else {
                switch(id) {
                    case R.id.radioButton_K:    //grade is Kindergarten
                        grade = "K";
                        break;
                    case R.id.radioButton_1:    //grade is 1st
                        grade = "1";
                        break;
                    case R.id.radioButton_2:    //grade is 2nd
                        grade = "2";
                        break;
                    case R.id.radioButton_3:    //grade is 3rd
                        grade = "3";
                        break;
                    case R.id.radioButton_4:    //grade is 4th
                        grade = "4";
                        break;
                    case R.id.radioButton_5:    //grade is 5th
                        grade = "5";
                        break;
                    case R.id.radioButton_6:    //grade is 6th
                        grade = "6";
                        break;
                }
            }
            */
            Database db = new Database(getApplicationContext(), null, null, 0, null);
            //insert (name, user type, pin, grade, time, timeup)
            // TEMPORARILY REMOVED FOR TESTING
            //db.insertUser(name, 0, 1234, grade, 0,0 );
            UserData newUserData = new UserData(name, grade, 0, 0);
            listadaptor.addUser(name, newUserData);
            Toast.makeText(ParentMenu.this, "Adding " + name + " to the list...",
                    Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        } else {
            System.out.println("don't dismiss");
            //dialog.show(getFragmentManager(), "add_user");
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
                case R.id.parent_edit_time5:
                    System.out.println("PLUS 5");
                    addTime(5);
                    break;
                case R.id.parent_edit_time10:
                    System.out.println("PLUS 10");
                    addTime(10);
                    break;
            }
        }
    }

    private void onUserSelected(UserData user) {
        Toast.makeText(getApplicationContext(), user.getName() + " Clicked", Toast.LENGTH_SHORT).show();


        // open a dialog to view / edit the child user's information
        AlertDialog.Builder builder = new AlertDialog.Builder(ParentMenu.this);
        LayoutInflater inflater = ParentMenu.this.getLayoutInflater();


        builder.setView(inflater.inflate(R.layout.parent_edituser, null))
                .setPositiveButton(R.string.submitText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog d = (Dialog) dialog;
                        //TODO: REMOVE the findViewById calls from this section
                        //      it's inefficient to call it other than in onCreate
                        editNameTextView = (TextView) d.findViewById(R.id.parent_edit_name);
                        editRadioGroup = (RadioGroup) (d.findViewById(R.id.radiogroup_edit));
                        editTimeTextView = (TextView) d.findViewById(R.id.parent_edit_time);
                        int radioId = editRadioGroup.getCheckedRadioButtonId();
                        if (radioId != -1) {
                            switch (radioId) {
                                case R.id.radioButton_K_edit:    //grade is Kindergarten
                                    break;
                                case R.id.radioButton_1_edit:    //grade is 1st
                                    break;
                                case R.id.radioButton_2_edit:    //grade is 2nd
                                    break;
                                case R.id.radioButton_3_edit:    //grade is 3rd
                                    break;
                                case R.id.radioButton_4_edit:    //grade is 4th
                                    break;
                                case R.id.radioButton_5_edit:    //grade is 5th
                                    break;
                                case R.id.radioButton_6_edit:    //grade is 6th
                                    break;
                            }
                        }
                        switch (which) {
                            case R.id.parent_edit_time5:
                                // add 5 minutes to time
                                int currTime5 = Integer.parseInt(editTimeTextView.getText().toString());
                                currTime5 += 5;
                                editNameTextView.setText(Integer.toString(currTime5));
                                break;
                            case R.id.parent_edit_time10:
                                // add 10 minutes to time
                                int currTime10 = Integer.parseInt(editTimeTextView.getText().toString());
                                currTime10 += 10;
                                editNameTextView.setText(Integer.toString(currTime10));
                                break;

                        }
                    }
                })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Edit Child Information");
        dialog.show();
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

    /*
         * inner class creates a thread to populated the RecyclerView
         * Data retrieved (applications installed on device, applications in BLOCKAPPS table)
         */
    private class LoadUsers extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            //userlist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            //listadaptor = new ApplicationAdapter(ParentMenu.this, userlist);
            userlist = new ArrayList<>();
            listadaptor = new UserAdapter(ParentMenu.this, userlist, new OnItemClickListener() {
                @Override
                public void onItemClick(UserData item) {
                    onUserSelected(item);
                }
            });

            // Populate the list with the current users in the database
            // TEMPORARILY REMOVED - does not work on my phone, but works on tablet
            /*
            Database db = new Database(getApplicationContext(), null, null, 0, null);
            Cursor data = db.getUsers();
            // get list of users from USERS table, add them to the list of current users
            while (data.moveToNext()) {
                //Application app = new Application(data.getString(1), data.getString(2), data.getString(3));
                // UserData( name, grade, currentTime, isParent)
                UserData newUser = new UserData(data.getString(1), data.getString(4),
                        Integer.parseInt(data.getString(5)), Integer.parseInt(data.getString(2)));
//                listadaptor.addApp(data.getString(1), newUser);
                listadaptor.addUser(data.getString(1), newUser);
            }
            data.close();
            db.close();
            */

            /* TESTING LIST POPULATION
            */
            String sampleName = "Jimmy";
            for(int i = 0; i < 3; i++) {
                UserData newUser = new UserData(sampleName + i, Integer.toString((i % 6) + 1),
                        (i+1)*5, (i+1)*5);
                listadaptor.addUser(sampleName + i, newUser);
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            mRecyclerView.setAdapter(listadaptor);
            System.out.println("POST EXECUTE");
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ParentMenu.this, null,
                    "Loading Users");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UserData item);
    }



}
