package com.iadf.TwoDUserInterface.MenuPackage;


import java.util.ArrayList;

import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.roommapapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;



public class CreateFurnitureDialog extends DialogFragment {
	

	FurnitureListener mListener;
	int shape;
	int checkedItem = 0;
	boolean[] isChecked;
	int type;
    
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
	    final View v = inflater.inflate(R.layout.create_furniture_viewer, null);
	    
	    final NumberPicker widthNP = (NumberPicker) v.findViewById(R.id.widthNP);
	    widthNP.setMaxValue(100000);
	    widthNP.setMinValue(1);
	    
	    final NumberPicker lengthNP = (NumberPicker) v.findViewById(R.id.lengthNP);
	    lengthNP.setMaxValue(100000);
	    lengthNP.setMinValue(1);
	    
	    ArrayList mSelectedItems = new ArrayList();
	    
		
	    builder.setView(v)
	    // Add action buttons
	           .setSingleChoiceItems(R.array.shapes,  checkedItem, new OnClickListener() {
	
				   @Override
				   public void onClick(DialogInterface dialog, int which) {
						shape = which;
						
				   }
			   })
	    	   .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   int width;
	            	   int length;
	            	   width = widthNP.getValue();
	            	   length = lengthNP.getValue();
	            	   
	            	   Furniture furniture = new Furniture((int)(Math.random()*1000000), 1, width, length, shape, 0, 0, type);
	            	   
	            	   mListener.onFurnitureDialogPositiveClick(CreateFurnitureDialog.this, furniture);
	               }
	           })
	           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   mListener.onFurnitureDialogNegativeClick(CreateFurnitureDialog.this);
	                   dialog.cancel();
	               }
	           
	           });
	    
	    builder.setTitle(R.string.create_furniture_dim);
        
	    
	    return builder.create();
	}
}
