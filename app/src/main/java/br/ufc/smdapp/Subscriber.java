package br.ufc.smdapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davi on 16/09/17.
 */

public class Subscriber{
    private Context ctx;
    FirebaseMessaging fm;
    private String[] topics;
    private ArrayList<String> topicsToSubscribe,topicstoUnsubscribe;
    
    public Subscriber(FirebaseMessaging fm, Context ctx){
        this.ctx = ctx;
        this.fm = fm;
        topics = ctx.getResources().getStringArray(R.array.topicos);
        topicsToSubscribe = new ArrayList<>();
        topicstoUnsubscribe = new ArrayList<>();
        Log.d("SUBSCRIBER","Instancia de Subscriber criada com sucesso!");
        Log.d("SUBSCRIBER", "fm = " + fm.toString() + " ctx= " + ctx.toString());
    }
    public void subscribe() {
        String temp;
        for (String t : topicsToSubscribe) {
            temp = t.substring(6);  //tirar "topic_" do nome do topico a ser assinado
            fm.subscribeToTopic(temp);
            Log.d("SUBSCRIBER", "Topico " + temp + " inscrito no FCM");
        }
        for (String t : topicstoUnsubscribe) {
            temp = t.substring(6);  //tirar "topic_" do nome do topico a ser assinado
            fm.unsubscribeFromTopic(temp);
            Log.d("SUBSCRIBER", "Topico " + temp + " removido do FCM");
        }
    }
    public boolean addTopic(String topic){
        for (String t : topics) {
            //Se o topico a ser assinado for válido e nao tiver sido inscrito ainda..
            if(t.equals(topic) && !topicsToSubscribe.contains(t)){
                Log.d("SUBSCRIBER","Topico " + topic + " adicionado ao topicsToSubscribe");
                topicsToSubscribe.add(t);
                return true;
            }

        }
        return false;
    }
    public boolean removeTopic(String topic){
        for (String t : topics) {
            //Se o topico a ser assinado for válido e nao tiver sido inscrito ainda..
            if(t.equals(topic) && !topicstoUnsubscribe.contains(t)){
                Log.d("SUBSCRIBER","Topico " + topic + " adicionado ao topicsToUnsubscribe");
                topicstoUnsubscribe.add(t);
                return true;
            }

        }
        return false;
    }

}
