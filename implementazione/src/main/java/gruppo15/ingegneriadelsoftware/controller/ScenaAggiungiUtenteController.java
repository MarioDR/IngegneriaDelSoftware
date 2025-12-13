/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Utente;
import gruppo15.ingegneriadelsoftware.view.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mario
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelErroreUtente.setText("");
    }    

    @FXML
    private void clickLogoutButton(ActionEvent event) {
        // 2. Cambio di Scena
        try {
            // Chiama il metodo statico per caricare ScenaMenu.fxml
            // e sostituire la radice della Scene corrente.
            App.setRoot("ScenaLogin"); 
        } 
        catch (IOException e) {
            // Gestione dell'errore nel caso il file ScenaMenu.fxml non venga trovato
            e.printStackTrace();
        }
    }

    @FXML
    private void clickSettingsButton(ActionEvent event) {
    }

    @FXML
    private void clickAggiungi(ActionEvent event) {
        try{
            
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String matricola = matricolaField.getText();
            String email = emailField.getText();
            
            if (nome.isEmpty() || cognome.isEmpty() || matricola.isEmpty() || email.isEmpty()) {
                labelErroreUtente.setText("Errore: Compila tutti i campi!");
                labelErroreUtente.setStyle("-fx-text-fill: red;");
                return;
            }

            // Creazione Oggetto Libro
            Utente nuovoUtente= new Utente(nome, cognome, matricola, email);

            // Salvataggio nel Gestore Condiviso
            GestoreUtenti.getInstance().add(nuovoUtente);

            labelErroreUtente.setText("Utente aggiunto con successo!");
            labelErroreUtente.setStyle("-fx-text-fill: green;");
            
            pulisciCampi(); 
            
        } catch (NumberFormatException e) {
            // Gestione errore se Copie o Valore non sono numeri
            labelErroreUtente.setText("Errore: La matricola deve essere un numero!");
            labelErroreUtente.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            labelErroreUtente.setText("Errore: " + e.getMessage());
            labelErroreUtente.setStyle("-fx-text-fill: red;");
        }
    }
    
     private void pulisciCampi() {
        nomeField.clear();
        cognomeField.clear();
        matricolaField.clear();
        emailField.clear();
    }

    @FXML
    private void clickAnnulla(ActionEvent event) {
    }

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
    }
    
}
