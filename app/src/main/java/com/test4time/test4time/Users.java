package com.test4time.test4time;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button settingsBtn;
    ListView userList;
    ImageView t4tLogo;
    //RecyclerView mRecyclerView;
    TextView usersText;
    TextView text;
    private Context context;

    private List<String> users;
    private List<String> grades;
    private List<String> timer;
    private ArrayAdapter<String> arrayAdapter;

    /**
     * Called when the activity is first created.
     */
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove the top title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);


        Intent i = new Intent(this, DeviceIntentService.class);
        startService(i);


        setContentView(R.layout.mainmenu);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chawp.ttf");
        settingsBtn = (Button) findViewById(R.id.settingsBtn);
        t4tLogo = (ImageView) findViewById(R.id.T4TLogo);
        userList = (ListView) findViewById(R.id.userList);
        usersText = (TextView) findViewById(R.id.UsersText);

        settingsBtn.setOnClickListener(new ClickListener());
        settingsBtn.setTypeface(font);
        settingsBtn.setBackgroundResource(R.drawable.settingsbutton_32dp_b);
        usersText.setTypeface(font);


        users = new ArrayList <String>();
        grades = new ArrayList <String>();

        timer = new ArrayList<String>();
        Database db = new Database(getApplicationContext(), null, null, 0, null);
        Cursor data = db.getUsers();

        while (data.moveToNext()) {
            String name = data.getString(1);
            String grade = data.getString(4);
            String time = data.getString(5);

            if (!name.equalsIgnoreCase("!@#$%")) { // don't add temporary bypass to list
                users.add(name);
                grades.add(grade);
                timer.add(time);
            }

        }

        data.close();
        db.close();

        arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.row_listview,
                users ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Set the border of View (ListView Item)

                //view.setBackground(getContext().getDrawable(R.drawable.userlistview));



                // Return the view
                return view;
            }
        };

        userList.setAdapter(arrayAdapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                userList.getSelectedItemId();


                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                intent.putExtra("KEY",users.get(position));
                intent.putExtra("KEY2",grades.get(position));
                intent.putExtra("KEY3",timer.get(position));
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
    public void onResume() {
        super.onResume();
        users = new ArrayList <String>();
        grades = new ArrayList <String>();

        timer = new ArrayList<String>();
        Database db = new Database(getApplicationContext(), null, null, 0, null);
        Cursor data = db.getUsers();
        while (data.moveToNext()) {
            String name = data.getString(1);
            String grade = data.getString(4);
            String time = data.getString(5);
            // if the arrayAdapter does not already contain 'name', add it to the list
            // unfortunately can't handle people with the same name
            //if(arrayAdapter.getPosition(name) == -1) {
            //    arrayAdapter.add(name);
            // }
//            users.add(name);
//            grades.add(grade);
//            timer.add(time);
            //users.add("Grade Level: " + grade);
            //users.add("Time Earned: " + time + " Minutes");

            if (!name.equalsIgnoreCase("!@#$%")) { // don't add temporary bypass to list
                users.add(name);
                grades.add(grade);
                timer.add(time);
            }

        }

        data.close();
        db.close();

        arrayAdapter = new ArrayAdapter<String>(
                this,
                R.layout.row_listview,
                users ) {
            public View getView(int position, View convertView, ViewGroup parent) {
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Set the border of View (ListView Item)

                //view.setBackground(getContext().getDrawable(R.drawable.userlistview));



                // Return the view
                return view;
            }
        };

        userList.setAdapter(arrayAdapter);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                userList.getSelectedItemId();


                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                intent.putExtra("KEY", users.get(position));
                intent.putExtra("KEY2", grades.get(position));
                intent.putExtra("KEY3", timer.get(position));
                startActivity(intent);

            }
        });
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
