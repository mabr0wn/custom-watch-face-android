package com.customwatchface.mbrown.customwatchface.SimpleAndroidWatchface;

//a dialog box can show up to the buttons, a list of selected items, or custom layout
import android.app.AlertDialog;
//small dialog window that prompts the user to make a decision or enter addtional information.
import android.app.Dialog;
//Provides all the controls you need to create your dialog and manage its appearance, insread of 
//calling methods on the dialog method.
import android.app.DialogFragment;
//this allows access to application-specific resources in classes, as well as up-calls for
//application-level operations such as launching activites. broadcasting and receiving intents, etc.
import android.content.Context;
//Interface that defines a dialog-type class that can be shown, dismissed, or cancelled,
//and may have buttons that can be clicked.
import android.content.DialogInterface;
//is used to pass data between activites.
import android.os.Bundle;

import com.cutomwatchface.mbrown.customwatchface.R;

/**
  * imported to Github by MBrown on 6/5/2017
  */

public class ColourChooseDialog extends DialogFragment {
   
    private static final String ARG_TITLE = "ARG_TITLE";
    private Listener colourSelectedListener;
  
    public static ColourChooseDialog newInstance(String dialogTitle) {
        Bundle arguments = new Bundle();
        //here putString puts ARG_TITLE into dialogTitle
        arguments.putString(ARG_TITLE, dialogTitle);
      ColourChooseDialog dialog = new ColourChooseDialog();
      //returns the arguments supplied to if there is any.
      dialog.setArguments(arguments);
      return dialog;
    }
  
  //use the onAttach when a fragement is first attached to its content
  //Need to use onAttach(content), activity has been depreciated..
  @Override
  pubic void onAttach(Context content) {
    super.onAttach(context);
  }
}
