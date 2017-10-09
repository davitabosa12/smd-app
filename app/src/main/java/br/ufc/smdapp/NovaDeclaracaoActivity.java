package br.ufc.smdapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufc.smdapp.utils.Formulario;

public class NovaDeclaracaoActivity extends AppCompatActivity {
    Spinner spTipoDeclaracao;
    ArrayList<Formulario> formularios;
    String[] tiposDeclaracao;
    Button btnContinuar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_declaracao);
        tiposDeclaracao = getResources().getStringArray(R.array.tipos_declaracao);
        setTitle("Escolha o tipo de formulário");
        btnContinuar = (Button) findViewById(R.id.btn_continuar);

        init();
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = spTipoDeclaracao.getSelectedItemPosition();
                Toast.makeText(getApplicationContext(), tiposDeclaracao[index], Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), DeclaracaoFormularioActivity.class);
                i.putExtra("FORMULARIO", index);
                startActivity(i);
            }
        });
    }
    private void init() {
        spTipoDeclaracao = (Spinner) findViewById(R.id.sp_tipo_declaracao);
        spTipoDeclaracao.setAdapter(
                        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tiposDeclaracao));
    }


}
