/*
package com.test4time.test4time;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Zach on 4/6/16.
 *//*

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private List<String> usersList = null;
    private Context mContext;
    private List<String> userSelected = null;

    public UsersAdapter(Context context, List<String> usersList) {
        this.usersList = usersList;
        this.mContext = context;
        this.userSelected = new ArrayList<String>();
    }

    public UsersViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.approw, null);
        final UsersViewHolder holder = new UsersViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        String users = usersList.get(position);

    }

    public int getItemCount() {
        return (usersList != null ? usersList.size() : 0);
    }

    protected void addUser(String name) {
        userSelected.add(name);
    }

    public void clearAdapter() {
        usersList.clear();
        notifyDataSetChanged();
    }

}*/
