package com.example.duncanro.widgetexample;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import java.util.Random;

import static com.example.duncanro.widgetexample.MainActivity.EXTRA_RECIPENAME;

public class UpdateWidgetService extends JobIntentService {
    private static final String LOG = "com.example.duncanro.widgetexample";
    public static final String ACTION_UPDATE_PLANT_WIDGETS = "com.example.duncanro.widgetexample.action.update_plant_widgets";

    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, UpdateWidgetService.class, JOB_ID, work);
    }

       @Override
    public void onStart(Intent intent, int startId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
                .getApplicationContext());

        int[] allWidgetIds = intent
                .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

//      ComponentName thisWidget = new ComponentName(getApplicationContext(),
//              MyWidgetProvider.class);
//      int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(100));
            String widgetText = "LoveBug!!";

            RemoteViews remoteViews = new RemoteViews(this
                    .getApplicationContext().getPackageName(),
                    R.layout.new_app_widget);
            Log.w("WidgetExample", String.valueOf(number));
            // Set the text
            //remoteViews.setTextViewText(R.id.appwidget_text,"Random: " + String.valueOf(number));
            //remoteViews.setTextViewText(R.id.textView3,widgetText);

            // Register an onClickListener
            Intent clickIntent = new Intent(this.getApplicationContext(),
                    NewAppWidget.class);

            clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                    allWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            //remoteViews.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
            remoteViews.setOnClickPendingIntent(R.id.textView3, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
        stopSelf();

        super.onStart(intent, startId);
    }



    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.i("SimpleJobIntentService", "Executing work: " + intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toast("All work complete");
    }

    @SuppressWarnings("deprecation")
    final Handler mHandler = new Handler();

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override public void run() {
                Toast.makeText(UpdateWidgetService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

   }
