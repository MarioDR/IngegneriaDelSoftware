package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;

/**
 * @file Restituzione.java
 * @brief Rappresenta la cessazione di un prestito
 * @invariant Ogni prestito in questa lista non potrà mai essere nella lista dei prestiti attivi e viceversa
 * 
 * La classe Restituzione rappresenta l'entità prestito quando viene cessato.
 * 
 * @see Prestito
 * @author Gruppo15
 * @version 1.0
 */

public class Restituzione {

    private Prestito prestitoDaRestituire;

    private final LocalDate dataEffettivaRestituzione;
    
    /**
     * Costruttore della classe Restituzione.
     * 
     * @param p L'oggetto prestito che viene restituito
     * @param dataEffettivaRestituzione La data in cui è stata effettuata la restituzione
     */

    public Restituzione(Prestito p, LocalDate dataEffettivaRestituzione) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // =========================================================
    // METODI GETTER
    // =========================================================
    
    /** 
     * Restituisce la data di restituzione del libro.
     * 
     * @return Data di restituzione
     */

    public LocalDate getDataRestituzione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce l'oggetto prestito.
     * 
     * @return Prestito 
     */

    public Prestito getPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // =========================================================
    // ALTRI METODI
    // =========================================================
    
    /**
     * Calcola il ritardo definitivo facendo un confronto con la data prevista di restituzione con quella attuale.
     * 
     * @return Numero di giorni di ritardo (positivi se in ritardo, negativi se in anticipo)
     */

    public int getRitardoDefinitivo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se un libro è stato restituito in ritardo o no.
     * 
     * @return  {@code true} se il libro è stato restituito in ritardo
     *          {@code false} se il libro non è stato restituito in ritardo
     */

    public boolean isRestituitoInRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Converte i dati in una stringa CSV.
     * 
     * @return Una stringa che contiene i dati del prestito restituito e la data effettiva di restituzione
     */

    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
