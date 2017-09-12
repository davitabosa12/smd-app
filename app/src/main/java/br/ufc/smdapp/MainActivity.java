package br.ufc.smdapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Noticia> mNoticias = new ArrayList<>();
    ArrayAdapter adapter;
    ListView lvNoticias;
    FirebaseDatabase database;
    DatabaseReference ref;
    View noticiaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,NotificationService.class));
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("noticia");

        lvNoticias = (ListView) findViewById(R.id.lv_noticias);
        adapter = new NoticiaAdapter(getApplicationContext(),mNoticias);
        lvNoticias.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    Log.d(">>>NOTICIA:",value.getValue().toString());
                    Noticia temp = new Noticia(value.child("titulo").getValue().toString(),
                            value.child("descricao").getValue().toString(),
                            value.child("tipo").getValue().toString()
                    );

                    //mNoticias.add(temp);
                    adapter.add(temp);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }


}
