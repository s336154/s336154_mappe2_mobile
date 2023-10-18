package com.example.s336154_mappe2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (MyInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Kallende klasse må implementere interfacet!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new
                AlertDialog.Builder(getActivity()).setTitle(R.string.dialogContact).setPositiveButton(R.string.contactDialogOK,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onYesClick();
                    }
                }).setNegativeButton(R.string.contactDialogNO,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onNoClick(); }
                }).create();
    }


    private MyInterface callback;

    public interface MyInterface {
        public void onYesClick();
        public void onNoClick();
    }




}