package com.test4time.test4time;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.LinkedList;
/*********************************************************
 * Class responsible for manipulating the math question view
 *********************************************************/
public class MyActivity extends Activity {

    Button submitBtn;
    //EditText answer;
    TextView answer;
    TextView num1, num2, sign;
    TextView grade, name;
    TextView time_saved, time_text;
    ImageView t4t;
    Question q;
    LinkedList<Question> questions;

    Button keypad_1, keypad_2, keypad_3, keypad_4, keypad_5;
    Button keypad_6, keypad_7, keypad_8, keypad_9;
    Button keypad_0, keypad_minus, keypad_back;

    Button playButton;

    String childName;

    int sampleTime;
    int userType;
    //Chronometer timer;
    AnalogClock clock;

    MediaPlayer mp;
    AssetFileDescriptor afd;

    /**
     * Called when the activity is first created.
     */
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove the top title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.mathquestion);
        setContentView(R.layout.mathquestion_v2);


        Intent intent = getIntent();
        if(intent.hasExtra("KEY")) {
            String easyPuzzle = intent.getExtras().getString("KEY");
        }


        mp = new MediaPlayer();
        afd = getResources().openRawResourceFd(R.raw.bellsound);

        t4t = (ImageView) findViewById(R.id.mqT4T);
        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        sign = (TextView) findViewById(R.id.sign);
        answer = (TextView) findViewById(R.id.answer);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        playButton = (Button) findViewById(R.id.play_button);
        grade = (TextView) findViewById(R.id.grade_label);
        name = (TextView) findViewById(R.id.name_label);
        sampleTime = 0;
        //timeText = (TextView) findViewById(R.id.time);
        //timer = (Chronometer) findViewById(R.id.chrono);
        clock = (AnalogClock) findViewById(R.id.analog_timer);
        time_saved = (TextView) findViewById(R.id.time_display_time);
        time_text = (TextView) findViewById(R.id.time_display_text);

        keypad_1 = (Button) findViewById(R.id.key_1);
        keypad_2 = (Button) findViewById(R.id.key_2);
        keypad_3 = (Button) findViewById(R.id.key_3);
        keypad_4 = (Button) findViewById(R.id.key_4);
        keypad_5 = (Button) findViewById(R.id.key_5);
        keypad_6 = (Button) findViewById(R.id.key_6);
        keypad_7 = (Button) findViewById(R.id.key_7);
        keypad_8 = (Button) findViewById(R.id.key_8);
        keypad_9 = (Button) findViewById(R.id.key_9);
        keypad_0 = (Button) findViewById(R.id.key_0);
//        keypad_minus = (Button) findViewById(R.id.key_minus);
        keypad_back = (Button) findViewById(R.id.key_back);



        ClickListener clickListener = new ClickListener();
        submitBtn.setOnClickListener(clickListener);
        playButton.setOnClickListener(clickListener);
        keypad_0.setOnClickListener(clickListener);
        keypad_1.setOnClickListener(clickListener);
        keypad_2.setOnClickListener(clickListener);
        keypad_3.setOnClickListener(clickListener);
        keypad_4.setOnClickListener(clickListener);
        keypad_5.setOnClickListener(clickListener);
        keypad_6.setOnClickListener(clickListener);
        keypad_7.setOnClickListener(clickListener);
        keypad_8.setOnClickListener(clickListener);
        keypad_9.setOnClickListener(clickListener);
//        keypad_minus.setOnClickListener(clickListener);
        keypad_back.setOnClickListener(clickListener);

        Intent myIntent = new Intent(getIntent());
        childName = (myIntent.getStringExtra("KEY"));
        String gradeLevel = (myIntent.getStringExtra("KEY2"));
        String timeRemaining = (myIntent.getStringExtra("KEY3"));

        // get whether user is using time or not, usertype will be 2 if using
        Database db = new Database(getApplicationContext(), null, null, 0, null);
        Cursor data = db.getUserData(childName);
        userType = 0;
        if (data.moveToNext()) {
            userType = Integer.parseInt(data.getString(2));
        }


        //answer.setOnEditorActionListener(submitListener);
        answer.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //Toast.makeText(MyActivity.this, answer.getText(), Toast.LENGTH_SHORT).show();
                    onSubmitAction();
                    return true;
                }
                return false;
            }
        });

        try {
            // instantiate font, then apply
            //Typeface font = Typeface.createFromAsset(getAssets(), "fonts/squeakychalksound.ttf");

            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chawp.ttf");
            num1.setTypeface(font);
            num2.setTypeface(font);
            sign.setTypeface(font);
            answer.setTypeface(font);
            submitBtn.setTypeface(font);
            playButton.setTypeface(font);
            //playButton.setText("Play");
            playButton.setText(userType==0?"Play" :"Pause");

            grade.setText("Grade: "+ gradeLevel);
            grade.setTypeface(font);
            name.setText(childName);
            name.setTypeface(font);

            time_saved.setTypeface(font);

            time_saved.setText(timeRemaining);
            time_text.setTypeface(font);
            time_text.setText(" minutes");


            keypad_1.setTypeface(font);
            keypad_2.setTypeface(font);
            keypad_3.setTypeface(font);
            keypad_4.setTypeface(font);
            keypad_5.setTypeface(font);
            keypad_6.setTypeface(font);
            keypad_7.setTypeface(font);
            keypad_8.setTypeface(font);
            keypad_9.setTypeface(font);
            keypad_0.setTypeface(font);
//            keypad_minus.setTypeface(font);
            keypad_back.setTypeface(font);
            //timer.setTypeface(font            );
            //text.setTextSize(16 * getResources().getDisplayMetrics().density);

        } catch (RuntimeException e) {
            Log.e("FontLoad", e.toString());
        }

        questions = new LinkedList<Question>();
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

    /****************************************************************
     Generate question and set the View object correctly
     **************************************************************/
    private void CreateQuestion() {
        if(questions.size() <= 0) {
            char gradelevel = 'K';

            //questions = q.generateQuestionPool(gradelevel);
            //System.out.printf("Grade: %s; charAt 0: %c\n", grade.getText(), grade.getText().charAt(7));
            questions = q.generateQuestionPool(grade.getText().charAt(7));
            //grade.setText("Grade: " + gradelevel);
        }
        answer.setText("");

        q = questions.remove();
        num1.setText(q.left);
        num2.setText(q.right);
        sign.setText(q.opSign);

    }

    private void onSubmitAction() {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(MyActivity.this);



        String ansString = answer.getText().toString();
        if (ansString.equals("")) {
            alertBuild.setCancelable(false).setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            alertBuild.setTitle("Please enter an answer.");
        } else  {
            try {
                // convert the submitted answer to an Integer (useful for '0' answer)
                // then convert back to a string for comparison
                ansString = Integer.toString(Integer.parseInt(ansString));
            }catch(NumberFormatException numEx) {
                Log.e("onSubmit", numEx.toString());
            }
            //ansString = ansString.replaceFirst("^0", "").replaceFirst("^0", "");
            if (ansString.equals(q.answer)) {
                //final MediaPlayer mp = MediaPlayer.create(this, R.raw.bellsound);
                //mp.start();

                try {
                    mp.reset();
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                mp.setDataSouce(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//                mp.reset();

                CreateQuestion();

                sampleTime++;

                int prev_time = Integer.parseInt(time_saved.getText().toString());
                prev_time++;
                time_saved.setText(Integer.toString(prev_time));
                Database db = new Database(getApplicationContext(), null, null, 0, null);
                db.modifyTime(childName,1);
                db.close();

            } else {
                alertBuild.setCancelable(false).setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                CreateQuestion();
                            }
                        });
                alertBuild.setTitle("Incorrect Answer");
                //alertBuild.setMessage("Correct Answer: " + q.answer);
                alertBuild.setMessage(q.left + " " + q.opSign + " " + q.right + " = " + q.answer);
                AlertDialog alert = alertBuild.create();
                alert.show();
            }

        }
    }

    /*
     * Handles play or pausing a users time
     */
    private void onPlayPauseAction() {
        Database db = new Database(getApplicationContext(), null, null, 0, null);
        Cursor data = db.getUserData(childName);
        if(data.moveToNext()) {
            userType = Integer.parseInt(data.getString(2));
        }

        // check if a user is already playing
        data = db.userUsingTime();
        if(data.moveToNext()) {
            if (!data.getString(1).equalsIgnoreCase(childName)) {
                AlertDialog.Builder alertBuild = new AlertDialog.Builder(MyActivity.this);
                alertBuild.setTitle(data.getString(1) + " is currently playing");
                alertBuild.setMessage("Please pause " + data.getString(1) + "'s time");
                alertBuild.setCancelable(false).setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                CreateQuestion();
                            }
                        });
                alertBuild.show();
                return;
            }
        }

        data.close();


        if (userType==0) {
            db.startUsingTime(childName,true);
            try {
                mp.reset();
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            playButton.setText("Pause");
        } else {
            db.startUsingTime(childName,false);
            playButton.setText("Play");
        }

        db.close();
    }

    private void onPressedKeypad(String num) {
        answer.setText(answer.getText() + num);
    }

    private void onPressedMinus(String minus) {
        if(answer.length() == 0)
            answer.setText(minus);
    }

    private void onRemoveAction() {
        /*TextView dialogText = (TextView) findViewById(R.id.key_back);
        dialogText.setText("cleared");
        try {
            Typeface font = Typeface.createFromAsset(getAssets(), "fonts/chawp.ttf");
            if(font == null)
                System.out.println("font is null");
            dialogText.setTypeface(font);
        }catch(RuntimeException e) {
            Log.e("Dialog Fontload", e.toString());
        }
        AlertDialog.Builder myalert = new AlertDialog.Builder(this);
        myalert.setTitle("Your title");
        myalert.setView(dialogText);
        myalert.setNeutralButton("Close dialog", null);
        myalert.setCancelable(true);
        myalert.show();*/

        if(!answer.getText().toString().isEmpty())
            answer.setText(answer.getText().toString().substring(0, answer.length() - 1));
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
                    onSubmitAction();
                    break;
                case R.id.play_button:
                    onPlayPauseAction();
                    break;
                case R.id.answer:
                    break;
                case R.id.key_0:
                    onPressedKeypad(keypad_0.getText().toString());
                    break;
                case R.id.key_1:
                    onPressedKeypad(keypad_1.getText().toString());
                    break;
                case R.id.key_2:
                    onPressedKeypad(keypad_2.getText().toString());
                    break;
                case R.id.key_3:
                    onPressedKeypad(keypad_3.getText().toString());
                    break;
                case R.id.key_4:
                    onPressedKeypad(keypad_4.getText().toString());
                    break;
                case R.id.key_5:
                    onPressedKeypad(keypad_5.getText().toString());
                    break;
                case R.id.key_6:
                    onPressedKeypad(keypad_6.getText().toString());
                    break;
                case R.id.key_7:
                    onPressedKeypad(keypad_7.getText().toString());
                    break;
                case R.id.key_8:
                    onPressedKeypad(keypad_8.getText().toString());
                    break;
                case R.id.key_9:
                    onPressedKeypad(keypad_9.getText().toString());
                    break;
//                case R.id.key_minus:
//                    onPressedMinus(keypad_minus.getText().toString());
//                    break;
                case R.id.key_back:
                    //remove the last typed number from answer
                    onRemoveAction();
                    //onSubmitAction();
                    break;
            }
        }
    }




}