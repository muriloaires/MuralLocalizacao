package peladafc.com.murallocalizacao.gcm;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import peladafc.com.murallocalizacao.MainActivity;
import peladafc.com.murallocalizacao.R;

public class GcmMessageHandler extends IntentService {
    private String message;
    private Handler handler;
    private NotificationUtils notificationUtils;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                message = "Send error: " + extras.toString();
                sendNotification();
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                message = "Deleted messages on server: " + extras.toString();
                sendNotification();
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                message = extras.getString("message");
                sendNotification();
            }
        }

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void sendNotification() {
        notificationUtils = new NotificationUtils(this, (int) System.currentTimeMillis());
        notificationUtils.show("Mural Localização", message, R.drawable.location, MainActivity.class);
    }
}