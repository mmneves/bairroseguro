package com.nevesti.www.bairroseguro.model;

import com.google.firebase.database.Exclude;

public class NaturezaOcorrencia {

    private String id;
    private String descricao;

    public NaturezaOcorrencia() {
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
