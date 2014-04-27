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


public class SQLRoomController extends SQLiteOpenHelper implements RoomController{
	
	public static final String DATABASE_NAME = "RoomMapApp";
    
    protected Context context;
    
    public SQLRoomController(Context context) {
            super(context, DATABASE_NAME, null, 1);
            this.context = context;
    }
	
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

	@Override
	public Cursor openRoom(Object db, int roomNumber) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		return database.rawQuery("SELECT * FROM rooms ", null); //WHERE room_number = " + roomNumber , null);
	}

	@Override
	public Cursor lookupFurniture(Object db, Furniture furniture) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		return database.rawQuery("SELECT * FROM furniture WHERE _id = " + furniture.getGUID() , null);
	}

	@Override
	public void addFurniture(Object db, Furniture furniture) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.execSQL("INSERT INTO furniture (_id, room_number, center_x, center_y, width, length, shape, type) VALUES (" + furniture + ")");
		
	}

	@Override
	public void addRoom(Object db, int width, int height) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.execSQL("INSERT INTO rooms (width, length) VALUES (" + width + ", " + height + ")");
		
	}

	@Override
	public Cursor getFurnitureList(Object db, int roomNumber) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		return database.rawQuery("SELECT * FROM furniture WHERE room_number = " + roomNumber, null);
	}

	@Override
	public void deleteRoom(Object db, int roomNumber) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.rawQuery("DELETE FROM rooms WHERE room_number = " + roomNumber, null);
		
	}

	@Override
	public void deleteFurniture(Object db, Furniture furniture) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.rawQuery("DELETE FROM furniture WHERE _id = " + furniture.getGUID(), null);
		
	}

	@Override
	public void modifyRoom(Object db, int roomNumber, int width, int length) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.rawQuery("UPDATE rooms SET width = " + width + ", length = " + length + " WHERE room_number = " , null);
		
	}

	@Override
	public void modifyFurniture(Object db, Furniture furniture) {
		SQLiteDatabase database = (SQLiteDatabase) db;
		database.rawQuery("UPDATE furniture SET " + furniture.sqlUpdateString() + "WHERE _id = " + furniture.getGUID(), null);
		
	}

	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS furniture");
            db.execSQL("DROP TABLE IF EXISTS rooms");
            onCreate(db);
    }

}
