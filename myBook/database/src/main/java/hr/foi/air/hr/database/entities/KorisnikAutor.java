package hr.foi.air.hr.database.entities;

public class KorisnikAutor {
    String korisnikKorime;
    String autorIdAutor;

    public KorisnikAutor() {
    }

    public KorisnikAutor(String korisnikKorime, String autorIdAutor) {
        this.korisnikKorime = korisnikKorime;
        this.autorIdAutor = autorIdAutor;
    }

    public String getKorisnikKorime() {
        return korisnikKorime;
    }

    public void setKorisnikKorime(String korisnikKorime) {
        this.korisnikKorime = korisnikKorime;
    }

    public String getAutorIdAutor() {
        return autorIdAutor;
    }

    public void setAutorIdAutor(String autorIdAutor) {
        this.autorIdAutor = autorIdAutor;
    }
}
