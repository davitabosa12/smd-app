package br.ufc.smdapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by davi on 17/09/17.
 */

public class DeclaracaoView extends ConstraintLayout implements View.OnClickListener {
    private Context ctx;
    private String assunto;
    private int status;
    private View rootView;
    private TextView txvTitulo,txvStatus;

    //constantes
    public static final int STATUS_ENVIANDO = 0;
    public static final int STATUS_ENVIADO = 1;
    public static final int STATUS_LIDO = 2;
    public static final int STATUS_EM_EXECUCAO = 3;
    public static final int STATUS_DISPONIVEL = 4;
    public static final int STATUS_NAO_REALIZADO = -1;


    public DeclaracaoView(Context ctx,String assunto,int status){

        super(ctx);
        this.ctx = ctx;
        this.assunto = assunto;
        this.status = status;
        init();
    }

    private void init() {

        rootView = inflate(ctx,R.layout.view_declaracao,this);
        txvTitulo = (TextView) rootView.findViewById(R.id.txv_declaracao_titulo);

        Log.d("DeclView",txvTitulo.getText().toString());

        txvStatus = (TextView) rootView.findViewById(R.id.txv_status);
        txvTitulo.setText(assunto);
        setStatus(status);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(ctx,"Ainda não implementado.",Toast.LENGTH_SHORT).show();
    }
    public void setStatus(int newStatus){
        switch (newStatus){
            case STATUS_ENVIANDO :{
                txvStatus.setText(R.string.status_enviando);
                txvStatus.setTextColor(getResources().getColor(R.color.preto));
                break;
            }
            case STATUS_ENVIADO :{
                txvStatus.setText(R.string.status_enviado);
                txvStatus.setTextColor(getResources().getColor(R.color.preto));
                break;
            }
            case STATUS_LIDO :{
                txvStatus.setText(R.string.status_lido);
                txvStatus.setTextColor(getResources().getColor(R.color.lido)); //setar cor lido
                break;
            }
            case STATUS_EM_EXECUCAO :{
                txvStatus.setText(R.string.status_execucao);
                txvStatus.setTextColor(getResources().getColor(R.color.lido)); //setar cor lido
                break;
            }
            case STATUS_DISPONIVEL :{
                txvStatus.setText(R.string.status_disponivel);
                txvStatus.setTextColor(getResources().getColor(R.color.ok)); //setar cor ok
                break;
            }
            case STATUS_NAO_REALIZADO :{
                txvStatus.setText(R.string.status_nao_realizado);
                txvStatus.setTextColor(getResources().getColor(R.color.nok)); //setar cor nok
                break;
            }
        }
    }
}

class DeclaracaoAdapter extends ArrayAdapter<DeclaracaoView>{
    private Context context;
    private List<DeclaracaoView> objects;

    public DeclaracaoAdapter(@NonNull Context context, @NonNull List<DeclaracaoView> objects) {
        super(context, R.layout.view_declaracao, objects);
        this.context = context;
        this.objects = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View elem = objects.get(position);

        return elem;
    }
}
