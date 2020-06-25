package hr.foi.air.hr.database.entities;

public class ZanrKnjiga {
    String zanrIdZanr;
    String knjigaIdKnjiga;

    public ZanrKnjiga() {
    }

    public ZanrKnjiga(String zanrIdZanr, String knjigaIdKnjiga) {
        this.zanrIdZanr = zanrIdZanr;
        this.knjigaIdKnjiga = knjigaIdKnjiga;
    }

    public String getZanrIdZanr() {
        return zanrIdZanr;
    }

    public void setZanrIdZanr(String zanrIdZanr) {
        this.zanrIdZanr = zanrIdZanr;
    }

    public String getKnjigaIdKnjiga() {
        return knjigaIdKnjiga;
    }

    public void setKnjigaIdKnjiga(String knjigaIdKnjiga) {
        this.knjigaIdKnjiga = knjigaIdKnjiga;
    }
}
