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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.iadf.FurnitureDatabase.LocalDatabaseConnection.DatabaseHelper;
import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.roommapapp.R;
import com.iadf.roommapapp.RoomViewer;

public class ViewRoomDialog extends DialogFragment {

	DatabaseHelper helper = RoomViewer.helper;
	int room = RoomViewer.roomNumber;
	
	Cursor c = helper.viewRooms(RoomViewer.db);
	
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
	    final ListView mFurnitureList = (ListView)v.findViewById(R.id.furniture_list);
	    mFurnitureList.setAdapter(adapter);
	    
	    mFurnitureList.setOnItemClickListener(new OnItemClickListener() {
	    	@Override
			public void onItemClick(final AdapterView<?> parentView, View view, int position, long id) {
	    	   ((ListView) parentView).setItemChecked(position, true);
	        	Cursor item = (Cursor) ((ListView) parentView).getAdapter().getItem(position);
	        	if(item.moveToPosition(position)) {
	        		mListener.onFurnitureDialogPositiveClick(ViewRoomDialog.this, (Object) item.getInt(0));
	        	}
	    	}
	    });

    builder.setView(v);
    builder.setTitle(R.string.select_furniture);
    return builder.create();
	}
}