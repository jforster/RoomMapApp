package com.iadf.roommapapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iadf.FurnitureDatabase.LocalDatabaseConnection.DatabaseHelper;
import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.TwoDUserInterface.MenuPackage.CreateFurnitureDialog;
import com.iadf.TwoDUserInterface.MenuPackage.CreateRoomDialog;
import com.iadf.TwoDUserInterface.MenuPackage.CreateRoomDialog.LenghtAndWidthListener;
import com.iadf.TwoDUserInterface.MenuPackage.FurnitureListener;
import com.iadf.TwoDUserInterface.MenuPackage.LookupFurnitureDialog;
import com.iadf.TwoDUserInterface.MenuPackage.ViewFurnitureDialog;
import com.iadf.TwoDUserInterface.MenuPackage.ViewRoomDialog;

/**
 * The main activity that contains all the buttons and fragments
 * 
 * @author CSE324 Spring 2014 Team 4
 */
public class RoomViewer extends FragmentActivity implements LenghtAndWidthListener, FurnitureListener {
	
	public static SQLiteDatabase db;
	public static DatabaseHelper helper;
	int width;
	int length;
	public static int roomNumber = 1;
	public static Furniture furnitureBuffer = new Furniture(0,0,0,0,0,0,0,0);
	Furniture selectedFurniture;
	int operation;
	
	SharedPreferences sp;
	
	// cases from each dialog
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
		
		sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
		if(sp.contains("room_number")) RoomViewer.roomNumber = sp.getInt("room_number", -1);

	}
	
	/**
	 * saves the currently open room so that when the application is reopened it will remember which room to open.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("room_number", roomNumber);
		editor.commit();
	}
	
	/**
	 * opens the room from when the application was last closed
	 */
	@Override
	protected void onResume() {
		super.onResume();
		sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
		if(sp.contains("room_number")) RoomViewer.roomNumber = sp.getInt("room_number", -1);
	}

	/**
	 * opens the menu when the menu button is pressed
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.room_viewer, menu);
		return true;
	}

	/**
	 * handles what happens when a menu option is touched
	 */
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
	
	/**
	 * shows a list of all rooms in the database
	 * @param v
	 */
	public void loadRoom(View v) {
		
		operation = RoomViewer.LOAD_ROOM;
		
		DialogFragment d = new ViewRoomDialog();
		d.show(getSupportFragmentManager(), "ViewRoomDialog");
    }
	
	/**
	 * opens a dialog to create a new room in the database
	 * @param v
	 */
	public void createRoom(View v) {
		
		DialogFragment d = new CreateRoomDialog();
		d.show(getSupportFragmentManager(), "LengthAndWidthAlertDialog");
    }
	
	/**
	 * shows a list of rooms so the user can select one to delete
	 * @param v
	 */
	public void deleteRoom(View v) {
		operation = RoomViewer.DELETE_ROOM;
		DialogFragment d = new ViewRoomDialog();
		d.show(getSupportFragmentManager(), "ViewRoomDialog");   
		//helper.addFurniture(db, null);

    }
	
	/**
	 * opens the create furniture dialog
	 * @param v
	 */
	public void createFurniture(View v) {
		DialogFragment d = new CreateFurnitureDialog();
		operation = RoomViewer.CREATE;
		d.show(getSupportFragmentManager(), "CreateFurnitureDialog");
		
    }
	
	/**
	 * opens the lookup furniture dialog
	 * @param v
	 */
	public void lookupFurniture(View v) {
        DialogFragment d = new LookupFurnitureDialog();
        operation = RoomViewer.LOOKUP_FURNITURE;
        d.show(getSupportFragmentManager(), "LookupFurnitureDialog");
    }
	
	/**
	 * opens the modify furniture dialog
	 * @param v
	 */
	public void modifyFurniture(View v) {
		operation = RoomViewer.MODIFY_ROOM;
		DialogFragment d = new CreateRoomDialog();
		d.show(getSupportFragmentManager(), "ViewRoomDialog");
    }
	
	/**
	 * opens the view furniture dialog with a list of furniture in the currently open room
	 * @param v
	 */
	public void viewFurniture(View v) {
	    operation = RoomViewer.VIEW;
		DialogFragment d = new ViewFurnitureDialog();
		d.show(getSupportFragmentManager(), "ViewFurnitureDialog");
    }

	/**
	 * handles what happens when the "OK" is clicked for a room dialog
	 */
	@Override
	public void onDialogPositiveClick(DialogFragment dialog, int length, int width) {
		this.width = width;
		this.length = length;
		helper.addRoom(db, width, length);
		dialog.dismiss();
	}

	/**
	 * closes the dialog when "Cancel" is clicked
	 */
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		
	}
	
	/**
	 * handles all cases when "OK" is clicked from a furniture dialog
	 */
	@Override
	public void onFurnitureDialogPositiveClick(DialogFragment dialog, Object f){
		switch(operation) {
			case RoomViewer.DELETE_FURNITURE:{
				helper.deleteFurniture(db, (Furniture) f);
			}; break;
			case RoomViewer.DELETE_ROOM:{
				helper.deleteRoom(db, (Integer) f);
				RoomViewer.roomNumber = 1;
				helper.openRoom(db, roomNumber);
				finish();
				startActivity(getIntent());
			}; break;
			case RoomViewer.MODIFY_FURNITURE:{
				helper.modifyFurniture(db, (Furniture) f);
			}; break;
			//Furniture object is used as a buffer to hold the room number, room width and room length.
			case RoomViewer.MODIFY_ROOM: {
				Furniture k = (Furniture)f;
				helper.modifyRoom(db, k.getRoomNumber(), k.getWidth(), k.getLength());
			} break;
			case RoomViewer.VIEW: {
				RoomViewer.furnitureBuffer.setGUID((Integer) f);
				Cursor c = helper.lookupFurniture(db, new Furniture((Integer) f, 0, 0, 0, 0, 0, 0, 0));
				c.moveToFirst();
				RoomViewer.roomNumber = c.getInt(1);
				helper.openRoom(db, roomNumber);
				finish();
				startActivity(getIntent());
			};break;
			case RoomViewer.LOOKUP_FURNITURE: {
				Cursor c = helper.lookupFurniture(db, (Furniture) f); 
				if(c.moveToFirst()) {
					RoomViewer.roomNumber = c.getInt(1);
					helper.openRoom(db, roomNumber);
					finish();
					startActivity(getIntent());
				} else {
					Toast.makeText(this, "Furniture Not Found", Toast.LENGTH_LONG).show(); 
				}
			}; break;
			case RoomViewer.LOAD_ROOM: {
				Cursor c = helper.openRoom(db, (Integer) f); 
				c.moveToFirst();
				RoomViewer.roomNumber = (Integer) f;
				finish();
				startActivity(getIntent());
			} break;
			case RoomViewer.CREATE: { 
				helper.addFurniture(db, (Furniture) f);
				finish();
				startActivity(getIntent());
			};  break;
			default: {System.out.println("Error");} break;
		}
		
		dialog.dismiss();
	}

	/**
	 * closes the dialog when "Cancel" is clicked
	 */
	@Override
	public void onFurnitureDialogNegativeClick(DialogFragment dialog) {
		
	}
	
	/**
	 * sets a furniture object to be a container when the user checks the 
	 * container checkbox
	 * @param v
	 */
	public void onCheckboxClicked(View v) {
		
	}

}
