package com.test4time.test4time;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by carsonschaefer on 2/4/16.
 */
public class Users extends Activity {
    //private UsersAdapter usersAdapter = null;
    ImageButton settingsBtn;
    ImageView pencila;
    ImageView pencilb;
    ListView userList;
    //RecyclerView mRecyclerView;
    TextView usersText;
    private Context context;

    /**
     * Called when the activity is first created.
     */
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove the top title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.mainmenu);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chawp.ttf");
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn);
        pencila = (ImageView) findViewById(R.id.pencil);
        pencilb = (ImageView) findViewById(R.id.pencil2);
        userList = (ListView) findViewById(R.id.userList);
        //mRecyclerView = (RecyclerView) findViewById(R.id.userList);
        usersText = (TextView) findViewById(R.id.UsersText);


        settingsBtn.setOnClickListener(new ClickListener());
        usersText.setTypeface(font);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(linearLayoutManager);

        //mRecyclerView.setHasFixedSize(true);

        //usersAdapter = new UsersAdapter(Users.this, users);

        final List<String> users = new ArrayList <String>();
        Database db = new Database(this, null, null, 0, null);
        Cursor data = db.getUsers();
        db.insertUser("Tom", 0, 1234, "K", 5, 0);


        while (data.moveToNext()) {
            String name = data.getString(1);
            String grade = data.getString(4);
            String time = data.getString(5);
            users.add(name);
            users.add("Grade Level: " + grade);
            users.add("Time Earned: " + time + " Minutes");
        }
        db.deleteUser("Tom");
        data.close();
        db.close();



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                users );

        userList.setAdapter(arrayAdapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    userList.getSelectedItemId();

                    Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                    intent.putExtra("KEY",users.get(position));
                    startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.test_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.settingsBtn:

                    Intent intent = new Intent(getApplicationContext(), ParentMenu.class);
                    startActivity(intent);

                    break;
                case R.id.userList:



                    break;

            }
        }
    }
}
