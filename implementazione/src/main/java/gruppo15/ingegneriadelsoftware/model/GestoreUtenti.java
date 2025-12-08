package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.UtenteNonValidoException;
import javafx.collections.ObservableList;

/**
 * @file GestoreUtenti.java
 * @brief Gestore principale per la collezione degli utenti
 * 
 * La classe GestoreUtenti consente la collezione di più oggetti utente. Viene
 * implementata anche l'interfaccia Manager che gestisce le operazioni sulla lista 
 * degli utenti
 * 
 * @see Utente
 * @see Manager
 * @author Gruppo15
 * @version 1.0
 */

public class GestoreUtenti implements Manager<Utente> {

    /// Lista degli utenti
    
    private ObservableList<Utente> listaUtenti;

    /**
     * Costruttore della classe GestoreUtenti
     * 
     * Inizializza la lista degli utenti come una observableList vuota
     */
    
    public GestoreUtenti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Aggiunge un nuovo utente nella collezione
     * 
     * @pre L'utente non deve già esistere nella lista
     * @post L'utente viene inserito
     * @param object
     * @return observableList aggiornata
     * @throws UtenteNonValidoException se l'utente è gia inserito o non è valido
     */
    
    @Override
    public Utente add(Utente object) throws UtenteNonValidoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Rimuove un utente dalla collezione
     * 
     * @pre L'utente deve essere già presente nella lista
     * @post L'utente viene rimosso
     * @param object
     * @return observableList aggiornata
     */
    
    @Override
    public Utente remove(Utente object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Cerca un utente specifico nella collezione
     * 
     * @param object
     * @return {@code Object} se l'utente è stato trovato,
     * {@code Null} se l'utente non esiste
     */
    
    @Override
    public Utente search(Utente object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * METODO GETTER
     * 
     * Restituisce la lista di tutti gli utenti
     * 
     * @return Lista utenti
     */
    
    @Override
    public ObservableList<Utente> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se l'utente è già presente nella lista
     * 
     * @param object
     * @return {@code true} se è già presente,
     * {@code false} se non è presente
     */
    
    @Override
    public boolean contains(Utente object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ObservableList<Utente> startsWith(String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
