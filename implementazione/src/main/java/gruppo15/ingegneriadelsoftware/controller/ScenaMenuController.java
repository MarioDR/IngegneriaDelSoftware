/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.view.App;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author fgrim
 */
public class ScenaMenuController {
    @FXML
    private AnchorPane sinistraSplitPane;
    @FXML
    private AnchorPane destraSplitPane;
    @FXML
    private Button logout_button1;
    @FXML
    private Button settings_button;
    @FXML
    private Hyperlink menuListaUtenti;
    @FXML
    private Hyperlink menuAggiungiUtente;
    @FXML
    private Hyperlink menuAggiungiLibro;
    @FXML
    private Hyperlink menuVisualizzaCatalogo;
    @FXML
    private Hyperlink menuPrestitiAttivi;
    @FXML
    private Hyperlink menuAggiungiPrestito;

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
    private void aggiungiUtente(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiUtente");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void visualizzaListaUtenti(ActionEvent event) {
        try {
            App.setRoot("VisualizzaUtenti");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void aggiungiLibro(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiLibro");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void visualizzaCatalogo(ActionEvent event) {
        try {
            App.setRoot("ScenaVisualizzaCatalogo");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void aggiungiPrestito(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiPrestito");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void prestitiAttivi(ActionEvent event) {
        try {
            App.setRoot("ScenaPrestitiAttivi");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}