package com.anang.gapuramovie.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.anang.gapuramovie.R;

/**
 * Implementation of App Widget functionality.
 */
public class ImageBannerWidget extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.anang.gapuramovie.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.anang.gapuramovie.EXTRA_ITEM";
    public static final String UPDATE_WIDGET = "com.anang.gapuramovie.UPDATE_WIDGET";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.image_banner_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent toastIntent = new Intent(context, ImageBannerWidget.class);
        toastIntent.setAction(ImageBannerWidget.TOAST_ACTION);
        toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //Kode di bawah akan dijalankan ketika widget ditekan. Seperti pada latihan sebelumnya,
    // percabangan digunakan untuk membedakan action yang terjadi.
    // Kita dapat mengambil data action tersebut dengan memanfaatkan extra dari sebuah intent.
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            String viewTitle = intent.getStringExtra(EXTRA_ITEM);
            Toast.makeText(context, viewTitle, Toast.LENGTH_SHORT).show();
        }

        if (intent.getAction().equals(UPDATE_WIDGET)){
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            int[] ids = gm.getAppWidgetIds(new ComponentName(context, ImageBannerWidget.class));

            gm.notifyAppWidgetViewDataChanged(ids ,R.id.stack_view);
        }

        super.onReceive(context, intent);
    }

}


