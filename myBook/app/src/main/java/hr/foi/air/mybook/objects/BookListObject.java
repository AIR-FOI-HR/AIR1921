package hr.foi.air.mybook.objects;

public class BookListObject {
    private String idKnjiga;
    private String naziv;
    private String sazetak;
    private String autor;
    private String urlSlike;
    private float ocjena;

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

    public float getOcjena() {
        return ocjena;
    }

    public void setOcjena(float ocjena) {
        this.ocjena = ocjena;
    }
}
