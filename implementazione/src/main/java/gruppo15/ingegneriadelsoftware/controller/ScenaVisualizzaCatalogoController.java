package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CATALOGO;
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
 * @file ScenaVisualizzaCatalogoController.java
 * @brief Questa classe implementa tutti i metodi e le azioni collegate 
 * agli oggetti della scena 'ScenaVisualizzaCatalogo.fxml'.
 *
 * @author Gruppo15
 * @version 1.0
 */

public class ScenaVisualizzaCatalogoController implements Initializable {

    @FXML
    private AnchorPane sinistraSplitPane;
    @FXML
    private Button logout_button;
    @FXML
    private Button settings_button;
    @FXML
    private AnchorPane destraSplitPane;
    @FXML
    private TableColumn<Libro, String> colonnaTitolo;
    @FXML
    private TableColumn<Libro, String> colonnaAutori;
    @FXML
    private TableColumn<Libro, String> colonnaISBN;
    @FXML
    private TableColumn<Libro, LocalDate> colonnaDataPubblicazione;
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
    private TextField barraRicercaCatalogo;
    @FXML
    private TableView<Libro> tabellaCatalogo;
    
    private ObservableList<Libro> listaLibri;

    /**
     * In questo metodo viene implementata la logica di ricerca e la visualizzazione della tabella.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
        listaLibri = FXCollections.observableArrayList();
        
        // Titolo
        colonnaTitolo.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getTitolo()));
        
        // ISBN
        colonnaISBN.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getISBN()));
        
        // Data di pubblicazione
        colonnaDataPubblicazione.setCellValueFactory(r -> new SimpleObjectProperty(r.getValue().getDataDiPubblicazione()));
        
        // Autori
        colonnaAutori.setCellValueFactory(r -> {
            String elenco = String.join(", ", r.getValue().getListaAutori());
            return new SimpleStringProperty(elenco);
        });

        // CARICAMENTO DATI
        // Prendo i dati dal GestoreLibri (Singleton)
        listaLibri.addAll(GestoreLibri.getInstance().getList());

        // FILTRO E RICERCA
        // Avvolgo la lista in una FilteredList
        FilteredList<Libro> filteredData = new FilteredList<>(listaLibri, p -> true);

        // Aggiungo il listener alla barra di ricerca
        barraRicercaCatalogo.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(libro -> {
               return libro.containsPattern(newValue);
            });
        });

        // ORDINAMENTO
        // Avvolgo la FilteredList in una SortedList
        SortedList<Libro> sortedData = new SortedList<>(filteredData);
        
        // Collego il comparatore della SortedList alla tabella (per cliccare sulle intestazioni)
        sortedData.comparatorProperty().bind(tabellaCatalogo.comparatorProperty());

        tabellaCatalogo.setItems(sortedData);
        
        tabellaCatalogo.setRowFactory(tv -> {
            TableRow<Libro> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                // 1. Verifica che sia un doppio click (clickCount == 2)
                // 2. Verifica che la riga non sia vuota (row.isEmpty() == false)
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    Libro libroSelezionato = row.getItem();
                    // Chiama il metodo per aprire il popup
                    showLibroDetailsPopup(libroSelezionato); 
                }
            });
            return row;
        });
    }    
    
    // =========================================================
    // METODI HELPER
    // =========================================================
    
    /**
    * Riscrive completamente il file libri.csv con lo stato attuale della collezione GestoreLibri.
    * Questo è necessario quando si aggiornano campi di un record esistente.
    * 
    * @throws IOException Se il file non può essere riscritto.
    */
    
    private void updateFileCSV() throws IOException {
        Path csvPath = Paths.get(PATH_CATALOGO);

        // 1. Ottieni la lista delle righe CSV dalla collezione aggiornata
        List<String> righeCSV = GestoreLibri.getInstance().getList().stream()
                                             .map(Libro::toCSV)
                                             .collect(Collectors.toList());

        // 2. Aggiungi l'intestazione all'inizio (se presente nel tuo file originale)
        // La prima riga è vuota sempre.
        righeCSV.add(0, "");

        // 3. Scrivi TUTTE le righe nel file, sovrascrivendo l'originale
        Files.write(
            csvPath, 
            righeCSV, 
            StandardOpenOption.WRITE, 
            StandardOpenOption.TRUNCATE_EXISTING, 
            StandardOpenOption.CREATE
        );
    }
         
    // =========================================================
    // HANDLE AZIONI
    // =========================================================
    
    /**
    * All'azione del doppio click su una riga della tabella dei libri verrà visualizzato un
    * pop-up informativo con i pulsanti di modifica e di elimina.
    * 
    * @param libro il libro di cui bisogna visualizzare le informazioni
    */
    
    private void showLibroDetailsPopup(Libro libro) {
        // Crea la nuova finestra/Stage
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca l'interazione con la finestra principale
        popupStage.setTitle("Dettagli Libro: " + libro.getTitolo()); 

        // Layout della finestra
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);

        // Etichette per visualizzare i dettagli dell'utente
        root.getChildren().addAll(
            new Label("Titolo: " + libro.getTitolo()),
            new Label("Autori: " + libro.getListaAutori().toString()),
            new Label("ISBN: " + libro.getISBN()),
            new Label("Data di pubblicazione: " + libro.getDataDiPubblicazione().format(DateTimeFormatter.ISO_DATE)),
            new Label("Copie nello stock: " + libro.getNumeroCopieDiStock()),
            new Label("Copie Disponibili: " + libro.getNumeroCopieRimanenti()),
            new Label("Valore: " + libro.getValore())

            // Aggiungi qui tutte le altre informazioni
        );
        
        // Pulsanti
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);

        Button btnEdit = new Button("Modifica");
        Button btnDelete = new Button("Elimina");

        // Aggiungi i gestori di eventi ai pulsanti
        btnEdit.setOnAction(e -> handleEdit(libro, popupStage));
        btnDelete.setOnAction(e -> handleDelete(libro, popupStage));

        buttonBar.getChildren().addAll(btnEdit, btnDelete);

        root.getChildren().add(buttonBar);

        // Configura e mostra la scena
        Scene scene = new Scene(root);
        popupStage.setScene(scene);
        popupStage.show();
    }
    
    /**
    * All'azione del pulsante elimina del pop-up informativo di un libro, verrà visualizzato un
    * pop-up che chiede conferma per procedere con l'eliminazione.
    * 
    * @param libro il libro da eliminare
    * @param popupStage La finestra di pop-up mostrata
    */
    
    private void handleDelete(Libro libro, Stage popupStage) {
        // **FASE 1: Conferma**
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Conferma Eliminazione");
        confirmationAlert.setHeaderText("Sei sicuro di voler eliminare il libro " + libro.getTitolo() + "?");
        confirmationAlert.setContentText("Questa azione è irreversibile.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // **FASE 2: Logica di Eliminazione**

            // 3. Elimina il libro
            GestoreLibri.getInstance().getList().remove(libro);

            // 4. Rimuovi il libro dalla TableView (listaLibri) per aggiornare l'interfaccia
            listaLibri.remove(libro);
            try{
                updateFileCSV();
            }catch (IOException ex) {
                Logger.getLogger(ScenaVisualizzaCatalogoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Chiudi il popup
            popupStage.close();
        }
    }
    
    /**
    * All'azione del pulsante modifica del pop-up informativo di un libro, verrà attivata una modalità che da la possibilità
    * di modificare alcuni campi del libro.
    * 
    * @param libro il libro da modificare
    * @param popupStage La finestra di pop-up mostrata
    */
    
    private void handleEdit(Libro libro, Stage popupStage) {
        // Logica di modifica
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