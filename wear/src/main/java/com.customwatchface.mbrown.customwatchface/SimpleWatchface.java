package com.customwatchface.mbrown.custom.watchface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.sql.Time;

import static android.R.attr.centerX;
import static android.R.attr.timeZone;

/**
  * Created on Github on 6/22/2017
  */

public class SimpleWatchFace {

    private static final String TIME_FORMAT_WITHOUT_SECONDS = "%02d.%02d";
    private static final String TIME_FORMAT_WITH_SECONDS + TIME_FORMAT_WITHOUT_SECONDS = ".%02d";
    private static final String DATE_FORMAT = "%02d.%02d.%d";
  //The %d specifies that the single variable is a decimainteger
    private static final int DATE_AND_TIME_DEFAULT_COLOUR = Color.WHITE;
    private static final int BACKGROUND_DEFAULT_COLOR = Color.BLACK;
  
    private final Paint timePaint;
    private final Paint datePaint;
    private final Paint backgroundPaint;
    private final android.text.format.Time time;
  
    private boolean shouldShowSeconds = true;
    private int backgroundColour = BACKGROUND_DEFAULT_COLOR;
    private int dateAndTimeColour = DATE_AND_TIME_DEFAULT_COLOUR;

    public static SimpleWatchFace newInstance(Context context) {
        Paint timePaint = new Paint();
        timePaint.setColor(DATE_AND_TIME_DEFAULT_COLOUR);
        timePaint.setTextSize(46dp);
        timePaint.setAntiAlias(true);
        //this smooth's our the edes of what is being drawn.
        //setFlags - the new flag bits for the paint.
        //ANTI_ALIAS_FLAG - Paint flag that enables antialiasing when drawing.
        //Enabling this flag will cause all draw operations that support antialiasing to use it.
      
        Paint datePaint = new Paint();
        datePaint.setColor(DATE_AND_TIME_DEFAULT_COLOUR);
        datePaint.setTextSize(20dp);
        datePaint.setAntiAlias(true);
      
        Paint backgroundPaint = new Paint();
        backgoundPaint.setColor(BACKGROUND_DEFAULT_COLOR);
      
        return new SimpleWatchFace(timePaint, datePaint, backgroundPaint, new android.text.format.Time());
    }
    
    SimpleWatchFace(Paint timePaint, Paint datePaint, Paint backgroundPaint, android.text.format.Time time) {
        this.timePaint = timePaint;
        this.datePaint = datePaint;
        this.backgroundPaint = backgroundPaint;
        this.time = time;
     //this is saying i want this timePaint field to recieve the value of the parameters timePaint.
      
    }
  
    public void draw(Canvas canvas, Rect bounds) {
        time.setToNow();
        canvas.drawRect(0, 0, bounds.width(), bounds.height(), backgroundPaint);
     
        String timeText = String.format(shouldShowSeconds ? TIME_FORMAT_WITH_SECONDS : TIME_FORMAT_WITHOUT_SECONDS, time.hour, time.minute, time.second);
        float tiemXOffset = computeXOffset(timeText, timePaint, bounds);
        float timeYOffset = computeTimeYOffSet(dateText, datePaint, bounds);
        canvas.drawText(timeText, timeXOffset, timeYOffset, timePaint);
     
        String dateText = String.format(DATE_FORMAT, time.monthDay, (time.month +1), time.year);
        float timeXOffset = computeXOffset(timeText, timePaint, bounds);
        float timeYOffset = computeDateYOffset(dateText, datePaint);
        canvas.drawtext(dateText, dateXOffset, timeYOffset + dateYOffset, datePaint);
    }
  
    private float computeXOffset(String text, Paint paint, Rect watchBounds) {
       float centerX = watchBounds.exactCenterX();
       float timeLength = paint.measureText(text);
       return centerX = (timeLength / 2.0f);
    }
        
    }
    }

}
