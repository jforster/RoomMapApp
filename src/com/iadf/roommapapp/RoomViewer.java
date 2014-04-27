package com.iadf.roommapapp;

import com.iadf.FurnitureDatabase.LocalDatabaseConnection.DatabaseHelper;
import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.TwoDUserInterface.MenuPackage.CreateFurnitureDialog;
import com.iadf.TwoDUserInterface.MenuPackage.FurnitureListener;
import com.iadf.TwoDUserInterface.MenuPackage.CreateRoomDialog;
import com.iadf.TwoDUserInterface.MenuPackage.CreateRoomDialog.LenghtAndWidthListener;
import com.iadf.TwoDUserInterface.MenuPackage.LookupFurnitureDialog;
import com.iadf.TwoDUserInterface.MenuPackage.ViewFurnitureDialog;
import com.iadf.TwoDUserInterface.MenuPackage.ViewRoomDialog;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class RoomViewer extends FragmentActivity implements LenghtAndWidthListener, FurnitureListener {
	
	public static SQLiteDatabase db;
	public static DatabaseHelper helper;
	int width;
	int length;
	public static int roomNumber = 1;
	public static Furniture furnitureBuffer = new Furniture(0,0,0,0,0,0,0,0);
	Furniture selectedFurniture;
	int operation;
	
	static final int CREATE = 1;
	static final int LOOKUP_FURNITURE = 2;
	static final int LOAD_ROOM = 8;
	static final int MODIFY_ROOM = 3;
	static final int MODIFY_FURNITURE = 7;
	static final int VIEW = 4;
	static final int DELETE_ROOM = 5;
	static final int DELETE_FURNITURE = 6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room_viewer);
		db = (new DatabaseHelper(this)).getWritableDatabase();
		helper = new DatabaseHelper(this);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.room_viewer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_room_viewer,
					container, false);
			return rootView;
		}
	}
	
	
	public void loadRoom(View v) {
		
		operation = RoomViewer.VIEW;
		DialogFragment d = new ViewRoomDialog();
		d.show(getSupportFragmentManager(), "ViewRoomDialog");
		
		//Cursor c = helper.lookupRoom(db, 1);
		//Toast.makeText(this, Integer.toString(c.getCount()), Toast.LENGTH_LONG).show();
    }
	
	public void createRoom(View v) {
		
		DialogFragment d = new CreateRoomDialog();
		d.show(getSupportFragmentManager(), "LengthAndWidthAlertDialog");
    }
	
	public void deleteRoom(View v) {
		operation = RoomViewer.DELETE_ROOM;
		DialogFragment d = new ViewRoomDialog();
		d.show(getSupportFragmentManager(), "ViewRoomDialog");   
		//helper.addFurniture(db, null);

    }
	
	public void createFurniture(View v) {
		DialogFragment d = new CreateFurnitureDialog();
		operation = RoomViewer.CREATE;
		d.show(getSupportFragmentManager(), "CreateFurnitureDialog");
		
    }
	
	public void lookupFurniture(View v) {
        DialogFragment d = new LookupFurnitureDialog();
        operation = RoomViewer.LOOKUP_FURNITURE;
        d.show(getSupportFragmentManager(), "LookupFurnitureDialog");
    }
	
	public void modifyFurniture(View v) {
		operation = RoomViewer.MODIFY_ROOM;
		DialogFragment d = new CreateRoomDialog();
		d.show(getSupportFragmentManager(), "ViewRoomDialog");
		
		//helper.addFurniture(db, null);
    }
	
	public void viewFurniture(View v) {
	    operation = RoomViewer.VIEW;
		DialogFragment d = new ViewFurnitureDialog();
		d.show(getSupportFragmentManager(), "ViewFurnitureDialog");
		//helper.addFurniture(db, null);
    }

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, int length, int width) {
		Toast.makeText(this,length + " " + width, Toast.LENGTH_LONG).show();
		this.width = width;
		this.length = length;
		helper.addRoom(db, width, length);
		dialog.dismiss();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onFurnitureDialogPositiveClick(DialogFragment dialog, Object f){
		switch(operation) {
			case RoomViewer.DELETE_FURNITURE:{
				helper.deleteFurniture(db, (Furniture) f);
			}; break;
			case RoomViewer.DELETE_ROOM:{
				helper.deleteRoom(db, (Integer) f);
			}; break;
			case RoomViewer.MODIFY_FURNITURE:{
				helper.modifyFurniture(db, (Furniture) f);
			}; break;
			//Furniture object is used as a buffer to hold the room number, room width and room length.  Look we saved a class!
			case RoomViewer.MODIFY_ROOM: {
				Furniture k = (Furniture)f;
				helper.modifyRoom(db, k.getRoomNumber(), k.getWidth(), k.getLength());
			} break;
			case RoomViewer.VIEW: {
				RoomViewer.furnitureBuffer.setGUID((Integer) f);
				Toast.makeText(this, RoomViewer.furnitureBuffer.getGUID() + "", Toast.LENGTH_LONG).show();
			};break;
			case RoomViewer.LOOKUP_FURNITURE: {
				Cursor c = helper.lookupFurniture(db, (Furniture) f); 
				Toast.makeText(this, c.getCount() + "", Toast.LENGTH_LONG).show(); 
			}; break;
			case RoomViewer.LOAD_ROOM: {
				Cursor c = helper.openRoom(db, (Integer) f); 
				Toast.makeText(this, c.getCount() + "", Toast.LENGTH_LONG).show(); 
			} break;
			case RoomViewer.CREATE: { 
				helper.addFurniture(db, (Furniture) f);
				Toast.makeText(this, ((Furniture)f).dbUpdateString(), Toast.LENGTH_LONG).show(); 
			};  break;
			default: {System.out.println("Error");} break;
		}
		
		//selectedFurniture = (Furniture) f;
		
		dialog.dismiss();
	}

	@Override
	public void onFurnitureDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
	
	public void onCheckboxClicked(View v) {
		
	}

}
