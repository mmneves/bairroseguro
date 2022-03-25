package com.nevesti.www.bairroseguro.model;

public class Conversa {

    private String idUsuario;
    private String data;
    private String hora;
    private String name;
    private String content;
    private String status;

    /*
    private String idUsuario;
    private String data;
    private String hora;
    private String name;
    private String content;
    private String status;*/

    public Conversa() {   }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getHora() { return hora; }

    public void setHora(String hora) { this.hora = hora; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
