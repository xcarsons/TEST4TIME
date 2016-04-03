package com.test4time.test4time;

import android.app.Activity;
import android.app.AlertDialog;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import android.util.Log;

import java.util.List;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
/*********************************************************
 * Class responsible for manipulating the math question view
 *********************************************************/
public class MyActivity extends Activity {

    Button submitBtn;
    EditText answer;
    TextView num1, num2, sign;
    Question q;


    /**
     * Called when the activity is first created.
     */
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove the top title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mathquestion);


        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        sign = (TextView) findViewById(R.id.sign);
        answer = (EditText) findViewById(R.id.answer);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new ClickListener());

        try {
            // instantiate font, then apply
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/squeakychalksound.ttf");
            num1.setTypeface(font);
            num2.setTypeface(font);
            sign.setTypeface(font);
            answer.setTypeface(font);
            submitBtn.setTypeface(font);
        } catch (RuntimeException e) {
            Log.e("FontLoad", e.toString());
        }

        CreateQuestion();

    }

    /**
     * Return whether the given PackgeInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param pkgInfo
     * @return
     */
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }

    /****************************************************************
        Generate question and set the View object correctly
     **************************************************************/
    private void CreateQuestion() {
        answer.setText("");
        q = new Question('1');
        num1.setText(q.left);
        num2.setText(q.right);
        sign.setText(q.opSign);
    }


    /****************************************************************
        Responsible for handling clicks
     **************************************************************/
    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // Submit Answer
                case R.id.submitBtn:
                    AlertDialog.Builder alertBuild = new AlertDialog.Builder(MyActivity.this);

                    if (answer.getText().toString().equals("")) {
                        alertBuild.setCancelable(false).setPositiveButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        alertBuild.setTitle("Please input Answer");
                    } else if (answer.getText().toString().equals(q.answer)) {
                        alertBuild.setCancelable(false).setPositiveButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        CreateQuestion();
                                    }
                                });
                        alertBuild.setTitle("Answer Correct!");
                    } else {
                        alertBuild.setCancelable(false).setPositiveButton(
                                "Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        CreateQuestion();
                                    }
                                });
                        alertBuild.setTitle("Answer Incorrect");
                        alertBuild.setMessage("Correct Answer: " + q.answer);
                    }
                    AlertDialog alert = alertBuild.create();
                    alert.show();
                    break;
            }
        }
    }


}
