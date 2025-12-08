package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.ValidationException;
import javafx.collections.ObservableList;

/**
 * @file Manager.java
 * @brief Interfaccia generica per la gestione di collezioni di oggetti di tipo T
 * @param <T> Tipo dell'oggetto gestito dal Manager
 * 
 * @author Gruppo15
 * @version 1.0
 */

public interface Manager<T> {
    
    /**
     * Aggiunge un nuovo oggetto nella collezione gestita
     * 
     * @pre L'oggetto non deve essere presente nella collezione
     * @post L'oggetto viene inserito
     * @param object
     * @return L'oggetto aggiunto T
     * @throws ValidationException se l'oggetto è già presente nella lista
     */
    
    public T add(T object) throws ValidationException;
    
    /**
     * Rimuove un oggetto da una collezione gestita
     * 
     * @pre L'oggetto deve essere presente nella collezione
     * @post L'oggetto viene rimosso dalla collezione
     * @param object
     * @return L'oggetto T rimosso
     */
    
    public T remove(T object);
    
    /**
     * Cerca un oggetto nella collezione gestita
     * 
     * @param object
     * @return L'oggetto T se è presente o {@code Null} se l'oggetto
     * non è presente
     */
    
    public T search(T object);
    
    /**
     * Restituisce la lista aggiornata
     * 
     * @return Lista aggiornata
     */
    
    public ObservableList<T> getList();
    
    /**
     * Verifica se l'oggetto è presente nella collezione
     * 
     * @param object
     * @return {@code true} se è presente
     * {@code false} se non è presente
     */
    
    public boolean contains(T object);
    
    public ObservableList<T> startsWith(String regex);
}
