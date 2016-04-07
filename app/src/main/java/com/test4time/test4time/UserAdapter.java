package com.test4time.test4time;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class is the adapter for the RecyclerView
 * This creates each row and populates the data in each row
 * of the RecycleView
 */
public class UserAdapter extends RecyclerView.Adapter<ListRowViewHolder> {
    // list of installed applications
    private List<UserData> usersList = null;
    private Context mContext;
    private int focusedItem = 0;
    //private PackageManager packageManager;
    // map of selected (checked) applications
    private HashMap<String,UserData> userDataHashMap = null;

    public UserAdapter(Context context, List<UserData> usersList) {
        this.usersList= usersList;
        this.mContext = context;
        //this.appSelected = new HashMap<String,Application>();
        this.userDataHashMap = new HashMap<String, UserData>();

        //packageManager = context.getPackageManager();
    }

    @Override
    public ListRowViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userrow, null);
        final ListRowViewHolder holder = new ListRowViewHolder(v, null);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ListRowViewHolder listRowViewHolder, final int position) {
        UserData user = usersList.get(position);
        listRowViewHolder.itemView.setSelected(focusedItem == position);

        listRowViewHolder.getLayoutPosition();

        //listRowViewHolder.appIcon.setImageDrawable(app.loadIcon(packageManager));
        //listRowViewHolder.appName.setText(app.loadLabel(packageManager));
        listRowViewHolder.userName.setText(user.getName());
        listRowViewHolder.gradeLevel.setText(user.getGradeLevel());
        listRowViewHolder.currentTime.setText(Integer.toString(user.getCurrentTime()));

        final AlertDialog.Builder alertBuild = new AlertDialog.Builder(mContext);

        listRowViewHolder.editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: open dialog. send UserData to dialog to display correct info
                alertBuild.setCancelable(false).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertBuild.setTitle("Edit Child Information");
            }
        });

    }

    @Override
    public int getItemCount() {
        return (usersList !=null ? usersList.size():0);
    }

    public void clearAdapter() {
        usersList.clear();
        notifyDataSetChanged();
    }

    /*
     * Save the selected applications to BLOCKAPPS table (db)
     */
    //TODO: re-implement as "saveUsers"
    protected void saveUserData() {
        Database db = new Database(mContext, null, null, 0, null);
        //db.deleteBlockAppsRows();
        Iterator iterator = userDataHashMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            UserData user = (UserData) pair.getValue();
            Log.d("USER_NAME", user.getName());
            db.insertUser(user.getName(), user.getIsParent(), 0, user.getGradeLevel(),
                user.getCurrentTime(), user.getCurrentTime());
        }
    }
   /* protected void saveApplications() {
        Database db = new Database(mContext, null, null, 0, null);
        db.deleteBlockAppsRows();
        Iterator iterator = appSelected.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Application app = (Application) pair.getValue();
            Log.d("PACKAGE", app.getPackageName());
            db.insertApp(app.getName(), app.getPackageName(), app.getProcessName());
        }

    }*/

    /*
     * add app to selected list
     */
    //TODO: might need to have an addUser?
    protected void addUser(String userName, UserData userData) {
        userDataHashMap.put(userName, userData);
        usersList.add(userData);
    }
}
