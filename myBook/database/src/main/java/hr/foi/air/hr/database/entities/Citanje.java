package hr.foi.air.hr.database.entities;

public class Citanje {
    String korisnikKorime;
    String knjigaIdKnjiga;
    String datum;
    Integer ocjena;
    String komentar;

    public Citanje() {
    }

    public Citanje(String korisnikKorime, String knjigaIdKnjiga, String datum, Integer ocjena, String komentar) {
        this.korisnikKorime = korisnikKorime;
        this.knjigaIdKnjiga = knjigaIdKnjiga;
        this.datum = datum;
        this.ocjena = ocjena;
        this.komentar = komentar;
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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }
}
