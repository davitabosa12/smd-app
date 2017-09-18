package br.ufc.smdapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * Created by davi on 15/09/17.
 */

public class NotificationPusher {
    Context ctx;
    public NotificationPusher(Context ctx){
        this.ctx = ctx;
        
    }
    public void push(RemoteMessage message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
        NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String uri = sharedPrefs.getString("ringtone_preference",null);

        Notification noti = builder.setContentTitle("SMDApp")
                .setSmallIcon(R.drawable.notification)
                .setTicker("Uma nova noticia foi publicada!")
                .setContentText(message.getNotification().getBody())
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .build();
        nm.notify(new Random(Calendar.getInstance().getTimeInMillis()).nextInt(),noti);
    }

}
