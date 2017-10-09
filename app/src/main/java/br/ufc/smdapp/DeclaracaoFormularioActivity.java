package br.ufc.smdapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import br.ufc.smdapp.utils.Formulario;

public class DeclaracaoFormularioActivity extends AppCompatActivity {
    Formulario formulario;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaracao_formulario);
        Bundle extras = getIntent().getExtras();
        int form = extras.getInt("FORMULARIO");
        layout = (LinearLayout) findViewById(R.id.scrollLayout);

        formulario = initFormulario(form);
        setTitle(formulario.getFormName());
        formulario.display(layout);
        formulario.setAction(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> dados = formulario.getForm();
                enviarParaFirebase(dados);
            }
        });
    }

    /**
     * Inicia todos os formularios
     */
    private Formulario initFormulario(int form){
        Formulario f = new Formulario(this);
        //INSERIR NA ORDEM QUE ESTÁ EM STRINGS.XML
        switch (form){
            case 0: //Formulario segunda chamada
                f.setFormName("Segunda Chamada");
                f.addEditText("professor","Professor", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                f.addEditText("prova","Prova", InputType.TYPE_CLASS_TEXT);
                f.addEditText("justificativa","Justificativa",InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                break;
            case 1: //Formulario trancamento total
                f.setFormName("Trancamento Total");
                String[] motivosTrancamento = getResources().getStringArray(R.array.motivo_trancamento_total);
                f.addEditText("endereco","Endereço",InputType.TYPE_CLASS_TEXT);
                f.addEditText("telefone","Telefone",InputType.TYPE_CLASS_PHONE);
                f.addSpinner("motivo",new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,motivosTrancamento));
                break;
        }
        return f;
    }
    private void enviarParaFirebase(HashMap<String,String> dados){
        //Para fins de teste
        String result = "";
        for(String key : dados.keySet()){
            result += key + ": " + dados.get(key);
        }
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
    }
}
