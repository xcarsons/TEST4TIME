package com.test4time.test4time;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by carsonschaefer on 2/4/16.
 * Login activity
 * Users can log into there cloud stored accounts
 */
public class Login extends Activity {

    Button loginBtn;
    Button signUpBtn;
    EditText emailTb;
    EditText passwordTb;

    public static final String SIGNUP_EMAIL = "com.test4time.SIGNUP_EMAIL";
    public static final String SIGNUP_PASS = "com.test4time.SIGNUP_PASS";

    /**
     * Called when the activity is first created.
     */
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove the top title bar
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.login);
        emailTb = (EditText) findViewById(R.id.emailTb);
        passwordTb = (EditText) findViewById(R.id.passwordTb);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new ClickListener());
        signUpBtn = (Button) findViewById(R.id.signupBtn);
        signUpBtn.setOnClickListener(new ClickListener());

        Database db = new Database(this, null, null, 0, null);
        db.insertUser("TEST", 1, 1111, "6", 0, 0);
        db.insertUser("TEST2", 1, 1111, "3", 1, 2);
        db.insertUser("TEST3", 1, 1952, "4", 8, 9);
        db.updateUser("TEST", 2, 2222, "7", 2, 2);
        //Cursor data = db.getUserData("TEST");
        //Log.d("getUser", "TABLE MOVE");
        Log.d("getUser","TABLE MOVE");
        Cursor data = db.getUsers();
        while (data.moveToNext()) {
            Log.d("getUser", data.getString(0));
            Log.d("getUser", data.getString(1));
            Log.d("getUser", data.getString(2));
            Log.d("getUser", data.getString(3));
            Log.d("getUser", data.getString(4));
            Log.d("getUser", data.getString(5));
            Log.d("getUser", data.getString(6));
        }
        Log.d("getUser", "DELETE");
        boolean h = db.deleteUser("TEST");
        Log.d("getUser", String.valueOf(h));
        data=db.getUserData("TEST");
        while(data.moveToNext()) {
            Log.d("getUser", data.getString(0));
        }




    }


    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loginBtn:

                    break;
                case R.id.signupBtn:
                    Intent intent = new Intent(getApplicationContext(), SignUp.class);
                    intent.putExtra(SIGNUP_EMAIL, emailTb.getText().toString());
                    intent.putExtra(SIGNUP_PASS, passwordTb.getText().toString());
                    startActivity(intent);
                    break;
            }
        }
    }



}
