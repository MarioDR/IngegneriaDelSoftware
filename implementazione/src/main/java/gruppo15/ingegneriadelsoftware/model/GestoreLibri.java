package gruppo15.ingegneriadelsoftware.model;

import java.util.List;

/**
 * @file GestoreLibri.java
 * @brief Gestore principale per la collezione dei libri
 * @invariant la collezione deve essere sempre in uno stato coerente con il file CSV
 * 
 * La classe GestoreLibri consente la collezione di più oggetti di tipo Libro. Viene
 * implementata anche l'interfaccia Manager che gestisce le operazioni sulla lista dei libri.
 * 
 * @see Libro
 * @see Manager
 * @author Gruppo15
 * @version 1.0
 */

public class GestoreLibri implements Manager<Libro> {

    private List<Libro> listaLibri;
    /**
     * @brief Costruttore della classe GestoreLibri
     * 
     * Inizializza la lista dei prestiti come una observableList vuota
     */
    public GestoreLibri() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    /**
     * Aggiunge un nuovo libro nella collezione gestita.
     * 
     * @pre Il libro non deve essere presente nella collezione
     * @pre Il libro deve rispettare tutte le caratteristiche lessicali dei suoi attributi
     * @post Il libro viene inserito correttamente
     * @param object Il libro da inserire
     * @return Il libro aggiunto
     */
    
    @Override
    public Libro add(Libro object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Rimuove un libro dalla collezione gestita.
     * 
     * @post Il libro viene rimosso dalla collezione se presente
     * @param objectIl Il libro da rimuovere
     * @return Il libro rimosso
     */
    
    @Override
    public Libro remove(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Cerca un libro nella collezione gestita.
     * 
     * @param object Il libro da cercare
     * @return Il libro se è presente o {@code Null} se Il libro non è presente
     */
    
    @Override
    public Libro search(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce la collezione nello stato attuale.
     * 
     * @return La lista aggiornata
     */
    
    @Override
    public List<Libro> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se il libro è presente nella collezione.
     * 
     * @param object il libro da cercare
     * @return  {@code true} se è presente
     *          {@code false} se non è presente
     */
    
    @Override
    public boolean contains(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Ritorna una lista di elementi che hanno almeno uno degli attributi (quelli di tipo String) che iniziano con un prefisso.
     * 
     * @param regex il prefisso usato per la ricerca
     * @return La lista di tutti i libri trovati corrispondenti al pattern. Se nessun libro viene trovato restituisce {@code Null}
     */
    
    @Override
    public List<Libro> startsWith(String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
