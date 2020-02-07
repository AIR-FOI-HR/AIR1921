package hr.foi.air.hr.database.entities;

public class Knjiga {
    String idKnjiga;
    String naziv;
    String godinaIzdavanja;
    String url;
    String sazetak;
    String izdavacIdIzdavac;

    public Knjiga() {
    }

    public Knjiga(String idKnjiga, String naziv, String godinaIzdavanja, String url, String sazetak, String izdavacIdIzdavac) {
        this.idKnjiga = idKnjiga;
        this.naziv = naziv;
        this.godinaIzdavanja = godinaIzdavanja;
        this.url = url;
        this.sazetak = sazetak;
        this.izdavacIdIzdavac = izdavacIdIzdavac;
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

    public String getGodinaIzdavanja() {
        return godinaIzdavanja;
    }

    public void setGodinaIzdavanja(String godinaIzdavanja) {
        this.godinaIzdavanja = godinaIzdavanja;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSazetak() {
        return sazetak;
    }

    public void setSazetak(String sazetak) {
        this.sazetak = sazetak;
    }

    public String getIzdavacIdIzdavac() {
        return izdavacIdIzdavac;
    }

    public void setIzdavacIdIzdavac(String izdavacIdIzdavac) {
        this.izdavacIdIzdavac = izdavacIdIzdavac;
    }
}
