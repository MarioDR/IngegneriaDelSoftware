package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.PrestitoNonValidoException;
import javafx.collections.ObservableList;

/**
 * @file GestorePrestiti.java
 * @brief Gestore principale per la collezione dei prestiti
 * 
 * La classe GestorePrestiti consente la collezione di più oggetti prestito. Viene
 * implementata anche l'interfaccia Manager che gestisce le operazioni sulla lista
 * dei prestiti
 * 
 * @see Prestito
 * @see Manager
 * @author Gruppo15
 * @version 1.0
 */

public class GestorePrestiti implements Manager<Prestito> {

    private ObservableList<Prestito> listaPrestitiAttivi;
    
    /**
     * Costruttore della classe GestorePrestiti
     * 
     * Inizializza la lista dei prestiti come una observableList vuota
     */

    public GestorePrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Aggiunge un prestito nella collezione
     * 
     * @pre L'oggetto non deve essere già presente nella lista
     * @post L'oggetto viene aggiunto nella lista
     * @param object
     * @return Lista con il nuovo prestito
     * @throws PrestitoNonValidoException se il prestito è già esistente nella lista
     */
    
    @Override
    public Prestito add(Prestito object) throws PrestitoNonValidoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Rimuove un prestito dalla collezione
     * 
     * @pre L'oggetto deve essere presente nella lista
     * @post L'oggetto viene rimosso
     * @param object
     * @return Lista aggiornata
     */
    
    @Override
    public Prestito remove(Prestito object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Cerca un prestito all'interno della collezione
     * @param object
     * @return L'oggetto Prestito se è presente,
     * {@code Null} se il prestito non è nella lista
     */
    
    @Override
    public Prestito search(Prestito object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * METODO GETTER
     * 
     * Restituisce la lista dei prestiti
     * 
     * @return Lista dei prestiti aggiornata
     */
    
    @Override
    public ObservableList<Prestito> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se un prestito è presente nella lista
     * 
     * @param object
     * @return {@code true} se il prestito è presente,
     * {@code false} se il prestito non è presente
     */
    
    @Override
    public boolean contains(Prestito object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ObservableList<Prestito> startsWith(String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
