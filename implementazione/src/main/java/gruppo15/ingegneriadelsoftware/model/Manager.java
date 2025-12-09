package gruppo15.ingegneriadelsoftware.model;

import java.util.List;

/**
 * @file Manager.java
 * @brief Interfaccia generica per la gestione di collezioni di oggetti di tipo T
 * @param <T> Tipo dell'oggetto gestito dal Manager
 * 
 * Questa interfaccia definisce quali sono le operazioni che devono implementare tutte le classi
 * che gestiscono una collezione di oggetti.
 * 
 * @author Gruppo15
 * @version 1.0
 */

public interface Manager<T> {
    
    /**
     * Aggiunge un nuovo oggetto nella collezione gestita.
     * 
     * @pre L'oggetto non deve essere presente nella collezione
     * @pre l'oggetto deve rispettare tutte le caratteristiche lessicali dei suoi attributi
     * @post L'oggetto viene inserito correttamente
     * @param object l'oggetto da inserire
     * @return L'oggetto aggiunto
     */
    
    public T add(T object);
    
    /**
     * Rimuove un oggetto dalla collezione gestita.
     * 
     * @post L'oggetto viene rimosso dalla collezione se presente
     * @param object l'oggetto da rimuovere
     * @return L'oggetto T rimosso
     */
    
    public T remove(T object);
    
    /**
     * Cerca un oggetto nella collezione gestita.
     * 
     * @param object l'oggetto da cercare
     * @return L'oggetto T se è presente o {@code Null} se l'oggetto non è presente
     */
    
    public T search(T object);
    
    /**
     * Restituisce la collezione nello stato attuale.
     * 
     * @return La lista aggiornata
     */
    
    public List<T> getList();
    
    /**
     * Verifica se l'oggetto è presente nella collezione.
     * 
     * @param object l'oggetto da cercare
     * @return  {@code true} se è presente
     *          {@code false} se non è presente
     */
    
    public boolean contains(T object);
    
    /**
     * Ritorna una lista di elementi che hanno almeno uno degli attributi (quelli di tipo String) che iniziano con un prefisso.
     * 
     * @param regex il prefisso usato per la ricerca
     * @return La lista di tutti gli oggetti trovati corrispondenti al pattern. Se nessun oggetto viene trovato restituisce {@code Null}
     */
    
    public List<T> startsWith(String regex);
}
