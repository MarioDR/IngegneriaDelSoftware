/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.view.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author fgrim
 */
public class ScenaLoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label labelErroreLogin;
    @FXML
    private Button loginButtonAccedi;

    @FXML
    private void clickAccedi(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // ----------------------------------------------------
        // 1. Validazione (Logica Fittizia)
        // ----------------------------------------------------
        if (username.equals("") && password.equals("")) {
            
            // 2. Cambio di Scena
            try {
                // Chiama il metodo statico per caricare ScenaMenu.fxml
                // e sostituire la radice della Scene corrente.
                App.setRoot("ScenaMenu"); 
            } 
            catch (IOException e) {
                // Gestione dell'errore nel caso il file ScenaMenu.fxml non venga trovato
                e.printStackTrace();
                labelErroreLogin.setText("Errore nel caricamento della scena principale. Controllare i file FXML.");
            }
        } 
        else {
            // Credenziali errate
            labelErroreLogin.setText("❌ Credenziali non valide. Riprovare.");
        }
    }
}
