package gruppo15.ingegneriadelsoftware.model.exceptions;

/**
 * @file LibroNonDisponibileException.java
 * @brief Eccezione sollevata quando si tenta di prestare un libro
 * che non è disponibile
 * 
 * @author Gruppo15
 * @version 1.0
 */

public class LibroNonDisponibileException extends Exception {

    /**
     * Costruttore di default che crea una nuova LibroNonDisponibileException
     * con un messaggio di errore
     */
    
    public LibroNonDisponibileException() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Costruttore che accetta un messaggio specifico
     * 
     * @param[inout] msg Il messaggio che descrive l'errore del libro non disponibile
     */

    public LibroNonDisponibileException(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
