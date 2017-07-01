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
           } else {
               unregisterTimeZoneReceiver();
               releaseGoogleApiClient();
           }
        
           startTimerIfNecessary();
       
       }
 
       private void releaseGoogleApiClient() {
          if (googleApiClient != null && googleApiClient.isConnected()) {
              Wearable.DataApi.removeListener(googleApiClient, onDataChangedListener);
              googleApiClient.disconnect();
           
           
          }
        
       
       }
       //basically unregister a prevoius registered broadcastReciever.
       private void unregisterTimeZoneReceiver() {
          unregisterReceiver(timeZoneChangedReceiver);
        
        
       }
 
       private void registerTimeZoneReceiver() {
          //we need this to register that timeZone has changed, i.e. EST to CST
          IntentFliter timeZoneFilter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
          registerReceiver(timeZoneChangedReceiver, timeZoneFilter);
        
        
       }
  
       //Broadcast receiver is the receiver to broadcast messages out of intent i.e. bluetooth
       //Base class for code that receives and handles broadcast intent.
       private BroadcastReceiver timeZoneChangedReceiver = new BroadcastReceiver() {
           @Override
           public void onReceive(Context context, Intent intent) {
               if (Intent.ACTION_TIMEZONE_CHANGED.equals(intent.getAction())) {
                   watchFace.updateTimeZoneWith(intent.getStringExtra(ACTION_TIME_ZONE));
               }
           }
       };
      
       @Override
       //here we have the onDraw method overriding the class onDraw, we are calling the super to
       //call the constructor of onDraw, to produce this method of drawing the rectangular canvas
       //we need this to draw the face of the watch.
       public void onDraw(Canvas canvas, Rect Bounds) {
          super.onDraw(canvas, bounds);
          watchFace.draw(canvas, bounds);
        
        
       }
 
       @Override
       //this call onTimeTick is called every minute it is in AmbientMode
       //we use invalidate to begin the process of onDraw.
       public void onTimeTick() {
           super.onTimeTick();
           invalidate();
        
        
       }
 
       @Override
        //here we have the method comparing the boolean to inAmbientMode
        //once we finish the class SimpleWatchFace, it will not be read, at the moment there is not a method created.
        public void onAmbientModeChanged(boolean inAmbientMode) {
          super.onAmbientModeChanged(inAmbientMode);
          watchFace.setAntiAlias(!inAmbientMode);
          watchFace.setShowSeconds(!inAmbientMode);
        
          if (inAmbientMode) {
             watchFace.updateBackgroundColourToDefault();
             watchFace.updateDateAndTimeColourToDefault();
          } else {
             watchFace.restoreBackgroundColour();
             watchFace.restoreDateAndTimeColour();
          }
        
          invalidate();
          
          startTimerIfNecessary();
        
       }
 
       @Override
       public void onConnected(@Nullable Bundle bundle) {
           Log.d(TAG, "connected GoogleAPI");
           //DataApi allows the wearable device to communicate to a network without a mobile device.
           //Needed if we want to use the wathc as a standalone.
          //call the addListener when you want to notify google play that your activity is interested in listening.
          //for the data layer.
          //need this to get data from GoogleApi, send a call for suceess or failure to google play
         Wearble.DataApi.addListener(googleApiClient, onDataChangedListener);
         Wearble.DataApi.getDataItems(googleApiClient).setResultCallBack(onConnectedResultCallBack);
        
        
        
       }
 
       //data API is the way wearble transfer data back and forth.  such as connecting.
       //DataItem provides data storage with automatic syncing between the handheld and wearble
       //DataListener to receive data events.
       private final DataApi.DataListener onDataChangedListener = new DataApi.DataListener() {
           @Override
           public void onDataChanged(DataEventBuffer dataEvents) {
              for (DataEvent event : dataEvents) {
                   DataItem item - event.getDataItem();
                   processConfigurationFor(item);
              }
           }
        
           dataEvents.release();
           invalidateIfNecessary();
         
       }
};

        private void processConfigurationFor(DataItem item) {
           if  (WatchfaceSyncCommons.PATH.equal(item.getUri().getPath())) {
                DataMap dataMap = DataMapItem.fromDataItem(item).getDataMAp();
                if (dataMap.containsKey(WatchfaceSyncCommons.KEY_BACKGROUND_COLOUR)) {
                    String backgroundColour = dataMap.getString(WatchfaceSyncCommons.KEY_BACKGROUND_COLOUR);
                    watchFace.updateBackgroundColourTo(Color.parseColor(backgroundColour));
                }
            
                if (dataMAp.containsKey(WatchfaceSyncCommons.KEY_DATE_TIME_COLOUR)) {
                    String timeColour = dataMap.getString(WatchfaceSyncCommons.KEY_DATE_TIME_COLOUR);
                    watchFace.updateDataAndTimeColourTo(Color.parseColor(timeColour));
                }
            
           }
        }

        private final ResultCallback<DataItemBuffer> onConnectedResultCallBack = new ResultCallback<DataITemBuffer>() {
           @Override
           public void onResult(@NonNull DataItemBuffer dataItems) {
              for (DataItem item : dataItems) {
                   processConfigurationFor(item);
              }
            
              dataItems.release();
              invalidateIfNecessary();
           }
        
        };


        @Override
        public void onConnectionSuspended(int i) {
            Log.e(TAG, "suspended GoogleApi")
             
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
             Log.e(TAG, "ConnectionFailed GoogleApi");
         
        }

        @Override
        public void onDestroy() {
           timeTick.removeCallbakcs(timeRunnable);
           //code removes pending posts from timeRunnable if timeTick is visible and in Ambientmode submit(post) data to timeRunnable messageQ.
         
           releaseGoogleApi();
           //this will release the API of communication back with google API.
         
           super.onDestroy();
           //calls the constructor, methods, and properties opf parent class.
        }
}

}

