package com.glucoapp.data.entities;

import java.io.Serializable;

public class User implements Serializable {

    String email;
    String password;
    String nombres;
    String apellidos;
    String tipoDiabetes;
    String sexo;
    String fechaNacimiento;
    String uid;

    public User(){}

    public User(String email, String password, String nombres, String apellidos, String tipoDiabetes, String sexo, String fechaNacimiento, String uid) {
        this.email = email;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDiabetes = tipoDiabetes;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", tipoDiabetes='" + tipoDiabetes + '\'' +
                ", sexo='" + sexo + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoDiabetes() {
        return tipoDiabetes;
    }

    public void setTipoDiabetes(String tipoDiabetes) {
        this.tipoDiabetes = tipoDiabetes;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
