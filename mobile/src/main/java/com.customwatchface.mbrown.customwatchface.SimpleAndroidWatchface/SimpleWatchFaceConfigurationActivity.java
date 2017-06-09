package com.customwatchface.mbrown.customwatchface.SimpleAndroidWatchface;

/**
  * added to Github on 6/7/2017
  *//
    
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.menuItem;
import android.view.View;

import com.customwatchface.mbrown.commons.WatchfaceSyncCommons;
import com.customwatchface.mbrown.cutomwatchface.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import static android.R.attr.id;
import static android.R.attr.tag;

public class SimpleWatchFaceConfigurationActivity extends ActionBarActivity implements ColourChooseDialog.Listener,
          GoogleApiClient.ConnectionCallbacks,
          GoogleApiClient.onConnectionFailedListener {
   
   private static final String TAG = "SimpleWatchface";
   private static final String TAG_BACKGROUND_COLOUR_CHOOSER = "background_chooser";
   private static final String TAG_DATE_AND_TIME_COLOUR_CHOOSER = "date_time_chooser";
            
   private GoogleApiClient googleApiClient;
   private View backgroundColourImagePreview;
   private View dateAndtimeColourImagePreview;
   private WatchConfigurationPreferences watchConfigurationPreferences;
          
   @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the background of our layout called activity_configuration
        setcontentView(R.layout.activity_configuration);
     
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     
        //To communicate to any google services. using gradle build tool.
        googleApiClient = new GoogleApiClient.Builder(this)
                  .addConnectionCallbacks(this)
                  .addOnConnectionFailedListener(this)
                  .addApi(Wearble.API)
                  .build();
        findViewById(R.id.configuration_background_colour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColourChooseDialog.newInstance(getString(R.string.pick_date_time_colour))
                        .show(getFragmentManager(), TAG_BACKGROUND_COLOUR_CHOOSER);
            }
        });
     
        findViewById(R.id.configuration_time_colour).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColourChooseDialog.newInstance(getString(R.string.pick_date_time_colour))
                        .show(getFragmentManager(), TAG_DATE_AND_TIME_COLOUR_CHOOSER);
            }
        });
   
        backgroundColourImagePreview = findViewById(R.id.configuration_background_colour_preview);
        dateAndTimeColourImagePreview = findViewById(R.id.configuration_date_and_time_colour_preview);
     
        watchConfigurationPreferences = WatchConfigurationPreferences.newInstance(this);
     
        backgroundColourImagePreview.setBackgroundColour(watchConfigurationPreferences.getBackgroundColour());
   }    
   

  @Override
  protected void onStart() {
    super.onStart();
    googleApiClient.connect();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId() == android.R.id.home) {
          finish();
          return true;
      }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColourSelected(String colour, String tag) {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create(WatchfaceSyncCommons.PATH);

        if(TAG_BACKGROUND_COLOUR_CHOOSER.equals(tag)) {
            backgroundColourImagePreview.setBackgroundColor(Color.parseColor(colour));
            
          
          
          
}
