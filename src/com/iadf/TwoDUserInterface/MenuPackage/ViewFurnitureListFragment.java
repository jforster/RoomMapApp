package com.iadf.TwoDUserInterface.MenuPackage;

import com.iadf.FurnitureDatabase.LocalDatabaseConnection.DatabaseHelper;
import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.roommapapp.R;
import com.iadf.roommapapp.RoomViewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class ViewFurnitureListFragment extends ListFragment {

	DatabaseHelper helper = RoomViewer.helper;
	int room = RoomViewer.roomNumber;
	
	Cursor c = helper.getFurnitureList(RoomViewer.db, room);
	
	ListOnClick mListener;
	int id;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = this.getActivity().getLayoutInflater();
		final View v = inflater.inflate(R.layout.view_furniture_list_viewer, null);
		
		setListAdapter(new SimpleCursorAdapter(
                this.getActivity(), 
                R.layout.furniture_list_item, 
                c, 
                new String[] {"_id"}, 
                new int[] {R.id.a_guid},
                0));
		
	}
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the LookupDialogListener so we can send events to the host
            mListener = (ListOnClick) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    
    public void onListItemClick(ListView l, View v, int position, long id) {
    	this.getListView().setItemChecked(position, true);
    	final Integer item = (Integer) this.getListAdapter().getItem(position);
    	mListener.onListClick(this, item);
    	
    }

}


