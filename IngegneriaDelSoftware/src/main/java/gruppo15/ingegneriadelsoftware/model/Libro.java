package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.LibroNonDisponibileException;
import java.time.LocalDate;
import java.util.List;

public class Libro implements Checkable {

    private String titolo;

    private List<String> listaAutori;

    private LocalDate annoDiPubblicazione;

    private final String ISBN;

    private int numeroCopie;

    private float valore;

    public Libro(String titolo, String autori, LocalDate annoDiPubblicazione, String ISBN, int numeroCopie, float valore) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String setTitolo(String titolo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LocalDate setAnnoDiPubblicazione(LocalDate annoDiPubblicazione) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int setNumeroCopie(int numeroCopie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public float setValore(float valore) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getTitolo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LocalDate getAnnoDiPubblicazione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getISBN() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNumeroCopie() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getListaAutori() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public float getValore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean presta() throws LibroNonDisponibileException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void restituisci() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
