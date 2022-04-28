package com.example.test.diary;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.example.test.LoginActivity;
import com.example.test.MainActivity;
import com.example.test.R;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    private Context context;
    private String channelId="alarm_channel";
    private int intId= 11;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Intent busRouteIntent = new Intent(context, LoginActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(busRouteIntent);
        PendingIntent busRoutePendingIntent =
                stackBuilder.getPendingIntent(1, PendingIntent.FLAG_CANCEL_CURRENT|PendingIntent.FLAG_UPDATE_CURRENT);


        final NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.mipmap.icon_bss).setDefaults(Notification.DEFAULT_ALL)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setAutoCancel(true)
                .setContentTitle("BSS")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(intent.getStringExtra("cate")))
                .setContentIntent(busRoutePendingIntent);


        final NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channelId,"Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

       // int id=(int)System.currentTimeMillis(); <= 공지할때 여러건이 노출되게 하려면 사용하면 됨.

        notificationManager.notify(intId,notificationBuilder.build());

    }
}