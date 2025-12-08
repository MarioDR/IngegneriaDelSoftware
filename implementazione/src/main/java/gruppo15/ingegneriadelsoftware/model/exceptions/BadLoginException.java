package gruppo15.ingegneriadelsoftware.model.exceptions;

/**
 * @file BadLoginException.java
 * @brief Eccezione sollevata in caso di errore durante il login
 * 
 * Questa classe di eccezione controllata genera un errore quando il bibliotecario
 * tenta di effettuare un login con credenziali errate
 * 
 * @author Gruppo15
 * @version 1.0
 */

public class BadLoginException extends Exception {

    /**
     * Costruttore di default che crea una nuova BadLoginException con un
     * messaggio di errore
     */
    
    public BadLoginException() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Costruttore che accetta un messaggio specifico
     * 
     * @param[inout] msg Il messaggio che descrive l'errore di login
     */

    public String BadLoginException(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
