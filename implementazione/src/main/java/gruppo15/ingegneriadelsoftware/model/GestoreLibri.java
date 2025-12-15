package gruppo15.ingegneriadelsoftware.model;

import java.util.ArrayList;
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

    private final List<Libro> listaLibri;
    
    private static GestoreLibri instance = null;
    
    /**
     * @brief Costruttore privato della classe GestoreLibri
     * 
     * Inizializza la lista dei prestiti come una lista vuota
     */
    
    private GestoreLibri() {
        this.listaLibri = new ArrayList<>();
    }
    
    /**
     * Crea una istanza di GestoreLibri. Un oggetto di questa classe potrà essere creato solo con questo metodo.
     * (Questo metodo ci assicura che può esistere solo un'istanza di questa classe).
     * 
     * @return La nuova istanza di GestoreLibri o l'istanza creata in precedenza
     */
    
    public static GestoreLibri getInstance(){
        if (instance == null){
            instance = new GestoreLibri();
        }
        return instance;
    }
    
    /**
     * Aggiunge un nuovo libro nella collezione gestita.
     * 
     * @pre Il libro non deve essere presente nella collezione
     * @pre Il libro deve rispettare tutte le caratteristiche lessicali dei suoi attributi
     * @post Il libro viene inserito correttamente
     * @param object Il libro da inserire
     * @return La lista aggiornata
     */
    
    @Override
    public List<Libro> add(Libro object) {
        if(object != null)
            this.listaLibri.add(object);
        
        return this.listaLibri;
    }
    
    /**
     * Rimuove un libro dalla collezione gestita.
     * 
     * @post Il libro viene rimosso dalla collezione se presente
     * @param object Il libro da rimuovere
     * @return La lista aggiornata
     */
    
    @Override
    public List<Libro> remove(Libro object){
        this.listaLibri.remove(object);
        return this.listaLibri;
    }

    /**
     * Restituisce la collezione nello stato attuale.
     * 
     * @return La lista aggiornata
     */
    
    @Override
    public List<Libro> getList(){
        return this.listaLibri;
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
        for(Libro l : this.listaLibri) {
            if(l.equals(object))
                return true;
        }
        
        return false;
    }
    
    /**
     * Ritorna una lista di elementi che hanno almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param pattern il prefisso usato per la ricerca
     * @return La lista di tutti i libri trovati corrispondenti al pattern
     */
    
    @Override
    public List<Libro> containsString(String pattern) {
        ArrayList a = new ArrayList<Libro>();
        
        for(Libro l : this.listaLibri) {
            if(l.containsPattern(pattern))
                a.add(l);
        }
        
        return a;
    }
}
