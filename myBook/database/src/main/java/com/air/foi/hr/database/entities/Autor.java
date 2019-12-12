package com.air.foi.hr.database.entities;

public class Autor {
    String idAutor;
    String ime;
    String prezime;
    String datumRodenja;
    String oAutoru;

    public Autor() {
    }

    public Autor(String idAutor, String ime, String prezime, String datumRodenja, String oAutoru) {
        this.idAutor = idAutor;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodenja = datumRodenja;
        this.oAutoru = oAutoru;
    }

    public String getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(String idAutor) {
        this.idAutor = idAutor;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(String datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    public String getoAutoru() {
        return oAutoru;
    }

    public void setoAutoru(String oAutoru) {
        this.oAutoru = oAutoru;
    }
}
