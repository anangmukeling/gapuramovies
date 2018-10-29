package com.anang.gapuramovie.alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.anang.gapuramovie.R;

import java.util.Calendar;

public class DailyAlarmReceiver extends BroadcastReceiver {

    private static final int NOTIF_ID_REPEATING = 101;

    public DailyAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = context.getString(R.string.repeat_notif);
        String title = context.getString(R.string.app_name);
        showAlarmNotification(context, title, message, NOTIF_ID_REPEATING);
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_movie_app)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, builder.build());
        }
    }


    public void setRepeatingAlarm(Context context) {
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 5);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, getPendingIntent(context));
        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    private static PendingIntent getPendingIntent(Context context) {

        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, NOTIF_ID_REPEATING, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    }

}
