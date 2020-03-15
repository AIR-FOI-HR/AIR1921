package hr.foi.air.hr.database.entities;

public class Citanje {
    String korisnikKorime;
    String knjigaIdKnjiga;
    String datumPocetka;
    String datumKraja;
    Float ocjena;
    String komentar;

    public Citanje() {
    }

    public Citanje(String korisnikKorime, String knjigaIdKnjiga, String datumPocetka, String datumKraja, Float ocjena, String komentar) {
        this.korisnikKorime = korisnikKorime;
        this.knjigaIdKnjiga = knjigaIdKnjiga;
        this.datumPocetka = datumPocetka;
        this.datumKraja = datumKraja;
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

    public String getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(String datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public String getDatumKraja() {
        return datumKraja;
    }

    public void setDatumKraja(String datumKraja) {
        this.datumKraja = datumKraja;
    }

    public float getOcjena() {
        return ocjena;
    }

    public void setOcjena(Float ocjena) {
        this.ocjena = ocjena;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    @Override
    public String toString() {
        return "Citanje{" +
                "korisnikKorime='" + korisnikKorime + '\'' +
                ", knjigaIdKnjiga='" + knjigaIdKnjiga + '\'' +
                ", datumPocetka='" + datumPocetka + '\'' +
                ", datumKraja='" + datumKraja + '\'' +
                ", ocjena=" + ocjena +
                ", komentar='" + komentar + '\'' +
                '}';
    }
}
