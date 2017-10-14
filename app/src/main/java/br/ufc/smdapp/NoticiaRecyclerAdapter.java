package br.ufc.smdapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by davi on 30/09/17.
 */

class NoticiaRecyclerAdapter extends RecyclerView.Adapter<NoticiaRecyclerAdapter.ViewHolder> {
    private ArrayList<Noticia> mNoticias;

    public NoticiaRecyclerAdapter(ArrayList<Noticia> mNoticias) {
        this.mNoticias = mNoticias;
    }

    /**
     * Funcao que roda cada vez que uma nova view é inflada
     * @param parent A view pai
     * @param viewType nao utilizada
     * @return Um ViewHolder para ser colocado no RecyclerView
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noticia_item,parent,false);
        return new ViewHolder(view);
    }

    /**
     * Função que atualiza views com novos dados para serem reutilizados (reciclados), assim
     * consumindo menos memória
     * @param holder A viewholder a ser atualizada
     * @param position A posicao utilizada na base de dados para atualizar a viewholder
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Noticia n = mNoticias.get(position);
        if(n != null){
            String mDesc = n.getDesc();
            holder.mTitutlo.setText(n.getTitulo());
            holder.mTipo.setText(n.getTipo());

            //Limitar o texto de previa em 30 caracteres
            holder.mPrevia.setText(mDesc.length() > 30 ?
                                   mDesc.substring(0,26) + "..."
                                   : mDesc);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context ctx = view.getContext();
                    Intent intent = new Intent(ctx, LerNoticiaActivity.class);
                    //enviar extras pro novo Intent
                    intent.putExtra("TITULO",n.getTitulo());
                    intent.putExtra("DESCRICAO",n.getDesc());
                    intent.putExtra("TIPO",n.getTipo());
                    ctx.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mNoticias == null || mNoticias.isEmpty())
            return 0;
        return mNoticias.size();
    }

    /**
     * Classe do ViewHolder
     * Tem que existir para o Adapter funcionar.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitutlo,mTipo,mPrevia;
        public ViewHolder(final View itemView) {
            super(itemView);
            mTitutlo = (TextView)itemView.findViewById(R.id.txv_titulo);
            mTipo = (TextView)itemView.findViewById(R.id.txv_tipo);
            mPrevia = (TextView)itemView.findViewById(R.id.txv_previa);

        }
    }
}
