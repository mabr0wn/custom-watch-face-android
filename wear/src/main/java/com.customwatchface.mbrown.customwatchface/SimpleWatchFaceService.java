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
