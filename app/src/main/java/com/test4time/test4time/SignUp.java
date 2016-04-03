package com.test4time.test4time;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

/**
 * @Author - Carson Schaefer
 * Class allows the user to sign up and store account in cloud
 */
public class SignUp extends Activity{

    Button onCreateBtn;
    EditText emailTb;
    EditText passwordTb;
    EditText confirmPasswordTb;

    /**
     * Called when the activity is first created.
     */
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.create);

        emailTb = (EditText) findViewById(R.id.create_emailTb);
        passwordTb = (EditText) findViewById(R.id.create_passwordTb);
        confirmPasswordTb = (EditText) findViewById(R.id.confirmPasswordTb);
        onCreateBtn = (Button) findViewById(R.id.createBtn);
        onCreateBtn.setOnClickListener(new ClickListener());

        // receive intent information from Login Activity
        Intent recIntent = getIntent();
        String email = recIntent.getStringExtra(Login.SIGNUP_EMAIL);
        String password = recIntent.getStringExtra(Login.SIGNUP_PASS);
        emailTb.setText(email);
        passwordTb.setText(password);
    }


    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.createBtn:

                    break;
            }
        }
    }
}
