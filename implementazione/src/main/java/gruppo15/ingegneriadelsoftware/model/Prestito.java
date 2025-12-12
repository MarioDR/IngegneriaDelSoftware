package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;
import java.util.regex.Pattern;

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

public class Prestito implements Searchable {
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

    public Prestito(Utente utenteAssegnatario, Libro libroPrestato, LocalDate dataPrevistaRestituzione) {
        this.utenteAssegnatario = utenteAssegnatario;
        this.libroPrestato = libroPrestato;
        this.dataPrevistaRestituzione = dataPrevistaRestituzione;
        this.dataInizioPrestito = LocalDate.now();
        this.ID = cont++;
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
        return this.utenteAssegnatario;
    }
    
    /**
     * Restituisce il libro che deve essere prestato.
     * 
     * @return Il libro prestato 
     */

    public Libro getLibroPrestato() {
        return this.libroPrestato;
    }
    
    /**
     * Restituisce l'ID del prestito
     * 
     * @return L'ID del prestito 
     */
    
    public int getID() {
        return this.ID;
    }

    /**
     * Restituisce la data prevista per la restituzione
     * 
     * @return La data prevista per la restituzione
     */
    
    public LocalDate getDataPrevistaRestituzione() {
        return this.dataPrevistaRestituzione;
    }

    /**
     * Restituisce la data di inizio del prestito.
     * 
     * @return La data di inizio del prestito
     */
    
    public LocalDate getDataInizioPrestito() {
        return this.dataInizioPrestito;
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
        return LocalDate.now().isAfter(this.dataPrevistaRestituzione);
    }
    
    /**
     * Restituisce il numero di giorni di ritardo.
     * 
     * @return Numero di giorni di ritardo (positivi se è in ritardo, negativi se è in anticipo) 
     */

    public long getGiorniDiRitardo() {
        return LocalDate.now().toEpochDay() - this.dataPrevistaRestituzione.toEpochDay();           
    }
    
    /**
     * Verifica se l'elemento ha almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param pattern la stringa usata per la ricerca
     * @return  {@code true} se l'oggetto corrisponde al pattern
     *          {@code false} in ogni altro caso
     */
    
    @Override
    public boolean containsPattern(String pattern) {
        // Possiamo delegare il risultato dell'elaborazione alla classe utente e alla classe libro
        return this.utenteAssegnatario.containsPattern(pattern) || this.libroPrestato.containsPattern(pattern);
    }
    
    /**
     * Controlla se un oggetto è uguale a questa istanza (due prestiti sono uguali se hanno lo stesso ID)
     * 
     * @return  {@code true} se i due prestiti hanno lo stesso ID
     *          {@code false} in tutti gli atri casi
     */
    
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        
        Prestito o2 = (Prestito)o;
        
        return o2.getID() == this.getID();
    }
    
    /**
     * Converte i dati in una stringa CSV. L'ordine è: tutti gli attributi dell'utente, tutti gli attributi del libro, data di inizio e data previta di fine.
     * 
     * @return Una stringa che contiene i dati dell'utente, quelli del libro e le due date di inizio e fine prevista
     */
    
    public String toCSV() {
        return this.utenteAssegnatario.toCSV() + "," + this.libroPrestato.toCSV() + "," + this.dataInizioPrestito + "," + this.dataPrevistaRestituzione;
    }
}
