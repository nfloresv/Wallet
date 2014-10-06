package com.flores.nico.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.flores.nico.wallet.R;

/**
 * Created by nicoflores on 05-10-14.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive (Context context, Intent intent) {
        // Notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("My Notification")
                .setContentText("Notification text");


    }
}
