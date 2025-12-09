package gruppo15.ingegneriadelsoftware.model;

import java.time.LocalDate;
import java.util.List;

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

public class Libro {

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
        throw new UnsupportedOperationException("Not supported yet.");
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
    
    public String setTitolo(String titolo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Imposta la data di pubblicazione.
     * 
     * @param dataDiPubblicazione la data di pubblicazione del libro
     *  
     */
    
    public LocalDate setDataDiPubblicazione(LocalDate dataDiPubblicazione) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Imposta il numero copie.
     * 
     * @param numeroCopie il numero di copie disponibili del libro
     *  
     */
    
    public int setNumeroCopie(int numeroCopie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Imposta il valore del libro.
     * 
     * @param valore il valore commerciale del libro
     *  
     */
    
    public float setValore(float valore) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce la data di pubblicazione del libro.
     * 
     * @return la data di pubblicazione del libro
     * 
     */
    
    public LocalDate getAnnoDiPubblicazione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce l'ISBN del libro.
     * 
     * @return l'ISBN del libro
     */
    
    public String getISBN() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     
    /** 
     * Restituisce il numero copie del libro.
     * 
     * @return il numero copie del libro
     */
    
    public int getNumeroCopie() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     
    /**
     * Restituisce la lista degli autori del libro.
     * 
     * @return la lista degli autori del libro
     */
    
    public List<String> getListaAutori() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
     
    /**
     * Restituisce il valore in denaro del libro.
     * 
     * @return il valore in denaro del libro
     */
       
    public float getValore() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Incrementa di un certo numero il numero di copie del libro. (usato per le aggiunte al catalogo)
     * 
     * @post numeroCopie sarà aumentato di 'copie'
     * @param copie il numero di copie da aggiungere al totale
     */
    
    public void aggiungiCopie(int copie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Rimuove una copia tra quelle disponibili.
     * 
     * @pre il numero di copie disponibili deve essere > 0
     * @post numeroCopie sara aumentato 
     */
    
    public void rimuoviCopia() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Converte i dati in una stringa CSV.
     * 
     * @return Una stringa CSV che contiene titolo, autori, ISBN, numeroCopie e valore
     */
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
