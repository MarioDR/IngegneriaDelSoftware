package Test;

import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    private Utente utenteStandard;
    
    // Oggetti di supporto reali (non mock) necessari per Prestito
    private Prestito prestito1;
    private Prestito prestito2;
    private Prestito prestito3;
    private Prestito prestitoExtra;

    @BeforeEach
    void setUp() {
        // Inizializza un nuovo oggetto Utente per ogni test
        utenteStandard = new Utente("Marco", "Rossi", "M123456", "marco.rossi@uni.it");
        
        // Creazione di Prestiti reali (necessari per testare addPrestito/hasMaxNumPrestiti)
        Libro libroFittizio = new Libro("Titolo", "Autore", LocalDate.now(), "ISBN-TEST", 1, 10f);
        LocalDate dataFutura = LocalDate.now().plusDays(30);

        // Creiamo istanze di Prestito reali (gli ID saranno incrementali)
        prestito1 = new Prestito(utenteStandard, libroFittizio, dataFutura);
        prestito2 = new Prestito(utenteStandard, libroFittizio, dataFutura);
        prestito3 = new Prestito(utenteStandard, libroFittizio, dataFutura);
        prestitoExtra = new Prestito(utenteStandard, libroFittizio, dataFutura);
    }

    // =========================================================
    // TEST COSTRUTTORE
    // =========================================================

    @Test
    void testCostruttoreInizializzazioneCorretta() {
        assertEquals("Marco", utenteStandard.getNome());
        assertEquals("Rossi", utenteStandard.getCognome());
        assertEquals("M123456", utenteStandard.getMatricola());
        assertEquals("marco.rossi@uni.it", utenteStandard.getEmail());
        assertNotNull(utenteStandard.getListaPrestiti());
        assertTrue(utenteStandard.getListaPrestiti().isEmpty());
    }

    // =========================================================
    // TEST GESTIONE PRESTITI
    // =========================================================

    @Test
    void testAddPrestitoAggiungeCorrettamente() {
        utenteStandard.addPrestito(prestito1);
        
        assertEquals(1, utenteStandard.getListaPrestiti().size());
        assertTrue(utenteStandard.getListaPrestiti().contains(prestito1));
    }

    @Test
    void testAddPrestitoIgnoraNull() {
        utenteStandard.addPrestito(prestito1);
        int dimensioneIniziale = utenteStandard.getListaPrestiti().size();
        
        utenteStandard.addPrestito(null); // Tenta di aggiungere null
        
        // Verifica che la lista non sia cambiata
        assertEquals(dimensioneIniziale, utenteStandard.getListaPrestiti().size());
    }

    @Test
    void testRemovePrestitoRimuoveEsistente() {
        utenteStandard.addPrestito(prestito1);
        utenteStandard.addPrestito(prestito2);
        
        utenteStandard.removePrestito(prestito1);
        
        assertEquals(1, utenteStandard.getListaPrestiti().size());
        assertFalse(utenteStandard.getListaPrestiti().contains(prestito1));
        assertTrue(utenteStandard.getListaPrestiti().contains(prestito2));
    }
    
    @Test
    void testRemovePrestitoInesistenteNonModifica() {
        utenteStandard.addPrestito(prestito1);
        
        // Rimuove un prestito mai aggiunto
        utenteStandard.removePrestito(prestitoExtra); 
        
        assertEquals(1, utenteStandard.getListaPrestiti().size());
    }

    // =========================================================
    // TEST LIMITE PRESTITI (hasMaxNumPrestiti)
    // =========================================================

    @Test
    void testHasMaxNumPrestitiFalseConMenoDiTre() {
        utenteStandard.addPrestito(prestito1); 
        utenteStandard.addPrestito(prestito2);
        
        assertFalse(utenteStandard.hasMaxNumPrestiti());
    }
    
    @Test
    void testHasMaxNumPrestitiTrueConTre() {
        utenteStandard.addPrestito(prestito1);
        utenteStandard.addPrestito(prestito2);
        utenteStandard.addPrestito(prestito3);
        
        assertTrue(utenteStandard.hasMaxNumPrestiti());
    }

    @Test
    void testHasMaxNumPrestitiTrueConPiuDiTre( ) {
        // Di norma un utente non può mai trovarsi in questa situazione, ma la gestiamo ugualmente ritornando false
        utenteStandard.addPrestito(prestito1);
        utenteStandard.addPrestito(prestito2);
        utenteStandard.addPrestito(prestito3);
        utenteStandard.addPrestito(prestitoExtra); // Aggiunta oltre il limite

        assertTrue(utenteStandard.hasMaxNumPrestiti());
    }
    
    // =========================================================
    // TEST EQUALS E TO CSV
    // =========================================================

    @Test
    void testEqualsMatricolaEmailCorrette() {
        // Stessa matricola e email (uguali logicamente)
        Utente utenteUguale = new Utente("Laura", "Verdi", "M123456", "marco.rossi@uni.it");
        
        assertTrue(utenteStandard.equals(utenteUguale));
    }

    @Test
    void testEqualsMatricolaCorretta() {
        // Stessa matricola e email (uguali logicamente)
        Utente utenteUguale = new Utente("Laura", "Verdi", "M123456", "marco.rossi2@uni.it");
        
        assertTrue(utenteStandard.equals(utenteUguale));
    }
    
    @Test
    void testEqualsEmailCorretta() {
        // Stessa matricola e email (uguali logicamente)
        Utente utenteUguale = new Utente("Laura", "Verdi", "M123436", "marco.rossi@uni.it");
        
        assertTrue(utenteStandard.equals(utenteUguale));
    }
    
    @Test
    void testEqualsMatricolaEmailDiverse() {
        // Matricola diversa
        Utente utenteDiverso = new Utente("Marco", "Rossi", "D456", "marco.rossi2@uni.it");
        
        assertFalse(utenteStandard.equals(utenteDiverso));
    }
    
    @Test
    void testToCSVFormatoCorretto() {
        utenteStandard.addPrestito(prestito1);
        utenteStandard.addPrestito(prestito2);
        
        String expectedCSV = "Marco,Rossi,M123456,marco.rossi@uni.it,#" + prestito1.getID() + "#" + prestito2.getID();
        assertEquals(expectedCSV, utenteStandard.toCSV());
    }
    
    // =========================================================
    // TEST CONTAINS PATTERN
    // =========================================================
    
    @Test
    void testContainsPatternTrovaMatchCaseInsensitiveInNome() {
        // Ricerca per nome (case insensitive)
        assertTrue(utenteStandard.containsPattern("mar"));
    }

    @Test
    void testContainsPatternTrovaMatchInMatricola() {
        // Ricerca per matricola (numerica)
        assertTrue(utenteStandard.containsPattern("1234"));
    }
    
    @Test
    void testContainsPatternTrovaMatchConSpaziNelPattern() {
        // La logica di containsPattern rimuove gli spazi dal pattern
        assertTrue(utenteStandard.containsPattern(" rossi "));
    }
    
    @Test
    void testContainsPatternNonTrovaInesistente() {
        assertFalse(utenteStandard.containsPattern("Giovanni"));
    }
    
    @Test
    void testContainsPatternRifiutaNullEmptyBlank() {
        assertFalse(utenteStandard.containsPattern(null));
        assertFalse(utenteStandard.containsPattern(""));
        assertFalse(utenteStandard.containsPattern(" "));
    }
}