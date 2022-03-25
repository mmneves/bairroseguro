package com.nevesti.www.bairroseguro.model;

import com.google.firebase.database.Exclude;

public class Telefone {

    private String id;
    private String nome;
    private String telefone;
    private String endereco;

    public Telefone() {
    }

    @Exclude
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
