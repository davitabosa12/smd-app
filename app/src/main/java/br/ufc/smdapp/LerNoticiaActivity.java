package br.ufc.smdapp;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class LerNoticiaActivity extends AppCompatActivity {

    String titulo, texto;
    private NestedScrollView mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler_noticia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        titulo = extras.getString("TITULO");
        texto = extras.getString("DESCRICAO");

        //pegar o layout
        mLayout = (NestedScrollView) findViewById(R.id.include_ler_noticia);
        //pegar a TXV dentro do include
        TextView txvTexto = (TextView) mLayout.findViewById(R.id.txv_texto_noticia);
        //Inserir a noticia dentro do txv
        txvTexto.setText(texto);

        //Setar titulo da Noticia
        toolbar.setTitle(titulo);
        ctl.setTitle(titulo);







        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
