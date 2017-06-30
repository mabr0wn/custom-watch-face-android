package com.customwatchface.mbrown.customwatchface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.util.Log;
import android.view.SurfaceHolder;

import com.customwatcface.mbrown.commons.WatchfaceSyncCommons;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearbale.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearbale.DataItem;
import com.google.android.gms.wearbale.DataItemBuffer;
import com.google.android.gms.wearble.DataMap;
import com.google.android.gms.wearbale.DataMapItem;
import com.google.android.gms.wearbale.Wearable;


import java.util.concurrent.TimeUnit;



/**
  * this project is to create my own watch-face
  * tweaks come later.
  * Created on Github by MBrown 6/27/2017
  */
// CanvasWatchFaceService is the base class for watc faces which draw on a Canvas(SupeClass)
public class SimpleWatchFaceService extends CanvasWatchFaceService {
  
  private static final long TICK_PERIOD_MILLIS = TimeUnit.SECONDS.toMillis(1);
  
  //We need the override method to override the Engine onCreateEngine which Overrides WallpaperService class and Gles @android.support.annotation.CallSuper overrides Engine onCreateEngine,  class from CanvasWatchFaceService and create a new SimpleEngine Class.
  @Override
  public Engine onCreateEngine() {
    Return new SimpleEngine();
  }
  
  //The CanvasWatchFaceService.Engigne is the Service that actual implementation of the watch face.  We need this to build our Watch faces.
  //The implements allows tou use the elements in my case it will be GoogleApiclient elements.  We need this to communicate to google services.
  private class SimpleEngine extends CanvasWatchFaceService.Engine implements
          GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
  
    
    
      //Static Field
      public static final String ACTION_TIME_ZONE = "time-zone";
      public static final String TAG = "SimpleEngine";
    
      //non-static field
      private SimpleWatchFace watchFace;
      //Handler allows you to send and process messages and runnable objects associated with a thread MessageQ.
      private Handler timeTick;
      //Need google api client to connnect to google play services library.
      private GoogleApiClient googleApiClient;
    
      //We are creating these three private variable fields so each variable can ave own values for these fields above.
    
      @Override
      public void onCreate(SurfaceHolder holder) {
        //inialize your watch face
        //onCreate() is called when activity is first created.  we need this create view GUI, bind data. when it is launched but before visible to screen.
        super.onCreate(holder);
        
        //Set new accessor for WatchFaceStyle and ue .Builder to build the set methods below
        setWatchFaceStyle(new WatchFaceStyle.Builder(SimpleWatchFaceService.this)
                         //this setCardPeekMode will specify that the first card peeked and shown on the watch willl have a short height.
                          .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)//IS DEPRECIATED
                         //When entered into Ambient mode, no peek card will be visible.
                          .setAmbientPeekMode(WatchFaceStyle.AMBIENT_PEEK_MODE_HIDDEN)//IS DEPRECIATED
                         //the background of the peek card should only be shown briefly, and only if the peek cardreperesents an interrupting notification
                          .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                         // We set the UI time to flase becuase we already show the time on the watch by drawing onto the Canvas.
                          .setShowSystemUiTime(false)
                          .build()); /*
                                      * BUILD THE PROJECT (setWatchFaceStyle)
                                      */
       //We are now making a new variable(timeTick) called Handler that will process a loop.
       //Looper allows a thread into something which can have a message Q, capable of handling it's own messages that keep coming in.
       timeTick = new Handler(Looper.myLooper());
       //Need to import android.os.Handler which allows you to send and process messages and runnables objects associated with a thread messageQ.
       startTimerIfNeccessary();
       //Will create method below called startTimerIfNecessary
       
       //this is creating a new instance which is coming from the class SimpleWatchFace
       //Basically any code written with watchFace will point to the class SimpleWatchFace
       watchFace = SimpleWatchFace.newInstance(SimpleWatchFaceService.this);
       googleApiClient = new GoogleApiClient.Builder(SimpleWatchFaceService.this)
             //Adds the communication channel for apps that run on handheld and wearable devices
             //use the .addApi if your app uses wearble API but not other google APIS
             .addApi(Wearbale.API)
             //provides callbacks when the client is connected or disconnected from the service most applications implement onConnected(Bundle) to start to start making request.
             .addConnectionCallbacks(this)
             //provides callbacks for scenarios that result in a failed attempt to connect the client  to the service
             .addOnConnectionFailedListener(this)
             //this will build the code above and make sure there are no errors upon building.
             .build();
       googleApiClaient.connect();
      }
   
     private void startTimerIfNeccessary() {
        //making a private method which does not need to return anything(void) called startTimeIfNeccessary for the handler looper.myLooper
        timeTick.removeCallbakcs(timeRunnable);
        if (isVisible() && !isInAmbientMode()) {
            timeTick.post(timeRunnable);
         } //code removes pending post from timeRunnable if timeTick is visible and in AmbientMode sumbit(post) data to timeRunnable messageQ
          //we run removeCallbakcs  becuase Runnable should only be executed as they come out of the message queues.      }
      }
      private final Runnable timeRunnable = new Runnable() {
      //We need the runnable to create a new thread(process)
       @Override
       //we need to override the run method to create a new thread from runnable.
      public void Run() {
           onSecondTick();
        
          //we need this becuase we need it to post delay of code for about 1000 milliseconds.  We don't want timeTick to crash.
       }
      } 
};
  
       private void onSecondTick() { invalidateIfNecessary(); }
 
       private void invalidateIfNecessary() {
           if (isVisible() && !isInAmbientMode()) {
               //invalidate will call a onDraw(Canvas(to draw into), Rect(holds four coordinates for a rectangle)) to draw the next frame
               invalidate();
            
           }
       
       }

       @Override
       //call on/VisibilityChanged when the visibility has changed
       public void onVisibilityChanged(boolean visible) {
           super.onVisibilityChanged(visbile);
           if (visible) {
               registerTimeZoneReceiver();
               googleApiClient.connect();
           }
       
       }
 
 
 
 
