package gruppo15.ingegneriadelsoftware.controller;

import gruppo15.ingegneriadelsoftware.view.App;
import static gruppo15.ingegneriadelsoftware.view.App.PATH_CREDENZIALI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @file ScenaLoginController.java
 * @brief Questa classe implementa tutti i metodi e le azioni collegate 
 * agli oggetti della scena 'ScenaLogin.fxml'.
 *
 * @author Gruppo15
 * @version 1.0
 */

public class ScenaLoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label labelErroreLogin;
    @FXML
    private Button aggiungiButton;
    @FXML
    private Button annullaButton;

    /**
     * Il metodo è collegato all'azione del pulsante 'Accedi' e tenta di autenticare l'utente.
     * 
     * @post Verrà permesso il login e il caricamento della scena principale se le credenziali sono corrette
     * @param event L'evento del click sul pulsante.
     * @throws Exception Se si verifica un errore non gestito nel cambio di scena.
     */
    
    @FXML
    private void clickAccedi(ActionEvent event) throws Exception {
        String usernameVera;
        String passwordVera;
        
        // ----------------------------------------------------
        // 1. Lettura e Validazione Credenziali da CSV
        // ----------------------------------------------------
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_CREDENZIALI))) {
            
            String line = br.readLine();
            
            // Gestione robusta: Controlla se il file è vuoto o se la riga letta è vuota
            if (line == null || line.trim().isEmpty()) {
                App.mostraMessaggioTemporaneo(labelErroreLogin, "❌ Errore: File credenziali vuoto o malformato.", "red", 3);
                return;
            }
            
            String values[] = line.split(",");
            
            // Gestione robusta: Controlla se ci sono almeno 2 campi
            if (values.length < 2) {
                App.mostraMessaggioTemporaneo(labelErroreLogin, "❌ Errore: Credenziali incomplete nel file CSV (servono username,password).", "red", 3);
                return;
            }
            
            // Estrazione sicura
            usernameVera = values[0].trim();
            passwordVera = values[1].trim();
            
        } catch (IOException e) {
            App.mostraMessaggioTemporaneo(labelErroreLogin, "❌ Errore I/O: Impossibile trovare o leggere il file credenziali!", "red", 3);
            return;
        }
        
        // ----------------------------------------------------
        // 2. Confronto e Login
        // ----------------------------------------------------
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (username.equals(usernameVera) && password.equals(passwordVera)) {
            // Credenziali valide: Cambio di Scena
            try {
                App.setRoot("ScenaMenu");
            } 
            catch (IOException e) {
                App.mostraMessaggioTemporaneo(labelErroreLogin, "❌ Errore nel caricamento della scena principale. Controllare i file FXML.", "red", 3);
            }
        } else {
            // Credenziali non valide
            App.mostraMessaggioTemporaneo(labelErroreLogin, "❌ Credenziali non valide. Riprovare.", "red", 3);
        }
    }


    /**
     * Questo metodo è associato all'azione del pulsante 'annullaButton' che serve a ripulire tutti i campi dei textField.
     * 
     * @post tutti i campi TextField vengono svuotati
     * @param event l'evento del click sul pulsante
     */
    
    @FXML
    private void clickAnnulla(ActionEvent event) {
        usernameField.clear();
        passwordField.clear();
    }
}
