/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.GestorePrestiti;
import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import gruppo15.ingegneriadelsoftware.view.App;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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
 * @author pierc
 */
public class ScenaAggiungiPrestitoController implements Initializable {

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
    private Button logout_button;
    @FXML
    private TextField matricolaField;
    @FXML
    private TextField ISBNField;
    @FXML
    private DatePicker dataRestituzioneField;
    @FXML
    private Label labelErrorePrestito;

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

    @FXML
    private void clickAggiungi(ActionEvent event) {
        String matricola = matricolaField.getText();
            String isbn = ISBNField.getText();
            LocalDate dataRestituzione = dataRestituzioneField.getValue();
            
            if (matricola.isEmpty() || isbn.isEmpty() || dataRestituzione == null) {
                labelErrorePrestito.setText("Errore: Compila tutti i campi!");
                labelErrorePrestito.setStyle("-fx-text-fill: red;");
                return;
            }
            
            List<Libro> listaLibriPro = GestoreLibri.getInstance().getList();
            List<Utente> listaUtentiPro = GestoreUtenti.getInstance().getList();
            Utente utente = null;
            Libro libro = null;
            
            for(Utente u : listaUtentiPro){
                if(u.getMatricola().equals(matricola)){
                    utente = u;
                    break;
                }
            }
            
            for(Libro l : listaLibriPro){
                if(l.getISBN().equals(isbn)){
                    libro = l;            
                    break;
                }
            }

            if(utente != null && libro != null && utente.hasMaxNumPrestiti() == false && libro.getNumeroCopie() > 0 ){
                // 4. Creazione Oggetto Libro
                Prestito nuovoPrestito= new Prestito(utente, libro, dataRestituzione);
                libro.rimuoviCopia();
                utente.addPrestito(nuovoPrestito);
                // 5. Salvataggio nel Gestore Condiviso (Singleton)
                GestorePrestiti.getInstance().add(nuovoPrestito);

                // 6. Feedback Successo
                labelErrorePrestito.setText("Prestito aggiunto con successo!");
                labelErrorePrestito.setStyle("-fx-text-fill: green;");
            
                // 7. Pulisco i campi per un nuovo inserimento
                pulisciCampi(); 
            }else{
                labelErrorePrestito.setText("Errore: Prestito non valido");
                labelErrorePrestito.setStyle("-fx-text-fill: red;");
                pulisciCampi();
            }
    }
    
    private void pulisciCampi() {
        matricolaField.clear();
        ISBNField.clear();
        dataRestituzioneField.setValue(null);
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
