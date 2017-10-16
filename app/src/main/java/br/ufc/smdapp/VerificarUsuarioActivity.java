package br.ufc.smdapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

import br.ufc.smdapp.utils.ValidaCPF;

public class VerificarUsuarioActivity extends AppCompatActivity {
    DatabaseReference alunosRef;
    Button btnProsseguir;
    EditText edtMatricula, edtCpf;
    String mMatricula, mCpf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_usuario);

        edtMatricula = (EditText) findViewById(R.id.edt_matricula);
        edtCpf = (EditText) findViewById(R.id.edt_cpf);




        btnProsseguir = (Button) findViewById(R.id.btn_prosseguir);
        btnProsseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMatricula = edtMatricula.getText().toString();
                mCpf = edtCpf.getText().toString();
                mCpf = ValidaCPF.removeCaracteresInvalidos(mCpf);
                if(ValidaCPF.isCPF(mCpf)){
                    //cpf ok, envia dados
                    HashMap<String,Object> dados = new HashMap<String, Object>();
                    dados.put("matricula",mMatricula);
                    dados.put("cpf",mCpf);
                    dados.put("","");
                }

            }
        });

    }


}
