package br.ufc.smdapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by davi on 28/08/17.
 */

public class NoticiaAdapter extends ArrayAdapter<Noticia> {
    private Context context;
    private List<Noticia> objects;

    public NoticiaAdapter(@NonNull Context context, @NonNull List<Noticia> objects) {
        super(context, R.layout.simple_list_item, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View elem = inflater.inflate(R.layout.simple_list_item,parent,false);
        TextView titulo = (TextView) elem.findViewById(R.id.txv_titulo_noticia);
        TextView tipo = (TextView) elem.findViewById(R.id.txv_tipo_noticia);
        titulo.setText(objects.get(position).getTitulo());
        tipo.setText(objects.get(position).getTipo());
        return elem;
    }
}
