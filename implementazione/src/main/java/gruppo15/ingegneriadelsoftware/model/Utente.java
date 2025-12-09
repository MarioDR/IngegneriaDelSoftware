package gruppo15.ingegneriadelsoftware.model;

import java.util.List;

/**
 * @file Utente.java
 * @brief Rappresenta un utente registrato nel sistema con la gestione dei suoi prestiti
 * @invariant Nessuna invariante
 * 
 * La classe Utente modella il concetto che si ha di Utente nel sistema. I suoi attributi
 * rapprerentano tutte le generalità necessarie e la lista dei libri presi in prestito.
 * Le istanze di questa classe possono trovarsi in uno stato incoerente e sarà il metodo isValid()
 * dell'interfaccia Checkable a decidere questa condizione.
 * 
 * @author Gruppo15
 * @version 1.0
 */

public class Utente {

    /// Dati anagrafici dell'utente
    
    private String nome;
    private String cognome;
    private String matricola;
    private String email;
    
    /// Lista che memorizza tutti i prestiti attivi dell'utente

    private List<Prestito> listaPrestiti;
    
    /**
     * Costruttore della classe Utente.
     * 
     * 
     * @param nome Il nome dell'utente
     * @param cognome Il cognome dell'utente
     * @param matricola La matricola dell'utente
     * @param email L'email istituzionale dell'utente
     */

    public Utente(String nome, String cognome, String matricola, String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // =========================================================
    // METODI SETTER
    // =========================================================

    /**
     * Imposta il nome dell'utente.
     * 
     * @param nome La stringa contenente il nuovo nome dell'utente
     */
    
    public void setNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Imposta il cognome dell'utente.
     * 
     * @param cognome La stringa contenente il nuovo cognome dell'utente
     */
    
    public void setCognome(String cognome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Imposta la matricola dell'utente.
     * 
     * @param matricola La stringa contenente la nuova matricola dell'utente
     */
    
    public void setMatricola(String matricola) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Imposta l'indirizzo email dell'utente.
     * 
     * @param email La stringa contenente il nuovo indirizzo email
     */
    
    public void setEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // =========================================================
    // METODI GETTER
    // =========================================================

    /**
     * Ritorna il nome dell'utente.
     * 
     * @return Il nome dell'utente
     */
    
    public String getNome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Ritorna il cognome dell'utente.
     * 
     * @return Il cognome dell'utente
     */
    
    public String getCognome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Ritorna la matricola dell'utente.
     * 
     * @return la matricola dell'utente
     */
    
    public String getMatricola() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Ritorna l'email dell'utente.
     * 
     * @return l'email dell'utente
     */
    
    public String getEmail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Restituisce la lista dei prestiti attivi dell'utente.
     * 
     * @return La lista dei prestiti attivi
     */

    public List<Prestito> getListaPrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // =========================================================
    // ALTRI METODI
    // =========================================================
    
    /**
     * Aggiunge un nuovo prestito nella lista dei prestiti dell'utente.
     * 
     * @pre L'utente destinatario del prestito deve essere questa istanza
     * @pre L'ID del prestito deve essere diverso da tutti gli ID dei prestiti attualmente collegati a questa istanza
     * @post Il prestito viene aggiunto alla lista dei prestiti dell'utente
     * @param prestito Il prestito che si vuole aggiungere all'utente
     * @return La lista dei prestiti aggiornata
     */

    public Prestito addPrestito(Prestito prestito) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Rimuove un prestito dalla lista prestiti dell'utente.
     * 
     * @post Il prestito viene rimosso dalla lista dei prestiti dell'utente
     * @param p Il prestito da rimuovere dalla lista dei prestiti dell'utente
     * @return La lista dei prestiti aggiornata
     */

    public Prestito removePrestito(Prestito p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Verifica se l'utente ha raggiunto il numero massimo di prestiti.
     * 
     * @return  {@code true} se l'utente ha già tre prestiti attivi, 
     *          {@code false} se l'utente ha meno di tre prestiti attivi
     */
    
    public boolean hasMaxNumPrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Converte i dati in una stringa CSV.
     * 
     * @return Una stringa che contiene Nome, Cognome, Matricola, Email e ID dei prestiti in formato CSV
     */
    
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
