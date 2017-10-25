package com.customwatchface.mbrown.customwatchface.SimpleAndroidWatchface;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
  * Created on Github on 6/17/2017
  */

public class WatchConfigurationPreferences {
  
    /**STATIC**/
    //FConstants...
    private static final String NAME = "WatchConfigurationPreferences";
    private static final String KEY_BACKGROUND_COLOUR = NAME + ".KEY_BACKGROUND_COLOUR";
    private static final String KEY_DATE_TIME_COLOUR = NAME + ".KEY_DATE_TIME_COLOUR";
    private static final int DEFAULT_BACKGROUND_COLOUR = Color.parseColor("black");
    private static final int DEFAULT_DATE_TIME_COLOUR = Color.parseColor("white");
  
    /**NON-STATIC**/
    private final SharedPreferences preferences;
  
    //context allows you to access app resoucres(Strings, drawable, animations, xml files)
    public static WatchConfigurationPreferences newInstance(Context context) {
          //MODE_PRIVATE - only your app can access the file.
          //SharedPreferences is a shared database.
          //can only share variables in shared database.
          SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
          return new WatchConfiguarationPreferences(preferences);
    
    }
  
    WatchConfigurationPreferences(SharedPreferences preferences) {
         this.preferences = preferences;
    }
  
    //is this method i want to get the data from key_background_colour and default_background_colour
    //will use this getBackgroundColour method in the class "SimpleWatchAndroidConfigurationActivity"
    //getInt - retrieves an int value from the preferences.
    public int getBackgroundColour() {
         return preferences.getInt(KEY_BACKGROUND_COLOUR, DEFAULT_BACKGROUND_COLOUR);
      
    }
    //we need this to get the default date and time colour which is "white".
    public int getDateAndTimeColour() {
        return preferences.getInt(KEY_DATE_TIME_COLOUR, DEFAULT_DATE_TIME_COLOUR);
      
    }
    //we need this to set a background colour
    public void setBackgroundColour(int color) {
        preferences.edit().putInt(KEY_BACKGROUND_COLOUR, color).apply();
      
    }
  
    public void setDateAndTimeColour(int color) {
        preferences.edit().putInt(KEY_DATE_TIME_COLOUR, color).apply();
    }
}
