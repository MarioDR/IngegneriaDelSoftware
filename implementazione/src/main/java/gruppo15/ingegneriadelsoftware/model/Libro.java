package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.LibroNonDisponibileException;
import java.time.LocalDate;
import java.util.List;
/**
 * @file Libro.java
 * @brief Rappresenta un libro registrato nel sistema con la gestione 
 * 
 * Le informazioni gestite includono i metadati bibliografici (Titolo, Autori, Anno, ISBN)
 * e lo stato dell'inventario (copie disponibili).
 * 
 * @author Gruppo15
 * @version 1.0
 */
public class Libro implements Checkable {

    ///Attributi fondamentali per la rappresentazione di un libro
    private String titolo;

    private List<String> listaAutori;

    private LocalDate annoDiPubblicazione;

    private final String ISBN;

    ///Attributi esterni alla rappresentazione con utilità significativa per il sistema e per le statistiche
    
    private int numeroCopie;

    private float valore;

    /**
     * @brief Costruttore della classe libro
     * 
     * @param titolo titolo libro
     * @param autori il nome di tutti gli autori che hanno lavorato al libro
     * @param annoDiPubblicazione un tipo LocalDate che serve a rappresentare la data di pubblicazione
     * @param ISBN è il codice identificativo del libro che serve a distinguere un libro da un altro
     * @param numeroCopie un intero che rappresenta il numero di copie di un titolo presente nel catalogo
     * @param valore rappresenta il valore in denaro del libro 
     * 
     */
    
    //NON SONO SICURO DEI TIPI DEL COSTRUTTORE
    public Libro(String titolo, String autori, LocalDate annoDiPubblicazione, String ISBN, int numeroCopie, float valore) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
        /**
     *  METODI SETTER
     * 
     * vengono impostati il titolo, annoDiPubblicazione, numeroCopie e il valore del libro.
     *  
     */
    public String setTitolo(String titolo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LocalDate setAnnoDiPubblicazione(LocalDate annoDiPubblicazione) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int setNumeroCopie(int numeroCopie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public float setValore(float valore) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     *  METODI GETTER
     * 
     * vengono restituiti il titolo, annoDiPubblicazione, ISBN, numeroCopie e il valore del libro.
     *  
     */
    public String getTitolo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public LocalDate getAnnoDiPubblicazione() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getISBN() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getNumeroCopie() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getListaAutori() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public float getValore() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * @brief metodo che presta un libro
     * 
     * serve a tracciare la disponibilità di un libro gestendo il numero copie
     * @pre numeroCopie deve essere > 0
     * @post numeroCopie sara diminuito 
     * @param[inout] numeroCopie
     * @return (@code true) se c'erano copie disponibili
     * (@code false) se il numeoro di copie disponibili era 0 
     * @throws LibroNonDisponibileExeption Se il numero di copie non è > 0
     * 
     */
    public boolean presta() throws LibroNonDisponibileException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief metodo che restituisce un libro
     * 
     * serve gestire il numeroCopie di un libro nella restituzione
     * @pre il libro deve essere stato prestato
     * @post numeroCopie sara aumentato 
     * @param[inout] numeroCopie
     *  
     * 
     */
    public void restituisci() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * @brief Verifica se i dati inseriti sono corretti
     * 
     * La validità viene definita da:
     * Campo titolo, campo autori e campo ISBN non devono essere vuoti;
     * L'ISBN deve essere univoco per ogni libro
     * Il numeroCopie deve essere >= 0
     * Il valore non può essere negativo
     * 
     * @return {code true}se le condizioni sono verificate,
     * {@code false} se le condizioni non sono inserite correttamente
     */
    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * Converte i dati in una stringa
     * 
     * @return Una stringa che contiene titolo, autori, ISBN e numeroCopie
     */
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
