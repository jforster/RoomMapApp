package com.iadf.TwoDUserInterface.MenuPackage;


import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.roommapapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;



public class ModifyRoomDialog extends DialogFragment {

	FurnitureListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (FurnitureListener) activity;
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
	    
	    final NumberPicker widthNP = (NumberPicker) v.findViewById(R.id.widthNP);
	    widthNP.setMaxValue(100000);
	    widthNP.setMinValue(1);
	    
	    final NumberPicker lengthNP = (NumberPicker) v.findViewById(R.id.lengthNP);
	    lengthNP.setMaxValue(100000);
	    lengthNP.setMinValue(1);
		
	    builder.setView(v)
	    // Add action buttons
	           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   Furniture f = new Furniture(0,0,0,0,0,0,0,0);
	            	   f.setRoomNumber(1);
	            	   f.setWidth(widthNP.getValue());
	            	   f.setLength(lengthNP.getValue());
	            	   mListener.onFurnitureDialogPositiveClick(ModifyRoomDialog.this, (Object) f);
	               }
	           })
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   mListener.onFurnitureDialogNegativeClick(ModifyRoomDialog.this);
	                   dialog.cancel();
	               }
	           });  
	    
	    builder.setTitle(R.string.modify_room_dim);
	    
	    return builder.create();
	}

}
