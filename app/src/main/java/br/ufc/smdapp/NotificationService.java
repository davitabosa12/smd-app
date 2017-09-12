package br.ufc.smdapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationService extends Service {
    private FirebaseDatabase database;
    private DatabaseReference ref;
    public NotificationService() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("noticia");
        Log.d("NOTIFICATION_SERVICE","Start");
    }

    @Override
    public void onCreate() {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //SE ESTIVER COM FILTROS
                //EXIBIR NOTIFICAÇÃO
                String titulo = dataSnapshot.child("titulo").getValue(String.class);
                String desc = dataSnapshot.child("descricao").getValue(String.class);
                String tipo = dataSnapshot.child("tipo").getValue(String.class);
                Noticia noticiaRecebida = new Noticia(titulo,desc,tipo);
                pushNotification(noticiaRecebida);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.d("NotificationService","DONE, MY FRIEND!!");
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void pushNotification(Noticia n){
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //reduzir o tamanho da descricao da noticia para no maximo 20 caracteres
        String contentText = n.getDesc().length() >= 20 ? n.getDesc().substring(0,19) + "..." : n.getDesc();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setContentTitle("SMDApp " + n.getTitulo())
               .setContentText(contentText)
               .setTicker("Uma nova notícia foi publicada!")
               .setSmallIcon(R.drawable.notification);
        Notification not = builder.build();
        nm.notify(777,not);
    }
}
