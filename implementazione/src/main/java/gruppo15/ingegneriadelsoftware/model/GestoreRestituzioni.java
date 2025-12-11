package gruppo15.ingegneriadelsoftware.model;

import java.util.List;

/**
 * @file GestoreRestituzioni.java
 * @brief Gestore principale per la collezione delle restituzioni
 * @invariant la collezione deve essere sempre in uno stato coerente con il file CSV
 * 
 * La classe GestoreRestituzioni consente la collezione di più oggetti Restituzione
 * Viene implementata anche l'interfaccia Manager che gestisce le operazioni sulla lista.
 * 
 * @see Restituzione
 * @see Manager
 * @author Gruppo15
 * @version 1.0
 */

public class GestoreRestituzioni implements Manager<Restituzione>{

    private List<Restituzione> listaRestituzioni;
    
    /**
     * Costruttore della classe GestoreRestituzioni
     * 
     * Inizializza la lista delle restituzioni come una observableList vuota
     */

    public GestoreRestituzioni() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Aggiunge una restituzione nella collezione gestita.
     * 
     * @pre La restituzione non deve essere presente nella collezione
     * @pre La restituzione deve rispettare tutte le caratteristiche lessicali dei suoi attributi
     * @post La restituzione viene inserita correttamente
     * @param object La restituzione da inserire
     * @return La restituzione aggiunta
     */
    
    @Override
    public List<Restituzione> add(Restituzione object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Rimuove una restituzione dalla collezione gestita.
     * 
     * @post La restituzione viene rimosso dalla collezione se presente
     * @param objectIl La restituzione da rimuovere
     * @return La restituzione rimossa
     */
    
    @Override
    public List<Restituzione> remove(Restituzione object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce la collezione nello stato attuale.
     * 
     * @return La lista aggiornata
     */
    
    @Override
    public List<Restituzione> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se la restituzione è presente nella collezione.
     * 
     * @param object la restituzione da cercare
     * @return  {@code true} se è presente
     *          {@code false} se non è presente
     */
    
    @Override
    public boolean contains(Restituzione object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Ritorna una lista di elementi che hanno almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param regex il prefisso usato per la ricerca
     * @return La lista di tutte le restituzioni trovate corrispondenti al pattern. Se nessuna restituzione viene trovata restituisce {@code Null}
     */
    
    @Override
    public List<Restituzione> containsString(String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
