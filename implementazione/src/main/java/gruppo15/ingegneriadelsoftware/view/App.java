package gruppo15.ingegneriadelsoftware.view;

import gruppo15.ingegneriadelsoftware.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * JavaFX App
 */ 
public class App extends Application {

    private static Scene scene;

    public static final String PATH_CATALOGO = "./src/main/resources/gruppo15/ingegneriadelsoftware/archivio/libri.csv";
    public static final String PATH_UTENTI = "./src/main/resources/gruppo15/ingegneriadelsoftware/archivio/utenti.csv";
    public static final String PATH_PRESTITI = "./src/main/resources/gruppo15/ingegneriadelsoftware/archivio/prestiti.csv";
    public static final String PATH_RESTITUZIONI = "./src/main/resources/gruppo15/ingegneriadelsoftware/archivio/restituzioni.csv";
    public static final String PATH_CREDENZIALI = "./src/main/resources/gruppo15/ingegneriadelsoftware/archivio/credenziali.csv";
    
    //=======================================
    // IN MANUTENZIONE
    //=======================================
    
    @Override
    public void start(Stage stage) throws IOException {
        // Fase di caricamento dell'archivio nei manager
        caricaLibriDaCSV();
        caricaUtentiDaCSV();
        caricaPrestitiDaCSV();
        caricaRestituzioniDaCSV();
        
        // Avvio della prima scena
        scene = new Scene(loadFXML("ScenaLogin"));
        stage.setTitle("MyBiblioUNISA");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * Visualizza un messaggio di errore o successo temporaneo in una Label
     * e lo fa sparire dopo un intervallo con effetto dissolvenza.
     * 
     * @param label La Label da utilizzare per la visualizzazione del messaggio.
     * @param messaggio Il testo da visualizzare.
     * @param colore CSS per il testo (es: "-fx-text-fill: red;").
     * @param durataVisualizzazione Durata in secondi prima che inizi la dissolvenza.
    */
    
    public static void mostraMessaggioTemporaneo(Label label, String messaggio, String colore, double durataVisualizzazione) {
        // 1. Imposta il messaggio e rendi la Label visibile
        label.setText(messaggio);
        label.setStyle("-fx-text-fill: " + colore + ";");
        label.setOpacity(1.0); // Assicurati che sia completamente visibile

        // 2. Crea la transizione di pausa (ritardo di 5 secondi)
        PauseTransition pausa = new PauseTransition(Duration.seconds(durataVisualizzazione));

        // 3. Definisci cosa succede dopo la pausa (Dissolvenza)
        pausa.setOnFinished(event -> {
            // Crea la transizione di dissolvenza (ad esempio, 1.5 secondi)
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), label);
            fadeOut.setFromValue(1.0); // Opacità iniziale (visibile)
            fadeOut.setToValue(0.0);   // Opacità finale (invisibile)

            // Alla fine della dissolvenza, pulisci il testo e nascondi la Label
            fadeOut.setOnFinished(fadeEvent -> {
                label.setText("");
                // Opzionale: rimetti l'opacità a 1.0 per la prossima volta che la userai
                label.setOpacity(1.0); 
            });

            fadeOut.play();
        });

        // 4. Avvia la pausa
        pausa.play();
    }
    
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void caricaLibriDaCSV() throws IOException {
        // Usiamo un try-with-resources per assicurarci che il file venga chiuso
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_CATALOGO))) {
            
            String line;
            
            // Leggiamo la prima riga per saltare l'intestazione, se presente
            br.readLine(); 
            
            // Leggiamo le righe successive finché ci sono dati
            while ((line = br.readLine()) != null && !line.equals("")) {
                
                // 1. Spezzare la riga usando la virgola come delimitatore
                String[] values = line.split(",");

                try {
                    // I primi 5 valori sono i campi fissi: Titolo, ISBN, Copie, Valore, data di pubblicazione. 
                    String titolo = values[0].trim();
                    String isbn = values[1].trim();
                    int numeroCopie = Integer.parseInt(values[2].trim());
                    float valore = Float.parseFloat(values[3].trim());
                    LocalDate data = LocalDate.parse(values[4].trim());
                    
                    // Ricostruisce la stringa degli autori (dal 5° elemento in poi)
                    String autoriCSV = Arrays.stream(values, 5, values.length).collect(Collectors.joining(","));
                    
                    Libro nuovoLibro = new Libro(titolo, autoriCSV, data, isbn, numeroCopie, valore);

                    // Aggiunta dell'oggetto al gestore
                    GestoreLibri.getInstance().add(nuovoLibro);
                    
                } catch (NumberFormatException e) {
                    System.err.println("Errore di conversione numerica nella riga: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore I/O durante la lettura del file CSV: " + e.getMessage());
        }
    }
    
    private void caricaUtentiDaCSV() throws IOException {
        
        // Usiamo un try-with-resources per assicurarci che il file venga chiuso
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_UTENTI))) {
            String line;
            // Leggiamo la prima riga per saltare l'intestazione, se presente
            br.readLine(); 
            
            // Leggiamo le righe successive finché ci sono dati
            while ((line = br.readLine()) != null && !line.equals("")) {
                // Spezzare la riga usando la virgola come delimitatore
                String[] values = line.split(",");

                // I primi 4 valori sono i campi fissi: Nome, Cognome, Matricola, Email. 
                String nome = values[0].trim();
                String cognome = values[1].trim();
                String matricola = values[2].trim();
                String email = values[3].trim();

                // Ricerca dei prestiti di questo utente nel CSV dei prestiti
                
                // Ricavo gli ID
                List<String> listaID = Arrays.stream(values[5].split("#"))
                                    .filter(s -> !s.isEmpty()) // Rimuove l'elemento vuoto iniziale
                                    .collect(Collectors.toList());
                
                List<Prestito> listaPrestiti = new ArrayList<>();
                
                // Ricostruisco i prestiti
                for(String elem : listaID) {
                    int id = Integer.valueOf(elem);
                    //listaPrestiti.add(ricercaPrestito(id));
                }
                
                Utente nuovoUtente = new Utente(nome, cognome, matricola, email, listaPrestiti);

                // Aggiunta dell'oggetto al gestore
                GestoreUtenti.getInstance().add(nuovoUtente);
            }
        } catch (IOException e) {
            System.err.println("Errore I/O durante la lettura del file CSV: " + e.getMessage());
        }
    }
    
    private void caricaPrestitiDaCSV() throws IOException {
        // Usiamo un try-with-resources per assicurarci che il file venga chiuso
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_PRESTITI))) {
            
            String line;
            // Leggiamo la prima riga per saltare l'intestazione, se presente
            br.readLine(); 
            
            // Leggiamo le righe successive finché ci sono dati
            while ((line = br.readLine()) != null && !line.equals("")) {
                
                // 1. Spezzare la riga usando la virgola come delimitatore
                String[] values = line.split(",");

                try {
                    // I primi 3 campi sono: ID, date di inizio e fine prevista
                    int ID = Integer.parseInt(values[0].trim());
                    LocalDate dataInizio = LocalDate.parse(values[1].trim());
                    LocalDate dataFine = LocalDate.parse(values[2].trim());
                    
                    // I prossimi 4 valori sono i campi fissi dell'utente: Nome, Cognome, Matricola, Email. 
                    String nome = values[3].trim();
                    String cognome = values[4].trim();
                    String matricola = values[5].trim();
                    String email = values[6].trim();
                    
                    // Separo e ricavo il campo dei prestiti attivi
                    List<String> listaIDPrestiti = Arrays.stream(values[7].split("#"))
                                    .filter(s -> !s.isEmpty()) // Rimuove l'elemento vuoto iniziale
                                    .collect(Collectors.toList());
                    
                    // I prossimi 5 valori sono i campi fissidel libro: Titolo, ISBN, Copie, Valore, data di pubblicazione. 
                    String titolo = values[7].trim();
                    String isbn = values[8].trim();
                    int numeroCopie = Integer.parseInt(values[9].trim());
                    float valore = Float.parseFloat(values[10].trim());
                    LocalDate dataPubblicazione = LocalDate.parse(values[11].trim());
                    
                    // Ricostruisce la stringa degli autori (dal 12° elemento in poi)
                    String autoriCSV = Arrays.stream(values, 12, values.length).collect(Collectors.joining(","));
                    
                    //costruzione dell'oggetto prestito
                    Utente nuovoUtenteAssegnatario = new Utente(nome, cognome, matricola, email);
                    Libro nuovoLibroPrestato = new Libro(titolo, autoriCSV, dataPubblicazione, isbn, numeroCopie, valore);
                    Prestito nuovoPrestito = new Prestito(ID, nuovoUtenteAssegnatario, nuovoLibroPrestato, dataInizio, dataFine);
                    
                    // Aggiunta dell'oggetto al gestore
                    GestorePrestiti.getInstance().add(nuovoPrestito);
                } catch (NumberFormatException e) {
                    System.err.println("Errore di conversione numerica nella riga: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore I/O durante la lettura del file CSV: " + e.getMessage());
        }
    }
    
    private void caricaRestituzioniDaCSV() throws IOException {
        // Usiamo un try-with-resources per assicurarci che il file venga chiuso
        try (BufferedReader br = new BufferedReader(new FileReader(PATH_RESTITUZIONI))) {
            
            String line;
            // Leggiamo la prima riga per saltare l'intestazione, se presente
            br.readLine(); 
            
            // Leggiamo le righe successive finché ci sono dati
            while ((line = br.readLine()) != null && !line.equals("")) {
                
                // 1. Spezzare la riga usando la virgola come delimitatore
                String[] values = line.split(",");

                try {
                    // I primi 4 campi sono: data effettiva di restituzione, ID prestito, date di inizio e fine prevista
                    LocalDate dataEffettiva = LocalDate.parse(values[0]);
                    int ID = Integer.parseInt(values[1].trim());
                    LocalDate dataInizio = LocalDate.parse(values[2].trim());
                    LocalDate dataFine = LocalDate.parse(values[3].trim());
                    
                    // I prossimi 4 valori sono i campi fissi dell'utente: Nome, Cognome, Matricola, Email. 
                    String nome = values[4].trim();
                    String cognome = values[5].trim();
                    String matricola = values[6].trim();
                    String email = values[7].trim();
                    
                    // I prossimi 5 valori sono i campi fissidel libro: Titolo, ISBN, Copie, Valore, data di pubblicazione. 
                    String titolo = values[8].trim();
                    String isbn = values[9].trim();
                    int numeroCopie = Integer.parseInt(values[10].trim());
                    float valore = Float.parseFloat(values[11].trim());
                    LocalDate dataPubblicazione = LocalDate.parse(values[12].trim());
                    
                    // Ricostruisce la stringa degli autori (dal 13° elemento in poi)
                    String autoriCSV = Arrays.stream(values, 13, values.length).collect(Collectors.joining(","));
                    
                    //costruzione dell'oggetto restituzione
                    Utente nuovoUtenteAssegnatario = new Utente(nome, cognome, matricola, email);
                    Libro nuovoLibroPrestato = new Libro(titolo, autoriCSV, dataPubblicazione, isbn, numeroCopie, valore);
                    Prestito prestitoRestituito = new Prestito(ID, nuovoUtenteAssegnatario, nuovoLibroPrestato, dataInizio, dataFine);
                    Restituzione nuovaRestituzione = new Restituzione(dataEffettiva, prestitoRestituito);
                    
                    // Aggiunta dell'oggetto al gestore
                    GestoreRestituzioni.getInstance().add(nuovaRestituzione);
                } catch (NumberFormatException e) {
                    System.err.println("Errore di conversione numerica nella riga: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore I/O durante la lettura del file CSV: " + e.getMessage());
        }
    }
    
    /**
     * Ricostruisce un oggetto Prestito da una singola riga CSV.
     * * Il formato atteso della riga (valori separati da virgola) è:
     * ID_Prestito,DataInizio,DataPrevista,
     * NomeUtente,CognomeUtente,MatricolaUtente,EmailUtente,ListaIDPrestitiUtente,
     * TitoloLibro,ISBNLibro,CopieLibro,ValoreLibro,DataPubblicazioneLibro,AutoriLibro
     * * @param csvLine La riga CSV da parsare.
     * @return L'oggetto Prestito ricostruito.
     * @throws IllegalArgumentException Se la riga è malformata o i dati non sono validi.
     */
    
    public static Prestito parsePrestitoDaCSV(String csvLine) throws IllegalArgumentException {
        
        if (csvLine == null || csvLine.trim().isEmpty()) {
            throw new IllegalArgumentException("La riga CSV è vuota.");
        }
        
        // Usiamo split con limitatore -1 per preservare anche i campi vuoti
        String[] values = csvLine.split(",", -1);
        
        // Verifica del numero minimo di campi (Prestito: 3 + Utente: 5 + Libro: 6 = 14 campi)
        // La lista ID Prestiti Utente è un campo singolo separato da '#'.
        if (values.length < 14) { 
            throw new IllegalArgumentException("Riga CSV Prestito malformata. Campi attesi >= 14, trovati: " + values.length);
        }

        try {
            // =========================================================
            // 1. CAMPI DEL PRESTITO
            // =========================================================
            int ID = Integer.parseInt(values[0].trim());
            LocalDate dataInizio = LocalDate.parse(values[1].trim());
            LocalDate dataPrevista = LocalDate.parse(values[2].trim());

            // =========================================================
            // 2. RICOSTRUZIONE UTENTE (Campi 3-7)
            // =========================================================
            String nomeUtente = values[3].trim();
            String cognomeUtente = values[4].trim();
            String matricola = values[5].trim();
            String email = values[6].trim();
            String prestitiIDString = values[7].trim(); // Formato: #1#2#3...
            
            // Creazione di un Utente 'Placeholder' per il costruttore del Prestito
            // Questo Utente avrà tutti i campi corretti, ma NON avrà ancora l'oggetto Prestito nella sua lista
            Utente utentePlaceholder = new Utente(nomeUtente, cognomeUtente, matricola, email);
            
            // Qui potresti opzionalmente parsare la lista di ID prestiti e aggiungerli all'utente
            /* if (!prestitiIDString.isEmpty()) {
                Arrays.stream(prestitiIDString.split("#"))
                      .filter(s -> !s.isEmpty())
                      .map(Integer::parseInt)
                      .forEach(id -> utentePlaceholder.addPrestitoID(id)); 
            }
            */

            // =========================================================
            // 3. RICOSTRUZIONE LIBRO (Campi 8-13+)
            // =========================================================
            String titoloLibro = values[8].trim();
            String isbn = values[9].trim();
            int copie = Integer.parseInt(values[10].trim());
            float valore = Float.parseFloat(values[11].trim());
            LocalDate dataPubblicazione = LocalDate.parse(values[12].trim());
            
            // Autori: Racco gliamo tutti i campi rimanenti (dal 13 in poi) e li riuniamo in una stringa CSV.
            // La logica è: Titolo,ISBN,...,DataPub,Autore1,Autore2,...
            String autoriCSV = Arrays.stream(values, 13, values.length)
                                     .collect(Collectors.joining(","));
                                     
            // Creazione dell'oggetto Libro 'Placeholder'
            Libro libroPlaceholder = new Libro(titoloLibro, autoriCSV, dataPubblicazione, isbn, copie, valore);


            // =========================================================
            // 4. CREAZIONE DEL PRESTITO
            // =========================================================
            // Nota: Il costruttore di Prestito assegna un nuovo ID autoincrementante.
            // Per preservare l'ID dal CSV, il costruttore di Prestito DEVE accettare l'ID
            // OPPURE il Prestito DEVE avere un setter privato per l'ID.
            
            // Assumendo che Prestito abbia un costruttore che accetta l'ID dal file:
            // public Prestito(int id, Utente utente, Libro libro, LocalDate dataPrevista, LocalDate dataInizio)
            
            // Se NON hai un costruttore con ID, il Prestito verrà creato con un nuovo ID, rompendo la sequenza.
            
            // Prestito nuovoPrestito = new Prestito(ID, utentePlaceholder, libroPlaceholder, dataPrevista, dataInizio);
            
            // Se il costruttore è solo (Utente, Libro, DataPrevista)
            Prestito nuovoPrestito = new Prestito(utentePlaceholder, libroPlaceholder, dataPrevista);
            
            // ⚠️ Se usi il costruttore senza ID, il Prestito avrà un ID generato.
            // Dovrai gestirlo nel GestorePrestiti.

            return nuovoPrestito;

        } catch (Exception e) {
            // Cattura NumberFormatException, DateTimeParseException e altre eccezioni
            throw new IllegalArgumentException("Errore di parsing in un campo del Prestito: " + e.getMessage(), e);
        }
    }
    
    public static void main(String[] args) {
        launch();
    }
}