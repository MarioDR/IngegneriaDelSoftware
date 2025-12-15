package gruppo15.ingegneriadelsoftware.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @file GestoreUtenti.java
 * @brief Gestore principale per la collezione degli utenti
 * @invariant la collezione deve essere sempre in uno stato coerente con il file CSV
 * 
 * La classe GestoreUtenti consente la collezione di più oggetti utente. Viene
 * implementata anche l'interfaccia Manager per gestire le operazioni sulla lista degli utenti.
 * 
 * @see Utente
 * @see Manager
 * @author Gruppo15
 * @version 1.0
 */

public class GestoreUtenti implements Manager<Utente> {

    /// Lista degli utenti
    
    private List<Utente> listaUtenti;
    
    private static GestoreUtenti instance = null;

    /**
     * Costruttore privato della classe GestoreUtenti
     * 
     * Inizializza la lista degli utenti come una lista vuota
     */
    
    private GestoreUtenti() {
        this.listaUtenti = new ArrayList<>();
    }
    
    /**
     * Crea una istanza di GestoreUtenti. Un oggetto di questa classe potrà essere creato solo con questo metodo.
     * (Questo metodo ci assicura che può esistere solo un'istanza di questa classe).
     * 
     * @return La nuova istanza di GestoreUtenti o l'istanza creata in precedenza
     */
    
    public static GestoreUtenti getInstance(){
        if (instance == null){
            instance = new GestoreUtenti();
        }
        return instance;
    }
    
    /**
     * Aggiunge un nuovo utente nella collezione.
     * 
     * @pre L'utente non deve già esistere nella lista
     * @pre L'utente deve avere tutti gli attributi sintatticamente corretti
     * @post L'utente viene inserito correttamente
     * @param object L'utente da inserire
     * @return La lista aggiornata degli utenti
     */
    
    @Override
    public List<Utente> add(Utente object) {
        if(object != null)
            this.listaUtenti.add(object);
        
        return this.listaUtenti;
    }
    
    /**
     * Rimuove un utente dalla collezione.
     * 
     * @post L'utente viene rimosso se presente
     * @param object L'utente da rimuovere.
     * @return La lista aggiornata degli utenti
     */
    
    @Override
    public List<Utente> remove(Utente object){
        this.listaUtenti.remove(object);
        return this.listaUtenti;
    }
    
    /**
     * Restituisce la lista di tutti gli utenti.
     * 
     * @return La lista degli utenti
     */
    
    @Override
    public List<Utente> getList(){
        return this.listaUtenti;
    }
    
    /**
     * Verifica se l'utente è già presente nella lista.
     * 
     * @param object L'utente da cercare
     * @return  {@code true} se è già presente,
     *          {@code false} se non è presente
     */
    
    @Override
    public boolean contains(Utente object){
        for(Utente u : this.listaUtenti) {
            if(u.equals(object))
                return true;
        }
        
        return false;
    }
    
    /**
     * Ritorna una lista di elementi che hanno almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param pattern il prefisso usato per la ricerca
     * @return La lista di tutti gli utenti trovati corrispondenti al pattern
     */
    
    @Override
    public List<Utente> containsString(String pattern) {
        ArrayList a = new ArrayList<Libro>();
        
        for(Utente u : this.listaUtenti) {
            if(u.containsPattern(pattern))
                a.add(u);
        }
        
        return a;
    }
}
