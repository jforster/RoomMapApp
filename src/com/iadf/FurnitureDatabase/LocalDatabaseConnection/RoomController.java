package com.iadf.FurnitureDatabase.LocalDatabaseConnection;

import com.iadf.SystemController.DatabaseController.Furniture;
import android.database.Cursor;

/**
 * interface containing all necessary methods for a database controller
 * 
 * @author CSE324 Spring 2014 Team 4
 */
public interface RoomController {            
        
	/**
	  * returns a room with the given roomNumber
	  */
    public Cursor openRoom(Object db, int roomNumber);
    
    /**
	 * returns the furniture object matching the guid of the furniture parameter
	 */
    public Cursor lookupFurniture(Object db, Furniture furniture);
    
    /**
	 * adds the furniture object into the table
	 */
    public void addFurniture(Object db, Furniture furniture);
    
    /**
	 * adds a room to the database
	 */
    public void addRoom(Object db, int width, int height);
    
    /**
	 * returns a list of furniture that are inside of a given room
	 */
    public Cursor getFurnitureList(Object db, int roomNumber);
    
    /**
	 * removes a room from the database
	 */
    public void deleteRoom(Object db, int roomNumber);
    
    /**
	 * removes a furniture object from the database
	 */
    public void deleteFurniture(Object db, Furniture furniture);
    
    /**
	 * changes the values for a given room
	 */
    public void modifyRoom(Object db, int roomNumber, int width, int length);
    
    /**
	 * updates the values of a given furniture object in the database
	 */
    public void modifyFurniture(Object db, Furniture furniture);

    /**
	 * returns a cursor over all rooms
	 */
	public Cursor viewRooms(Object db);
    
}
