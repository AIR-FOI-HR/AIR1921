package com.air.foi.hr.database.entities;

public class Izdavac {
    String idIzdavac;
    String imeIzdavaca;
    String mjesto;

    public Izdavac(){

    }

    public Izdavac(String idIzdavac, String imeIzdavaca, String mjesto) {
        this.idIzdavac = idIzdavac;
        this.imeIzdavaca = imeIzdavaca;
        this.mjesto = mjesto;
    }

    public String getIdIzdavac() {
        return idIzdavac;
    }

    public void setIdIzdavac(String idIzdavac) {
        this.idIzdavac = idIzdavac;
    }

    public String getImeIzdavaca() {
        return imeIzdavaca;
    }

    public void setImeIzdavaca(String imeIzdavaca) {
        this.imeIzdavaca = imeIzdavaca;
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }
}
