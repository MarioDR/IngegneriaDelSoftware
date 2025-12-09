package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;

/**
 * @file Prestito.java
 * @breaf Rappresenta un prestito di un libro ad un determinato utente
 * @invariant la data prevista per la restituzione deve essere sempre successiva a quella di inizio ma mai oltre i 6 mesi
 * @invariant l'ID deve essere un numero positivo e ogni istanza deve avere un ID  diverso da tutte le altre
 * 
 * La classe Prestito tiene traccia di ogni prestito effettuato agli utenti con
 * la data di inizio del prestito e quella di restituzione del libro. 
 * 
 * @see Utente
 * @see Libro
 * @author Gruppo15
 * @version 1.0
 */

public class Prestito {
    private static int cont = 1;
    
    private final int ID;
    
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
     * @param dataPrevistaRestituzione La data prevista di restituzione
     */

    public Prestito(Utente utente, Libro libro, LocalDate dataPrevistaRestituzione) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // =========================================================
    // METODI GETTER
    // =========================================================
    
    /**
     * Restituisce l'utente che ha richiesto il prestito.
     * 
     * @return L'utente assegnatario
     */

    public Utente getUtenteAssegnatario() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce il libro che deve essere prestato.
     * 
     * @return Il libro prestato 
     */

    public Libro getLibroPrestato() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce l'ID del prestito
     * 
     * @return L'ID del prestito 
     */
    
    public Libro getID() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Restituisce la data prevista per la restituzione
     * 
     * @return La data prevista per la restituzione
     */
    
    public LocalDate getDataPrevistaRestituzione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Restituisce la data di inizio del prestito.
     * 
     * @return La data di inizio del prestito
     */
    
    public LocalDate getDataInizioPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // =========================================================
    // ALTRI METODI
    // =========================================================
    
    /**
     * Verifica se l'utente è in ritardo con la restituzione del libro.
     * 
     * @return  {@code true} se l'utente è in ritardo,
     *          {@code false} se l'utente non è in ritardo
     */
    
    public boolean isInRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce il numero di giorni di ritardo.
     * 
     * @return Numero di giorni di ritardo (positivi se è in ritardo, negativi se è in anticipo) 
     */

    public int getGiorniDiRitardo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Converte i dati in una stringa CSV.
     * 
     * @return Una stringa che contiene i dati dell'utente, quelli del libro e le due date di inizio e fine prevista
     */
    
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
