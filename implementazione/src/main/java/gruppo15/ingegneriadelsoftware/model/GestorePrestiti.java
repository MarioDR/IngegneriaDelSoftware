package gruppo15.ingegneriadelsoftware.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @file GestorePrestiti.java
 * @brief Gestore principale per la collezione dei prestiti
 * @invariant la collezione deve essere sempre in uno stato coerente con il file CSV
 * 
 * La classe GestorePrestiti consente la collezione di più oggetti prestito. Viene
 * implementata anche l'interfaccia Manager che gestisce le operazioni sulla lista dei prestiti.
 * 
 * @see Prestito
 * @see Manager
 * @author Gruppo15
 * @version 1.0
 */

public class GestorePrestiti implements Manager<Prestito> {

    private List<Prestito> listaPrestitiAttivi;
    
    private static GestorePrestiti instance = null;
    
    /**
     * Costruttore privato della classe GestorePrestiti
     * 
     * Inizializza la lista dei prestiti come una lista vuota
     */

    private GestorePrestiti() {
        this.listaPrestitiAttivi = new ArrayList<>();
    }
    
    /**
     * Crea una istanza di GestorePrestiti. Un oggetto di questa classe potrà essere creato solo con questo metodo.
     * (Questo metodo ci assicura che può esistere solo un'istanza di questa classe).
     * 
     * @return La nuova istanza di GestorePrestiti o l'istanza creata in precedenza
     */
    
    public static GestorePrestiti getInstance(){
        if (instance == null){
            instance = new GestorePrestiti();
        }
        return instance;
    }
    
    /**
     * Aggiunge un nuovo prestito nella collezione gestita.
     * 
     * @pre Il prestito non deve essere presente nella collezione
     * @pre Il prestito deve rispettare tutte le caratteristiche lessicali dei suoi attributi
     * @post Il prestito viene inserito correttamente
     * @param object Il prestito da inserire
     * @return La lista aggiornata
     */
    
    @Override
    public List<Prestito> add(Prestito object) {
        if(object != null)
            this.listaPrestitiAttivi.add(object);
        
        return this.listaPrestitiAttivi;
    }
    
    /**
     * Rimuove un prestito dalla collezione gestita.
     * 
     * @post Il prestito viene rimosso dalla collezione se presente
     * @param object Il prestito da rimuovere
     * @return La lista aggiornata
     */
    
    @Override
    public List<Prestito> remove(Prestito object){
        this.listaPrestitiAttivi.remove(object);
        return this.listaPrestitiAttivi;
    }
    
    /**
     * Restituisce la collezione nello stato attuale.
     * 
     * @return La lista aggiornata
     */
    
    @Override
    public List<Prestito> getList(){
        return this.listaPrestitiAttivi;
    }
    
    /**
     * Verifica se il prestito è presente nella collezione.
     * 
     * @param object il prestito da cercare
     * @return  {@code true} se è presente
     *          {@code false} se non è presente
     */
    
    @Override
    public boolean contains(Prestito object){
        for(Prestito p : this.listaPrestitiAttivi) {
            if(p.equals(object))
                return true;
        }
        
        return false;
    }
    
    /**
     * Ritorna una lista di elementi che hanno almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param pattern il prefisso usato per la ricerca
     * @return La lista di tutti i prestiti trovati corrispondenti al pattern
     */
    
    @Override
    public List<Prestito> containsString(String pattern) {
        ArrayList a = new ArrayList<Libro>();
        
        for(Prestito p : this.listaPrestitiAttivi) {
            if(p.containsPattern(pattern))
                a.add(p);
        }
        
        return a;
    }
}
