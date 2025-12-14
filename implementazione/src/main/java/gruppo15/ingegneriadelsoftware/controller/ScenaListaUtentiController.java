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
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author pierc
 */
public class ScenaListaUtentiController implements Initializable {

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
    private TextField barraRicercaUtenti;
    @FXML
    private TableView<Utente> tabellaUtenti;
    @FXML
    private TableColumn<Utente, String> colonnaNome;
    @FXML
    private TableColumn<Utente, String> colonnaCognome;
    @FXML
    private TableColumn<Utente, String> colonnaMatricola;
    @FXML
    private TableColumn<Utente, String> colonnaEmail;
    
    private ObservableList<Utente> listaUtenti;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        listaUtenti = FXCollections.observableArrayList();
        
        // Nome
        colonnaNome.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getNome()));
        
        // Cognome
        colonnaCognome.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getCognome()));
        
        // Matricola
        colonnaMatricola.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getMatricola()));
        
        // Email
        colonnaEmail.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getEmail()));

        // CARICAMENTO DATI
        // Prendo i dati dal GestoreLibri 
        listaUtenti.addAll(GestoreUtenti.getInstance().getList());

        // FILTRO E RICERCA
        // Avvolgo la lista in una FilteredList
        FilteredList<Utente> filteredData = new FilteredList<>(listaUtenti, p -> true);

        // Aggiungo il listener alla barra di ricerca
        barraRicercaUtenti.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(utente -> {
                
                return utente.containsPattern(newValue);
            });
        });
        
        // ORDINAMENTO
        // Avvolgo la FilteredList in una SortedList
        SortedList<Utente> sortedData = new SortedList<>(filteredData);
        
        // Collego il comparatore della SortedList alla tabella (per cliccare sulle intestazioni)
        sortedData.comparatorProperty().bind(tabellaUtenti.comparatorProperty());

        tabellaUtenti.setItems(sortedData);
    }    

//============================================================================================================================
//                                        NAVIGABILITA' PARTE SINISTRA DELLO SPLIT PANE
//============================================================================================================================

    // =========================================================
    // SEZIONE PULSANTI DEL MENU'
    // =========================================================
    
    /**
     * Questo metodo è collegato all'azione del pulsante 'logout_button' e serve a tornare alla pagina di login.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickLogoutButton(ActionEvent event) {
        try {
            App.setRoot("ScenaLogin");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Questo metodo è collegato all'azione del pulsante 'settings_button' e serve ad aprire il menù delle impostazioni.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickSettingsButton(ActionEvent event) {
    }
    
    // =========================================================
    // SEZIONE GESTIONE DEL MENU'
    // =========================================================
    
    // ----------------------- UTENTI --------------------------
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaListaUtenti'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickListaUtenti(ActionEvent event) {
        try {
            App.setRoot("ScenaListaUtenti");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaAggiungiUtente'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickAggiungiUtente(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiUtente");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    // ---------------------- CATALOGO -------------------------
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaVisualizzaCatalogo'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickVisualizzaCatalogo(ActionEvent event) {
        try {
            App.setRoot("ScenaVisualizzaCatalogo");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaAggiungiLibro'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickAggiungiLibro(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiLibro");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    // -------------- PRESTITI E RESTITUZIONI ------------------
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaPrestitiAttivi'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickPrestitiAttivi(ActionEvent event) {
        try {
            App.setRoot("ScenaPrestitiAttivi");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaAggiungiPrestito'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickAggiungiPrestito(ActionEvent event) {
        try {
            App.setRoot("ScenaAggiungiPrestito");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaStoricoRestituzioni'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickStoricoRestituzioni(ActionEvent event) {
        try {
            App.setRoot("ScenaStoricoRestituzioni");
        } catch (IOException ex) {
            ex.printStackTrace();            
        }
    }

    // -------------------- STATISTICHE ------------------------
    
    /**
     * Questo metodo è legato all'azione dell'hyperlink del menù a tendina che ti riporta a 'ScenaVisualizzaStatistiche'.
     * 
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickVisualizzaStatistiche(ActionEvent event) {
    }
}
