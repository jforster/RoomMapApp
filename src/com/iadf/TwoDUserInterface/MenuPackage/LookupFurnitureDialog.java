package com.iadf.TwoDUserInterface.MenuPackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.roommapapp.R;

public class LookupFurnitureDialog extends DialogFragment {
	
	
	

	FurnitureListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the LookupDialogListener so we can send events to the host
            mListener = (FurnitureListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = this.getActivity().getLayoutInflater();
	    
	    

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    final View v = inflater.inflate(R.layout.lookup_furniture_viewer, null);

    builder.setView(v)
    // Add action buttons
           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

			@Override
               public void onClick(DialogInterface dialog, int id) {
            	   EditText et = (EditText) v.findViewById(R.id.furniture_guid);
            	   String s = et.getText().toString();
            	   Furniture f = null;
            	   if(!s.equals("")) {
            		   f = new Furniture(Integer.parseInt(s), 0, 0, 0, 0, 0, 0, 0);
            		   mListener.onFurnitureDialogPositiveClick(LookupFurnitureDialog.this, f );
            	   }
            	 
            	   	dialog.dismiss();
            	 
               }
           })
           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
            	   mListener.onFurnitureDialogNegativeClick(LookupFurnitureDialog.this);
                   dialog.cancel();
               }
           });  
    
    builder.setTitle(R.string.furniture_lookup);
    return builder.create();
	}
}
