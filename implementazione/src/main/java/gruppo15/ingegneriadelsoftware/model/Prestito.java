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

public class Prestito implements Searchable {
    private static int cont = 1;
    
    private final int ID;
    
    private Utente utenteAssegnatario = null;

    private Libro libroPrestato = null;

    private LocalDate dataPrevistaRestituzione;

    private LocalDate dataInizioPrestito;
    
    /**
     * Costruttore della classe Prestito
     * 
     * Inizializza un nuovo prestito con la data di inizio uguale a quella attuale
     * e la data di restituzione.
     * 
     * @pre matricola e ISBN devono essere validi
     * @param matricolaUtenteAssegnatario La matricola dell'utente a cui è stato assegnato il prestito
     * @param ISBNlibroPrestato L'ISBN del libro dato in prestito
     * @param dataPrevistaRestituzione La data prevista di restituzione
     */

    public Prestito(String matricolaUtenteAssegnatario, String ISBNlibroPrestato, LocalDate dataPrevistaRestituzione) {
        //ricerchiamo l'utente e il libro corrispondente
        for(Utente u : GestoreUtenti.getInstance().getList()) {
            if(u.getMatricola().equals(matricolaUtenteAssegnatario)) {
                this.utenteAssegnatario = u;
                break;
            }
        }
        
        for(Libro l : GestoreLibri.getInstance().getList()) {
            if(l.getISBN().equals(ISBNlibroPrestato)) {
                this.libroPrestato = l;
                break;
            }
        }
        
        this.dataPrevistaRestituzione = dataPrevistaRestituzione;
        this.dataInizioPrestito = LocalDate.now();
        this.ID = cont;
        cont = cont + 1;
    }
    
    /**
     * Seconodo ostruttore della classe Prestito (Viene usato in fase di ricostruzione dell'oggetto da file CSV).
     * Inizializza un nuovo prestito esplicitando tutti i dettagli.
     * 
     * @param ID L'ID dell'utente
     * @param matricolaUtenteAssegnatario La matricola dell'utente a cui è stato assegnato il prestito
     * @param ISBNlibroPrestato L'ISBN del libro dato in prestito
     * @param dataInizioPrestito La data di inizio del prestito
     * @param dataPrevistaRestituzione La data prevista di restituzione
     */
    
    public Prestito(int ID, String matricolaUtenteAssegnatario, String ISBNlibroPrestato, LocalDate dataInizioPrestito, LocalDate dataPrevistaRestituzione) {
        //ricerchiamo l'utente e il libro corrispondente
        for(Utente u : GestoreUtenti.getInstance().getList()) {
            if(u.getMatricola().equals(matricolaUtenteAssegnatario)) {
                this.utenteAssegnatario = u;
                break;
            }
        }
        
        for(Libro l : GestoreLibri.getInstance().getList()) {
            if(l.getISBN().equals(ISBNlibroPrestato)) {
                this.libroPrestato = l;
                break;
            }
        }
        
        this.dataPrevistaRestituzione = dataPrevistaRestituzione;
        this.dataInizioPrestito = dataInizioPrestito;
        this.ID = ID;
        // Alla fine del caricamento, cont assumerà un valore consistente
        cont = ID + 1;
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
     * Controlla se un oggetto è uguale a questa istanza. (due prestiti sono uguali se hanno lo stesso ID)
     * 
     * @param o L'oggetto con cui fare il confronto
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
        return this.ID + "," + this.dataInizioPrestito + "," + this.dataPrevistaRestituzione + "," + this.utenteAssegnatario.getMatricola() + "," + this.libroPrestato.getISBN();
    }
}
