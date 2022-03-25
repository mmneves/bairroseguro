package com.nevesti.www.bairroseguro.model;

import com.google.firebase.database.Exclude;

public class Denuncia {

    private String idUsuario;
    private String data;
    private String hora;
    private String natureza;
    private String mensagem;
    private String path_imagem;
    private String localizacao;

    public Denuncia() {
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getPath_imagem() {
        return path_imagem;
    }

    public void setPath_imagem(String path_imagem) {
        this.path_imagem = path_imagem;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
