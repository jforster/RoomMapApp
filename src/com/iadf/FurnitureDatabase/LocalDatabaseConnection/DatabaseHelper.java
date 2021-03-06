package com.iadf.FurnitureDatabase.LocalDatabaseConnection;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.iadf.SystemController.DatabaseController.Furniture;
import com.iadf.roommapapp.R;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * performs all database actions
 * 
 * @author CSE324 Spring 2014 Team 4
 */
public class DatabaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "RoomMapApp";
        
        protected Context context;
        
        public DatabaseHelper(Context context) {
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
        public Cursor viewRooms(SQLiteDatabase db) {
        	return db.rawQuery("SELECT * FROM rooms ", null);
        }
        
        /**
	   	  * returns a room with the given roomNumber
	   	  */
        public Cursor openRoom(SQLiteDatabase db, int roomNumber) {
        	return db.rawQuery("SELECT * FROM rooms ", null); //WHERE _id = " + roomNumber , null);
        }
        
        /**
    	 * returns the furniture object matching the guid of the furniture parameter
    	 */
        public Cursor lookupFurniture(SQLiteDatabase db, Furniture furniture) {
        	return db.rawQuery("SELECT * FROM furniture WHERE _id = " + furniture.getGUID() , null);
        }
        
        /**
    	 * adds the furniture object into the table
    	 */
        public void addFurniture(SQLiteDatabase db, Furniture furniture) {
        	db.execSQL("INSERT INTO furniture (_id, room_number, width, length, shape, center_x, center_y, type) VALUES (" + furniture + ")");
        }
        
        /**
    	 * adds a room to the database
    	 */
        public void addRoom(SQLiteDatabase db, int width, int height) {
        	db.execSQL("INSERT INTO rooms (width, length) VALUES (" + width + ", " + height + ")");
        }
        
        /**
    	 * returns a list of furniture that are inside of a given room
    	 */
        public Cursor getFurnitureList(SQLiteDatabase db, int roomNumber) {
        	return db.rawQuery("SELECT * FROM furniture WHERE room_number = " + roomNumber, null);
        }
        
        /**
    	 * removes a room from the database
    	 */
        public void deleteRoom(SQLiteDatabase db, int roomNumber) {
        	db.execSQL("DELETE FROM rooms WHERE _id = " + roomNumber);
        }
        
        /**
    	 * removes a furniture object from the database
    	 */
        public void deleteFurniture(SQLiteDatabase db, Furniture furniture) {
        	db.execSQL("DELETE FROM furniture WHERE _id = " + furniture.getGUID());
        }
        
        /**
    	 * changes the values for a given room
    	 */
        public void modifyRoom(SQLiteDatabase db, int roomNumber, int width, int length) {
        	db.execSQL("UPDATE rooms SET width = " + width + ", length = " + length + " WHERE _id = ");
        }
        
        /**
    	 * updates the values of a given furniture object in the database
    	 */
        public void modifyFurniture(SQLiteDatabase db, Furniture furniture) {
        	db.execSQL("UPDATE furniture SET " + furniture.dbUpdateString() + " WHERE _id = " + furniture.getGUID());
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
