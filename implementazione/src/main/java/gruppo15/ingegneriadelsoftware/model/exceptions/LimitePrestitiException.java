package gruppo15.ingegneriadelsoftware.model.exceptions;

/**
 * @file LimitePrestitiException.java
 * @brief Questo file contiene l'implementazione dell'eccezione LimitePrestitiException
 * 
 * L'eccezione viene lanciata se l'utente ha più di tre prestiti attivi nella sua lista dei prestiti
 * 
 * @author Gruppo15
 * @version 1.0
 */
public class LimitePrestitiException extends Exception {

    /**
     * @brief Costruttore dell'eccezione senza parametro
     */
    public LimitePrestitiException() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Costruttore dell'eccezione con parametro
     * @param[in] msg Il messaggio che sarà mostrato al lancio dell'eccezione 
     */
    public LimitePrestitiException(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
