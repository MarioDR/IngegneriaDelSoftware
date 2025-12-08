package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;

/**
 * @file Prestito.java
 * @breaf Rappresenta un prestito di un libro ad un determinato utente
 * 
 * La classe Prestito tiene traccia di ogni prestito effettuato agli utenti con
 * la data di inizio del prestito e quella di restituzione del libro. Viene
 * implementata la classe Checkable che verifica se il prestito è idoneo
 *
 * @see Checkable
 * @see Utente
 * @see Libro
 * @author Gruppo15
 * @version 1.0
 */

public class Prestito implements Checkable {

    private final Utente utenteAssegnatario;

    private final Libro libroPrestato;

    private final LocalDate dataPrevistaRestituzione;

    private final LocalDate dataInizioPrestito;
    
    /**
     * Costruttore della classe Prestito
     * 
     * Inizializza un nuovo prestito con la data di inizio uguale a quella attuale
     * e la data di restituzione
     * 
     * @param utente L'utente a cui è stato segnato il prestito
     * @param libro Libro dato in prestito
     * @param anno Anno in cui è stato prestato
     * @param mese Mese in cui è stato prestato
     * @param dayOfMonth Giorno in cui è stato prestato
     */

    public Prestito(Utente utente, Libro libro, int anno, int mese, int dayOfMonth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * METODO GETTER
     * 
     * Restituisce l'utente che ha richiesto il prestito
     * 
     * @return Utente
     */

    public Utente getUtenteAssegnatario() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * METODO GETTER
     * 
     * Restituisce il libro che deve essere prestato
     * 
     * @return Libro 
     */

    public Libro getLibroPrestato() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se l'utente è in ritardo con la restituzione del libro
     * 
     * @return {@code true} se l'utente è in ritardo,
     * {@code false} se l'utente non è in ritardo
     */

    public boolean isInRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * METODO GETTER
     * 
     * Restituisce il numero di giorni di ritardo
     * 
     * @return Numero di giorni
     */

    public int getGiorniDiRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se il prestito è valido
     * 
     * @pre L'utente non deve avere più di tre prestiti e devono essere presenti copie
     * del libro
     * @post Il libro viene dato in prestito
     * @return {@code true} se vengono rispettate le condizioni,
     * {@code false} se non vengono rispettate le condizioni
     */
    
    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Converte i dati in una stringa
     * 
     * @return Una stringa che contiene i dati dell'utente e quelli del libro
     */
    
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
