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
                alunosRef = FirebaseDatabase.getInstance().getReference("alunos/" + mMatricula);
                alunosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot != null){
                            HashMap<String,Object> snap = (HashMap) dataSnapshot.getValue();
                            Log.d("Verificar usuario", "message "  + snap.toString());
                            String cpf = dataSnapshot.child("cpf").getValue(String.class);
                            //cpf = retirarCaracteresInvalidosCPF(cpf);
                            if(cpf.equals(mCpf)){
                                Intent cadastrarUsuario = new Intent(getApplicationContext(),CadastrarUsuarioActivity.class);
                                cadastrarUsuario.putExtra("MATRICULA",mMatricula);
                                startActivity(cadastrarUsuario);

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Os dados não conferem.",Toast.LENGTH_SHORT).show();
                            }

                        } //dataSnapshot != null
                        else{
                            new AlertDialog.Builder(getApplicationContext())
                                    .setTitle("Matrícula não encontrada")
                                    .setMessage("A Matricula informada não foi encontrada." +
                                            "Se você acha isso um erro, entre em contato com a Secretaria.")
                                    .setPositiveButton("Entendi",null)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Erro de BD: " + databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private boolean cpfValido(String CPF){
        if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);
        char[] cpfArray = CPF.toCharArray();
        //TODO: Verificacao completa do CPF
        return true;
    }
    private String retirarCaracteresInvalidosCPF(String CPF){
        char[] cpfArray = CPF.toCharArray();
        String result = "";
        for(int i = 0; i < cpfArray.length; i++){
            if(cpfArray[i] >='0' && cpfArray[i] <= '9'){
                result += cpfArray[i];
            }
        }
        return result;
    }


}
