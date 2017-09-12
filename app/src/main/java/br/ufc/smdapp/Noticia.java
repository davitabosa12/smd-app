package br.ufc.smdapp;

import android.text.Html;

/**
 * Created by davi on 28/08/17.
 * Estrutura de dados que representa uma noticia, com título, descrição e tipo.
 */

public class Noticia {
    //TODO: Transformar "tipo" em um enumerador
    private String titulo,desc,tipo;
    public Noticia(String titulo, String desc, String tipo){
        this.titulo = titulo;
        this.desc = desc;
        //TODO: Nao esquecer de mudar o tipo da variavel.
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDesc() {
        return desc;
    }

    public String getTipo() {
        return tipo;
    }
}
