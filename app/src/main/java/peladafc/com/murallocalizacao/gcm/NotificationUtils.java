package peladafc.com.murallocalizacao.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {

    private Context context;
    private static int notificationId;

    public NotificationUtils(final Context context, int notificationId) {
        this.context = context;
        this.notificationId = notificationId;
    }


    private NotificationManager getNotificationManager() {
        return (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }


    public void show(
            String title, String content, int srcIcon, Class<?> activity) {
        getNotificationManager().notify(
                notificationId,
                getNotificationBuilder(title, content, srcIcon, activity).build());
    }

    private NotificationCompat.Builder getNotificationBuilder(
            String title, String content, int srcIcon, Class<?> activity) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        Intent notificationIntent = new Intent(context,
                activity);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(
                context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        long when = System.currentTimeMillis();

        builder.setSmallIcon(srcIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(when)
                .setOngoing(false)
                .setContentIntent(contentIntent);

        return builder;
    }
}
