package com.tbp.foi.hr.core.objects;

import java.util.List;

public class ModulDataObject {

    public String getNazivKnjige() {
        return nazivKnjige;
    }

    public void setNazivKnjige(String nazivKnjige) {
        this.nazivKnjige = nazivKnjige;
    }

    public String getAutorKnjige() {
        return autorKnjige;
    }

    public void setAutorKnjige(String autorKnjige) {
        this.autorKnjige = autorKnjige;
    }

    public String getDatumPocetkaCitanja() {
        return datumPocetkaCitanja;
    }

    public void setDatumPocetkaCitanja(String datumPocetkaCitanja) {
        this.datumPocetkaCitanja = datumPocetkaCitanja;
    }

    public String getDatumKrajaCitanja() {
        return datumKrajaCitanja;
    }

    public void setDatumKrajaCitanja(String datumKrajaCitanja) {
        this.datumKrajaCitanja = datumKrajaCitanja;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public float getOcjena() {
        return ocjena;
    }

    public void setOcjena(float ocjena) {
        this.ocjena = ocjena;
    }

    public List<String> getZanrKnjige() {
        return zanrKnjige;
    }

    public void setZanrKnjige(List<String> zanrKnjige) {
        this.zanrKnjige = zanrKnjige;
    }

    private String nazivKnjige;
    private String autorKnjige;
    private String datumPocetkaCitanja;
    private String datumKrajaCitanja;
    private String komentar;
    private float ocjena;
    private List<String> zanrKnjige;
}
