package gruppo15.ingegneriadelsoftware.model;

import javafx.collections.ObservableList;

/**
 * @file GestoreRestituzioni.java
 * @brief Gestore principale per la collezione delle restituzioni
 * 
 * La classe GestoreRestituzioni consente la collezione di più oggetti Restituzione
 * Viene implementata anche l'interfaccia Manager che gestisce le operazioni sulla lista
 * 
 * @see Restituzione
 * @see Manager
 * @author Gruppo15
 * @version 1.0
 */

public class GestoreRestituzioni implements Manager<Restituzione>{

    private ObservableList<Restituzione> listaRestituzioni;
    
    /**
     * Costruttore della classe GestoreRestituzioni
     * 
     * Inizializza la lista delle restituzioni come una observableList vuota
     */

    public GestoreRestituzioni() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Aggiunge una restituzione nella collezione
     * 
     * @pre L'oggetto non deve essere già presente nella lista
     * @post L'oggetto viene aggiunto nella lista
     * @param object
     * @return Lista con la nuova restituzione
     */
    
    @Override
    public Restituzione add(Restituzione object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Rimuove una restituzione dalla collezione
     * 
     * @pre L'oggetto deve essere presente nella lista
     * @post L'oggetto viene rimosso
     * @param object
     * @return Lista aggiornata
     */
    
    @Override
    public Restituzione remove(Restituzione object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Cerca una restituzione all'interno della collezione
     * @param object
     * @return L'oggetto Restituzione se è presente,
     * {@code Null} se non è nella lista
     */
    
    @Override
    public Restituzione search(Restituzione object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * METODO GETTER
     * 
     * Restituisce la lista con le restituzioni
     * 
     * @return Lista delle restituzioni aggiornata
     */
    
    @Override
    public ObservableList<Restituzione> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se una restituzione è presente nella lista
     * 
     * @param object
     * @return {@code true} se è presente,
     * {@code false} se non è presente
     */
    
    @Override
    public boolean contains(Restituzione object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ObservableList<Restituzione> startsWith(String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
