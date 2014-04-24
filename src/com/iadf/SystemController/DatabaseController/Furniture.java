package com.iadf.SystemController.DatabaseController;

import android.graphics.Point;

public abstract class Furniture {
	
	private int GUID;
	private int ROOM_NUMBER;
	private int base;
	private int height;
	private int shape;
	private Point center;
	private int type;
	
	public static final int TRIANGLE = 1;
	public static final int RECTANGLE = 2;
	public static final int CIRCLE = 3;
	
	public static final int STACKABLE = 111;
	public static final int CONTAINER = 222;
	
	public Furniture(int GUID, int ROOM_NUMBER, int base, int height, int shape, int x, int y, int type) {
		setGUID(GUID);
		setRoomNumber(ROOM_NUMBER);
		setBase(base);
		setHeight(height);
		setShape(shape);
		setCenter(x, y);
		setType(type);
	}
	
	@Override
	public String toString() {
		return this.GUID + ", " + this.ROOM_NUMBER + ", " + this.base + ", " + this.height + ", " + this.shape + ", " + this.center.x + ", " + this.center.y + ", " + this.type;
	}
	
	public String sqlUpdateString() {
		return "GUID = " + this.GUID + ", room_number = " + this.ROOM_NUMBER + ", base = " + this.base + ", height = " + this.height + 
				", shape = " + this.shape + ", center_x" + this.center.x + ", center_y" + this.center.y + ", type = " + this.type;
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
	public int getBase() {
		return base;
	}
	/**
	 * @param base the base to set
	 */
	public void setBase(int base) {
		this.base = base;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
