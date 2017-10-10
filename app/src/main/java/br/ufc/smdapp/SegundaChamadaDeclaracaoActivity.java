package br.ufc.smdapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SegundaChamadaDeclaracaoActivity extends AppCompatActivity {

    private EditText edtNomeAluno, edtMatricula, edtProf, edtProva, edtJustificativa;
    private String bkNomeAluno, bkMatricula, bkProf, bkProva, bkJustificativa;
    private Button btnLimpa, btnEnvia;
    SharedPreferences sharedPrefs;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_chamada_declaracao);

        //inicializar Preferencias
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Inicializar Firebase
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("declaracoes");


        //pegar os EditTexts do layout
        edtNomeAluno = (EditText) findViewById(R.id.edt_nome);
        edtMatricula = (EditText) findViewById(R.id.edt_matricula);
        //edtProf = (EditText) findViewById(R.id.edt_professor);
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
                //edtProf.setText("");
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

    private void salvarDadosDoFormulario() {
        bkNomeAluno = edtNomeAluno.getText().toString();
        bkMatricula = edtMatricula.getText().toString();
        //bkProf = edtProf.getText().toString();
        bkProva = edtProva.getText().toString();
        bkJustificativa = edtJustificativa.getText().toString();
    }

    //Funcao que verifica se todos os campos foram preenchidos corretamente.
    private boolean verificarCampos() {
        String nomeAluno = edtNomeAluno.getText().toString();
        String matricula = edtMatricula.getText().toString();
        //String prof = edtProf.getText().toString();
        String prova = edtProva.getText().toString();
        String justificativa = edtJustificativa.getText().toString();
        if (nomeAluno.isEmpty() || matricula.isEmpty() || /*prof.isEmpty() ||*/ prova.isEmpty() || justificativa.isEmpty())
            return false;
        else
            return true;
    }

    //Envia os dados para o servidor
    private boolean enviarDados() {
        String userId = sharedPrefs.getString("userID", null);
        if (verificarCampos()) {
            salvarDadosDoFormulario();
            HashMap<String, Object> result = new HashMap<>();

            result.put("nome", bkNomeAluno);
            result.put("mMatricula", bkMatricula);
            //result.put("professor", bkProf);
            result.put("prova", bkProva);
            result.put("justificativa", bkJustificativa);
            result.put("titulo", "Declaracao de Segunda Chamada");
            result.put("status", 0);
            ref.child(userId).push().setValue(result).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(SegundaChamadaDeclaracaoActivity.this, "Upado no DB.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SegundaChamadaDeclaracaoActivity.this, "Erro de dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        } else {
            Toast.makeText(this, "Complete todos os campos.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
