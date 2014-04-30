package com.iadf.roommapapp;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.iadf.SystemController.DatabaseController.Furniture;

/**
 * Contains the canvas to draw the room and furniture
 * 
 * @author CSE324 Spring 2014 Team 4
 */
public class RoomCanvas extends Fragment {
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	return (new RoomView(this.getActivity()));
    }
    
    /**
     * This class does the drawing when the room is opened or modified or when furniture
     * is moved or modified
     */
    public class RoomView extends View {
    	
    	private ArrayList<Furniture> furnitureItems;
    	private int i=-1;
    	int xOffset = 25;
    	int yOffset = 25;
    	int width = 999999;
    	int height = 999999;
    	Furniture f;
    	boolean canvasDrawn = false;
        
        public RoomView(Context context) {
        	super(context);
            setFocusable(true); //necessary for getting the touch events
            
            furnitureItems = createFurnitureList();
            
        }
        
        /**
         * returns a list of furniture contained within the room so that they can be drawn to the canvas
         * @return the list of furniture
         */
        public ArrayList<Furniture> createFurnitureList() {
        	ArrayList<Furniture> furniture = new ArrayList<Furniture>();
            
            Cursor c = RoomViewer.helper.getFurnitureList(RoomViewer.db, RoomViewer.roomNumber);
            
            c.moveToFirst();
            while (c.isAfterLast() == false) 
            {
                Furniture f = new Furniture(c.getInt(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5), c.getInt(6), c.getInt(7));
                furniture.add(f);
                c.moveToNext();
            }
            
            return furniture;
        }
         
         /**
          * draws all furniture objects that are contained within the furniture list 
          */
         @SuppressLint("DrawAllocation")
		 @Override 
         protected void onDraw(Canvas canvas) {  
  
        	width = canvas.getWidth();
          	height = canvas.getHeight();
          	
          	Paint paint = new Paint();
          	
      		paint.setColor(Color.BLACK);
            paint.setStrokeWidth(5);
            canvas.drawRect(95, 95, width - 95, height - 95, paint);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(5);
            canvas.drawRect(100, 100, width - 100, height - 100, paint);
            
            canvasDrawn = true;
             
         	for (Furniture furniture : furnitureItems) {
         		Bitmap bitmap;
         		if(furniture.getShape() == Furniture.OVAL) bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.oval);
         		else bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.rectangle);
         		Bitmap furnitureItem = Bitmap.createScaledBitmap(bitmap, furniture.getLength(), furniture.getWidth(), true);
         		Point p = furniture.getCenter();
                canvas.drawBitmap(furnitureItem, p.x, p.y, null);
            }
     
         }
         
         /**
          * determines where to draw the item based on X-coordinate in the database
          */
         public int setX(int x) {
        	 if(x < 102) {
         		return 102;
         	} else if(x > width - 100 - 2*xOffset) {
         		return width - 100 - 2*xOffset;
         	}
         	return x;
         }
         
         /**
          * determines where to draw the item based on Y-coordinate in the database
          */
         public int setY(int y) {
         	if(y < 102) {
         		return 102;
         	} else if (y > height - 100 - 2*yOffset) {
         		return height - 100 - 2*yOffset;
         	}
         	return y;
         }

         /**
          * handles moving the furniture
          */
         public boolean onTouchEvent(MotionEvent event) {
             int eventaction = event.getAction(); 
             
             int X = (int)event.getX(); 
             int Y = (int)event.getY(); 

             switch (eventaction ) { 

             case MotionEvent.ACTION_DOWN: // checks if the touch is on a furniture item
            	i = -1;
             	for (Furniture furniture : furnitureItems) {
             		Point p = furniture.getCenter();

             		if (X > p.x && X < p.x+furniture.getLength() && Y > p.y && Y < p.y+furniture.getWidth()){
                     	i = furnitureItems.indexOf(furniture);
                     	xOffset = furniture.getLength()/2 + 1;
                     	yOffset = furniture.getWidth()/2 + 1;
                     	break;
                    }
                   }
                  
                  break; 


             case MotionEvent.ACTION_MOVE:   // drags the furniture object updating its coordinates
                 if (i >= 0) {
                	f = furnitureItems.get(i);
                	f.setCenter(setX(X-xOffset), setY(Y-yOffset));
                	furnitureItems.set(i, f);
                 	
                 }
             	
                 break; 

             case MotionEvent.ACTION_UP: // update the database after the finger is taken off the screen
            	 xOffset = 25;
             	 yOffset = 25;
             	 if(f != null) {
             		RoomViewer.helper.modifyFurniture(RoomViewer.db, f);
             		furnitureItems = createFurnitureList();
             	 }
                 break; 
             } 
             
             // redraws the canvas
             invalidate(); 
             return true; 
     	
         }
    }
}