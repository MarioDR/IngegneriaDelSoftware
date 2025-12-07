package gruppo15.ingegneriadelsoftware.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {

    @FXML
    private Label nameLbl; // Deve corrispondere a fx:id="nameLbl" nel FXML

    // ... altri campi e metodi ...

    @FXML
    private void handleModify() {
        System.out.println("Modify button clicked");
    }
    
    @FXML
    private void handleDelete() {
        System.out.println("Delete button clicked");
    }

    @FXML
    private void handleBack() {
        System.out.println("Back button clicked");
    }
}