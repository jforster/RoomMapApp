package com.iadf.TwoDUserInterface.MenuPackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.iadf.FurnitureDatabase.LocalDatabaseConnection.DatabaseHelper;
import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.roommapapp.R;
import com.iadf.roommapapp.RoomViewer;

public class ViewFurnitureDialog extends DialogFragment {

	DatabaseHelper helper = RoomViewer.helper;
	int room = RoomViewer.roomNumber;
	
	Cursor c = helper.getFurnitureList(RoomViewer.db, room);
	
	FurnitureListener mListener;
	int id;
    
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
	    final View v = inflater.inflate(R.layout.view_furniture_list_viewer, null);
	    
	    final ListAdapter adapter = new SimpleCursorAdapter(
                this.getActivity(), 
                R.layout.furniture_list_item, 
                c, 
                new String[] {"_id"}, 
                new int[] {R.id.a_guid},
                0);
	    ListView mFurnitureList = (ListView)v.findViewById(R.id.furniture_list);
	    mFurnitureList.setAdapter(adapter);
	    
	    mFurnitureList.setOnItemClickListener(new OnItemClickListener() {
	    	@Override
			public void onItemClick(final AdapterView<?> parentView, View view, int position, long id) {
	    		Cursor c = (Cursor) adapter.getItem(position);
	    		if(c.moveToFirst()) {
	    		   id = c.getColumnIndex("_id");
	    		}
	    	}
	    });

    builder.setView(v)
    // Add action buttons
           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

			@Override
               public void onClick(DialogInterface dialog, int id) {
				Furniture f = new Furniture(id, 0 ,0, 0, 0, 0, 0, 0);
				mListener.onFurnitureDialogPositiveClick(ViewFurnitureDialog.this, f);
            	   	dialog.dismiss();
            	 
               }
           })
           .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
            	   mListener.onFurnitureDialogNegativeClick(ViewFurnitureDialog.this);
                   dialog.cancel();
               }
           });  
    
    builder.setTitle(R.string.select_furniture);
    return builder.create();
	}
}