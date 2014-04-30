package com.iadf.FurnitureDatabase.LocalDatabaseConnection;

import android.database.Cursor;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.roommapapp.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * performs all database actions
 * 
 * @author CSE324 Spring 2014 Team 4
 */
public class SQLRoomController extends SQLiteOpenHelper implements RoomController{
	
	public static final String DATABASE_NAME = "RoomMapApp";
    
    protected Context context;
    
    public SQLRoomController(Context context) {
            super(context, DATABASE_NAME, null, 1);
            this.context = context;
    }
	
    /**
     * creates the database if it does not already exist
     */
	@Override
    public void onCreate(SQLiteDatabase db) {
             String s;
             try {
                     InputStream in = context.getResources().openRawResource(R.raw.sql);
                     DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                     Document doc = builder.parse(in, null);
                     NodeList statements = doc.getElementsByTagName("statement");
                     for (int i=0; i<statements.getLength(); i++) {
                             s = statements.item(i).getChildNodes().item(0).getNodeValue();
                             db.execSQL(s);
                     }
             } catch (Throwable t) {
                     Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
             }
     }
	 
	/**
	 * returns a cursor over all rooms
	 */
	 @Override
	 public Cursor viewRooms(Object db) {
		SQLiteDatabase database = (SQLiteDatabase) db;
     	return database.rawQuery("SELECT * FROM rooms ", null);
     }
	
	 /**
	  * returns a room with the given roomNumber
	  */
	@Override
	public Cursor openRoom(Object db, int roomNumber) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		return database.rawQuery("SELECT * FROM rooms WHERE room_number = " + roomNumber , null);
	}

	/**
	 * returns the furniture object matching the guid of the furniture parameter
	 */
	@Override
	public Cursor lookupFurniture(Object db, Furniture furniture) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		return database.rawQuery("SELECT * FROM furniture WHERE _id = " + furniture.getGUID() , null);
	}

	/**
	 * adds the furniture object into the table
	 */
	@Override
	public void addFurniture(Object db, Furniture furniture) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.execSQL("INSERT INTO furniture (_id, room_number, center_x, center_y, width, length, shape, type) VALUES (" + furniture + ")");
		
	}

	/**
	 * adds a room to the database
	 */
	@Override
	public void addRoom(Object db, int width, int height) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.execSQL("INSERT INTO rooms (width, length) VALUES (" + width + ", " + height + ")");
		
	}

	/**
	 * returns a list of furniture that are inside of a given room
	 */
	@Override
	public Cursor getFurnitureList(Object db, int roomNumber) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		return database.rawQuery("SELECT * FROM furniture WHERE room_number = " + roomNumber, null);
	}

	/**
	 * removes a room from the database
	 */
	@Override
	public void deleteRoom(Object db, int roomNumber) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.rawQuery("DELETE FROM rooms WHERE room_number = " + roomNumber, null);
		
	}

	/**
	 * removes a furniture object from the database
	 */
	@Override
	public void deleteFurniture(Object db, Furniture furniture) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.rawQuery("DELETE FROM furniture WHERE _id = " + furniture.getGUID(), null);
		
	}

	/**
	 * changes the values for a given room
	 */
	@Override
	public void modifyRoom(Object db, int roomNumber, int width, int length) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.rawQuery("UPDATE rooms SET width = " + width + ", length = " + length + " WHERE room_number = " , null);
		
	}

	/**
	 * updates the values of a given furniture object in the database
	 */
	@Override
	public void modifyFurniture(Object db, Furniture furniture) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.rawQuery("UPDATE furniture SET " + furniture.dbUpdateString() + "WHERE _id = " + furniture.getGUID(), null);
		
	}

	/**
	 * drops the table if it needs to be changed
	 */
	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS furniture");
            db.execSQL("DROP TABLE IF EXISTS rooms");
            onCreate(db);
    }

}
