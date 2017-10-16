package br.ufc.smdapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import br.ufc.smdapp.utils.RefBanco;
import br.ufc.smdapp.utils.User;
import br.ufc.smdapp.utils.ValidaCPF;

public class VerificarUsuarioActivity extends AppCompatActivity {
    DatabaseReference alunosRef;
    Button btnProsseguir;
    EditText edtMatricula, edtCpf,edtNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_usuario);

        alunosRef = FirebaseDatabase.getInstance().getReference(RefBanco.ALUNOS);
        edtMatricula = (EditText) findViewById(R.id.edt_matricula);
        edtCpf = (EditText) findViewById(R.id.edt_cpf);
        edtNome = (EditText) findViewById(R.id.edt_nome);



        btnProsseguir = (Button) findViewById(R.id.btn_prosseguir);
        btnProsseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mMatricula, mCpf, mNome;

                mMatricula = edtMatricula.getText().toString();
                mCpf = edtCpf.getText().toString(); mCpf = ValidaCPF.removeCaracteresInvalidos(mCpf);
                mNome = edtNome.getText().toString();

                if(ValidaCPF.isCPF(mCpf)){
                    //cpf ok, envia dados
                    HashMap<String,Object> dados = new HashMap<String, Object>();
                    dados.put(User.MATRICULA,mMatricula);
                    dados.put(User.CPF,mCpf);
                    dados.put(User.NOME, mNome);
                }
                else{ //cpf invalido
                    Toast.makeText(getApplicationContext(),"CPF inválido",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
