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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufc.smdapp.utils.User;

public class MainActivity extends AppCompatActivity {

    Button btnNoticias, btnDeclaracoes, btnPrefs, btnSalas,btnLogin,btnCadastro;
    FirebaseDatabase database;
    DatabaseReference ref;
    SharedPreferences.Editor editor;
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
                        Toast.makeText(MainActivity.this, "Commit Falhou.", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getApplicationContext(),"Logado", Toast.LENGTH_SHORT).show();
                } else {
                    //O usuário saiu
                    Toast.makeText(getApplicationContext(),"Usuario saiu.", Toast.LENGTH_SHORT).show();
                }
            }
        };


        //pegar botoes
        btnPrefs = (Button) findViewById(R.id.btn_settings);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnDeclaracoes = (Button) findViewById(R.id.btn_declaracao);
        btnNoticias = (Button) findViewById(R.id.btn_ler_noticias);
        btnCadastro = (Button) findViewById(R.id.btn_cadastrar);
        btnSalas = (Button) findViewById(R.id.btn_ver_salas);

        btnSalas.setVisibility(View.GONE);

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
                startActivity(new Intent(getApplicationContext(),NovaDeclaracaoActivity.class));
            }
        });

        btnNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NoticiasAgregadorActivity.class));
            }
        });
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CadastrarUsuarioActivity.class));
            }
        });


        //Iniciar serviços do FCM
        startService(new Intent(this, MyFirebaseInstanceIdService.class));
        startService(new Intent(this, SMDMessagingService.class));

    }

    private void verificarUsuario(FirebaseAuth auth){
        FirebaseUser user = auth.getCurrentUser();
        //Se o usuario existir
        if(user == null){
            auth.signInAnonymously();
        }
        //Se o usuario for anonimo
        else if(user.isAnonymous()){
            //proibir de pedir declaracao
            btnDeclaracoes.setVisibility(View.GONE);
            //mostrar o botao de cadastro
            btnCadastro.setVisibility(View.VISIBLE);
            //mostrar botao de login
            btnLogin.setVisibility(View.VISIBLE);
        }
        //O usuario esta cadastrado
        else{
            btnCadastro.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            String uid = user.getUid();
            if(uid.equals("")) Log.d("DADOS"," uid vazio. Pegar dados do BD pode ser uma pessima ideia :|");
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users/" + uid);
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if( (boolean) dataSnapshot.child(User.CONFIRMADO_SECRETARIA).getValue() ){
                        //mostrar botao de pedir declaracao
                        btnDeclaracoes.setVisibility(View.VISIBLE);
                    }
                    else if( (boolean) dataSnapshot.child(User.ENVIADO_PEDIDO).getValue()){
                        //mostrar botao de status de confirmacao de cadastro
                        //TODO: Activity que mostra o status para usuario
                    }
                    else {
                        //mostrar botao de enviar pedido de fazer pedidos
                        //TODO: Modificar a Activity VerificarUsuariosActivity
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            }); //listener
        } //else Usuario esta cadastrado


    }

}
