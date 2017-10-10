package br.ufc.smdapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import br.ufc.smdapp.utils.Formulario;

public class TesteFormulario extends AppCompatActivity {

    Formulario formSegundaChamada;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_formulario);

        setTitle("Teste de Formulario");
        layout = (LinearLayout) findViewById(R.id.scrollLayout);
        formSegundaChamada = new Formulario(this);
        formSegundaChamada.addEditText("nome","Nome", InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        formSegundaChamada.addCheckBox("legal","Legal?");
        formSegundaChamada.addEditText("mMatricula", "Matrícula", InputType.TYPE_NUMBER_VARIATION_NORMAL);
        formSegundaChamada.display(layout);
    }
}
