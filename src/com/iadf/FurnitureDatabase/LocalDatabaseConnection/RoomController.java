package com.iadf.FurnitureDatabase.LocalDatabaseConnection;

import com.iadf.SystemController.DatabaseController.Furniture;
import android.database.Cursor;


public interface RoomController {            
        
        public Cursor openRoom(Object db, int roomNumber);
        
        
        public Cursor lookupFurniture(Object db, Furniture furniture);
        
        
        public void addFurniture(Object db, Furniture furniture);
        
        
        public void addRoom(Object db, int width, int height);
        
        
        public Cursor getFurnitureList(Object db, int roomNumber);
        
        
        public void deleteRoom(Object db, int roomNumber);
        
        
        public void deleteFurniture(Object db, Furniture furniture);
        
        
        public void modifyRoom(Object db, int roomNumber, int width, int length);
        
        
        public void modifyFurniture(Object db, Furniture furniture);


		public Cursor viewRooms(Object db);
        
}
