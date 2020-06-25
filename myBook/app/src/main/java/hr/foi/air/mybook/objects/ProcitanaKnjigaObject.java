package hr.foi.air.mybook.objects;

public class ProcitanaKnjigaObject {
    private String korisnikKorime;
    private String idKnjiga;
    private String naziv;
    private String autor;
    private String urlSlike;
    private String datumPocetka;
    private String datumKraja;
    private String komentar;
    private float ocjena;

    public String getKorisnikKorime() {
        return korisnikKorime;
    }

    public void setKorisnikKorime(String korisnikKorime) {
        this.korisnikKorime = korisnikKorime;
    }


    public String getIdKnjiga() {
        return idKnjiga;
    }

    public void setIdKnjiga(String idKnjiga) {
        this.idKnjiga = idKnjiga;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlSlike() {
        return urlSlike;
    }

    public void setUrlSlike(String urlSlike) {
        this.urlSlike = urlSlike;
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

    public String getKomentar() { return komentar; }

    public void setKomentar(String komentar) { this.komentar = komentar; }

    public float getOcjena() { return ocjena; }

    public void setOcjena(float ocjena) { this.ocjena = ocjena; }
}
