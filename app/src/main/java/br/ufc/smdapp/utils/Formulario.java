package br.ufc.smdapp.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by davi on 07/10/17.
 */

public class Formulario {
    private Context mContext; //Context do Formulario
    private ArrayList<View> items; //Itens do formulario
    private ArrayList<String> names;
    private String formName; //Titulo do Formulario
    private Button submit;

    public Formulario(Context context){
        this.mContext = context;
        this.items = new ArrayList<>();
        this.names = new ArrayList<>();
        this.formName ="Formulario Genérico";
        submit = new Button(mContext);
        submit.setText("Enviar");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext,"Button action has not been set.",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addEditText(String name, String hint,int inputType){
        EditText editText = new EditText(mContext);
        editText.setHint(hint);
        editText.setInputType(inputType);
        items.add(editText);
        names.add(name);
    }

    public void addCheckBox(String name, String hint){
        CheckBox checkBox = new CheckBox(mContext);
        checkBox.setHint(hint);
        items.add(checkBox);
        names.add(name);
    }

    public void addSpinner(String name, ArrayAdapter adapter){
        Spinner spinner = new Spinner(mContext);
        spinner.setAdapter(adapter);
        items.add(spinner);
        names.add(name);

    }
    public void display(ViewGroup viewGroup){
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        for (View v: items) {
            viewGroup.addView(v,params);
        }
        viewGroup.addView(submit,params);
    }

    public HashMap<String,String> getForm(){
        HashMap<String,String> result = new HashMap<>();
        for(int i = 0; i < items.size(); i++){
            String key=  names.get(i);
            String value;
            View v = items.get(i);
            if(v instanceof EditText){
                value = ((EditText) v).getText().toString();
            }
            else if(v instanceof Spinner){
                value = ((Spinner) v).getSelectedItem().toString();
            }
            else {
                value = ((CheckBox) v).isChecked() ? "true" : "false";
            }
            result.put(key,value);
        }
        return result;
    }
    public void setAction(View.OnClickListener listener){
        submit.setOnClickListener(listener);
    }
    public String getFormName(){
        return formName;
    }
    public void setFormName(String newFormName){
        this.formName= newFormName;
    }

}
