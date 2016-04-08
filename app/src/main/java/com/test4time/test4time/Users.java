package com.test4time.test4time;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by carsonschaefer on 2/4/16.
 */
public class Users extends Activity {

    ImageButton settingsBtn;
    ImageView pencila;
    ImageView pencilb;
    ListView userList;
    TextView usersText;

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
        usersText = (TextView) findViewById(R.id.UsersText);
        settingsBtn.setOnClickListener(new ClickListener());
        usersText.setTypeface(font);

        Database db = new Database(this, null, null, 0, null);
        db.getReadableDatabase();



    }

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.settingsBtn:

                    break;
                case R.id.userList:

                    break;

            }
        }
    }



}
