package gruppo15.ingegneriadelsoftware.model;

/**
 * @file Checkable.java
 * @brief Interfaccia che fornisce metodi di controllo
 * 
 * L'interfaccia Checkable fornisce il metodo isValid utile a diverse classi per il controllo dei dati
 * 
 * @see Libro
 * @see Utente
 * @see Prestito
 * 
 * @author Gruppo15
 * @version 1.0
 */
public interface Checkable {
/**
 * 
 * @brief Metodo che controlla la validità dei dati inseriti
 * 
 * Questo metodo è implementato nelle classi Libro, Utente e Prestito e controlla la coerenza di formato dei dati  
 * oltre alla validità delle precondizioni per effettuare alcune operazioni come quella di prestito.
 * 
 * 
 * @return (@code true) se le condizioni specificate sono verificate
 * (@code false) se non sono verificate tutte le condizioni assegnate nel metodo
 */
    public boolean isValid();
}
