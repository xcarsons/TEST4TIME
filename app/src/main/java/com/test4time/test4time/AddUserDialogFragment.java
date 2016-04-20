package com.test4time.test4time;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RadioGroup;

public class AddUserDialogFragment extends DialogFragment {

    String newUserName;
    String newGradeLevel;
    private RadioGroup addRadioGroup;

    public AddUserDialogFragment() {

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
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddUserDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement AddUserDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        addRadioGroup = (RadioGroup) getActivity().findViewById(R.id.radiogroup_grade);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.parent_createuser, null))
                // Add action buttons
                .setPositiveButton(R.string.submitText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;
                        // submit the info, add user to the list
                        EditText newName = (EditText) d.findViewById(R.id.parent_add_name);
                        newUserName = newName.getText().toString();

                        //TODO: remove the findViewById from this section
                        //      it's inefficient to call it in the onClick method

                        RadioGroup radioGroup = (RadioGroup) (d.findViewById(R.id.radiogroup_grade));
                        int radioID= radioGroup.getCheckedRadioButtonId();
                        if(radioID == -1){
                            //no item selected -> currently not possible b/c we init to Kindergarten
                        } else {
                            switch(radioID) {
                                case R.id.radioButton_K:    //grade is Kindergarten
                                    newGradeLevel = "K";
                                    break;
                                case R.id.radioButton_1:    //grade is 1st
                                    newGradeLevel = "1";
                                    break;
                                case R.id.radioButton_2:    //grade is 2nd
                                    newGradeLevel = "2";
                                    break;
                                case R.id.radioButton_3:    //grade is 3rd
                                    newGradeLevel = "3";
                                    break;
                                case R.id.radioButton_4:    //grade is 4th
                                    newGradeLevel = "4";
                                    break;
                                case R.id.radioButton_5:    //grade is 5th
                                    newGradeLevel = "5";
                                    break;
                                case R.id.radioButton_6:    //grade is 6th
                                    newGradeLevel = "6";
                                    break;
                            }
                        }

                        mListener.onDialogPositiveClick(AddUserDialogFragment.this);
//                        AddUserDialogFragment.this.getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel the creation
                        mListener.onDialogNegativeClick(AddUserDialogFragment.this);
//                        AddUserDialogFragment.this.getDialog().dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
//            return builder.create();
        dialog.setTitle("Child Information");
        return dialog;
    }
}
