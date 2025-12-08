package gruppo15.ingegneriadelsoftware.model.exceptions;

/**
 * @file UtenteNonValidoException.java
 * @brief Questo file contiene l'implementazione dell'eccezione UtenteNonValidoException
 * 
 * L'eccezione viene lanciata se l'utente risulta non essere valido(alcuni dei 
 * campi inseriti per la registrazione dell'utente sono errati)
 * 
 * @see ValidationException
 * @author Gruppo15
 * @version 1.0
 */
public class UtenteNonValidoException extends ValidationException {

    /**
     * @brief Costruttore dell'eccezione senza parametro
     */
    public UtenteNonValidoException() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief Costruttore dell'eccezione con parametro
     * @param[in] msg Il messaggio che sarà mostrato al lancio dell'eccezione 
     */
    public String ModificaUtenteErrataException(String msg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
