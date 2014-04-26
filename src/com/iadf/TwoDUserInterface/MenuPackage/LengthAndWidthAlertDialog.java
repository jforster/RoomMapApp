package com.iadf.TwoDUserInterface.MenuPackage;


import com.iadf.roommapapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



public class LengthAndWidthAlertDialog extends DialogFragment {
	
	public interface LenghtAndWidthListener {
        public void onDialogPositiveClick(DialogFragment dialog, int length, int width);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

	LenghtAndWidthListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (LenghtAndWidthListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = this.getActivity().getLayoutInflater();
	    
	    

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    final View v = inflater.inflate(R.layout.create_room_viewer, null);
	    builder.setView(v)
	    // Add action buttons
	           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   int width;
	            	   int length;
	            	   TextView tv = (TextView) v.findViewById(R.id.width);
		       			width = Integer.parseInt(tv.getText().toString());
		       			tv = (TextView) v.findViewById(R.id.length);
		       			length = Integer.parseInt(tv.getText().toString());
	            	   mListener.onDialogPositiveClick(LengthAndWidthAlertDialog.this, length, width);
	               }
	           })
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   mListener.onDialogNegativeClick(LengthAndWidthAlertDialog.this);
	                   dialog.cancel();
	               }
	           });      
	    return builder.create();
	}
}
