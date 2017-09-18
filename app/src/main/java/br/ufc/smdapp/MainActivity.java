package br.ufc.smdapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    SharedPreferences.Editor editor;
    View noticiaView;
    SharedPreferences sharedPrefs;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_layout);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        editor = sharedPrefs.edit();

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //O usuário logou
                    editor.putString("userID", user.getUid());
                    if(editor.commit())
                        Log.d("SharedPreferences","Commit funcionou: " + user.getUid());
                    else
                        Toast.makeText(MainActivity.this, "Deu bode. :(", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(),"Logado", Toast.LENGTH_SHORT).show();
                } else {
                    //O usuário saiu
                    Toast.makeText(getApplicationContext(),"Usuario saiu.", Toast.LENGTH_SHORT).show();
                }
            }
        };
       // database = FirebaseDatabase.getInstance();
       // ref = database.getReference("noticia");
        Button btnNoticias, btnDeclaracoes, btnPrefs, btnSalas,btnLogin;
        //pegar botoes
        btnPrefs = (Button) findViewById(R.id.btn_settings);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnDeclaracoes = (Button) findViewById(R.id.btn_declaracao);
        btnNoticias = (Button) findViewById(R.id.btn_ler_noticias);

        btnPrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInAnonymously();
            }
        });

        btnDeclaracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DeclaracaoActivity.class));
            }
        });

        btnNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NoticiasAgregadorActivity.class));
            }
        });



        //Iniciar serviços do FCM
        startService(new Intent(this, MyFirebaseInstanceIdService.class));
        startService(new Intent(this, SMDMessagingService.class));







    }

    /*public void showNews(){
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
    }*/
}
