package gruppo15.ingegneriadelsoftware.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        this.listaRestituzioni = new ArrayList<>();
    }
    
    /**
     * Aggiunge una restituzione nella collezione gestita.
     * 
     * @pre La restituzione non deve essere presente nella collezione
     * @pre La restituzione deve rispettare tutte le caratteristiche lessicali dei suoi attributi
     * @post La restituzione viene inserita correttamente
     * @param object La restituzione da inserire
     * @return La lista aggiornata
     */
    
    @Override
    public List<Restituzione> add(Restituzione object) {
        if(object != null)
            this.listaRestituzioni.add(object);
        
        return this.listaRestituzioni;
    }
    
    /**
     * Rimuove una restituzione dalla collezione gestita.
     * 
     * @post La restituzione viene rimosso dalla collezione se presente
     * @param object Il La restituzione da rimuovere
     * @return La lista aggiornata
     */
    
    @Override
    public List<Restituzione> remove(Restituzione object){
        this.listaRestituzioni.remove(object);
        return this.listaRestituzioni;
    }
    
    /**
     * Restituisce la collezione nello stato attuale.
     * 
     * @return La lista aggiornata
     */
    
    @Override
    public List<Restituzione> getList(){
        return this.listaRestituzioni;
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
        for(Restituzione r : this.listaRestituzioni) {
            if(r.equals(object))
                return true;
        }
        
        return false;
    }
    
    /**
     * Ritorna una lista di elementi che hanno almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param pattern il prefisso usato per la ricerca
     * @return La lista di tutte le restituzioni trovate corrispondenti al pattern
     */
    
    @Override
    public List<Restituzione> containsString(String pattern) {
        ArrayList a = new ArrayList<Restituzione>();

        for(Restituzione r : this.listaRestituzioni) {
            if(r.getPrestitoDaRestituire().containsPattern(pattern))
                a.add(r);
        }
        
        return a;
    }
}
