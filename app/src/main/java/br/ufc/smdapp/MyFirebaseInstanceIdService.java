package br.ufc.smdapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.ufc.smdapp.utils.User;

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
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null && !user.isAnonymous()){
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + user.getUid());
            userRef.child(User.TOKEN_FCM).setValue(refreshedToken);
        }


    }
}
