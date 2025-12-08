package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;

/**
 * @file Restituzione.java
 * @brief Registra la data di restituzione del libro
 * 
 * La classe Restituzione salva la data in cui è stato restituito un libro
 * 
 * @see Prestito
 * @author Gruppo15
 * @version 1.0
 */

public class Restituzione {

    private Prestito prestitoDaRestituire;

    private final LocalDate dataEffettivaRestituzione;
    
    /**
     * Costruttore della classe Restituzione
     * 
     * @param p L'oggetto prestito che viene restituito
     * @param year Anno in cui viene restituito
     * @param month Mese in cui viene restituito
     * @param dayOfMonth Giorno in cui viene restituito
     */

    public Restituzione(Prestito p, int year, int month, int dayOfMonth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Calcola il ritardo definitivo facendo un confronto con la data di inizio 
     * prestito con quella attuale
     * 
     * @return Numero di giorni di ritardo
     */

    public int getRitardoDefinitivo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se un libro è stato restituito in ritardo o no
     * 
     * @return {@code true} se il libro è stato restituito in ritardo
     * {@code false} se il libro non è stato restituito in ritardo
     */

    public boolean isRestituitoInRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * METODO GETTER
     * 
     * Restituisce la data di restituzione del libro
     * 
     * @return Data di restituzione
     */

    public LocalDate getDataRestituzione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * METODO GETTER
     * 
     * Restituisce l'oggetto prestito
     * 
     * @return Prestito 
     */

    public Prestito getPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Converte i dati in una stringa
     * 
     * @return Una stringa che contiene i dati della restituzione del prestito
     */

    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
