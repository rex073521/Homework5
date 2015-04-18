package com.example.rex.homework5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by REX on 2015/4/18.
 */
public class MyService extends Service {
    private final Binder binder = new Binder();
    private final int NOTIFICATION_ID = 100;
    private NotificationManager notificationManager;

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    public void cancelNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }


    @Override
    public IBinder onBind(Intent intent) {
        //與使用者取得連線
        Toast.makeText(this, "服務連線", Toast.LENGTH_SHORT).show();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this)
                .setTicker("3秒後收到訊息")
                .setContentTitle("3秒後收到訊息")
                .setContentText("這是通知訊息")
                .setSmallIcon(R.drawable.ic_secret_notification)
                .build();
        notificationManager.notify(NOTIFICATION_ID, notification);
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this,"Disconnected",Toast.LENGTH_SHORT).show();
        return false;
    }
}
