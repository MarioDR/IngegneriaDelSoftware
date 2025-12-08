package gruppo15.ingegneriadelsoftware.model.exceptions;

/**
 * @file LibroNonValidoException.java
 * @brief Eccezione sollevata quando i dati di un libro non sono validi
 * 
 * Questa classe di eccezione estende ValidationException e viene utilizzata quando
 * si tenta di aggiungere un libro alla collezione con dati errati
 * 
 * @see ValidationException
 * @author Gruppo15
 * @version 1.0
 */

public class LibroNonValidoException extends ValidationException {

    /**
     * Costruttore di default che crea una nuova LibroNonValidoException
     * con un messaggio di errore
     */
    
    public LibroNonValidoException() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Costruttore che accetta un messaggio specifico
     * 
     * @param[in] msg Il messaggio che descrive l'errore
     */

    public String LibroNonValidoException(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
