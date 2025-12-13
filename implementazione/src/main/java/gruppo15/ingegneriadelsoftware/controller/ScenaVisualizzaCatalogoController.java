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
public class ScenaVisualizzaCatalogoController implements Initializable {

    @FXML
    private AnchorPane sinistraSplitPane;
    @FXML
    private Button logout_button;
    @FXML
    private Button settings_button;
    @FXML
    private AnchorPane destraSplitPane;
    @FXML
    private TableColumn<Libro, String> colonnaTitolo;
    @FXML
    private TableColumn<Libro, String> colonnaAutori;
    @FXML
    private TableColumn<Libro, String> colonnaISBN;
    @FXML
    private TableColumn<Libro, LocalDate> colonnaDataPubblicazione;
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
    private TextField barraRicercaCatalogo;
    @FXML
    private TableView<Libro> tabellaCatalogo;
    
    private ObservableList<Libro> listaLibri;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
        listaLibri = FXCollections.observableArrayList();
        
        // Titolo
        colonnaTitolo.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getTitolo()));
        
        // ISBN
        colonnaISBN.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getISBN()));
        
        // Data di pubblicazione
        colonnaDataPubblicazione.setCellValueFactory(r -> new SimpleObjectProperty(r.getValue().getDataDiPubblicazione()));
        
        // Autori
        colonnaAutori.setCellValueFactory(r -> {
            String elenco = String.join(", ", r.getValue().getListaAutori());
            return new SimpleStringProperty(elenco);
        });

        // CARICAMENTO DATI
        // Prendo i dati dal GestoreLibri (Singleton)
        listaLibri.addAll(GestoreLibri.getInstance().getList());

        // FILTRO E RICERCA
        // Avvolgo la lista in una FilteredList
        FilteredList<Libro> filteredData = new FilteredList<>(listaLibri, p -> true);

        // Aggiungo il listener alla barra di ricerca
        barraRicercaCatalogo.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(libro -> {
               return libro.containsPattern(newValue);
            });
        });

        // ORDINAMENTO
        // Avvolgo la FilteredList in una SortedList
        SortedList<Libro> sortedData = new SortedList<>(filteredData);
        
        // Collego il comparatore della SortedList alla tabella (per cliccare sulle intestazioni)
        sortedData.comparatorProperty().bind(tabellaCatalogo.comparatorProperty());

        tabellaCatalogo.setItems(sortedData);
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
