package br.ufc.smdapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticiasAgregadorActivity extends AppCompatActivity {

    private ListView lvNoticias;
    private ArrayList<Noticia> mNoticias;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_agregador);




        //inicializar Firebase Database
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("noticia");
        mNoticias = new ArrayList<>();
        lvNoticias = (ListView) findViewById(R.id.lv_noticias);

        //Pegar dados utilizando o Firebase.
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String titulo,desc,tipo;
                mNoticias.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    titulo = data.child("titulo").getValue(String.class);
                    desc = data.child("descricao").getValue(String.class);
                    tipo = data.child("tipo").getValue(String.class);
                    mNoticias.add(new Noticia(titulo,desc,tipo));
                }
                configuraRecycler();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Ocorreu um erro: "+ databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        /*lvNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Noticia n = (Noticia) lvNoticias.getItemAtPosition(i);

                Intent intent = new Intent(getApplicationContext(), LerNoticiaActivity.class);
                //enviar extras pro novo Intent
                intent.putExtra("TITULO",n.getTitulo());
                intent.putExtra("DESCRICAO",n.getDesc());
                intent.putExtra("TIPO",n.getTipo());
                startActivity(intent);
            }
        });*/

    }

    private void configuraRecycler(){
        if(!isSet){
            isSet = true;
            //iniciar o RecyclerView
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_main);

            //iniciar o LayoutManager (No caso, LinearLayoutManager)
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            //Setar o Adapter
            mAdapter = new NoticiaRecyclerAdapter(mNoticias);
            mRecyclerView.setAdapter(mAdapter);
        }

    }
}
