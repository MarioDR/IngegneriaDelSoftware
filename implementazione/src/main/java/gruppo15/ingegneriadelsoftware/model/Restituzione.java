package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;

public class Restituzione {

    private Prestito prestitoDaRestituire;

    private final LocalDate dataEffettivaRestituzione;

    public Restituzione(Prestito p, int year, int month, int dayOfMonth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getRitardoDefinitivo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isRestituitoInRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LocalDate getDataRestituzione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Prestito getPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
