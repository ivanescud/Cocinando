package com.simplelifestudio.cocinando.model;

import java.util.Date;

public class User {
    String name;
    String email;
    String userId;
    String userImg;
    String pais;
    String rango;
    String favoritos;
    String compras;
    boolean premiun;
    String eventos;
    String paquetes;
    boolean acceptaTerminos;
    Date   fechaCreacion;
    public User() {
    }

    public User(String name, String email, String userId, String userImg, String pais, String rango, String favoritos, String compras, boolean premiun, String eventos, String paquetes,boolean acceptaTerminos, Date fechaCreacion) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.userImg = userImg;
        this.pais = pais;
        this.rango = rango;
        this.favoritos = favoritos;
        this.compras = compras;
        this.premiun = premiun;
        this.eventos = eventos;
        this.paquetes = paquetes;
        this.fechaCreacion = fechaCreacion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public String getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(String favoritos) {
        this.favoritos = favoritos;
    }

    public String getCompras() {
        return compras;
    }

    public void setCompras(String compras) {
        this.compras = compras;
    }

    public boolean isPremiun() {
        return premiun;
    }

    public void setPremiun(boolean premiun) {
        this.premiun = premiun;
    }

    public String getEventos() {
        return eventos;
    }

    public void setEventos(String eventos) {
        this.eventos = eventos;
    }

    public String getPaquetes() {
        return paquetes;
    }

    public void setPaquetes(String paquetes) {
        this.paquetes = paquetes;
    }

    public boolean isAcceptaTerminos() {
        return acceptaTerminos;
    }

    public void setAcceptaTerminos(boolean acceptaTerminos) {
        this.acceptaTerminos = acceptaTerminos;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
