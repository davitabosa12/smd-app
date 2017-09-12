package br.ufc.smdapp;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by davi on 28/08/17.
 */

public class NoticiaView extends LinearLayout implements View.OnClickListener {
    private Noticia noticia;
    private TextView txvTitulo;
    private TextView txvTipo;
    private View rootView;
    private Context context;

    public NoticiaView(Context context, Noticia noticia){
        super(context);
        this.noticia = noticia;
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context,R.layout.simple_list_item,this);
        txvTitulo = (TextView) findViewById(R.id.txv_titulo_noticia);
        txvTipo = (TextView) findViewById(R.id.txv_tipo_noticia);
        txvTitulo.setText(noticia.getTitulo());
        txvTipo.setText(noticia.getTipo());
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context,noticia.getDesc(),Toast.LENGTH_LONG).show();
    }
}
