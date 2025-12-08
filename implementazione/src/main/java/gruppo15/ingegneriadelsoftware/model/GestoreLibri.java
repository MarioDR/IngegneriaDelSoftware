package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.LibroNonValidoException;
import javafx.collections.ObservableList;

/**
 * @file GestoreLibri.java
 * @brief Gestore principale per la collezione dei libri
 * 
 * La classe GestoreLibri consente la collezione di più oggetti di tipo Libro. Viene
 * implementata anche l'interfaccia Manager che gestisce le operazioni sulla lista
 * dei libri.
 * 
 * @see Libro
 * @see Manager
 * @author Gruppo15
 * @version 1.0
 */

public class GestoreLibri implements Manager<Libro> {

    private ObservableList<Libro> listaLibri;
    /**
     * @brief Costruttore della classe GestoreLibri
     * 
     * Inizializza la lista dei prestiti come una observableList vuota
     */
    public GestoreLibri() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
     /**
     * @brief Aggiunge un libro alla collezione
     * 
     * @pre L'oggetto deve essere valido 
     * @postL'oggetto viene aggiunto con successo alla collezione
     * @param object
     * @return Lista con il nuovo libro aggiunto
     * @throws LibroNonValidoExeption nel caso di errore
     * 
     */
    
    @Override
    public Libro add(Libro object) throws LibroNonValidoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * @brief Rimuove un prestito dalla collezione
     * 
     * @pre L'oggetto deve essere presente nella collezione
     * @post L'oggetto viene rimosso
     * @param object
     * @return Lista aggiornata
     */
    @Override
    public Libro remove(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Cerca un libro all'interno della collezione
     * @param object
     * @return L'oggetto Libro se è presente,
     * {@code Null} se il Libro non è nella lista
     */
    
    @Override
    public Libro search(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief METODO GET della lista
     * 
     * Restituisce la lista di libri.
     * 
     * @return Lista di libri aggiornata
     * 
     */
    
    @Override
    public ObservableList<Libro> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @brief Verifica se un Libro è presente nella collezione
     * 
     * @param object
     * @return {@code true} se il Libro è presente,
     * {@code false} se il Libro non è presente
     */
    
    @Override
    public boolean contains(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * @brief metodo per ricerca di libri nella collezione
     * 
     * @param object
     * @return una lista di libri((((((((((((((DA COMPLETARE))))))))))))))
     * 
     */
    @Override
    public ObservableList<Libro> startsWith(String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
