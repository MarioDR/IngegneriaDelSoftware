package gruppo15.ingegneriadelsoftware.model.exceptions;

/**
 * @file PrestitoNonValidoException.java
 * @brief Questo file contiene l'implementazione dell'eccezione PrestitoNonValidoException
 * 
 * L'eccezione viene lanciata se il prestito risulta non essere valido(alcuni dei 
 * campi inseriti per la registrazione del prestito sono errati)
 * 
 * @see ValidationException
 * @author Gruppo15
 * @version 1.0
 */
public class PrestitoNonValidoException extends ValidationException {

    /**
     * @brief Costruttore dell'eccezione senza parametro
     */
    public PrestitoNonValidoException() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Costruttore dell'eccezione con parametro
     * @param[in] msg Il messaggio che sarà mostrato al lancio dell'eccezione 
     */
    public String PrestitoNonValidoException(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
