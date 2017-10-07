package br.ufc.smdapp.utils;

import android.content.Context;
import android.text.Layout;
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
import java.util.Map;

/**
 * Created by davi on 07/10/17.
 */

public class Formulario {
    private Context mContext; //Context do Formulario
    private ArrayList<View> items; //Itens do formulario
    private ArrayList<String> names;
    private Button teste;

    public Formulario(Context context){
        this.mContext = context;
        this.items = new ArrayList<>();
        this.names = new ArrayList<>();
        teste = new Button(mContext);
        teste.setText("Enviar");
        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> map = getForm();
                String temp = "";
                for(String value : map.values()){
                    temp = temp + value + " ";
                }
                Toast.makeText(mContext,temp,Toast.LENGTH_LONG).show();
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
        for (View v: items) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                       ViewGroup.LayoutParams.WRAP_CONTENT);
            viewGroup.addView(v,params);
        }
        viewGroup.addView(teste);
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



}
