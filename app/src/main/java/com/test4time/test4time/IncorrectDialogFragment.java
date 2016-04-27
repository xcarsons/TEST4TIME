package com.test4time.test4time;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class IncorrectDialogFragment extends DialogFragment {

    String newUserName;
    String newGradeLevel;
    //private RadioGroup addRadioGroup;
    Bundle bundle;

    public IncorrectDialogFragment() {
    }

    public String getUserName() {
        return newUserName;
    }

    public String getNewGradeLevel() {
        return newGradeLevel;
    }

    /* The activity that creates an instance of this dialog fragment must
    * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface AddUserDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    AddUserDialogListener mListener;


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        // Verify that the host activity implements the callback interface
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            mListener = (AddUserDialogListener) activity;
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(activity.toString()
//                    + " must implement AddUserDialogListener");
//        }
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //addRadioGroup = (RadioGroup) getActivity().findViewById(R.id.radiogroup_grade);

        //EditText newName = (EditText) getActivity().findViewById(R.id.parent_add_name);



//        Bundle bundle = getArguments();
//        left.setText(bundle.getString("num1"));
//        op.setText(bundle.getString("sign"));
//        right.setText(bundle.getString("num2"));
//        ans.setText(bundle.getString("answer"));


        //        alertBuild.setCancelable(false).setPositiveButton(
        //                "OK",
        //                new DialogInterface.OnClickListener() {
        //                    public void onClick(DialogInterface dialog, int id) {
        //                        dialog.cancel();
        //                        CreateQuestion();
        //                    }
        //                });
        //        alertBuild.setTitle("Incorrect Answer");
        //        //alertBuild.setMessage("Correct Answer: " + q.answer);
        //        alertBuild.setMessage(q.left + " " + q.opSign + " " + q.right + " = " + q.answer);
        //        AlertDialog alert = alertBuild.create();
        //        alert.show();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView(inflater.inflate(R.layout.incorrect_dialog, null))
                .setCancelable(false)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;
//                        TextView left = (TextView)dialog.findViewById(R.id.incorrect_left);
                        TextView left = (TextView) d.findViewById(R.id.incorrect_left);
                        TextView op= (TextView) d.findViewById(R.id.incorrect_op);
                        TextView right= (TextView) d.findViewById(R.id.incorrect_right);
                        TextView ans= (TextView) d.findViewById(R.id.incorrect_ans);

                        Bundle bundle = getArguments();
//                        left.setText(bundle.getString("num1"));
//                        op.setText(bundle.getString("sign"));
//                        right.setText(bundle.getString("num2"));
//                        ans.setText(bundle.getString("answer"));
                        // submit the info, add user to the list


//                        newUserName = newName.getText().toString();

                        //TODO: remove the findViewById from this section
                        //      it's inefficient to call it in the onClick method


//                        mListener.onDialogPositiveClick(IncorrectDialogFragment.this);
//                        AddUserDialogFragment.this.getDialog().dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
//            return builder.create();
        dialog.setTitle("");
        return dialog;
    }

    protected void showCustomDialog(final TextView num1, final TextView op, final TextView num2, final TextView ans) {

    }
}
