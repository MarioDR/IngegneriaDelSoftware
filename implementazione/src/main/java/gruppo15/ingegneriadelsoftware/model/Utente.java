package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.PrestitoNonValidoException;
import java.util.List;

/**
 * @file Utente.java
 * @brief Rappresenta un utente registrato nel sistema con la gestione dei suoi prestiti
 * 
 * La classe Utente gestisce tutte le informazioni anagrafiche e tiene conto dei
 * prestiti attivi. Viene implementata l'interfaccia Checkable per verificare la validità
 * dei dati
 * 
 * @author Gruppo15
 * @version 1.0
 */

public class Utente implements Checkable {

    /// Dati anagrafici dell'utente
    
    private String nome;

    private String cognome;

    private String matricola;

    private String email;
    
    /// Lista che memorizza tutti i prestiti attivi dell'utente

    private List<Prestito> listaPrestiti;
    
    /**
     * Costruttore della classe Utente
     * 
     * 
     * @param nome Il nome dell'utente
     * @param cognome Il cognome dell'utente
     * @param matricola La matricola dell'utente
     * @param email L'email istituzionale dell'utente
     */

    public Utente(String nome, String cognome, String matricola, String email) {
    }
    
    /**
     *  METODI SETTER
     * 
     * vengono impostati il nome, cognome, matricola e l'indirizzo email dell'utente
     *  
     */

    public String setNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String setCognome(String cognome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String setMatricola(String matricola) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String setEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     *  METODI GETTER
     * 
     * Vengono restituiti il nome, cognome, matricola e l'indirizzo email dell'utente
     */

    public String getNome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getCognome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getMatricola() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEmail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce la lista dei prestiti attivi dell'utente
     * @return La lista dei prestiti attivi
     */

    public List<Prestito> getListaPrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Aggiunge un nuovo prestito nella lista dei prestiti dell'utente
     * 
     * @pre L'utente non deve avere più di tre prestiti attivi
     * @post Il prestito viene aggiunto alla lista
     * @param prestito
     * @return Lista prestiti aggiornata
     * @throws PrestitoNonValidoException Se l'utente ha già tre prestiti attivi o
     * se il prestito non rispetta le condizioni di validità
     */

    public Prestito addPrestito(Prestito prestito) throws PrestitoNonValidoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Rimuove un prestito dalla lista prestiti dell'utente
     * 
     * @pre L'utente deve avere almeno un prestito
     * @post Il prestito viene rimosso
     * @param p
     * @return Lista prestiti aggiornata
     */

    public Prestito removePrestito(Prestito p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se l'utente ha raggiunto il numero massimo di prestiti
     * 
     * @return {@code true} se l'utente ha già tre prestiti attivi, 
     * {@code false} se l'utente ha meno di tre prestiti attivi
     */
    
    public boolean hasMaxNumPrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se i dati anagrafici dell'utente sono corretti
     * 
     * La validità viene definita da:
     * Campo nome e campo cognome non devono essere vuoti;
     * La matricola deve essere univoca per ogni utente
     * L'indirizzo email deve essere istituzionale, quindi terminare con @studenti.unisa.it
     * 
     * @return {code true}se le condizioni sono verificate,
     * {@code false} se le condizioni non sono verificate
     */
    
    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Converte i dati in una stringa
     * 
     * @return Una stringa che contiene Nome, Cognome, Matricola, Email
     */
    
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
