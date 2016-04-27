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
import android.provider.MediaStore;
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
    private Button changePin;

    private EditText userName;

    private RecyclerView mRecyclerView;
    private PackageManager packageManager = null;
    private List<UserData> userlist = null;
    //private ApplicationAdapter listadaptor = null;  // might need a different class for Users
    private UserAdapter listadaptor = null;

    private TextView editNameTextView;
    private TextView editTimeTextView;
    private TextView editTimeValue;
    private RadioGroup editRadioGroup;

    private Button deleteUser;

    private AlertDialog editDialog, deleteDialog;
    // The add time buttons are not currently implemented
    //private Button editTime5, editTime10;

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
        changePin = (Button) findViewById(R.id.parent_changePin);



        userName = (EditText)findViewById(R.id.parent_add_name);

//        editTime5 = (Button)findViewById(R.id.parent_edit_time5);
//        editTime10= (Button)findViewById(R.id.parent_edit_time10);

//        editRadioGroup = (RadioGroup)findViewById(R.id.radiogroup_edit);

//        addUser.setOn
        ClickListener clickListener = new ClickListener();
        viewApps.setOnClickListener(clickListener);
        addUser.setOnClickListener(clickListener);
        changePin.setOnClickListener(clickListener);

//        setContentView(R.layout.parent_edituser);
//        deleteUser = (Button) findViewById(R.id.parent_edit_delete);
//        deleteUser.setOnClickListener(clickListener);
//        setContentView(R.layout.parentmenu);


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
//        System.out.println("VIEW BLOCKED APPS");
        Intent intent = new Intent(this, BlockedApps.class);
        startActivity(intent);
    }

    private void onAddUserAction(View view) {
        DialogFragment fragment = new AddUserDialogFragment();
        fragment.show(getFragmentManager(), "add_user");
    }

    private void onChangePin(View view) {
        Toast.makeText(ParentMenu.this, "Sorry, changing your PIN is currently unavailable",
                Toast.LENGTH_SHORT).show();
    }

    private void onDeleteUser(View view) {

        AlertDialog.Builder alertBuild = new AlertDialog.Builder(getApplicationContext());
        alertBuild.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertBuild.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Dialog d = (Dialog) dialog;
                        String name = ((TextView) d.findViewById(R.id.parent_edit_name)).getText().toString();
                        Toast.makeText(ParentMenu.this, name + " has been removed",
                                Toast.LENGTH_LONG).show();
                        Database db = new Database(getApplicationContext(), null, null, 0, null);
                        db.deleteUser(name);
                    }
                });
        alertBuild.setTitle("Delete User");
        alertBuild.setMessage("Are you sure you want to DELETE this user? This CANNOT be undone.");
        deleteDialog = alertBuild.create();
        deleteDialog.show();
    }

    private void addTime(int time) {
        int currTime = Integer.parseInt(editTimeTextView.getText().toString());
        currTime += time;
        editTimeTextView.setText(Integer.toString(currTime));
    }

    private void updateUserGrade(UserData user, String newGrade) {
        user.setGradeLevel(newGrade);
    }

    //takes a String gradelevel and returns the appropriate radioButton to set as checked/on
    private int convertGradeToRadioButton(String gradeLevel, boolean isEditing) {
        if(! isEditing) {
            if(gradeLevel.equals("K"))
                return R.id.radioButton_K;
            else if(gradeLevel.equals("1"))
                return R.id.radioButton_1;
            else if(gradeLevel.equals("2"))
                return R.id.radioButton_2;
            else if(gradeLevel.equals("3"))
                return R.id.radioButton_3;
            else if(gradeLevel.equals("4"))
                return R.id.radioButton_4;
            else if(gradeLevel.equals("5"))
                return R.id.radioButton_5;
            else if(gradeLevel.equals("6"))
                return R.id.radioButton_6;
        } else {
            if(gradeLevel.equals("K"))
                return R.id.radioButton_K_edit;
            else if(gradeLevel.equals("1"))
                return R.id.radioButton_1_edit;
            else if(gradeLevel.equals("2"))
                return R.id.radioButton_2_edit;
            else if(gradeLevel.equals("3"))
                return R.id.radioButton_3_edit;
            else if(gradeLevel.equals("4"))
                return R.id.radioButton_4_edit;
            else if(gradeLevel.equals("5"))
                return R.id.radioButton_5_edit;
            else if(gradeLevel.equals("6"))
                return R.id.radioButton_6_edit;
        }
        //default return checked Kindergarten button
        return R.id.radioButton_K;
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // user pressed the "submit" button
//        System.out.println("test submit");
        //if the name is not empty, then we can dismiss the dialog and update the user list
        //userName = (EditText)findViewById(R.id.parent_add_name);
        //if(!userName.getText().toString().equals("")) {
        String name = ((AddUserDialogFragment)dialog).getUserName();
//        System.out.println("name: " + name);
        String grade = ((AddUserDialogFragment)dialog).getNewGradeLevel();
//        System.out.println("grade: " + grade);
        if(!name.equals("")) {
//            System.out.println("dismiss");
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
            //trim extra spaces
            name = name.trim();
            Cursor cursor = db.getUserData(name);
            if(!cursor.moveToFirst()) {
                System.out.println("inserted: " + name);
                db.insertUser(name, 0, 1234, grade, 0, 0);
                UserData newUserData = new UserData(name, grade, 0, 0);
                listadaptor.addUser(name, newUserData);
                Toast.makeText(ParentMenu.this, "Adding " + name + " to the list...",
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(ParentMenu.this, "Sorry, " + name + " has already been added.",
                        Toast.LENGTH_LONG).show();
            }

        } else {
//            System.out.println("don't dismiss");
            //dialog.show(getFragmentManager(), "add_user");
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // user pressed the "cancel" button
//        System.out.println("test cancel");
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
                    viewBlockedApps(view);
                    break;
                // Open a view to add a new child user
                case R.id.parent_addUser:
                    onAddUserAction(view);
                    break;
                // Open a view to edit the parent's pin
                case R.id.parent_changePin:
                    onChangePin(view);
                    break;
                case R.id.parent_edit_delete:
                    System.out.println("Delete User - pressed");
                    onDeleteUser(view);
                    break;
                /* Add Time buttons not currently implemented
                 * - Allow parents to add bonus time to children
                case R.id.parent_edit_time5:
                    addTime(5);
                    break;
                case R.id.parent_edit_time10:
                    addTime(10);
                    break;
                    */
            }
        }
    }

    // are final parameters allowed?
    private void onUserSelected(final UserData user) {
        Toast.makeText(getApplicationContext(), "Selected " + user.getName(), Toast.LENGTH_SHORT).show();

//        listadaptor[2];
        // open a dialog to view / edit the child user's information
        AlertDialog.Builder builder = new AlertDialog.Builder(ParentMenu.this);
        LayoutInflater inflater = ParentMenu.this.getLayoutInflater();

        View content = inflater.inflate(R.layout.parent_edituser, null);
        editNameTextView = (TextView) content.findViewById(R.id.parent_edit_name);
        editNameTextView.setText(user.getName());
        editTimeTextView = (TextView) content.findViewById(R.id.parent_edit_time);
        //editTimeTextView.setText(user.getCurrentTime());
        editTimeValue = (TextView) content.findViewById(R.id.parent_edit_time_value);
        editTimeValue.setText(user.getCurrentTime() + "");

        final String name = editNameTextView.getText().toString();

        deleteUser = (Button) content.findViewById(R.id.parent_edit_delete);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("DELETE USER?");
//                AlertDialog.Builder alertBuild = new AlertDialog.Builder(getApplicationContext());
                AlertDialog.Builder alertBuild = new AlertDialog.Builder(ParentMenu.this);
                alertBuild.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                alertBuild.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Dialog d = (Dialog) dialog;
//                                String name = ((TextView) d.findViewById(R.id.parent_edit_name)).getText().toString();
                                Toast.makeText(ParentMenu.this, name + " has been removed",
                                        Toast.LENGTH_LONG).show();
                                Database db = new Database(getApplicationContext(), null, null, 0, null);
                                db.deleteUser(name);
                                dialog.cancel();
                                editDialog.cancel();
                                new LoadUsers().execute(); // start thread to update user list
                            }
                        });
                alertBuild.setTitle("Delete User");
                alertBuild.setMessage("Are you sure you want to DELETE this user? This CANNOT be undone.");
                deleteDialog = alertBuild.create();
                deleteDialog.show();
//                //onDeleteUser(v);
//                //String name = ((TextView) v.findViewById(R.id.parent_edit_name)).getText().toString();
//                Toast.makeText(ParentMenu.this, name + " has been removed",
//                        Toast.LENGTH_LONG).show();
//                Database db = new Database(getApplicationContext(), null, null, 0, null);
//                db.deleteUser(name);
            }
        });

        builder.setView(content)
                .setPositiveButton(R.string.submitText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog d = (Dialog) dialog;
                        //TODO: REMOVE the findViewById calls from this section
                        //      it's inefficient to call it other than in onCreate
                        editNameTextView = (TextView) d.findViewById(R.id.parent_edit_name);
                        editRadioGroup = (RadioGroup) (d.findViewById(R.id.radiogroup_edit));
                        //editTimeTextView = (TextView) d.findViewById(R.id.parent_edit_time);
                        //editTimeTextView.setText(user.getCurrentTime());
//                        deleteUser = (Button) d.findViewById(R.id.parent_edit_delete);
//
//                        deleteUser.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                            }
//                        });

                        //editTimeValue = (TextView) d.findViewById(R.id.parent_edit_time_value);

                        if(user != null) {
                            if(user.getCurrentTime() != -1) {
//                                editTimeValue.setText(user.getCurrentTime();
//                                editTimeValue.setText("1");
                                editTimeValue.setText(Integer.toString(user.getCurrentTime()));
                            } else {
                                editTimeValue.setText("-2");
                            }
                        } else {
                            editTimeValue.setText("-4");
                        }

                        //editTimeValue.setText(user.getCurrentTime());
                        int radioId = editRadioGroup.getCheckedRadioButtonId();
                        if (radioId != -1) {
                            switch (radioId) {
                                case R.id.radioButton_K_edit:    //grade is Kindergarten
//                                    updateUserGrade(user, "K");
                                    user.setGradeLevel("K");
                                    break;
                                case R.id.radioButton_1_edit:    //grade is 1st
                                    user.setGradeLevel("1");
                                    break;
                                case R.id.radioButton_2_edit:    //grade is 2nd
                                    user.setGradeLevel("2");
                                    break;
                                case R.id.radioButton_3_edit:    //grade is 3rd
                                    user.setGradeLevel("3");
                                    break;
                                case R.id.radioButton_4_edit:    //grade is 4th
                                    user.setGradeLevel("4");
                                    break;
                                case R.id.radioButton_5_edit:    //grade is 5th
                                    user.setGradeLevel("5");
                                    break;
                                case R.id.radioButton_6_edit:    //grade is 6th
                                    user.setGradeLevel("6");
                                    break;
                            }
                            Database db = new Database(getApplicationContext(), null, null, 0, null);
                            //update: String name, int type, int pin, String grade, int time, int timeUp
                            //cursor:
                            /* - data.getString(0) returns ID
                             * - data.getString(1) returns NAME
                             * - data.getString(2) returns TYPE
                             * - data.getString(3) returns PIN
                             * - data.getString(4) returns GRADE
                             * - data.getString(5) returns TIME
                             * - data.getString(6) returns TIMEUP
                             */
                            System.out.printf("getName:%s\n", user.getName());

                            Cursor data = db.getUserData(user.getName());
//                            data = db.getUsers();
//                            db.
                            if (data != null) {
                                if (data.moveToFirst()) {
                                    System.out.printf("getName: %s; 0:%s\n", user.getName(), data.getString(4));
                                    db.updateUser(data.getString(1), Integer.parseInt(data.getString(2)),
                                            Integer.parseInt(data.getString(3)), user.getGradeLevel(),
                                            Integer.parseInt(data.getString(5)), Integer.parseInt(data.getString(6)));
                                } else {
                                    Toast.makeText(ParentMenu.this, "Cursor can't move to first; " + user.getName(),
                                            Toast.LENGTH_SHORT).show();

                                }

                            } else {
                                Toast.makeText(ParentMenu.this, "No such user: " + user.getName(),
                                        Toast.LENGTH_SHORT).show();
                            }

                            //                            db.updateUser(user.getName(), user.getIsParent(), db.get)
                        }
                        switch (which) {
                            //                            case R.id.parent_edit_time5:
                            //                                // add 5 minutes to time
                            //                                int currTime5 = Integer.parseInt(editTimeTextView.getText().toString());
                            //                                currTime5 += 5;
                            //                                editNameTextView.setText(Integer.toString(currTime5));
                            //                                break;
                            //                            case R.id.parent_edit_time10:
                            //                                // add 10 minutes to time
                            //                                int currTime10 = Integer.parseInt(editTimeTextView.getText().toString());
                            //                                currTime10 += 10;
                            //                                editNameTextView.setText(Integer.toString(currTime10));
                            //                                break;

                        }
                    }
                })
                 .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                     }
                 });
                editDialog = builder.create();
                TextView editChildName = (TextView) content.findViewById(R.id.parent_edit_name);
                editChildName.setText(user.getName());
                RadioGroup editChildRG = (RadioGroup) content.findViewById(R.id.radiogroup_edit);

                editChildRG.check(convertGradeToRadioButton(user.getGradeLevel(), true));
                editDialog.setTitle("Edit Child Information");
                editDialog.show();
            }

     /*
     * generate list of users
     */
    private List<ApplicationInfo> checkForLaunchIntent
    (List < ApplicationInfo > list) {
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
                Database db = new Database(getApplicationContext(), null, null, 0, null);
                // TEMPORARILY REMOVED - does not work on my phone, but works on tablet
                Cursor data = db.getUsers();
                // get list of users from USERS table, add them to the list of current users
                while (data.moveToNext()) {
                    // UserData( name, grade, currentTime, isParent)
                    UserData newUser = new UserData(data.getString(1), data.getString(4),
                            Integer.parseInt(data.getString(5)), Integer.parseInt(data.getString(2)));
                    listadaptor.addUser(data.getString(1), newUser);
                }
                data.close();
                db.close();

/* TESTING LIST POPULATION
String sampleName = "Jimmy";
for(int i = 0; i < 3; i++) {
UserData newUser = new UserData(sampleName + i, Integer.toString((i % 6) + 1),
    (i+1)*5, (i+1)*5);
listadaptor.addUser(sampleName + i, newUser);
}
*/

                return null;
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }

            @Override
            protected void onPostExecute(Void result) {
                mRecyclerView.setAdapter(listadaptor);
//            System.out.println("POST EXECUTE");
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
