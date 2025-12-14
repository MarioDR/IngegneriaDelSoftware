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

public class Restituzione implements Searchable {

    private final Prestito prestitoDaRestituire;

    private final LocalDate dataEffettivaRestituzione;
    
    /**
     * Costruttore della classe Restituzione.
     * 
     * @param p L'oggetto prestito che viene restituito
     */

    public Restituzione(Prestito p) {
        this.prestitoDaRestituire = p;
        this.dataEffettivaRestituzione = LocalDate.now();
    }
    
    /**
     * Secondo costruttore della classe Restituzione. (viene usato in fase di ricostruzione da un file CSV)
     * 
     * @param p L'oggetto prestito che viene restituito
     * @param dataEffettivaRestituzione La data in cui è stata effettuata la restituzione
     */

    public Restituzione(LocalDate dataEffettivaRestituzione,Prestito p) {
        this.prestitoDaRestituire = p;
        this.dataEffettivaRestituzione = dataEffettivaRestituzione;
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
        return this.dataEffettivaRestituzione;
    }
    
    /**
     * Restituisce l'oggetto prestito.
     * 
     * @return Prestito 
     */

    public Prestito getPrestitoDaRestituire() {
        return this.prestitoDaRestituire;
    }
    
    // =========================================================
    // ALTRI METODI
    // =========================================================
    
    /**
     * Calcola il ritardo definitivo facendo un confronto con la data prevista di restituzione con quella attuale.
     * 
     * @return Numero di giorni di ritardo (positivi se in ritardo, negativi se in anticipo)
     */

    public long getRitardoDefinitivo() {
        return this.dataEffettivaRestituzione.toEpochDay() - this.prestitoDaRestituire.getDataPrevistaRestituzione().toEpochDay();
    }
    
    /**
     * Verifica se un libro è stato restituito in ritardo o no.
     * 
     * @return  {@code true} se il libro è stato restituito in ritardo
     *          {@code false} se il libro non è stato restituito in ritardo
     */

    public boolean isRestituitoInRitardo() {
        return this.dataEffettivaRestituzione.isAfter(this.prestitoDaRestituire.getDataPrevistaRestituzione());
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
        // Possiamo rimandare il risultato di questa operazione alla containsPattern del prestito da restituire
        return this.prestitoDaRestituire.containsPattern(pattern);
    }
    
    /**
     * Controlla se un oggetto è uguale a questa istanza (due restituzioni sono uguali se i due prestiti restituiti hanno lo stesso ID)
     * 
     * @return  {@code true} se i due prestiti da restituire hanno lo stesso ID
     *          {@code false} in tutti gli atri casi
     */
    
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        
        Restituzione o2 = (Restituzione)o;
        
        return o2.prestitoDaRestituire.equals(this.getPrestitoDaRestituire());
    }
    
    /**
     * Converte i dati in una stringa CSV. L'ordine è: tutti gli attributi del prestito e la data effettiva di restituzione
     * 
     * @return Una stringa che contiene i dati del prestito restituito e la data effettiva di restituzione
     */
    
    public String toCSV() {
        return this.dataEffettivaRestituzione + "," + this.prestitoDaRestituire.toCSV();
    }
}
