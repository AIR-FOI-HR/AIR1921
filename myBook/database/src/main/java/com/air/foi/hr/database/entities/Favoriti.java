package com.air.foi.hr.database.entities;

public class Favoriti {
    String korisnikKorime;
    String knjigaIdKnjiga;


    public Favoriti() {
    }

    public Favoriti(String korisnikKorime, String knjigaIdKnjiga) {
        this.korisnikKorime = korisnikKorime;
        this.knjigaIdKnjiga = knjigaIdKnjiga;
    }

    public String getKorisnikKorime() {
        return korisnikKorime;
    }

    public void setKorisnikKorime(String korisnikKorime) {
        this.korisnikKorime = korisnikKorime;
    }

    public String getKnjigaIdKnjiga() {
        return knjigaIdKnjiga;
    }

    public void setKnjigaIdKnjiga(String knjigaIdKnjiga) {
        this.knjigaIdKnjiga = knjigaIdKnjiga;
    }
}
