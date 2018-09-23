package com.webteklabs.navigationdrawer;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by shubhamgupta on 5/7/17.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
      long min_value;
    Context context;
    String title;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        min_value=CreateTask.update_value;
        StoreData sd = new StoreData(context);
        title = sd.getTitle(min_value);


        notification(title);


        updateT();

     updateClock();


    }

    void notification(String T)
    {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.notificationicon);
        builder.setContentTitle(T);
        builder.setContentText("Task to Reminder");
        builder.setSound(soundUri);
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        builder.setLights(Color.RED, 3000, 3000);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());

    }
   public void updateT(){
        java.util.Calendar car = java.util.Calendar.getInstance();
        long value = min_value/(1000*60);
        String str = Long.toString(value);
        StoreData sd = new StoreData(context);
        boolean isInserted =  sd.updateTime(str);
        if(isInserted)
        {
            Toast.makeText(context, "Successfully updated in broadcast", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Error in  broadcast", Toast.LENGTH_SHORT).show();
        }
    }

    public void setClock(long value)
    {
        if(value!=0)
        {
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, value, pendingIntent);
        }
    }
    public void updateClock()
    {
        try {
            StoreData sd = new StoreData(context);
            long minvalue = sd.getMinValue();
            minvalue = minvalue*1000*60;
            CreateTask.update_value=minvalue;
            setClock(minvalue);
          //  minvalue = minvalue*1000*60;
           // setClock(minvalue);

        }
        catch (Exception ex)
        {
            Toast.makeText(context, "Ok E", Toast.LENGTH_SHORT).show();
        }
    }
}
