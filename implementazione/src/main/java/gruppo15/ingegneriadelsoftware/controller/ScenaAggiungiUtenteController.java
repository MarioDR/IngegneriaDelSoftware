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
    private Label labelErroreLogin;
    @FXML
    private Button aggiungiButton;
    @FXML
    private Button annullaButton;

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
    
}
