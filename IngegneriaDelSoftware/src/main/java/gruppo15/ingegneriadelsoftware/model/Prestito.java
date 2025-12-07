package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;

public class Prestito {

    private final Utente utenteAssegnatario;

    private final Libro libroPrestato;

    private final LocalDate dataPrevistaRestituzione;

    private final LocalDate dataInizioPrestito;

    public Prestito(Utente utente, Libro libro, int anno, int mese, int dayOfMonth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Utente getUtenteAssegnatario() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Libro getLibroPrestato() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isInRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getGiorniDiRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
