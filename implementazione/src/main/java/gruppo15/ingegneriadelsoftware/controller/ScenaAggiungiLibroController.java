/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.view.App;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mario
 */
public class ScenaAggiungiLibroController implements Initializable {

    @FXML
    private Button logout_button;
    @FXML
    private Button settings_button;
    @FXML
    private Label labelErroreLibro;
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
    private TextField titoloField;
    @FXML
    private TextField autoreField;
    @FXML
    private TextField ISBNField;
    @FXML
    private DatePicker dataPubblicazioneField;
    @FXML
    private TextField numeroCopieField;
    @FXML
    private TextField valoreField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labelErroreLibro.setText("");
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
    }

    @FXML
    private void clickAggiungi(ActionEvent event) {
        try{
            
            String titolo = titoloField.getText();
            String autori = autoreField.getText();
            String isbn = ISBNField.getText();
            LocalDate dataPub = dataPubblicazioneField.getValue();
            String copieStr = numeroCopieField.getText();
            String valoreStr = valoreField.getText();
            
            if (titolo.isEmpty() || autori.isEmpty() || isbn.isEmpty() || dataPub == null || copieStr.isEmpty() || valoreStr.isEmpty()) {
                labelErroreLibro.setText("Errore: Compila tutti i campi!");
                labelErroreLibro.setStyle("-fx-text-fill: red;");
                return;
            }

            // Conversione Numerica (può generare eccezione se scrivo lettere)
            int copie = Integer.parseInt(copieStr);
            float valore = Float.parseFloat(valoreStr);

            // Creazione Oggetto Libro
            Libro nuovoLibro = new Libro(titolo, autori, dataPub, isbn, copie, valore);

            // Salvataggio nel Gestore Condiviso 
            GestoreLibri.getInstance().add(nuovoLibro);

            labelErroreLibro.setText("Libro aggiunto con successo!");
            labelErroreLibro.setStyle("-fx-text-fill: green;");
            
            pulisciCampi(); 
            
        } catch (NumberFormatException e) {
            // Gestione errore se Copie o Valore non sono numeri
            labelErroreLibro.setText("Errore: 'Copie' e 'Valore' devono essere numeri validi!");
            labelErroreLibro.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            labelErroreLibro.setText("Errore: " + e.getMessage());
            labelErroreLibro.setStyle("-fx-text-fill: red;");
        }
    }

    private void pulisciCampi() {
        titoloField.clear();
        autoreField.clear();
        ISBNField.clear();
        numeroCopieField.clear();
        valoreField.clear();
        dataPubblicazioneField.setValue(null);
    }
    
    @FXML
    private void clickAnnulla(ActionEvent event) {
        pulisciCampi();
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
