package com.iadf.roommapapp;


import java.util.ArrayList;

import com.iadf.SystemController.DatabaseController.Furniture;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class RoomCanvas extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	return (new RoomView(this.getActivity()));
        //return inflater.inflate(R.layout.room_canvas, container, true);
    }
    
    public void refresh() {
    	
    }
    
    
    public class RoomView extends View {
    	
    	private ArrayList<Furniture> furnitureItems;
    	private int i=-1;
    	int xOffset = 25;
    	int yOffset = 25;
    	int width = 999999;
    	int height = 999999;
    	Furniture f;
        
        public RoomView(Context context) {
        	super(context);
            setFocusable(true); //necessary for getting the touch events
            
            furnitureItems = createFurnitureList();
            
        }
        
        
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
        
        public boolean hasFurnitureChanged() {
        	return !furnitureItems.equals(createFurnitureList());
        }
         
         // the method that draws the balls
         @Override 
         protected void onDraw(Canvas canvas) {
             //canvas.drawColor(0xFFCCCCCC);     //if you want another background color     
        	 
        	width = canvas.getWidth();
          	height = canvas.getHeight();
          	
          	Paint paint = new Paint();
          	
          	paint.setColor(Color.BLACK);
            paint.setStrokeWidth(5);
            canvas.drawRect(95, 95, width - 95, height - 95, paint);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(5);
            canvas.drawRect(100, 100, width - 100, height - 100, paint);
             
         	//draw the balls on the canvas
         	for (Furniture furniture : furnitureItems) {
         		Bitmap bitmap;
         		if(furniture.getShape() == Furniture.OVAL) bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.oval);
         		else bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.rectangle);
         		Bitmap furnitureItem = Bitmap.createScaledBitmap(bitmap, furniture.getLength(), furniture.getWidth(), true);
         		Point p = furniture.getCenter();
                canvas.drawBitmap(furnitureItem, p.x, p.y, null);
            }
     
         }
         
         public int setX(int x) {
        	 if(x < 102) {
         		return 102;
         	} else if(x > width - 100 - 2*xOffset) {
         		return width - 100 - 2*xOffset;
         	}
         	return x;
         }
         
         public int setY(int y) {
         	if(y < 102) {
         		return 102;
         	} else if (y > height - 100 - 2*yOffset) {
         		return height - 100 - 2*yOffset;
         	}
         	return y;
         }

         // events when touching the screen
         public boolean onTouchEvent(MotionEvent event) {
             int eventaction = event.getAction(); 
             
             int X = (int)event.getX(); 
             int Y = (int)event.getY(); 

             switch (eventaction ) { 

             case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
            	i = -1;
             	for (Furniture furniture : furnitureItems) {
             		// check if inside the bounds of the ball (circle)
             		// get the center for the ball
             		Point p = furniture.getCenter();

             		// check all the bounds of the ball (square)
             		if (X > p.x && X < p.x+furniture.getLength() && Y > p.y && Y < p.y+furniture.getWidth()){
                     	i = furnitureItems.indexOf(furniture);
                     	xOffset = furniture.getLength()/2 + 1;
                     	yOffset = furniture.getWidth()/2 + 1;
                     	break;
                    }
                   }
                  
                  break; 


             case MotionEvent.ACTION_MOVE:   // touch drag with the ball
             	// move the balls the same as the finger
                 if (i >= 0) {
                	f = furnitureItems.get(i);
                	f.setCenter(setX(X-xOffset), setY(Y-yOffset));
                 	furnitureItems.set(i, f);
                 	
                 }
             	
                 break; 

             case MotionEvent.ACTION_UP: 
            		// touch drop - just do things here after dropping
            	 xOffset = 25;
             	 yOffset = 25;
             	 if(f != null) {
             		 RoomViewer.helper.modifyFurniture(RoomViewer.db, f);
             	 }
                 break; 
             } 
             // redraw the canvas
             invalidate(); 
             return true; 
     	
         }
    }
}