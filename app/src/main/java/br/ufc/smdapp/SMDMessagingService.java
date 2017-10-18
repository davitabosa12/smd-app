package br.ufc.smdapp;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class SMDMessagingService extends FirebaseMessagingService {
    public SMDMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("SMDMessagingService ", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("SMDMessagingService ", "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("SMDMessagingService ", "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //TODO: Diferenciar Noticias de Atualizacoes de Pendencias
        new NotificationPusher(getApplicationContext()).pushNoticia(remoteMessage);
    }
}
