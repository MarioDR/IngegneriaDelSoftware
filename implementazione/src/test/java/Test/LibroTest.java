package Test;

import gruppo15.ingegneriadelsoftware.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    private Libro libro;
    private final LocalDate DATA_PUBBLICAZIONE = LocalDate.of(2000, 1, 1);

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
    }

    // =========================================================
    // TEST COSTRUTTORE
    // =========================================================

    @Test
    void testCostruttoreInizializzazioneCorretta() {
        assertEquals("Design Patterns", libro.getTitolo());
        assertEquals("0201633612101", libro.getISBN());
        assertEquals(5, libro.getNumeroCopie());
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
    void testAggiungiCopiaIncrementaDiUno() {
        libro.aggiungiCopia();
        assertEquals(6, libro.getNumeroCopie());
    }
    
    @Test
    void testRimuoviCopiaDecrementaDiUno() {
        libro.rimuoviCopia();
        assertEquals(4, libro.getNumeroCopie());
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
        
        // Ricerche che non vanno a buon fine
        assertFalse(libro.containsPattern("Pippo"));
        assertFalse(libro.containsPattern(""));
        assertFalse(libro.containsPattern(" "));
        assertFalse(libro.containsPattern("02 01"));
        assertFalse(libro.containsPattern("ignpatt"));
    }
    
    @Test
    void testToCSV() {
        assertTrue(libro.toCSV().equals("Design Patterns,0201633612101,5,65.99,Gamma,Helm,Johnson,Vlissides"));
    }
}