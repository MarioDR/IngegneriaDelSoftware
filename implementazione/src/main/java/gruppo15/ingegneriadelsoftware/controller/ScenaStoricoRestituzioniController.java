/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.view.App;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author pierc
 */
public class ScenaStoricoRestituzioniController implements Initializable {

    @FXML
    private AnchorPane sinistraSplitPane;
    @FXML
    private Button logout_button1;
    @FXML
    private Button settings_button;
    @FXML
    private AnchorPane destraSplitPane;
    @FXML
    private TableColumn<?, ?> colonnaTitolo;
    @FXML
    private TableColumn<?, ?> colonnaAutori;
    @FXML
    private TableColumn<?, ?> colonnaISBN;
    @FXML
    private TableColumn<?, ?> colonnaDataPubblicazione;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
