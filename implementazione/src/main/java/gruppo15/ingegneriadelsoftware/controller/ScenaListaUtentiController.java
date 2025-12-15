package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CREDENZIALI;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_UTENTI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
 * @file ScenaListaUtentiController.java
 * @brief Questa classe implementa tutti i metodi e le azioni collegate 
 * agli oggetti della scena 'ScenaListaUtenti.fxml'.
 *
 * @author Gruppo15
 * @version 1.0
 */

public class ScenaListaUtentiController implements Initializable {
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
    private TextField barraRicercaUtenti;
    @FXML
    private TableView<Utente> tabellaUtenti;
    @FXML
    private TableColumn<Utente, String> colonnaNome;
    @FXML
    private TableColumn<Utente, String> colonnaCognome;
    @FXML
    private TableColumn<Utente, String> colonnaMatricola;
    @FXML
    private TableColumn<Utente, String> colonnaEmail;
    
    private ObservableList<Utente> listaUtenti;
    
    private TextField tfNome= new TextField();
    private TextField tfCognome = new TextField();
    private TextField tfMatricola = new TextField();
    private TextField tfEmail = new TextField();
    private Button btnEdit = new Button("");
    private Button btnDelete = new Button("");

    /**
     * In questo metodo viene implementata la logica di ricerca e la visualizzazione della tabella.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        listaUtenti = FXCollections.observableArrayList();
        
        // Nome
        colonnaNome.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getNome()));
        
        // Cognome
        colonnaCognome.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getCognome()));
        
        // Matricola
        colonnaMatricola.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getMatricola()));
        
        // Email
        colonnaEmail.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getEmail()));

        // CARICAMENTO DATI
        // Prendo i dati dal GestoreLibri 
        listaUtenti.addAll(GestoreUtenti.getInstance().getList());

        // FILTRO E RICERCA
        // Avvolgo la lista in una FilteredList
        FilteredList<Utente> filteredData = new FilteredList<>(listaUtenti, p -> true);

        // Aggiungo il listener alla barra di ricerca
        barraRicercaUtenti.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(utente -> {
                
                return utente.containsPattern(newValue);
            });
        });
        
        // ORDINAMENTO
        // Avvolgo la FilteredList in una SortedList
        SortedList<Utente> sortedData = new SortedList<>(filteredData);
        
        // Collego il comparatore della SortedList alla tabella (per cliccare sulle intestazioni)
        sortedData.comparatorProperty().bind(tabellaUtenti.comparatorProperty());

        tabellaUtenti.setItems(sortedData);
        
         tabellaUtenti.setRowFactory(tv -> {
            TableRow<Utente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                // 1. Verifica che sia un doppio click (clickCount == 2)
                // 2. Verifica che la riga non sia vuota (row.isEmpty() == false)
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    Utente utenteSelezionato = row.getItem();
                    // Chiama il metodo per aprire il popup
                    showUserDetailsPopup(utenteSelezionato); 
                }
            });
            return row;
        });
    }    
    
    // =========================================================
    // METODI HELPER
    // =========================================================
    
    /**
    * Riscrive completamente il file utenti.csv con lo stato attuale della collezione GestoreUtenti.
    * Questo è necessario quando si aggiornano campi di un record esistente.
    * 
    * @throws IOException Se il file non può essere riscritto.
    */
    
    private void riscriviFileUtenti() throws IOException {
        Path csvPath = Paths.get(PATH_UTENTI);

        // 1. Ottieni la lista delle righe CSV dalla collezione aggiornata
        List<String> righeCSV = GestoreUtenti.getInstance().getList().stream()
                                             .map(Utente::toCSV)
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
    * All'azione del doppio click su una riga della tabella degli utenti, verrà visualizzato un
    * pop-up informativo con i pulsanti di modifica e di elimina.
    * 
    * @param utente l'utente di cui bisogna visualizzare le informazioni
    */
    
    private void showUserDetailsPopup(Utente utente) {
            // Crea la nuova finestra/Stage
            
            tfNome.setEditable(false);
            tfCognome.setEditable(false);
            tfMatricola.setEditable(false);
            tfEmail.setEditable(false);
            
            tfNome.setText(utente.getNome());
            tfCognome.setText(utente.getCognome());
            tfMatricola.setText(utente.getMatricola());
            tfEmail.setText(utente.getEmail());
            
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // Blocca l'interazione con la finestra principale
            popupStage.setTitle("Dettagli Utente: " + utente.getNome()); 

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

            grid.add(new Label("Nome:"), 0, 0);
            grid.add(tfNome, 1, 0);

            grid.add(new Label("Cognome:"), 0, 1);
            grid.add(tfCognome, 1, 1);

            grid.add(new Label("Matricola:"), 0, 2);
            grid.add(tfMatricola, 1, 2);

            grid.add(new Label("Email:"), 0, 3);
            grid.add(tfEmail, 1, 3);
            
            grid.add(new Label("Lista Prestiti:"), 0, 4);

            // Aggiungi la griglia al root
            root.getChildren().add(grid); 
            
            VBox prestitiContainer = new VBox(5); // Contenitore per i singoli prestiti
    
            if (!utente.getListaPrestiti().isEmpty()) {

                // Itera sulla lista dei prestiti dell'utente
                for (Prestito prestito : utente.getListaPrestiti()) {

                    // Crea una Label per visualizzare i dettagli del prestito
                    String dettaglio = String.format("• Libro: %s | ISBN: %s", 
                                                    prestito.getLibroPrestato().getTitolo(), 
                                                    prestito.getLibroPrestato().getISBN());

                    Label prestitoLabel = new Label(dettaglio);
                    prestitiContainer.getChildren().add(prestitoLabel);
                }
            } else {
                prestitiContainer.getChildren().add(new Label("Nessun prestito attivo."));
            }

            // Aggiungi il contenitore dei prestiti al layout principale
            root.getChildren().add(prestitiContainer);

            // Pulsanti
            HBox buttonBar = new HBox(10);
            buttonBar.setAlignment(Pos.CENTER);

            btnEdit.setText("Modifica");
            btnDelete.setText("Elimina");

            // Aggiungi i gestori di eventi ai pulsanti
            btnEdit.setOnAction(e -> handleEdit(utente, popupStage));
            btnDelete.setOnAction(e -> handleDelete(utente, popupStage));

            buttonBar.getChildren().addAll(btnEdit, btnDelete);

            root.getChildren().add(buttonBar);

            // Configura e mostra la scena
            Scene scene = new Scene(root);
            popupStage.setScene(scene);
            popupStage.show();
        }
    
    /**
    * All'azione del pulsante elimina del pop-up informativo di un utente, verrà visualizzato un
    * pop-up che chiede conferma per procedere con l'eliminazione.
    * 
    * @param utente l'utente da eliminare
    * @param popupStage La finestra di pop-up mostrata
    */
    
    private void handleDelete(Utente utente, Stage popupStage) {
        // **FASE 1: Conferma**
        if (!utente.getListaPrestiti().isEmpty()) {
            // Cambia il nome variabile: non è una conferma, è un errore
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);

            // Titolo della finestra (breve)
            errorAlert.setTitle("Errore Eliminazione");

            // Messaggio principale
            errorAlert.setHeaderText("Impossibile eliminare l'utente");

            // Dettaglio del problema
            errorAlert.setContentText("L'utente selezionato ha ancora dei prestiti attivi.\n" +
                                      "I libri devono essere restituiti prima di poter eliminare il profilo.");

            errorAlert.showAndWait();

            // Chiudi il popup corrente (se questa è l'azione desiderata dopo l'errore)
            popupStage.close();
        
            } else {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Conferma Eliminazione");
            confirmationAlert.setHeaderText("Sei sicuro di voler eliminare l'utente " + utente.getNome() + "?");
            confirmationAlert.setContentText("Questa azione è irreversibile.");

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // **FASE 2: Logica di Eliminazione**

                // 1. Elimina l'utente dal file e dalla lista utenti
                GestoreUtenti.getInstance().getList().remove(utente);

                // 2. Rimuovi l'utente dalla TableView per aggiornare l'interfaccia
                listaUtenti.remove(utente); 
                
                // 3. Riscrivi il file degli utenti con le nuove modifiche
                try {
                    riscriviFileUtenti();
                } catch (IOException ex) {
                    Logger.getLogger(ScenaListaUtentiController.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Chiudi il popup
                popupStage.close();
            }
        }
    }
    
    /**
    * All'azione del pulsante modifica del pop-up informativo di un utente, verrà attivata una modalità che da la possibilità
    * di modificare i campi dell'utente.
    * 
    * @param utente l'utente da modificare
    * @param popupStage La finestra di pop-up mostrata
    */
    
    private void handleEdit(Utente utente, Stage popupStage) {
            tfNome.setEditable(true);
            tfCognome.setEditable(true);
            tfEmail.setEditable(true);
            
            btnDelete.setText("Conferma");
            btnEdit.setText("Annulla");
            
            btnDelete.setOnAction(e -> handleConfermaEdit(utente, popupStage));
            btnEdit.setOnAction(e -> handleAnnullaEdit(utente, popupStage));
    }
    
    
    /**
     * All'azione del pulsante "conferma", verrà mostrato un pop-up di conferma che ci chiede
     * se vogliamo modificare in modo permanete l'utente.
     * 
     * @param utente l'utente di cui si intende confermare la modifica
     * @param popupStage la finestra di pop-up mostrata
     */
    
    private void handleConfermaEdit(Utente utente, Stage popupStage) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Conferma Modifica");
        confirmationAlert.setHeaderText("Sei sicuro di voler modificare l'utente?");
        confirmationAlert.setContentText("Questa azione è irreversibile.");

    Optional<ButtonType> result = confirmationAlert.showAndWait();

    if (result.isPresent() && result.get() == ButtonType.OK) {
        
            utente.setNome(tfNome.getText());
            utente.setCognome(tfCognome.getText());
            utente.setMatricola(tfMatricola.getText());
            utente.setEmail(tfEmail.getText());
            
            tabellaUtenti.refresh();
            
            try{
            riscriviFileUtenti();
            }catch(IOException ex){
                Logger.getLogger(ScenaListaUtentiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            popupStage.close();
    }   
    }
    
    /**
     * Cliccando su annulla il pop-up riguardante la modifica verrà chiuso
     * 
     * @param utente l'utente di cui si intende annullare la modifica
     * @param popupStage la finestra di pop-up mostrata
     */
    private void handleAnnullaEdit(Utente utente, Stage popupStage) {
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
