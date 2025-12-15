package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Utente;
import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CREDENZIALI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @file ScenaAggiungiUtenteController.java
 * @brief Questa classe implementa tutti i metodi e le azioni collegate 
 * agli oggetti della scena 'ScenaAggiungiUtente.fxml'.
 *
 * @author Gruppo15
 * @version 1.0
 */

public class ScenaAggiungiUtenteController implements Initializable {

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
    private Label labelErroreUtente;
    @FXML
    private Button logout_button;
    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField matricolaField;
    @FXML
    private TextField emailField;

    /**
     * In questo metodo viene implementata la logica di binding tra il pulsante AGGIUNGI e i campi compilabili.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        labelErroreUtente.setText("");
        
        //IMPLEMENTAZIONE LOGICA DI DISABLE DEL PULSANTE AGGIUNGI
        BooleanBinding isFormValid = Bindings.createBooleanBinding(
        () -> {
            // Recupriamo i valori
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String matricola = matricolaField.getText().trim();
            String email = emailField.getText().trim();
            
            // --- CONDIZIONI DI BASE (TUTTO DEVE ESSERE VERO) ---

            // Tutti i campi TextField devono essere NON vuoti
            boolean textFieldsFilled = !nome.isEmpty() &&
                                       !cognome.isEmpty() &&
                                       !matricola.isEmpty() &&
                                       !email.isEmpty();                 
            
            // --- CONDIZIONI DI FORMATO (TUTTO DEVE ESSERE VERO) ---

            // 1. Nome (almeno 1 carattere qualsiasi)
            boolean nomeValid = nome.matches("[a-zA-Z\\sàèéìòùÀÈÉÌÒÙ']+"); 
            
            // 2. Cognome (almeno 1 carattere qualsiasi)
            boolean cognomeValid = cognome.matches("[a-zA-Z\\sàèéìòùÀÈÉÌÒÙ']+");
            
            // 3. Matricola (esattamente 10 cifre intere)
            boolean matricolaValid = matricola.matches("^[0-9]{10}$");
            
            // 6. Email 
            // (deve terminare con @studenti.unisa.it e il nome di dominio deve essere composto solo da caratteri alfanumerici e punti)
            boolean emailValid = email.matches("^[a-zA-Z0-9.]+@studenti\\.unisa\\.it$");
         
            // La forma è valida solo se tutte le verifiche di FORMATO sono vere
            return nomeValid && cognomeValid && matricolaValid && emailValid;
        },
        // Dipendenze: si aggiorna quando cambia il testo o la data
        nomeField.textProperty(),
        cognomeField.textProperty(),
        matricolaField.textProperty(),
        emailField.textProperty()
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
        nomeField.clear();
        cognomeField.clear();
        matricolaField.clear();
        emailField.clear();
    }
    
    // =========================================================
    // AZIONI DEI PULSANTI
    // =========================================================
    
    @FXML
    private void clickAggiungi(ActionEvent event) {
        try{
            
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String matricola = matricolaField.getText().trim();
            String email = emailField.getText().trim();

            // Creazione Oggetto Utente
            Utente nuovoUtente= new Utente(nome, cognome, matricola, email);
            
            // Verifica se si tratta di un duplicato
            if(GestoreUtenti.getInstance().contains(nuovoUtente)) {
                App.mostraMessaggioTemporaneo(labelErroreUtente, "Utente duplicato! Inserimento abortito.", "red", 3);
                return;
            }

            // Salvataggio del record sul file CSV corrispondente
            try (FileWriter writer = new FileWriter(App.PATH_UTENTI, true)) {
                // Aggiunge una nuova riga (newline) e il record CSV
                writer.write(System.lineSeparator() + nuovoUtente.toCSV());
            } catch (IOException e) {
                // Se la scrittura fallisce, visualizza l'errore
                App.mostraMessaggioTemporaneo(labelErroreUtente, "Errore nel salvataggio del nuovo utente!", "red", 3);
                return;
            }
            
            // Salvataggio nel Gestore Condiviso
            GestoreUtenti.getInstance().add(nuovoUtente);
            
            // Pulizia dei campi di testo
            App.mostraMessaggioTemporaneo(labelErroreUtente, "Utente aggiunto con successo!", "green", 3);
            
            // Visualizzazione del messaggio di operazione avvenuta con successo
            pulisciCampi(); 
        } catch (Exception e) {
            App.mostraMessaggioTemporaneo(labelErroreUtente, "Errore: " + e.getMessage(), "red", 3);
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
