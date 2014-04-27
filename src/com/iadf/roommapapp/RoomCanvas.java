package com.iadf.roommapapp;


import android.content.Context;
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

public class RoomCanvas extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	return (new MyView(this.getActivity()));
        //return inflater.inflate(R.layout.room_canvas, container, true);
    }
    
    
    public class MyView extends View {
    	
    	private ColorBall[] colorballs = new ColorBall[3]; // array that holds the balls
        
        public MyView(Context context) {
        	super(context);
            setFocusable(true); //necessary for getting the touch events
            
            /*// setting the start point for the balls
            Point point1 = new Point();
            point1.x = 50;
            point1.y = 20;
            Point point2 = new Point();
            point2.x = 100;
            point2.y = 20;
            Point point3 = new Point();
            point3.x = 150;
            point3.y = 20;
            
                           
            // declare each ball with the ColorBall class
            colorballs[0] = new ColorBall(context,R.drawable.ball_blue, point1);
            colorballs[1] = new ColorBall(context,R.drawable.ball_blue, point2);
            colorballs[2] = new ColorBall(context,R.drawable.ball_blue, point3);*/
        }
         
         // the method that draws the balls
         @Override 
         protected void onDraw(Canvas canvas) {
             //canvas.drawColor(0xFFCCCCCC);     //if you want another background color       
             
         	//draw the balls on the canvas
         	for (ColorBall ball : colorballs) {
                 canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), null);
               }
         }

         // events when touching the screen
         public boolean onTouchEvent(MotionEvent event) {
             int eventaction = event.getAction(); 
             
             int X = (int)event.getX(); 
             int Y = (int)event.getY(); 

             switch (eventaction ) { 

             case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
             	balID = 0;
             	for (ColorBall ball : colorballs) {
             		// check if inside the bounds of the ball (circle)
             		// get the center for the ball
             		int centerX = ball.getX() + 25;
             		int centerY = ball.getY() + 25;
             		
             		// calculate the radius from the touch to the center of the ball
             		double radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));
             		
             		// if the radius is smaller then 23 (radius of a ball is 22), then it must be on the ball
             		if (radCircle < 23){
             			balID = ball.getID();
                         break;
             		}

             		// check all the bounds of the ball (square)
             		if (X > ball.getX() && X < ball.getX()+100 && Y > ball.getY() && Y < ball.getY()+1000){
                     	balID = ball.getID();
                     	break;
                    }
                   }
                  
                  break; 


             case MotionEvent.ACTION_MOVE:   // touch drag with the ball
             	// move the balls the same as the finger
                 if (balID > 0) {
                 	colorballs[balID-1].setX(X-25);
                 	colorballs[balID-1].setY(Y-25);
                 }
             	
                 break; 

             case MotionEvent.ACTION_UP: 
            		// touch drop - just do things here after dropping

                  break; 
             } 
             // redraw the canvas
             invalidate(); 
             return true; 
     	
         }
    }
}