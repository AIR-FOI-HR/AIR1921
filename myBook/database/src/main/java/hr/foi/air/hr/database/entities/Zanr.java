package hr.foi.air.hr.database.entities;

public class Zanr {
    String idZanr;
    String naziv;
    String opis;

    public Zanr() {
    }

    public Zanr(String idZanr, String naziv, String opis) {
        this.idZanr = idZanr;
        this.naziv = naziv;
        this.opis = opis;
    }

    @Override
    public String toString() {
        return naziv;
    }

    public String getIdZanr() {
        return idZanr;
    }

    public void setIdZanr(String idZanr) {
        this.idZanr = idZanr;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
