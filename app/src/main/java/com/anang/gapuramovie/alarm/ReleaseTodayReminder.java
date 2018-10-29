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
import android.util.Log;
import android.widget.Toast;

import com.anang.gapuramovie.DetailMoviesActivity;
import com.anang.gapuramovie.Entity.Movies.MovieResult;
import com.anang.gapuramovie.R;

import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ReleaseTodayReminder extends BroadcastReceiver {

    private static final int NOTIF_ID_REPEATING = 101;
    private static int notifId;

    public ReleaseTodayReminder() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        notifId = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("movieTitle");

        showAlarmNotification(context, title, notifId);
    }

    private void showAlarmNotification(Context context, String title, int notifId) {

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Log.d(TAG, "showAlarmNotification: " + notifId);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_movie_app)
                .setContentTitle(title)
                .setContentText("Today " + title + " release")
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, builder.build());
        }
    }


    public void setRepeatingAlarm(Context context, List<MovieResult> movieResults) {

        Log.d(TAG, "setRepeatingAlarm: " + movieResults.size());

        int notifDelay = 0;

        for (int i = 0; i < movieResults.size(); i++) {
            cancelAlarm(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, ReleaseTodayReminder.class);
            intent.putExtra("movieTitle", movieResults.get(i).getMovieTitle());
            intent.putExtra("id", notifId);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 5);

            if (alarmManager != null) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + notifDelay, AlarmManager.INTERVAL_DAY, pendingIntent);
            }

            notifId ++;
            notifDelay += 1000;
        }

        Toast.makeText(context, "Release reminder set up", Toast.LENGTH_SHORT).show();

    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(getPendingIntent(context));
        }
    }

    private static PendingIntent getPendingIntent(Context context) {

        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        return PendingIntent.getBroadcast(context, 101, intent, PendingIntent.FLAG_CANCEL_CURRENT);

    }
}
