package hr.foi.air.hr.database.entities;

public class AutorKnjige {
    String knjigaIdKnjiga;
    String autorIdAutor;

    public AutorKnjige() {
    }

    public AutorKnjige(String knjigaIdKnjiga, String autorIdAutor) {
        this.knjigaIdKnjiga = knjigaIdKnjiga;
        this.autorIdAutor = autorIdAutor;
    }

    public String getKnjigaIdKnjiga() {
        return knjigaIdKnjiga;
    }

    public void setKnjigaIdKnjiga(String knjigaIdKnjiga) {
        this.knjigaIdKnjiga = knjigaIdKnjiga;
    }

    public String getAutorIdAutor() {
        return autorIdAutor;
    }

    public void setAutorIdAutor(String autorIdAutor) {
        this.autorIdAutor = autorIdAutor;
    }
}
