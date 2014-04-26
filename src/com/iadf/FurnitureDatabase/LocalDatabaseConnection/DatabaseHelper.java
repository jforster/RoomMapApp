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

public class DatabaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "RoomMapApp";
        
        protected Context context;
        
        public DatabaseHelper(Context context) {
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
        
        
        public Cursor openRoom(SQLiteDatabase db, int roomNumber) {
        	return db.rawQuery("SELECT * FROM rooms ", null); //WHERE room_number = " + roomNumber , null);
        }
        
        public Cursor lookupFurniture(SQLiteDatabase db, Furniture furniture) {
        	return db.rawQuery("SELECT * FROM furniture WHERE _id = " + furniture.getGUID() , null);
        }
        
        public void addFurniture(SQLiteDatabase db, Furniture furniture) {
        	db.execSQL("INSERT INTO furniture (_id, room_number, center_x, center_y, width, length, shape, type) VALUES (" + furniture + ")");
        }
        
        
        public void addRoom(SQLiteDatabase db, int width, int height) {
        	db.execSQL("INSERT INTO rooms (width, length) VALUES (" + width + ", " + height + ")");
        }
        
        
        public Cursor getFurnitureList(SQLiteDatabase db, int roomNumber) {
        	return db.rawQuery("SELECT * FROM furniture WHERE room_number = " + roomNumber, null);
        }
        
        
        public void deleteRoom(SQLiteDatabase db, int roomNumber) {
        	db.rawQuery("DELETE FROM rooms WHERE room_number = " + roomNumber, null);
        }
        
        
        public void deleteFurniture(SQLiteDatabase db, Furniture furniture) {
        	db.rawQuery("DELETE FROM furniture WHERE _id = " + furniture.getGUID(), null);
        }
        
        
        public void modifyRoom(SQLiteDatabase db, int roomNumber, int width, int length) {
        	db.rawQuery("UPDATE rooms SET width = " + width + ", length = " + length + " WHERE room_number = " , null);
        }
        
        
        public void modifyFurniture(SQLiteDatabase db, Furniture furniture) {
        	db.rawQuery("UPDATE furniture SET " + furniture.sqlUpdateString() + "WHERE _id = " + furniture.getGUID(), null);
        }
        

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS furniture");
                db.execSQL("DROP TABLE IF EXISTS rooms");
                onCreate(db);
        }
}
