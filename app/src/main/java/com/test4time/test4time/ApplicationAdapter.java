package com.test4time.test4time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ApplicationAdapter extends RecyclerView.Adapter<ListRowViewHolder> {
    private List<ApplicationInfo> appsList = null;
    private Context mContext;
    private int focusedItem = 0;
    private PackageManager packageManager;
    private HashMap<Integer,Application> appSelected = null;
    private ArrayList<String> appBlocked = null;

    public ApplicationAdapter(Context context, List<ApplicationInfo> appsList) {
        this.appsList = appsList;
        this.mContext = context;
        this.appSelected = new HashMap<Integer,Application>();
        this.appBlocked = new ArrayList<String>();

        packageManager = context.getPackageManager();
    }

    @Override
    public ListRowViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.approw, null);
        final ListRowViewHolder holder = new ListRowViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ListRowViewHolder listRowViewHolder, final int position) {
        ApplicationInfo app = appsList.get(position);
        listRowViewHolder.itemView.setSelected(focusedItem == position);

        listRowViewHolder.getLayoutPosition();

        listRowViewHolder.appIcon.setImageDrawable(app.loadIcon(packageManager));
        listRowViewHolder.appName.setText(app.loadLabel(packageManager));
        listRowViewHolder.packageName.setText(app.packageName);
        listRowViewHolder.processName.setText(app.processName);

        // prevents app from being removed from appSelected(hashmap) when scrolling out of view
        listRowViewHolder.checkBox.setOnCheckedChangeListener(null);

        if (appSelected.get(position) != null || appBlocked.contains(listRowViewHolder.appName.getText().toString())) {
            listRowViewHolder.checkBox.setChecked(true);
        } else {
            listRowViewHolder.checkBox.setChecked(false);
        }


        listRowViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listRowViewHolder.checkBox.setChecked(isChecked);
                if(isChecked) {
                    appSelected.put(position, new Application(listRowViewHolder.appName.getText().toString(),listRowViewHolder.packageName.getText().toString(), listRowViewHolder.processName.getText().toString()));
                } else {
                    appSelected.remove(position);
                    appBlocked.remove(listRowViewHolder.appName.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (appsList !=null ? appsList.size():0);
    }

    public void clearAdapter() {
        appsList.clear();
        notifyDataSetChanged();
    }

    protected void saveApplications() {
        Database db = new Database(mContext, null, null, 0, null);
        db.deleteBlockAppsRows();
        Iterator iterator = appSelected.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Application app = (Application) pair.getValue();
            db.insertApp(app.getName(), app.getPackageName(), app.getProcessName());
        }
    }


    protected void addApp(String appName) {
        appBlocked.add(appName);
    }

}


