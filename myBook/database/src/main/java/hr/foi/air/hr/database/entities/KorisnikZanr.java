package hr.foi.air.hr.database.entities;

public class KorisnikZanr {
    String korisnikKorime;
    String zanrIdZanr;

    public KorisnikZanr() {
    }

    public KorisnikZanr(String korisnikKorime, String zanrIdZanr) {
        this.korisnikKorime = korisnikKorime;
        this.zanrIdZanr = zanrIdZanr;
    }

    public String getKorisnikKorime() {
        return korisnikKorime;
    }

    public void setKorisnikKorime(String korisnikKorime) {
        this.korisnikKorime = korisnikKorime;
    }

    public String getZanrIdZanr() {
        return zanrIdZanr;
    }

    public void setZanrIdZanr(String zanrIdZanr) {
        this.zanrIdZanr = zanrIdZanr;
    }
}
