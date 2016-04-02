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

    public ApplicationAdapter(Context context, List<ApplicationInfo> appsList) {
        this.appsList = appsList;
        this.mContext = context;
        this.appSelected = new HashMap<Integer,Application>();
        Database db = new Database(mContext, null, null, 0, null);
        Cursor data = db.getBlockedApps();
        data = db.getBlockedApps();
        while (data.moveToNext()) {
            Log.d("getApp", data.getString(0));
            Log.d("getApp", data.getString(1));
            Log.d("getApp", data.getString(2));
            Log.d("getApp", data.getString(3));
            Application app = new Application(data.getString(1), data.getString(2), data.getString(3));
            //appsSelected.add(app);
        }
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
        listRowViewHolder.checkBox.setChecked(appSelected.get(position) == null ? false : true);

//        holder.checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.checkBox.isChecked()) {
//                    Log.d("CHECK", holder.appName.getText() + " is checked");
//                    Log.d("CHECK", holder.packageName.getText() + " is package");
//                    Log.d("CHECK", holder.processName.getText() + " is process");
//                    appsSelected.add(position, new Application(holder.appName.getText().toString(), holder.packageName.getText().toString(), holder.processName.getText().toString()));
//                } else {
//                    Log.d("CHECK", holder.appName.toString() + " is unchecked");
//                    appsSelected.remove(position);
//                }
//            }
//        });

        listRowViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listRowViewHolder.checkBox.setChecked(isChecked);
                if(isChecked) {
                    appSelected.put(position, new Application(listRowViewHolder.appName.getText().toString(),listRowViewHolder.packageName.getText().toString(), listRowViewHolder.processName.getText().toString()));
                } else {
                    appSelected.remove(position);
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
        Iterator iterator = appSelected.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            Application app = (Application) pair.getValue();
            db.insertApp(app.getName(), app.getPackageName(), app.getProcessName());
        }
    }


}
//    private Context context;
//    private PackageManager packageManager;
//
//    public ApplicationAdapter(Context context, int textViewResourceId,
//                              List<ApplicationInfo> appsList) {
//        super(context, textViewResourceId, appsList);
//        this.context = context;
//        this.appsList = appsList;
//        packageManager = context.getPackageManager();
//    }
//
//    @Override
//    public int getCount() {
//        return ((null != appsList) ? appsList.size() : 0);
//    }
//
//    @Override
//    public ApplicationInfo getItem(int position) {
//        return ((null != appsList) ? appsList.get(position) : null);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if (null == view) {
//            LayoutInflater layoutInflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            view = layoutInflater.inflate(R.layout.approw, null);
//        }
//
//        final ApplicationInfo applicationInfo = appsList.get(position);
//        if (null != applicationInfo) {
//            TextView appName = (TextView) view.findViewById(R.id.app_name);
//            //TextView packageName = (TextView) view.findViewById(R.id.app_paackage);
//            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);
//            CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
//
//            appName.setText(applicationInfo.loadLabel(packageManager));
//            //packageName.setText(applicationInfo.packageName);
//            iconview.setImageDrawable(applicationInfo.loadIcon(packageManager));
//            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                    Log.d("CHECKBOX", "Is checked? "+isChecked + "\nPackageName: " + applicationInfo.packageName);
//                }
//            });
//        }
//        return view;
//    }
//}

