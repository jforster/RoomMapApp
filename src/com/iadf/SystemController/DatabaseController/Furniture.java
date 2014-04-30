package com.iadf.SystemController.DatabaseController;

import android.graphics.Point;

/**
 * This class is used to store the attributes of a furniture item
 * 
 * @author CSE324 Spring 2014 Team 4
 */
public class Furniture implements DatabaseUpdateStrings{
	
	private int GUID;
	private int ROOM_NUMBER;
	private int width;
	private int length;
	private int shape;
	private Point center;
	private int type;
	
	// different shapes that the furniture objects can be
	public static final int TRIANGLE = 2;
	public static final int RECTANGLE = 0;
	public static final int OVAL = 1;
	
	// the different types of furniture
	public static final int DEFAULT = 111;
	public static final int CONTAINER = 222;
	
	/**
	 * public constructor to create a new furniture object
	 * @param GUID the global unique identifier of the furniture item
	 * @param ROOM_NUMBER the room the furniture item is contained in
	 * @param width the width of the furniture
	 * @param length the length of the furniture
	 * @param shape the shape of the furniture (Rectangle, Oval)
	 * @param x the x-coordinate in the room
	 * @param y the y-coordinate in the room
	 * @param type - the type of furniture (Default, Container)
	 */
	public Furniture(int GUID, int ROOM_NUMBER, int width, int length, int shape, int x, int y, int type) {
		setGUID(GUID);
		setRoomNumber(ROOM_NUMBER);
		setWidth(width);
		setLength(length);
		setShape(shape);
		setCenter(x, y);
		setType(type);
	}
	
	/**
	 * returns all of the attributes of the furniture object in an SQL format so that it can be added to the database
	 */
	@Override
	public String toString() {
		return this.GUID + ", " + this.ROOM_NUMBER + ", " + this.width + ", " + this.length + ", " + this.shape + ", " + this.center.x + ", " + this.center.y + ", " + this.type;
	}
	
	/**
	 * returns a database update string so that the furniture object can be updated in the database
	 */
	public String dbUpdateString() {
		return "_id = " + this.GUID + ", room_number = " + this.ROOM_NUMBER + ", width = " + this.width + ", length = " + this.length + 
				", shape = " + this.shape + ", center_x = " + this.center.x + ", center_y = " + this.center.y + ", type = " + this.type;
	}
	
	/**
	 * @return the gUID
	 */
	public int getGUID() {
		return GUID;
	}
	/**
	 * @param guid the guid to set
	 */
	public void setGUID(int guid) {
		this.GUID = guid;
	}
	/**
	 * @return the roomNumber
	 */
	public int getRoomNumber() {
		return ROOM_NUMBER;
	}
	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(int roomNumber) {
		ROOM_NUMBER = roomNumber;
	}
	/**
	 * @return the base
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param base the base to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param height the height to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * @return the shape
	 */
	public int getShape() {
		return shape;
	}
	/**
	 * @param shape the shape to set
	 */
	public void setShape(int shape) {
		this.shape = shape;
	}
	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}
	/**
	 * @param center the center to set
	 */
	public void setCenter(int x, int y) {
		this.center = new Point(x,y);
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

}
