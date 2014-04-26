package com.iadf.TwoDUserInterface.MenuPackage;

import com.iadf.SystemController.DatabaseController.Furniture;

import android.support.v4.app.DialogFragment;

public interface FurnitureListener {
     public void onFurnitureDialogPositiveClick(DialogFragment dialog, Furniture f);
     public void onFurnitureDialogNegativeClick(DialogFragment dialog);
  
}
