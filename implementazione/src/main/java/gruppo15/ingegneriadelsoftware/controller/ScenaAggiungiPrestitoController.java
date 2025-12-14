package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.GestorePrestiti;
import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CATALOGO;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @file ScenaAggiungiPrestitoController.java
 * @brief Questa classe implementa tutti i metodi e le azioni collegate 
 * agli oggetti della scena 'ScenaAggiungiPrestito.fxml'.
 *
 * @author Gruppo15
 * @version 1.0
 */

public class ScenaAggiungiPrestitoController implements Initializable {

    @FXML
    private Button settings_button;
    @FXML
    private Button aggiungiButton;
    @FXML
    private Button annullaButton;
    @FXML
    private Hyperlink menuListaUtenti;
    @FXML
    private Hyperlink menuAggiungiUtente;
    @FXML
    private Hyperlink menuVisualizzaCatalogo;
    @FXML
    private Hyperlink menuAggiungiLibro;
    @FXML
    private Hyperlink menuPrestitiAttivi;
    @FXML
    private Hyperlink menuAggiungiPrestito;
    @FXML
    private Hyperlink menuStoricoRestituzioni;
    @FXML
    private Hyperlink menuVisualizzaStatistiche;
    @FXML
    private Button logout_button;
    @FXML
    private TextField matricolaField;
    @FXML
    private TextField ISBNField;
    @FXML
    private DatePicker dataRestituzioneField;
    @FXML
    private Label labelErrorePrestito;

    /**
     * In questo metodo viene implementata la logica di binding tra il pulsante AGGIUNGI e i campi compilabili.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        labelErrorePrestito.setText("");
        
        //IMPLEMENTAZIONE LOGICA DI DISABLE DEL PULSANTE AGGIUNGI
        BooleanBinding isFormValid = Bindings.createBooleanBinding(
        () -> {
            // Recupriamo i valori
            String isbn = ISBNField.getText().trim();
            String matricola = matricolaField.getText().trim();
            LocalDate dataRestituzione = dataRestituzioneField.getValue();
            
            // --- CONDIZIONI DI BASE (TUTTO DEVE ESSERE VERO) ---

            // 1. Tutti i campi TextField devono essere NON vuoti
            boolean textFieldsFilled = !matricola.isEmpty() && !isbn.isEmpty();
                                       
            // 2. Il DatePicker deve essere NON nullo
            if (!textFieldsFilled || dataRestituzione == null) {
                return false; // Se manca qualcosa, non è valido
            }
            
            // --- CONDIZIONI DI FORMATO (TUTTO DEVE ESSERE VERO) ---
            
            // 1. ISBN (esattamente 13 cifre intere)
            boolean isbnValid = isbn.matches("^[0-9]{13}$");
            
            // 2. Matricola (esattamente 10 cifre intere)
            boolean matricolaValid = matricola.matches("^[0-9]{10}$");
            
            // 3. Data prevista di restituzione (deve essere nel futuro, ma entro 6 mesi)
            boolean dataValid = dataRestituzione.isAfter(LocalDate.now()) && dataRestituzione.isBefore(LocalDate.now().plus(6, ChronoUnit.MONTHS));
         
            // La forma è valida solo se tutte le verifiche di FORMATO sono vere
            return isbnValid && matricolaValid && dataValid;
        },
        // Dipendenze: si aggiorna quando cambia il testo o la data
        matricolaField.textProperty(),
        ISBNField.textProperty(),
        dataRestituzioneField.valueProperty()
    );

    // Colleghiamo: il pulsante è DISATTIVATO se la forma NON è valida.
    aggiungiButton.disableProperty().bind(isFormValid.not());
    }    

    // =========================================================
    // METODI HELPER
    // =========================================================
    
    /**
     * Questo è un metodo helper che svuota tutti i campi dei textField.
     */
    
    private void pulisciCampi() {
        matricolaField.clear();
        ISBNField.clear();
        dataRestituzioneField.setValue(null);
    }
    
    /**
    * Riscrive completamente i file utenti.csv e prestiti.csv con lo stato attuale delle collezioni.
    * 
    * @throws IOException Se il file non può essere riscritto.
    */
    
    private void updateFileCSV() throws IOException {
        Path csvPathLibri = Paths.get(PATH_CATALOGO);

        // 1. Ottieni la lista delle righe CSV dalla collezione aggiornata
        List<String> righeCSVLibri = GestoreLibri.getInstance().getList().stream()
                                             .map(Libro::toCSV)
                                             .collect(Collectors.toList());

        // 2. Aggiungi l'intestazione all'inizio (se presente nel tuo file originale)
        // La prima riga è vuota sempre.
        righeCSVLibri.add(0, "");

        // 3. Scrivi TUTTE le righe nel file, sovrascrivendo l'originale
        Files.write(
            csvPathLibri, 
            righeCSVLibri, 
            StandardOpenOption.WRITE, 
            StandardOpenOption.TRUNCATE_EXISTING, 
            StandardOpenOption.CREATE
        );
    }
    
    // =========================================================
    // AZIONI DEI PULSANTI
    // =========================================================
    
    /**
     * Il metodo è collegato all'azione del pulsante 'aggiungiButton' che permette di aggiungere un prestito alla lista
     * con tutte le operazioni di inserimento nel file CSV e nella collezione di gestione. La gestione della sintassi corretta
     * è rimandata all'interfaccia grafica.
     * 
     * @param event l'evento del click sul pulsante
     * @pre tutti i campi devono essere conformi ai requisiti sintattici previsti
     */
    
    @FXML
    private void clickAggiungi(ActionEvent event) {
        String matricola = matricolaField.getText().trim();
        String isbn = ISBNField.getText().trim();
        LocalDate dataRestituzione = dataRestituzioneField.getValue();

        List<Libro> listaLibriPro = GestoreLibri.getInstance().getList();
        List<Utente> listaUtentiPro = GestoreUtenti.getInstance().getList();
        Utente utente = null;
        Libro libro = null;

        for(Utente u : listaUtentiPro){
            if(u.getMatricola().equals(matricola)){
                utente = u;
                break;
            }
        }

        for(Libro l : listaLibriPro){
            if(l.getISBN().equals(isbn)){
                libro = l;            
                break;
            }
        }

        // Controlla se l'utente ha già 3 prestiti attivi
        if(utente != null && utente.hasMaxNumPrestiti()) {
            App.mostraMessaggioTemporaneo(labelErrorePrestito, "L'utente ha gia 3 prestiti attivi!", "red", 3);
            return;
        }
        
        // Controlla se il libro selezionato ha copie ancora disponibili (controlliamo se è <= 0 per robustezza, basterebbe == 0)
        if(libro != null && libro.getNumeroCopie() <= 0) {
            App.mostraMessaggioTemporaneo(labelErrorePrestito, "Non ci sono copie disponibili del libro selezionato!", "red", 3);
            return;
        }
        
        // Controlla se l'utente possiede già quel libro
        for(Prestito p : GestorePrestiti.getInstance().getList()) {
            if(p.getUtenteAssegnatario().equals(utente) && p.getLibroPrestato().equals(libro)) {
                App.mostraMessaggioTemporaneo(labelErrorePrestito, "L'utente possiede già questo libro!", "red", 3);
                return;
            }
        }
        
        // Controlla se effettivamente l'utente e il libro sono stati trovati e agisce di conseguenza
        if(utente != null && libro != null){
            // Creazione Oggetto Prestito
            Prestito nuovoPrestito = new Prestito(utente, libro, dataRestituzione);
            
            // Update dei valori sia nella struttura che nel file CSV per i libri
            libro.rimuoviCopia();
            
            try {
                updateFileCSV(); 
            } catch(IOException e) {
                App.mostraMessaggioTemporaneo(labelErrorePrestito, "Errore I/O: Impossibile salvare il nuovo prestito sul file!", "red", 3);
                return;
            }
            
            // Salvataggio nel Gestore Condiviso
            GestorePrestiti.getInstance().add(nuovoPrestito);

            // Salvataggio del record sul file CSV corrispondente
            try (FileWriter writer = new FileWriter(App.PATH_PRESTITI, true)) {
                // Aggiunge una nuova riga (newline) e il record CSV
                writer.write(System.lineSeparator() + nuovoPrestito.toCSV());
            } catch (IOException e) {
                // Se la scrittura fallisce, visualizza l'errore
                App.mostraMessaggioTemporaneo(labelErrorePrestito, "Errore nel salvataggio del nuovo prestito!", "red", 3);
                return;
            }
            
            App.mostraMessaggioTemporaneo(labelErrorePrestito, "Prestito aggiunto con successo!", "green", 3);
            pulisciCampi(); 
        } else {
            App.mostraMessaggioTemporaneo(labelErrorePrestito, "Errore: Prestito non valido! (utente o libro non trovati)", "red", 3);
        }
    }

    /**
     * Questo metodo è associato all'azione del pulsante 'annullaButton' che serve a ripulire tutti i campi dei textField.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickAnnulla(ActionEvent event) {
        pulisciCampi();
    }
    
//============================================================================================================================
//                                        NAVIGABILITA' PARTE SINISTRA DELLO SPLIT PANE
//============================================================================================================================

    // =========================================================
    // SEZIONE PULSANTI DEL MENU'
    // =========================================================
    
    /**
     * Questo metodo è collegato all'azione del pulsante 'logout_button' e serve a tornare alla pagina di login.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickLogoutButton(ActionEvent event) {
        try {
            App.setRoot("ScenaLogin");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Questo metodo è collegato all'azione del pulsante 'settings_button' e serve ad aprire il menù delle impostazioni.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickSettingsButton(ActionEvent event) {
    }
    
    // =========================================================
    // SEZIONE GESTIONE DEL MENU'
    // =========================================================
    
    // ----------------------- UTENTI --------------------------
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaListaUtenti'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickListaUtenti(ActionEvent event) {
        try {
            App.setRoot("ScenaListaUtenti");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaAggiungiUtente'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickAggiungiUtente(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiUtente");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    // ---------------------- CATALOGO -------------------------
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaVisualizzaCatalogo'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickVisualizzaCatalogo(ActionEvent event) {
        try {
            App.setRoot("ScenaVisualizzaCatalogo");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaAggiungiLibro'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickAggiungiLibro(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiLibro");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    // -------------- PRESTITI E RESTITUZIONI ------------------
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaPrestitiAttivi'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickPrestitiAttivi(ActionEvent event) {
        try {
            App.setRoot("ScenaPrestitiAttivi");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaAggiungiPrestito'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickAggiungiPrestito(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiPrestito");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaStoricoRestituzioni'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickStoricoRestituzioni(ActionEvent event) {
        try {
            App.setRoot("ScenaStoricoRestituzioni");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    // -------------------- STATISTICHE ------------------------
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaVisualizzaStatistiche'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickVisualizzaStatistiche(ActionEvent event) {
    }
}
