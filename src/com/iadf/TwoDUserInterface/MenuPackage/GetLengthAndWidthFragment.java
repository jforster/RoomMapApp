package com.iadf.TwoDUserInterface.MenuPackage;

import com.iadf.roommapapp.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class GetLengthAndWidthFragment extends DialogFragment{
	


	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.hello_world)
           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   // FIRE ZE MISSILES!
               }
           })
           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
            	   // User cancelled the dialog
               }
           });
    	// Create the AlertDialog object and return it
    	return builder.create();
	}
}
