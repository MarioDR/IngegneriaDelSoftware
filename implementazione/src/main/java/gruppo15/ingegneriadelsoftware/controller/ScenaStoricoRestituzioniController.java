package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreRestituzioni;
import gruppo15.ingegneriadelsoftware.model.Restituzione;
import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CREDENZIALI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @file ScenaStoricoRestituzioniController.java
 * @brief Questa classe implementa tutti i metodi e le azioni collegate 
 * agli oggetti della scena 'ScenaStoricoRestituzioni.fxml'.
 *
 * @author Gruppo15
 * @version 1.0
 */

public class ScenaStoricoRestituzioniController implements Initializable {

    @FXML
    private AnchorPane sinistraSplitPane;
    @FXML
    private Button settings_button;
    @FXML
    private AnchorPane destraSplitPane;
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
    private TextField barraRicercaRestituzioni;
    @FXML
    private TableView<Restituzione> tabellaRestituzioni;
    @FXML
    private TableColumn<Restituzione, String> colonnaMatricola;
    @FXML
    private TableColumn<Restituzione, String> colonnaEmail;
    @FXML
    private TableColumn<Restituzione, String> colonnaISBN;
    @FXML
    private TableColumn<Restituzione, LocalDate> colonnaDataRestituzione;
    @FXML
    private TableColumn<Restituzione, Long> colonnaRitardo;

    private ObservableList<Restituzione> listaRestituzioni;
    
    /**
     * In questo metodo viene implementata la logica di ricerca e la visualizzazione della tabella.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaRestituzioni = FXCollections.observableArrayList();
        
        // MATRICOLA: Entro nel prestito -> prendo l'utente -> prendo la matricola
        colonnaMatricola.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getPrestitoDaRestituire().getUtenteAssegnatario().getMatricola()));
        
        // EMAIL: Entro nel prestito -> prendo l'utente -> prendo l'email
        colonnaEmail.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getPrestitoDaRestituire().getUtenteAssegnatario().getEmail()));
        
        // ISBN: Entro nel prestito -> prendo il libro -> prendo l'ISBN
        colonnaISBN.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getPrestitoDaRestituire().getLibroPrestato().getISBN()));
        
        // DATA: Questa è direttamente nel prestito
        colonnaDataRestituzione.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getDataRestituzione()));

        // RITARDO: Anche questo è nel prestito
        colonnaRitardo.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getRitardoDefinitivo()));
         
        // CARICAMENTO DATI
        listaRestituzioni.addAll(GestoreRestituzioni.getInstance().getList());
        
        // FILTRO 
        FilteredList<Restituzione> filteredData = new FilteredList<>(listaRestituzioni, p -> true);

        barraRicercaRestituzioni.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(restituzione -> {
                return restituzione.containsPattern(newValue);
            });
        });

        SortedList<Restituzione> sortedData = new SortedList<>(filteredData);
        
        // Collego il comparatore della SortedList alla tabella (per cliccare sulle intestazioni)
        sortedData.comparatorProperty().bind(tabellaRestituzioni.comparatorProperty());

        tabellaRestituzioni.setItems(sortedData);
        
         tabellaRestituzioni.setRowFactory(tv -> {
            TableRow<Restituzione> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                // 1. Verifica che sia un doppio click (clickCount == 2)
                // 2. Verifica che la riga non sia vuota (row.isEmpty() == false)
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    Restituzione restituzioneSelezionata = row.getItem();
                    // Chiama il metodo per aprire il popup
                    showRestituzioneDetailsPopup(restituzioneSelezionata); 
                }
            });
            return row;
        });
    }    
    
    // =========================================================
    // HANDLE AZIONI
    // =========================================================
    
    /**
    * All'azione del doppio click su una riga della tabella delle restituzioni, verrà visualizzato un
    * pop-up informativo con tutti i dettagli della restituzione.
    * 
    * @param restituzione la restituzione di cui bisogna visualizzare le informazioni
    */
    
    private void showRestituzioneDetailsPopup(Restituzione restituzione) {
            // Crea la nuova finestra/Stage
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca l'interazione con la finestra principale
            popupStage.setTitle("Dettagli Restituzione: "); 

            // Layout della finestra
            VBox root = new VBox(10);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER_LEFT);

            // Etichette per visualizzare i dettagli dell'utente
            root.getChildren().addAll(
                new Label("Utente: " + restituzione.getPrestitoDaRestituire().getUtenteAssegnatario().getNome()
                                     + " " + restituzione.getPrestitoDaRestituire().getUtenteAssegnatario().getCognome()),
                new Label("Matricola: " + restituzione.getPrestitoDaRestituire().getUtenteAssegnatario().getMatricola()),
                new Label("Email: " + restituzione.getPrestitoDaRestituire().getUtenteAssegnatario().getEmail()),
                new Label("Titolo: " + restituzione.getPrestitoDaRestituire().getLibroPrestato().getTitolo()),
                new Label("Autori: " + restituzione.getPrestitoDaRestituire().getLibroPrestato().getListaAutori().toString()),
                new Label("ISBN: " + restituzione.getPrestitoDaRestituire().getLibroPrestato().getISBN()),
                new Label("Data di restituzione: " + restituzione.getDataRestituzione()),
                new Label("Penale applicata: " + restituzione.calcolaPenale())
            );

            // Configura e mostra la scena
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.show();
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
     * Questo metodo è collegato all'azione del pulsante 'settings_button' e serve ad aprire il menù delle impostazioni
     * per modificare le credenziali.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    private TextField vecchioUsernameField;
    private TextField vecchiaPasswordField;
    private TextField nuovoUsernameField;
    private TextField nuovaPasswordField;
    private Label labelMessaggioErrore;
    
    @FXML
    private void clickSettingsButton(ActionEvent event) {
        try {
            // 1. Inizializza i campi globali che rappresentano i TextField
            vecchioUsernameField = new TextField();
            vecchiaPasswordField = new TextField();
            nuovoUsernameField = new TextField();
            nuovaPasswordField = new TextField();
            labelMessaggioErrore = new Label(); 

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("MODIFICA CREDENZIALI");

            VBox root = new VBox(10);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER);
            
            root.setPrefHeight(200);
            root.setPrefWidth(300);
            
            // Aggiunta dei campi alla UI
            root.getChildren().addAll(
                new Label("Vecchio Username:"),
                vecchioUsernameField,
                new Label("Vecchia Password:"),
                vecchiaPasswordField,
                new Label("Nuovo Username:"),
                nuovoUsernameField,
                new Label("Nuova Password:"),
                nuovaPasswordField,
                labelMessaggioErrore
            );

            // Pulsanti
            HBox buttonBar = new HBox(10);
            buttonBar.setAlignment(Pos.CENTER);

            Button btnSave = new Button("Salva");
            Button btnQuit = new Button("Esci");

            // Associa l'azione a un metodo helper
            btnSave.setOnAction(e -> {
                try {
                    handleSave(popupStage);
                } catch (IOException ex) {
                    Logger.getLogger(ScenaMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            btnQuit.setOnAction(e -> popupStage.close());

            buttonBar.getChildren().addAll(btnSave, btnQuit);
            root.getChildren().add(buttonBar);

            // Configura e mostra la scena
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.setResizable(false);
            popupStage.showAndWait(); // showAndWait blocca l'esecuzione fino alla chiusura

        } catch (Exception e) {
            // Se c'è un errore nella creazione della finestra
            System.err.println("Errore nell'apertura della finestra di impostazioni: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
    * All'azione del pulsante salva, verranno validate le vecchie credenziali
    * per capire se bisogna procedere al salvataggio delle nuove. Se sono valide, si procede con l'aggiornamento.
    * 
    * @param popupStage La finestra di pop-up mostrata
    */
    
    private void handleSave(Stage popupStage) throws IOException {
        String vecchioU = vecchioUsernameField.getText().trim();
        String vecchiaP = vecchiaPasswordField.getText().trim();
        String nuovoU = nuovoUsernameField.getText().trim();
        String nuovaP = nuovaPasswordField.getText().trim();;
        
        String usernameVecchia;
        String passwordVecchia;
        
        // 1. Validazione di base (Nessun campo vuoto)
        if (vecchioU.isEmpty() || vecchiaP.isEmpty() || nuovoU.isEmpty() || nuovaP.isEmpty()) {
            App.mostraMessaggioTemporaneo(labelMessaggioErrore, "Riempi tutti i campi!", "red", 3);
            return;
        }
        
        // ----------------------------------------------------
        // 2. Lettura e Validazione Credenziali da CSV
        // ----------------------------------------------------
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_CREDENZIALI))) {

            String line = br.readLine();

            // Gestione robusta: Controlla se il file è vuoto o se la riga letta è vuota
            if (line == null || line.trim().isEmpty()) {
                App.mostraMessaggioTemporaneo(labelMessaggioErrore, "❌ Errore: File credenziali vuoto o malformato.", "red", 3);
                return;
            }

            String values[] = line.split(",");

            // Gestione robusta: Controlla se ci sono almeno 2 campi
            if (values.length < 2) {
                App.mostraMessaggioTemporaneo(labelMessaggioErrore, "❌ Errore: Credenziali incomplete nel file CSV (servono username,password).", "red", 3);
                return;
            }

            // Estrazione sicura
            usernameVecchia = values[0].trim();
            passwordVecchia = values[1].trim();

        } catch (IOException e) {
            App.mostraMessaggioTemporaneo(labelMessaggioErrore, "❌ Errore I/O: Impossibile trovare o leggere il file credenziali!", "red", 3);
            return;
        }

        // 3. Confronto con le vecchie credenziali inserite dall'utente
        if (!vecchioU.equals(usernameVecchia) || !vecchiaP.equals(passwordVecchia)) {
            // Le vecchie credenziali non sono corrette
            App.mostraMessaggioTemporaneo(labelMessaggioErrore, "❌ Vecchie credenziali non corrispondenti.", "red", 3);
            return;
        }

        // 4. Procedi al Salvataggio delle Nuove Credenziali
        try (FileWriter writer = new FileWriter(App.PATH_CREDENZIALI, false)) { 
            // Formatta i dati come richiesto (separati da virgola)
            writer.write(nuovoU + "," + nuovaP);

            // Aggiungi un newline alla fine se necessario, per pulizia del file (opzionale)
            writer.write(System.lineSeparator()); 
        } catch (IOException e) {
            App.mostraMessaggioTemporaneo(labelMessaggioErrore, "❌ Errore I/O durante il salvataggio delle credenziali.", "red", 3);
        }
        
        App.mostraMessaggioTemporaneo(labelMessaggioErrore, "Credenziali aggiornate con successo!", "green", 3);

        // Chiudi la finestra dopo un piccolo ritardo (opzionale, per mostrare il messaggio di successo)
        javafx.animation.PauseTransition delay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1));
        delay.setOnFinished(e -> popupStage.close());
        delay.play();
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
        try {
            App.setRoot("ScenaVisualizzaStatistiche");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
}