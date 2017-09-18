package br.ufc.smdapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeclaracaoActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference ref;
    ConstraintLayout mLayout;
    ListView mListView;
    DeclaracaoAdapter adapter;
    ArrayList<DeclaracaoView> views;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaracao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        views = new ArrayList<>();

        setSupportActionBar(toolbar);
        //Conectar ao banco de dados, no relacionamento "Declaracoes"

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("declaracoes");

        //Pegar itens do layout incluido no XML
        mLayout = (ConstraintLayout) findViewById(R.id.include_content_declaracao);

        //Açoes do FloatingActionButton (Criar uma nova declaração)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeclaracaoActivity.this,EnviarDeclaracaoActivity.class));
            }
        });
        mostraDeclaracoes();

    }

    private void mostraDeclaracoes() {
        mListView = (ListView) mLayout.findViewById(R.id.lv_declaracoes_feitas);
        adapter = new DeclaracaoAdapter(getApplicationContext(),views);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String assunto;
                int status;
                mListView.removeAllViewsInLayout();
                views.clear();
                mListView.setAdapter(adapter);
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    assunto = value.child("titulo").getValue(String.class);
                    status = value.child("status").getValue(Integer.class);
                    DeclaracaoView temp = new DeclaracaoView(getApplicationContext(),assunto,status);
                    views.add(temp);
                    adapter.notifyDataSetChanged();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Erro de DB: " + databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
