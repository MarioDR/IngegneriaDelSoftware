package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @file Libro.java
 * @brief Rappresenta un libro registrato nel sistema con le sue caratteristiche
 * @invariant la data di pubblicazione deve essere precedente a quella al momento della registrazione (implementabile graficamente)
 * 
 * Le informazioni gestite includono i metadati bibliografici (Titolo, Autori, Anno, ISBN)
 * e lo stato dell'inventario (copie disponibili).
 * 
 * @author Gruppo15
 * @version 1.0
 */

public class Libro implements Searchable {

    ///Attributi fondamentali per la rappresentazione di un libro
    private String titolo;

    private List<String> listaAutori;

    private LocalDate dataDiPubblicazione;

    private final String ISBN;

    ///Attributi esterni alla rappresentazione con utilità significativa per il sistema e per le statistiche
    
    private int numeroCopie;

    private float valore;

    /**
     * Costruttore della classe libro.
     * 
     * @param titolo titolo libro
     * @param autori il nome di tutti gli autori che hanno lavorato al libro (separati da virgole)
     * @param dataDiPubblicazione un tipo LocalDate che serve a rappresentare la data di pubblicazione
     * @param ISBN è il codice identificativo del libro che serve a distinguere un libro da un altro
     * @param numeroCopie un intero che rappresenta il numero di copie di un titolo presente nel catalogo
     * @param valore rappresenta il valore in denaro del libro 
     * 
     */
    
    //NON SONO SICURO DEI TIPI DEL COSTRUTTORE
    public Libro(String titolo, String autori, LocalDate dataDiPubblicazione, String ISBN, int numeroCopie, float valore) {
        this.titolo = titolo;
        this.dataDiPubblicazione = dataDiPubblicazione;
        this.ISBN = ISBN;
        this.numeroCopie = numeroCopie;
        this.valore = valore;
        
        //questa istruzione converte una stringa CSV in una lista di stringhe, trimmando opportunamente anche gli spazi
        this.listaAutori = Arrays.stream(autori.split(",")).map(String::trim).collect(Collectors.toList()); 
    }
    
    // =========================================================
    // METODI SETTER
    // =========================================================
    
    /**
     * Imposta il titolo.
     * 
     * @param titolo titolo del libro
     *  
     */
    
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    
    /**
     * Imposta la data di pubblicazione.
     * 
     * @param dataDiPubblicazione la data di pubblicazione del libro
     *  
     */
    
    public void setDataDiPubblicazione(LocalDate dataDiPubblicazione) {
        this.dataDiPubblicazione = dataDiPubblicazione;
    }
    
    /**
     * Imposta il numero copie.
     * 
     * @param numeroCopie il numero di copie disponibili del libro
     *  
     */
    
    public void setNumeroCopie(int numeroCopie) {
        this.numeroCopie = numeroCopie;
    }
    
    /**
     * Imposta e riempe la lista degli autori con una nuova lista di autori separata da virgole
     * 
     * @param autori la nuova lista di autori separati da virgola
     *  
     */
    
    public void setListaAutori(String autori) {
        this.listaAutori = Arrays.stream(autori.split(",")).map(String::trim).collect(Collectors.toList());
    }
    
    /**
     * Imposta il valore del libro.
     * 
     * @param valore il valore commerciale del libro
     *  
     */
    
    public void setValore(float valore) {
        this.valore = valore;
    }
    
    // =========================================================
    // METODI GETTER
    // =========================================================
    
    /**
     * Restituisce il titolo del libro.
     * 
     * @return il titolo del libro
     */
    
    public String getTitolo() {
        return this.titolo;
    }
    
    /**
     * Restituisce la data di pubblicazione del libro.
     * 
     * @return la data di pubblicazione del libro
     * 
     */
    
    public LocalDate getDataDiPubblicazione() {
        return this.dataDiPubblicazione;
    }
    
    /**
     * Restituisce l'ISBN del libro.
     * 
     * @return l'ISBN del libro
     */
    
    public String getISBN() {
        return this.ISBN;
    }
     
    /** 
     * Restituisce il numero copie del libro.
     * 
     * @return il numero copie del libro
     */
    
    public int getNumeroCopie() {
        return this.numeroCopie;
    }
     
    /**
     * Restituisce la lista degli autori del libro.
     * 
     * @return la lista degli autori del libro
     */
    
    public List<String> getListaAutori() {
        return this.listaAutori;
    }
     
    /**
     * Restituisce il valore in denaro del libro.
     * 
     * @return il valore in denaro del libro
     */
       
    public float getValore() {
        return this.valore;
    }
    
    // =========================================================
    // ALTRI METODI
    // =========================================================
    
    /**
     * Incrementa di 1 il numero di copie del libro. (usato per le restituzioni)
     * 
     * @post numeroCopie sarà aumentato di 1
     */
    
    public void aggiungiCopia() {
        this.numeroCopie++;
    }

    /**
     * Incrementa di un certo numero il numero di copie del libro. (usato per le aggiunte al catalogo)
     * 
     * @pre Il numero di copie deve essere un numero intero positivo diverso da {@code null}
     * @post numeroCopie sarà aumentato di 'copie'
     * @param copie il numero di copie da aggiungere al totale
     */
    
    public void aggiungiCopie(int copie) {
        this.numeroCopie += copie;
    }
    
    /**
     * Rimuove una copia tra quelle disponibili.
     * 
     * @pre il numero di copie disponibili deve essere > 0
     * @post numeroCopie sara aumentato 
     */
    
    public void rimuoviCopia() {
        this.numeroCopie--;
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
        if (pattern == null || pattern.trim().isEmpty())
            return false;

        // Il pattern di ricerca non deve contenere spazi all'inizio e alla fine
        String patternRicerca = pattern.trim();

        // 1. Verifica Titolo (usiamo contains per la ricerca flessibile)
        if (this.getTitolo().toLowerCase().contains(patternRicerca.toLowerCase()))
            return true;

        // 2. Verifica ISBN (usiamo contains per ID)
        if (this.getISBN().toLowerCase().contains(patternRicerca.toLowerCase()))
            return true;

        // 3. Verifica Autori
        for (String autore : this.getListaAutori()) {
            if (autore.toLowerCase().contains(patternRicerca.toLowerCase()))
                return true;
        }

        return false;
    }
    
    /**
     * Controlla se un oggetto è uguale a questa istanza (due libri sono uguali se hanno lo stesso ISBN)
     * 
     * @return  {@code true} se i due libri hanno lo stesso ISBN
     *          {@code false} in tutti gli atri casi
     */
    
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        
        Libro o2 = (Libro)o;
        
        return o2.getISBN().equalsIgnoreCase(this.getISBN());
    }
    
    /**
     * Converte i dati in una stringa CSV. L'ordine è: titolo, ISBN, numeroCopie, valore e tutti gli autori
     * 
     * @return Una stringa CSV che contiene titolo, ISBN, numeroCopie, valore e autori
     */
    public String toCSV() {
        String s = this.titolo + "," + this.ISBN + "," + this.numeroCopie + "," + this.valore;
        
        for(String a : this.listaAutori)
            s += ("," + a);
        
        return s;
    }
}
