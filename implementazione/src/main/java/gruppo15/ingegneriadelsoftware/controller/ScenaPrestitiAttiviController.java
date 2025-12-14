package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestorePrestiti;
import gruppo15.ingegneriadelsoftware.model.GestoreRestituzioni;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Restituzione;
import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_PRESTITI;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
 * @file ScenaPrestitiAttiviController.java
 * @brief Questa classe implementa tutti i metodi e le azioni collegate 
 * agli oggetti della scena 'ScenaPrestitiAttivi.fxml'.
 *
 * @author Gruppo15
 * @version 1.0
 */

public class ScenaPrestitiAttiviController implements Initializable {

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
    private TextField barraRicercaPrestiti;
    @FXML
    private TableView<Prestito> tabellaPrestitiAttivi;
    @FXML
    private TableColumn<Prestito, String> colonnaMatricola;
    @FXML
    private TableColumn<Prestito, String> colonnaEmail;
    @FXML
    private TableColumn<Prestito, String> colonnaISBN;
    @FXML
    private TableColumn<Prestito, LocalDate> colonnaDataPrevistaRestituzione;
    @FXML
    private TableColumn<Prestito, Long> colonnaRitardo;
    
    private ObservableList<Prestito> listaPrestiti;

    /**
     * In questo metodo viene implementata la logica di ricerca e la visualizzazione della tabella.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaPrestiti = FXCollections.observableArrayList();
        
        // MATRICOLA: Entro nel prestito -> prendo l'utente -> prendo la matricola
        colonnaMatricola.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtenteAssegnatario().getMatricola()));
        
        // EMAIL: Entro nel prestito -> prendo l'utente -> prendo l'email
        colonnaEmail.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtenteAssegnatario().getEmail()));
        
        // ISBN: Entro nel prestito -> prendo il libro -> prendo l'ISBN
        colonnaISBN.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getLibroPrestato().getISBN()));
        
        // DATA: Questa è direttamente nel prestito
        colonnaDataPrevistaRestituzione.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getDataPrevistaRestituzione()));

        // RITARDO: Anche questo è nel prestito
        colonnaRitardo.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getGiorniDiRitardo()));
         
        // CARICAMENTO DATI
        listaPrestiti.addAll(GestorePrestiti.getInstance().getList());
        
        // FILTRO 
        FilteredList<Prestito> filteredData = new FilteredList<>(listaPrestiti, p -> true);

        barraRicercaPrestiti.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(prestito -> {
                return prestito.containsPattern(newValue);
            });
        });

        SortedList<Prestito> sortedData = new SortedList<>(filteredData);
        
        // Collego il comparatore della SortedList alla tabella (per cliccare sulle intestazioni)
        sortedData.comparatorProperty().bind(tabellaPrestitiAttivi.comparatorProperty());

        tabellaPrestitiAttivi.setItems(sortedData);
        
         tabellaPrestitiAttivi.setRowFactory(tv -> {
            TableRow<Prestito> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                // 1. Verifica che sia un doppio click (clickCount == 2)
                // 2. Verifica che la riga non sia vuota (row.isEmpty() == false)
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    Prestito prestitoSelezionato = row.getItem();
                    // Chiama il metodo per aprire il popup
                    showPrestitoDetailsPopup(prestitoSelezionato); 
                }
            });
            return row;
        });
    }    

    // =========================================================
    // METODI HELPER
    // =========================================================
    
    /**
    * Riscrive completamente il file prestiti.csv con lo stato attuale della collezione.
    * Questo è necessario quando si aggiornano campi di un record esistente.
    * 
    * @throws IOException Se il file non può essere riscritto.
    */
    
    private void updateFileCSV() throws IOException {
        Path csvPathPrestiti = Paths.get(PATH_PRESTITI);
        
        // 1. Ottieni la lista delle righe CSV dalla collezione aggiornata
        List<String> righeCSVPrestiti = GestorePrestiti.getInstance().getList().stream()
                                             .map(Prestito::toCSV)
                                             .collect(Collectors.toList());

        // 2. Aggiungi l'intestazione all'inizio (se presente nel tuo file originale)
        // La prima riga è vuota sempre.
        righeCSVPrestiti.add(0, "");

        // 3. Scrivi TUTTE le righe nel file, sovrascrivendo l'originale
        Files.write(
            csvPathPrestiti, 
            righeCSVPrestiti, 
            StandardOpenOption.WRITE, 
            StandardOpenOption.TRUNCATE_EXISTING, 
            StandardOpenOption.CREATE
        );
    }
    
    // =========================================================
    // HANDLE AZIONI
    // =========================================================
    
    /**
    * All'azione del doppio click su una riga della tabella degli utenti, verrà visualizzato un
    * pop-up informativo con i pulsanti di modifica e di elimina.
    * 
    * @param prestito Il prestito di cui bisogna visualizzare le informazioni
    */
    
    private void showPrestitoDetailsPopup(Prestito prestito) {
            // Crea la nuova finestra/Stage
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca l'interazione con la finestra principale
            popupStage.setTitle("Dettagli Prestito: "); 

            // Layout della finestra
            VBox root = new VBox(10);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER_LEFT);

            // Etichette per visualizzare i dettagli del prestito
            root.getChildren().addAll(
                new Label("Utente assegnatario: " + prestito.getUtenteAssegnatario().getNome() + " " + prestito.getUtenteAssegnatario().getCognome()),
                new Label("Email: " + prestito.getUtenteAssegnatario().getEmail()),
                new Label("Matricola: " + prestito.getUtenteAssegnatario().getMatricola()),
                new Label("Libro: " + prestito.getLibroPrestato().getTitolo()),
                new Label("Autore/i: " + prestito.getLibroPrestato().getListaAutori().toString()),
                new Label("ISBN: " + prestito.getLibroPrestato().getISBN()),
                new Label("Data inizio prestito: " + prestito.getDataInizioPrestito().format(DateTimeFormatter.ISO_DATE)),
                new Label("Data prevista restituzione: " + prestito.getDataPrevistaRestituzione().format(DateTimeFormatter.ISO_DATE))
                // Aggiungi qui tutte le altre informazioni
            );

            // Pulsanti
            HBox buttonBar = new HBox(10);
            buttonBar.setAlignment(Pos.CENTER);

            Button btnEnd = new Button("Termina");

            // Aggiungi i gestori di eventi ai pulsanti
            btnEnd.setOnAction(e -> handleEnd(prestito, popupStage));

            buttonBar.getChildren().addAll(btnEnd);

            root.getChildren().add(buttonBar);

            // Configura e mostra la scena
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.show();
        }
    
    /**
    * All'azione del pulsante termina del pop-up informativo di un prestito, verrà avviato
    * il processo di terminazine di un prestito.
    * 
    * @param prestito il prestito da terminare
    * @param popupStage La finestra di pop-up mostrata
    */
    
    private void handleEnd(Prestito prestito, Stage popupStage){
        // **FASE 1: Conferma**
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Conferma Terminazione Prestito");
        confirmationAlert.setHeaderText("Sei sicuro di voler terminare questo prestito?");
        confirmationAlert.setContentText("Questa azione è irreversibile.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // **FASE 2: Logica di Eliminazione**

            // 3. Elimina il libro

            Restituzione nuovaRestituzione = new Restituzione(prestito);
            GestoreRestituzioni.getInstance().getList().add(nuovaRestituzione);

            GestorePrestiti.getInstance().getList().remove(prestito);

            // 4. Rimuovi il libro dalla TableView (listaLibri) per aggiornare l'interfaccia
            listaPrestiti.remove(prestito);

            // 5. Riscrivi il file dei prestiti con le nuove modifiche
            try{
                updateFileCSV();
            }catch (IOException ex) {
                Logger.getLogger(ScenaPrestitiAttiviController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Chiudi il popup
            popupStage.close();
        }
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