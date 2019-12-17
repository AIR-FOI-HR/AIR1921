package com.air.foi.hr.database.entities;

public class Korisnik {

    String korime;
    String ime;
    String prezime;
    String mail;
    String datumRodenja;

    public Korisnik(){

    }

    public Korisnik(String korime, String ime, String prezime, String mail, String datumRodenja) {
        this.korime = korime;
        this.ime = ime;
        this.prezime = prezime;
        this.mail = mail;
        this.datumRodenja = datumRodenja;
    }

    public String getKorime() {
        return korime;
    }

    public void setKorime(String korime) {
        this.korime = korime;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(String datumRodenja) {
        this.datumRodenja = datumRodenja;
    }
}
