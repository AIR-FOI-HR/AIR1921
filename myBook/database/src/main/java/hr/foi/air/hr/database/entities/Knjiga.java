package hr.foi.air.hr.database.entities;

public class Knjiga {
    String idKnjiga;
    String naziv;
    String godinaIzdavanja;
    String sazetak;
    String autor;
    String urlSlike;

    public Knjiga() {
    }

    public Knjiga(String idKnjiga, String naziv, String godinaIzdavanja, String sazetak, String autor, String urlSlike) {
        this.idKnjiga = idKnjiga;
        this.naziv = naziv;
        this.godinaIzdavanja = godinaIzdavanja;
        this.sazetak = sazetak;
        this.autor = autor;
        this.urlSlike = urlSlike;
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

    public String getSazetak() {
        return sazetak;
    }

    public void setSazetak(String sazetak) {
        this.sazetak = sazetak;
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

    @Override
    public String toString() {
        return "Knjiga{" +
                "idKnjiga='" + idKnjiga + '\'' +
                ", naziv='" + naziv + '\'' +
                ", godinaIzdavanja='" + godinaIzdavanja + '\'' +
                ", sazetak='" + sazetak + '\'' +
                ", autor='" + autor + '\'' +
                ", urlSlike='" + urlSlike + '\'' +
                '}';
    }
}