/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.GestorePrestiti;
import gruppo15.ingegneriadelsoftware.model.GestoreRestituzioni;
import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Restituzione;
import gruppo15.ingegneriadelsoftware.model.Utente;
import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CREDENZIALI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author pierc
 */
public class ScenaVisualizzaStatisticheController implements Initializable {

    @FXML
    private AnchorPane sinistraSplitPane;
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
    private Button settings_button;
    @FXML
    private AnchorPane destraSplitPane;
    @FXML
    private TableView<Utente> tabellaUtenti;
    @FXML
    private TableColumn<Utente, String> colonnaNomeCognome;
    @FXML
    private TableColumn<Utente, String> colonnaMatricola;
    @FXML
    private TableColumn<Utente, String> colonnaEmail;
    @FXML
    private TableColumn<Utente, String> colonnaTemp;
    @FXML
    private Label numLibri;
    @FXML
    private Label numUtenti;
    @FXML
    private Label numPrestiti;
    @FXML
    private Label numSpese;
    
    private TextField vecchioUsernameField;
    private TextField vecchiaPasswordField;
    private TextField nuovoUsernameField;
    private TextField nuovaPasswordField;
    private Label labelMessaggioErrore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        colonnaNomeCognome.setCellValueFactory(cellData -> {
        Utente u = cellData.getValue(); 
        String nomeCompleto = u.getNome() + " " + u.getCognome();
        return new javafx.beans.property.SimpleStringProperty(nomeCompleto);
    });
        colonnaMatricola.setCellValueFactory(new PropertyValueFactory<>("matricola"));
        colonnaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        colonnaTemp.setCellValueFactory(cellData -> {
        Utente u = cellData.getValue();

        String testoDaMostrare = inattivoNuovo(u);

        return new javafx.beans.property.SimpleStringProperty(testoDaMostrare);
    });

        caricaUtentiDormienti();
        caricaLibriUtenti();
        prestitiDueMesi();
        speseUltimoMese();
    }

    // INIZIO COLLEGAMENTO SCENE

    @FXML
    private void clickListaUtenti(ActionEvent event) {
        try {
            App.setRoot("ScenaListaUtenti");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    @FXML
    private void clickAggiungiUtente(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiUtente");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    @FXML
    private void clickVisualizzaCatalogo(ActionEvent event) {
        try {
            App.setRoot("ScenaVisualizzaCatalogo");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    @FXML
    private void clickAggiungiLibro(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiLibro");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    @FXML
    private void clickPrestitiAttivi(ActionEvent event) {
        try {
            App.setRoot("ScenaPrestitiAttivi");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    @FXML
    private void clickAggiungiPrestito(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiPrestito");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    @FXML
    private void clickStoricoRestituzioni(ActionEvent event) {
        try {
            App.setRoot("ScenaStoricoRestituzioni");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    @FXML
    private void clickVisualizzaStatistiche(ActionEvent event) {
        try {
            App.setRoot("ScenaVisualizzaStatistiche");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    @FXML
    private void clickLogoutButton(ActionEvent event) {
        try {
            App.setRoot("ScenaLogin");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

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
    
    // FINE COLLEGAMENTO SCENE
    
    /* il metodo utentiDormienti serve a verificare se un utente è inattivo o no da 3 o più mesi.
       La verifica viene effettuata confrontando la data dell'ultimo prestito con la data odierna
    */
    
    public List<Utente> utentiDormienti() {
        List<Utente> dormienti = new ArrayList<>();
        LocalDate dataSoglia = LocalDate.now().minusMonths(3); 
        List<Utente> tuttiGliUtenti = GestoreUtenti.getInstance().getList();
        List<Prestito> tuttiIPrestiti = GestorePrestiti.getInstance().getList();
        List<Restituzione> tutteLeRestituzioni = GestoreRestituzioni.getInstance().getList();
        
        LocalDate ultimaRestituzione = LocalDate.MIN;
        boolean haStorico = false;

        for (Utente u : tuttiGliUtenti) {
            String matricolaCorrente = u.getMatricola();
            
            boolean haPrestitiAttivi = false;
            
            for (Prestito p : tuttiIPrestiti) {
                if (p.getUtenteAssegnatario().getMatricola().equalsIgnoreCase(matricolaCorrente)) {
                    haPrestitiAttivi = true;
                    break; //Se l'utente ha prestiti attivi significa che non è dormiente
                }
            }

            if (haPrestitiAttivi) {
                continue;
            }

            for (Restituzione r : tutteLeRestituzioni) {
                if (r.getPrestitoDaRestituire().getUtenteAssegnatario().getMatricola().equalsIgnoreCase(matricolaCorrente)) {
                    haStorico = true;
                    if (r.getDataRestituzione().isAfter(ultimaRestituzione)) {
                        ultimaRestituzione = r.getDataRestituzione();
                    }
                }
            }
            /*Se l'utente ha uno storico senza prestiti attivi e/o l'ultima restituzione risale a 3 o più mesi fa
              viene aggiunto all'interno della lista.
            */
            if (!haStorico) {
                dormienti.add(u);
            } else if (ultimaRestituzione.isBefore(dataSoglia)) {
                dormienti.add(u);
            }
        }    
        return dormienti;
    }
    
    // Il metodo caricaUtentiDormienti prende la lista degli utenti dormienti e li carica in tabella.

    private void caricaUtentiDormienti() {
        List<Utente> listaDormienti = utentiDormienti();
        tabellaUtenti.setItems(FXCollections.observableArrayList(listaDormienti));
    }
    
    /* Il metodo inattivoNuovo verifica se l'utente è inattivo oppure è nuovo. Un utente si
       definisce "nuovo" se si è registrato da meno di 3 mesi.
    */
    
    private String inattivoNuovo(Utente u) {
        LocalDate ultimaRestituzione = LocalDate.MIN;
        boolean haStorico = false;

        for (Restituzione r : GestoreRestituzioni.getInstance().getList()) {
            if (r.getPrestitoDaRestituire().getUtenteAssegnatario().equals(u)) {
                haStorico = true;
                if (r.getDataRestituzione().isAfter(ultimaRestituzione)) {
                    ultimaRestituzione = r.getDataRestituzione();
                }
            }
        }

        if (!haStorico || ultimaRestituzione.equals(LocalDate.MIN)) {
            return "Nuovo utente";
        }

        long giorniPassati = java.time.temporal.ChronoUnit.DAYS.between(ultimaRestituzione, LocalDate.now());

        return giorniPassati + " giorni fa";
    }
      
    /* Il metodo caricaLibriUtenti fa visualizzare a schermo il numero totale dei
       libri all'interno della biblioteca.
    */
    
    private void caricaLibriUtenti() {
        int totaleCopie = 0;
        
        for (Libro l : GestoreLibri.getInstance().getList()) {
            totaleCopie += l.getNumeroCopieRimanenti(); 
        }

        numLibri.setText(String.valueOf(totaleCopie));

        int totUtenti = GestoreUtenti.getInstance().getList().size();
        numUtenti.setText(String.valueOf(totUtenti));
    }
    
    /* Il metodo prestitiDueMesi fa visualizzare a schermo il numero di libri
       prestati negli ultimi due mesi.
    */
    
    private void prestitiDueMesi() {
        int contatore = 0;
        LocalDate dataSoglia = LocalDate.now().minusMonths(2);

        for (Prestito p : gruppo15.ingegneriadelsoftware.model.GestorePrestiti.getInstance().getList()) {
            if (p.getDataInizioPrestito().isAfter(dataSoglia)) {
                contatore++;
            }
        }

        for (Restituzione r : GestoreRestituzioni.getInstance().getList()) {
            if (r.getPrestitoDaRestituire().getDataInizioPrestito().isAfter(dataSoglia)) {
                contatore++;
            }
        }

        numPrestiti.setText(String.valueOf(contatore));
    }
    
    /* Il metodo speseUltimoMese mostra a schermo quanto è stato speso
       nell'ultimo mese per aggiungere dei libri nel catalogo
    */
    
    private void speseUltimoMese() {
        double totaleSpese = 0.0;
        LocalDate dataSoglia = LocalDate.now().minusMonths(1);

        for (Libro l : GestoreLibri.getInstance().getList()) {
            if (l.getDataDiPubblicazione().isAfter(dataSoglia)) {
                double costoTotaleLibro = l.getValore() * l.getNumeroCopieDiStock();
                totaleSpese += costoTotaleLibro;
            }
        }
        numSpese.setText(String.format("%.2f €", totaleSpese));
    }
    
}
