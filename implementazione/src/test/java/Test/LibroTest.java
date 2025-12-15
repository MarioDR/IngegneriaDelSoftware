package Test;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.GestorePrestiti;
import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    private Libro libro;
    private Utente utente;
    private Prestito prestito;
    private LocalDate DATA_PUBBLICAZIONE = LocalDate.of(2000, 1, 1);

    @BeforeEach
    void setUp() {
        libro = new Libro(
            "Design Patterns", 
            "Gamma, Helm, Johnson, Vlissides", 
            DATA_PUBBLICAZIONE, 
            "0201633612101", 
            5, 
            65.99f
        );
        
        utente = new Utente("mario", "rossi", "0123456789", "mariorossi@uni.it");
        
        // Aggiungo il libro e l'utente nei manager
        GestoreUtenti.getInstance().getList().clear();
        GestoreLibri.getInstance().getList().clear();
        
        GestoreUtenti.getInstance().add(utente);
        GestoreLibri.getInstance().add(libro);
        
        prestito = new Prestito("0123456789", "0201633612101", DATA_PUBBLICAZIONE.plusDays(7));
    }

    // =========================================================
    // TEST COSTRUTTORE
    // =========================================================

    @Test
    void testCostruttoreInizializzazioneCorretta() {
        assertEquals("Design Patterns", libro.getTitolo());
        assertEquals("0201633612101", libro.getISBN());
        assertEquals(5, libro.getNumeroCopieDiStock());
        assertEquals(65.99f, libro.getValore(), 0.001); 
        assertEquals(DATA_PUBBLICAZIONE, libro.getDataDiPubblicazione());
    }
    
    @Test
    void testListaAutoriGenerataCorrettamente() {
        List<String> autoriAttesi = Arrays.asList("Gamma", "Helm", "Johnson", "Vlissides");
        assertEquals(autoriAttesi, libro.getListaAutori());
    }

    // =========================================================
    // TEST GESTIONE COPIE
    // =========================================================

    @Test
    void testAggiungiCopie() {
        libro.aggiungiCopie(3);
        assertEquals(8, libro.getNumeroCopieDiStock());
    }
    
    @Test
    void testGetNumeroCopieRimanenti() {
        GestorePrestiti.getInstance().getList().clear();
        GestorePrestiti.getInstance().getList().add(prestito);
        
        assertEquals(4, libro.getNumeroCopieRimanenti());
    }
    
    // =========================================================
    // TEST STAMPA AUTORI
    // =========================================================
    
    @Test
    void testStampaAutori() {
        String aut = libro.stampaAutori();
        
        String expectedString = "Gamma; Helm; Johnson; Vlissides";
        String unexpectedString = "Gamma, Helm, Johnson, Vlissides";
        
        assertEquals(expectedString, aut);
        assertNotEquals(unexpectedString, aut);
        assertNotEquals(null, aut);
    }
    
    // =========================================================
    // TEST EQUALS, SEARCHABLE E ToCSV
    // =========================================================

    @Test
    void testEquals() {
        Libro libroUguale = new Libro("Altro Titolo", "Autore", DATA_PUBBLICAZIONE.plusYears(1), "0201633612101", 1, 10.0f);
        Libro libroDiverso = new Libro("Altro Titolo", "Autore", DATA_PUBBLICAZIONE.plusYears(1), "0209633612101", 1, 10.0f);
        Libro libroNull = null;
        
        assertTrue(libro.equals(libroUguale));
        assertFalse(libro.equals(libroDiverso));
        assertFalse(libro.equals(libroNull));
    }

    @Test
    void testContainsPattern() {
        // Ricerche che vanno a buon fine
        assertTrue(libro.containsPattern("Design"));
        assertTrue(libro.containsPattern("Helm"));
        assertTrue(libro.containsPattern("63361"));
        assertTrue(libro.containsPattern("ign patt"));
        assertTrue(libro.containsPattern("DESIGN"));
        assertFalse(libro.containsPattern("Pippo"));
        assertTrue(libro.containsPattern(""));
        assertTrue(libro.containsPattern(" "));
        assertTrue(libro.containsPattern(null));

        // Ricerche che non vanno a buon fine
        assertFalse(libro.containsPattern("02 01"));
        assertFalse(libro.containsPattern("ignpatt"));
    }
    
    @Test
    void testToCSVCorretto() {
        assertTrue(libro.toCSV().equals("Design Patterns,0201633612101,5,65.99,2000-01-01,Gamma,Helm,Johnson,Vlissides"));
    }
    
    @Test
    void testToCSVSbagliato() {
        assertFalse(libro.toCSV().equals("Design Patterns,0201633612101,5,65.99,2000-01-01,Gamme,Helm,Johnson,Vlissides"));
    }
}