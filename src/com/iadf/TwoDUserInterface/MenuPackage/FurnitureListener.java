package com.iadf.TwoDUserInterface.MenuPackage;


import android.support.v4.app.DialogFragment;

/**
 * This interface is for implementing actions when buttons are pressed on
 * the different dialogs dealing with furniture objects
 * 
 * @author CSE324 Spring 2014 Team 4
 */
public interface FurnitureListener {
     public void onFurnitureDialogPositiveClick(DialogFragment dialog, Object f);
     public void onFurnitureDialogNegativeClick(DialogFragment dialog);
  
}
