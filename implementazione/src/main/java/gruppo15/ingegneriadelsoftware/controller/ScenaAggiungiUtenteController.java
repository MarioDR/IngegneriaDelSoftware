/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

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
    private Button logout_button1;
    @FXML
    private Button settings_button;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField usernameField1;
    @FXML
    private TextField usernameField11;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    }

    @FXML
    private void clickAnnulla(ActionEvent event) {
    }

    @FXML
    private void clickListaUtenti(ActionEvent event) {
        try {
            App.setRoot("VisualizzaUtenti");
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
