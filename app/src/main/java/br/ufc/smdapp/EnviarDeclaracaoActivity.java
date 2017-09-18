package br.ufc.smdapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EnviarDeclaracaoActivity extends AppCompatActivity {

    private EditText edtNomeAluno, edtMatricula, edtProf, edtProva, edtJustificativa;
    private String nomeAluno, matricula, prof, prova, justificativa;
    private Button btnLimpa, btnEnvia;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_declaracao);

        //Inicializar Firebase
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("declaracoes");


        //pegar os EditTexts do layout
        edtNomeAluno = (EditText) findViewById(R.id.edt_nome);
        edtMatricula = (EditText) findViewById(R.id.edt_matricula);
        edtProf = (EditText) findViewById(R.id.edt_professor);
        edtProva = (EditText) findViewById(R.id.edt_prova);
        edtJustificativa = (EditText) findViewById(R.id.edt_justificativa);

        //pegar botoes
        btnLimpa = (Button) findViewById(R.id.btn_limpa);
        btnEnvia = (Button) findViewById(R.id.btn_envia);

        //colocar OnClick nos botoes
        btnLimpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarDadosDoFormulario();
                edtNomeAluno.setText("");
                edtMatricula.setText("");
                edtProf.setText("");
                edtProva.setText("");
                edtJustificativa.setText("");

                //TODO: programar SnackBar para desfazer o comando


            }
        });

        btnEnvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDados();
            }
        });


    }

    private void salvarDadosDoFormulario(){
        nomeAluno = edtNomeAluno.getText().toString();
        matricula = edtMatricula.getText().toString();
        prof = edtProf.getText().toString();
        prova = edtProva.getText().toString();
        justificativa = edtJustificativa.getText().toString();
    }

    //Funcao que verifica se todos os campos foram preenchidos corretamente.
    private boolean verificarCampos(){
        //TODO: verificarCampos
        return false;
    }
    //Envia os dados para o servidor
    private boolean enviarDados(){
        //TODO: enviarDados
        return false;
    }
}
