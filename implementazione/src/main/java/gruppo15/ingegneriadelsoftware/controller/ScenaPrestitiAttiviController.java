/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.model.GestorePrestiti;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.view.App;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author mario
 */
public class ScenaPrestitiAttiviController implements Initializable {

    @FXML
    private AnchorPane sinistraSplitPane;
    @FXML
    private Button settings_button;
    @FXML
    private AnchorPane destraSplitPane;
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
    private TextField barraRicercaPrestiti;
    @FXML
    private TableView<Prestito> tabellaPrestitiAttivi;
    @FXML
    private TableColumn<Prestito, String> colonnaMatricola;
    @FXML
    private TableColumn<Prestito, String> colonnaEmail;
    @FXML
    private TableColumn<Prestito, String> colonnaISBN;
    @FXML
    private TableColumn<Prestito, LocalDate> colonnaDataPrevistaRestituzione;
    @FXML
    private TableColumn<Prestito, Long> colonnaRitardo;
    
    private ObservableList<Prestito> listaPrestiti;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
                 // --- A. CONFIGURAZIONE COLONNE (Con Lambda) ---
        listaPrestiti = FXCollections.observableArrayList();
        
        // --- COLONNE CORRETTE ---
        
        // MATRICOLA: Entro nel prestito -> prendo l'utente -> prendo la matricola
        colonnaMatricola.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtenteAssegnatario().getMatricola()));
        
        // EMAIL: Entro nel prestito -> prendo l'utente -> prendo l'email
        colonnaEmail.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getUtenteAssegnatario().getEmail()));
        
        // ISBN: Entro nel prestito -> prendo il libro -> prendo l'ISBN
        colonnaISBN.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getLibroPrestato().getISBN()));
        
        // DATA: Questa è direttamente nel prestito
        colonnaDataPrevistaRestituzione.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getDataPrevistaRestituzione()));

        // RITARDO: Anche questo è nel prestito (se hai implementato il metodo)
        // Usa asObject() per evitare problemi con i long primitivi
        colonnaRitardo.setCellValueFactory(r -> new SimpleObjectProperty<>(r.getValue().getGiorniDiRitardo()));
        
        
        // --- CARICAMENTO DATI ---
        listaPrestiti.addAll(GestorePrestiti.getInstance().getList());
        
        // --- FILTRO ---
        FilteredList<Prestito> filteredData = new FilteredList<>(listaPrestiti, p -> true);

        barraRicercaPrestiti.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(prestito -> {
                if (newValue == null || newValue.isEmpty()) return true;
                
                String lowerCaseFilter = newValue.toLowerCase();
                
                // Ricerca approfondita
                if (prestito.getUtenteAssegnatario().getMatricola().toLowerCase().contains(lowerCaseFilter)) return true;
                else if (prestito.getLibroPrestato().getISBN().toLowerCase().contains(lowerCaseFilter)) return true;
                
                return false;
            });
        });

        SortedList<Prestito> sortedData = new SortedList<>(filteredData);
        
        // Collego il comparatore della SortedList alla tabella (per cliccare sulle intestazioni)
        sortedData.comparatorProperty().bind(tabellaPrestitiAttivi.comparatorProperty());

        // Imposta i dati nella tabella
        tabellaPrestitiAttivi.setItems(sortedData);
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
