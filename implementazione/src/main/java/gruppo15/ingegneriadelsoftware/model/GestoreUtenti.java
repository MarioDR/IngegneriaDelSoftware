package gruppo15.ingegneriadelsoftware.model;

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

    /**
     * Costruttore della classe GestoreUtenti
     * 
     * Inizializza la lista degli utenti come una observableList vuota
     */
    
    public GestoreUtenti() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce la lista di tutti gli utenti.
     * 
     * @return La lista degli utenti
     */
    
    @Override
    public List<Utente> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Ritorna una lista di elementi che hanno almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param regex il prefisso usato per la ricerca
     * @return La lista di tutti gli utenti trovati corrispondenti al pattern. Se nessun oggetto viene trovato restituisce {@code Null}
     */
    
    @Override
    public List<Utente> containsString(String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
