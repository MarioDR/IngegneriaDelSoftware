package gruppo15.ingegneriadelsoftware.model;

import javafx.collections.ObservableList;
/**
 * @file GeneratoreStatistiche.java
 * @brief Classe dedicata al calcolo delle statistiche 
 * 
 * La classe GeneratoreStatistiche usa i dati forniti nelle altre classi per 
 * calcolare una serie di statistiche utili 
 *
 * @see Libro
 * @see Prestito
 * @author Gruppo15
 * @version 1.0
 * 
 */
public class GeneratoreStatistiche {

    /**
     * @brief Metodo per il calcolo del valore di un prestito 
     * 
     * Calcola il valore in denaro di un prestito ovvero quando è 
     * costato alla biblioteca un libro 
     * 
     * @return il valore in denaro del valore del libro fratto le
     * le volte che è stato prestato
     * 
     */
    public void valorePerPrestito() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * 
     * @brief Metodo che gestisce una lista di utenti dormienti
     * 
     * Aggiunge ad una lista tutti gli utenti "dormienti" ovvero che 
     * non effettuano un prestito da più di 3 mesi
     * 
     * @return una lista di utenti.
     */
    public ObservableList<Utente> utentiDormienti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
