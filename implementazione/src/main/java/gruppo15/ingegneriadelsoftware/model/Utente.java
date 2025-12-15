package gruppo15.ingegneriadelsoftware.model;

import java.util.ArrayList;
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

public class Utente implements Searchable {

    /// Dati anagrafici dell'utente
    
    private String nome;
    private String cognome;
    private final String matricola;
    private String email;
    
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
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.email = email;
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
        this.nome = nome;
    }

    /**
     * Imposta il cognome dell'utente.
     * 
     * @param cognome La stringa contenente il nuovo cognome dell'utente
     */
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Imposta l'indirizzo email dell'utente.
     * 
     * @param email La stringa contenente il nuovo indirizzo email
     */
    
    public void setEmail(String email) {
        this.email = email;
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
        return this.nome;
    }

    /**
     * Ritorna il cognome dell'utente.
     * 
     * @return Il cognome dell'utente
     */
    
    public String getCognome() {
        return this.cognome;
    }
    
    /**
     * Ritorna la matricola dell'utente.
     * 
     * @return la matricola dell'utente
     */
    
    public String getMatricola() {
        return this.matricola;
    }
    
    /**
     * Ritorna l'email dell'utente.
     * 
     * @return l'email dell'utente
     */
    
    public String getEmail() {
        return this.email;
    }
    
    /**
     * Restituisce la lista dei prestiti attivi dell'utente.
     * 
     * @return La lista dei prestiti attivi
     */

    public List<Prestito> getListaPrestiti() {
        ArrayList<Prestito> a = new ArrayList<>();
        
        for(Prestito p : GestorePrestiti.getInstance().getList()) {
            if(p.getUtenteAssegnatario().equals(this))
                a.add(p);
        }
        
        return a;
    }
    
    /**
     * Verifica se l'utente ha raggiunto il numero massimo di prestiti.
     * 
     * @return  {@code true} se l'utente ha già tre prestiti attivi, 
     *          {@code false} se l'utente ha meno di tre prestiti attivi
     */
    
    public boolean hasMaxNumPrestiti() {
        return this.getListaPrestiti().size() >= 3;
    }
    
    /**
     * Verifica se l'elemento ha almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param pattern la stringa usata per la ricerca
     * @return  {@code true} se l'oggetto corrisponde al pattern
     *          {@code false} in ogni altro caso
     */
    
    @Override
    public boolean containsPattern(String pattern) {
        if (pattern == null || pattern.trim().isEmpty())
            return true;

        // Il pattern di ricerca non deve contenere spazi all'inizio e alla fine
        String patternRicerca = pattern.trim();

        // 1. Verifica Nome
        if (this.getNome().toLowerCase().contains(patternRicerca.toLowerCase()))
            return true;

        // 2. Verifica Cognome
        if (this.getCognome().toLowerCase().contains(patternRicerca.toLowerCase()))
            return true;

        // 3. Verifica Matricola
        if (this.getMatricola().contains(patternRicerca.toLowerCase()))
            return true;
        
        // 4. Verifica Email
        if (this.getEmail().toLowerCase().contains(patternRicerca.toLowerCase()))
            return true;

        return false;
    }
    
    /**
     * Controlla se un oggetto è uguale a questa istanza (due utenti sono uguali se hanno la stessa matricola e la stessa emai)
     * 
     * @return  {@code true} se i due utenti hanno la stessa matricola e la stessa email
     *          {@code false} in tutti gli atri casi
     */
    
    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        
        Utente o2 = (Utente)o;
        
        return o2.getMatricola().equalsIgnoreCase(this.getMatricola()) || o2.getEmail().equalsIgnoreCase(this.getEmail());
    }
    
    /**
     * Converte i dati in una stringa CSV. L'ordine è: nome, cognome, matricola, email 
     * ed eventuali ID di prestiti attivi (ogni ID prestito inizia con #).
     * 
     * @return Una stringa che contiene Nome, Cognome, Matricola, Email e ID dei prestiti in formato CSV
     */
    
    public String toCSV() {
        return this.nome + "," + this.cognome + "," + this.matricola + "," + this.email;
    }
}
