package com.test4time.test4time;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by carsonschaefer on 4/2/16.
 */
public class ListRowViewHolder extends RecyclerView.ViewHolder {
    // layout settings for BlockedApps / Application View
    protected ImageView appIcon;
    protected TextView appName;
    protected TextView packageName;
    protected TextView processName;
    protected CheckBox checkBox;
    protected LinearLayout layout;

    // layout settings for UserData / list of Users view
    protected TextView userName;
    protected TextView gradeLevel;
    protected TextView currentTime;
    protected Button editUserButton;

    public ListRowViewHolder(View view) {
        super(view);
        this.appIcon = (ImageView) view.findViewById(R.id.app_icon);
        this.appName = (TextView) view.findViewById(R.id.app_name);
        this.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        this.layout = (LinearLayout) view.findViewById(R.id.rowLayout);
        this.packageName = (TextView) view.findViewById(R.id.packageName);
        this.processName = (TextView) view.findViewById(R.id.processName);
        view.setClickable(true);
    }

    public ListRowViewHolder(View view, UserData userData) {
        super(view);
        this.userName = (TextView) view.findViewById(R.id.user_name);
        this.gradeLevel = (TextView) view.findViewById(R.id.user_grade);
        this.currentTime = (TextView) view.findViewById(R.id.user_time);
        this.editUserButton = (Button) view.findViewById(R.id.user_edit);
        view.setClickable(true);
    }

}
