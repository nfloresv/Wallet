package com.flores.nico.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.flores.nico.database.Debt;
import com.flores.nico.wallet.DebtFragment;
import com.flores.nico.wallet.HomeActivity;
import com.flores.nico.wallet.R;

/**
 * Created by nicoflores on 05-10-14.
 */
public class AlarmReceiver extends BroadcastReceiver {
    private final int notificationId = 0x00101;
    public static final String DEBT_ID = "com.flores.nico.wallet.DEBT_ID";

    @Override
    public void onReceive (Context context, Intent intent) {
        long debtId = intent.getLongExtra(DEBT_ID, 0);
        Debt debt = Debt.findById(Debt.class, debtId);

        if (debt != null) {
            String content = String.format("Yow own $%1$.0f to %2$s", debt.getAmount(),
                    debt.getReceiver());
            // Notification
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText(content);

            Intent resultIntent = new Intent(context, HomeActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(HomeActivity.class);
            stackBuilder.addNextIntent(resultIntent);

            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager = (NotificationManager) context
                    .getSystemService
                            (Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(notificationId, mBuilder.build());
        }
    }
}
