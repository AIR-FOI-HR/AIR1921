package hr.foi.air.hr.database.entities;

public class OsvojenaZnacka {
    String korisnikKorime;
    String znackaIdZnacka;
    String datum;

    public OsvojenaZnacka() {
    }

    public OsvojenaZnacka(String korisnikKorime, String znackaIdZnacka, String datum) {
        this.korisnikKorime = korisnikKorime;
        this.znackaIdZnacka = znackaIdZnacka;
        this.datum = datum;
    }

    public String getKorisnikKorime() {
        return korisnikKorime;
    }

    public void setKorisnikKorime(String korisnikKorime) {
        this.korisnikKorime = korisnikKorime;
    }

    public String getZnackaIdZnacka() {
        return znackaIdZnacka;
    }

    public void setZnackaIdZnacka(String znackaIdZnacka) {
        this.znackaIdZnacka = znackaIdZnacka;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
