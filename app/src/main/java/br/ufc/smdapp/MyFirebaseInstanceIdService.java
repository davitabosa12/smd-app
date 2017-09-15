package br.ufc.smdapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    public MyFirebaseInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Novo token: ", refreshedToken );

        //mandar token para o servidor
        sendRegistrationToServer(refreshedToken);
    }

    //Envia o token para o servidor Firebase
    private void sendRegistrationToServer(String refreshedToken) {

    }
}
