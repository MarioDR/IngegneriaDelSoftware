package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CATALOGO;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CREDENZIALI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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
import javafx.scene.layout.GridPane;
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
    
    private TextField tfTitolo= new TextField();
    private TextField tfAutori = new TextField();
    private TextField tfISBN = new TextField();
    private TextField tfDataPubblicazione = new TextField();
    private TextField tfCopieNelloStock= new TextField();
    private TextField tfCopieDisponibili = new TextField();
    private TextField tfValore = new TextField();
    private Button btnEdit = new Button("");
    private Button btnDelete = new Button("");

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
        
        tfTitolo.setEditable(false);
        tfAutori.setEditable(false);
        tfISBN.setEditable(false);
        tfDataPubblicazione.setEditable(false);
        tfCopieNelloStock.setEditable(false);
        tfCopieDisponibili.setEditable(false);
        tfValore.setEditable(false);
            
        tfTitolo.setText(libro.getTitolo());
        tfAutori.setText(libro.stampaAutori());
        tfISBN.setText(libro.getISBN());
        tfDataPubblicazione.setText(libro.getDataDiPubblicazione().format(DateTimeFormatter.ISO_DATE));
        tfCopieNelloStock.setText(String.valueOf(libro.getNumeroCopieDiStock()));
        tfCopieDisponibili.setText(String.valueOf(libro.getNumeroCopieRimanenti()));
        tfValore.setText(String.valueOf(libro.getValore()));
            
            
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca l'interazione con la finestra principale
        popupStage.setTitle("Dettagli Libro: " + libro.getTitolo()); 

        // Layout della finestra
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER_LEFT);

        // 2. Crea un GridPane per allineare Label e TextField
        GridPane grid = new GridPane();
        grid.setHgap(10); // Spazio orizzontale tra Label e TextField
        grid.setVgap(10); // Spazio verticale tra le righe

        // 3. Aggiungi Label (Colonna 0) e TextField (Colonna 1) alla griglia
        // Sintassi: grid.add(Nodo, Colonna, Riga)

        grid.add(new Label("Titolo:"), 0, 0);
        grid.add(tfTitolo, 1, 0);

        grid.add(new Label("Autori:"), 0, 1);
        grid.add(tfAutori, 1, 1);

        grid.add(new Label("ISBN:"), 0, 2);
        grid.add(tfISBN, 1, 2);

        grid.add(new Label("Data Pubblicazione:"), 0, 3);
        grid.add(tfDataPubblicazione, 1, 3);

        grid.add(new Label("Copie in Stock:"), 0, 4);
        grid.add(tfCopieNelloStock, 1, 4);

        grid.add(new Label("Copie Disponibili:"), 0, 5);
        grid.add(tfCopieDisponibili, 1, 5);

        grid.add(new Label("Valore (€):"), 0, 6);
        grid.add(tfValore, 1, 6);

        // Aggiungi la griglia al root
        root.getChildren().add(grid);
        
        // Pulsanti
        HBox buttonBar = new HBox(10);
        buttonBar.setAlignment(Pos.CENTER);

        btnEdit.setText("Modifica");
        btnDelete.setText("Elimina");

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
        
         if(libro.getNumeroCopieDiStock() != libro.getNumeroCopieRimanenti()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Errore Eliminazione");
            errorAlert.setHeaderText("Impossibile eliminare il libro");
            errorAlert.setContentText("Il libro selezionato è ancora in prestito");

            errorAlert.showAndWait();

            // Chiudi il popup corrente (se questa è l'azione desiderata dopo l'errore)
            popupStage.close();
        
            }else{
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
    }
    
    /**
    * All'azione del pulsante modifica del pop-up informativo di un libro, verrà attivata una modalità che da la possibilità
    * di modificare alcuni campi del libro.
    * 
    * @param libro il libro da modificare
    * @param popupStage La finestra di pop-up mostrata
    */
    
    private void handleEdit(Libro libro, Stage popupStage) {
            tfTitolo.setEditable(true);
            tfAutori.setEditable(true);
            tfISBN.setEditable(false);
            tfDataPubblicazione.setEditable(false);
            tfCopieNelloStock.setEditable(true);
            tfCopieDisponibili.setEditable(false);
            tfValore.setEditable(true);
            
            btnDelete.setText("Conferma");
            btnEdit.setText("Annulla");
            
            btnDelete.setOnAction(e -> handleConfermaEdit(libro, popupStage));
            btnEdit.setOnAction(e -> handleAnnullaEdit(libro, popupStage));
    }
    
    /**
     * All'azione del pulsante "conferma", verrà mostrato un pop-up di conferma che ci chiede
     * se vogliamo modificare in modo permanete il libro.
     * 
     * @param libro il libro di cui si intende confermare la modifica
     * @param popupStage la finestra di pop-up mostrata
     */
    
    private void handleConfermaEdit(Libro libro, Stage popupStage) {
        
        if(Integer.parseInt(tfCopieNelloStock.getText()) < (libro.getNumeroCopieDiStock() - libro.getNumeroCopieRimanenti())){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Errore Modifica");
            errorAlert.setHeaderText("Impossibile modificare il libro");
            errorAlert.setContentText("Il numero di copie di stock inserito è minore del numero di copie attualmente in prestito");

            errorAlert.showAndWait();

            // Chiudi il popup corrente (se questa è l'azione desiderata dopo l'errore)
            popupStage.close();
        
        }else{
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Conferma Modifica");
        confirmationAlert.setHeaderText("Sei sicuro di voler modificare il libro?");
        confirmationAlert.setContentText("Questa azione è irreversibile.");

    Optional<ButtonType> result = confirmationAlert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
        
            libro.setTitolo(tfTitolo.getText());
            libro.setListaAutori(tfAutori.getText());
            libro.setNumeroCopieDiStock(Integer.parseInt(tfCopieNelloStock.getText()));
            libro.setValore(Float.parseFloat(tfValore.getText()));
            
            
            tabellaCatalogo.refresh();
            
            try{
            updateFileCSV();
            }catch(IOException ex){
                Logger.getLogger(ScenaListaUtentiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            popupStage.close();
    }
    }   
    }
    
    /**
     * Cliccando su annulla il pop-up riguardante la modifica verrà chiuso
     * 
     * @param libro il libro di cui si intende annullare la modifica
     * @param popupStage la finestra di pop-up mostrata
     */
    
    private void handleAnnullaEdit(Libro libro, Stage popupStage) {
        popupStage.close();
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