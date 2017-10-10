package br.ufc.smdapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class CadastrarUsuarioActivity extends AppCompatActivity {
    FirebaseAuth auth;
    DatabaseReference usersRef;
    Button btnCadastrar;
    EditText edtEmail, edtSenha, edtSenhaConfima;
    String matricula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);
        setTitle("Cadastro");

        Bundle extras = getIntent().getExtras();
        matricula = extras.getString("MATRICULA");

        auth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("users/" + matricula);


        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtSenha = (EditText) findViewById(R.id.edt_senha);
        edtSenhaConfima = (EditText) findViewById(R.id.edt_senha_confirmacao);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastro();
            }
        });

    }
    private void cadastro(){
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        String senhaConfirma = edtSenhaConfima.getText().toString();
        if(senhaConfirma.equals(senha)){ //Se as senhas digitadas forem iguais
            auth.createUserWithEmailAndPassword(email,senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(getApplicationContext(),"Cadastrado com sucesso.",Toast.LENGTH_SHORT).show();
                    authResult.getUser().sendEmailVerification();
                    String uid = authResult.getUser().getUid(); //pegar a id do usuario
                    HashMap<String,Object> dados = new HashMap<String, Object>();
                    dados.put("uid",uid);
                    dados.put("cadastrado",true);
                    usersRef.setValue(dados).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                                Log.d("Cadastro","Dados enviados");
                            else
                                Log.d("Cadastro","Houve um erro");
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Cadastro Falhou. " + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
