package br.ufc.smdapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by davi on 15/09/17.
 */

public class NotificationPusher {
    Context ctx;
    private String noticiaId;
    private DataSnapshot resposta;


    public NotificationPusher(Context ctx){
        this.ctx = ctx;
    }
    public void pushNoticia(RemoteMessage message) {

        noticiaId = message.getData().get("pushId");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("noticia/" + noticiaId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Iniciar o NotificationBuilder
                NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
                NotificationManager nm = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx); //Para pegar o ringtone escolhido
                String uri = sharedPrefs.getString("ringtone_preference", null);
                Log.d("RINGTONE_PREFERENCE", uri); //para fins de debug

                //pegar dados da noticia
                String titulo = dataSnapshot.child("titulo").getValue(String.class);
                String desc = dataSnapshot.child("descricao").getValue(String.class);
                String tipo = dataSnapshot.child("tipo").getValue(String.class);
                //Criar o PendingIntent
                Intent intent = new Intent(ctx,LerNoticiaActivity.class);
                //Colocar os Extras Necessarios:
                intent.putExtra("TITULO",titulo);
                intent.putExtra("DESCRICAO",desc);
                intent.putExtra("TIPO",tipo);


                Notification noti = builder.setContentTitle(titulo)
                        .setSmallIcon(R.drawable.notification)
                        .setTicker("Uma nova noticia foi publicada!")
                        .setContentIntent(PendingIntent.getActivity(ctx,0,intent,PendingIntent.FLAG_CANCEL_CURRENT))
                        .setSound(Uri.parse(uri))
                        .setContentText(desc.length() > 29 ? desc.substring(0,29) + "..." : desc) //limitar descricao para 30 caracteres
                        .setDefaults(Notification.DEFAULT_VIBRATE) //TODO: Deixar o usuario escolher seu som de notificacao
                        .build();

                nm.notify(new Random(Calendar.getInstance().getTimeInMillis()).nextInt(), noti);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("NotificationPusher","Erro de DB" + databaseError.getMessage());
            }
        });
    }

}
