package Test;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.GestorePrestiti;
import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UtenteTest {

    private GestorePrestiti gestore;
    
    private Utente utenteStandard;
    
    // Oggetti di supporto reali (non mock) necessari per Prestito
    private Prestito prestito1;
    private Prestito prestito2;
    private Prestito prestito3;
    private Prestito prestitoExtra;

    @BeforeEach
    void setUp() {
        // Recupera l'istanza Singleton di GestorePrestiti e pulisci lo stato
        gestore = GestorePrestiti.getInstance();
        gestore.getList().clear(); 
        
        // Inizializza un nuovo oggetto Utente per ogni test
        utenteStandard = new Utente("Marco", "Rossi", "0123456789", "marco.rossi@uni.it");
        
        // Creazione di Prestiti reali (necessari per testare addPrestito/hasMaxNumPrestiti)
        Libro libroFittizio1 = new Libro("Titolo", "Autore", LocalDate.now(), "1234567890123", 1, 10f);
        Libro libroFittizio2 = new Libro("Titolo", "Autore", LocalDate.now(), "1234567890124", 1, 10f);
        Libro libroFittizio3 = new Libro("Titolo", "Autore", LocalDate.now(), "1234567890125", 1, 10f);
        Libro libroFittizio4 = new Libro("Titolo", "Autore", LocalDate.now(), "1234567890126", 1, 10f);
        
        LocalDate dataFutura = LocalDate.now().plusDays(30);
        
        // Aggiungo i prestiti e l'utente nei manager
        GestoreUtenti.getInstance().getList().clear();
        GestoreLibri.getInstance().getList().clear();
        
        GestoreUtenti.getInstance().add(utenteStandard);
        GestoreLibri.getInstance().add(libroFittizio1);
        GestoreLibri.getInstance().add(libroFittizio2);
        GestoreLibri.getInstance().add(libroFittizio3);
        GestoreLibri.getInstance().add(libroFittizio4);
        
        // Creiamo istanze di Prestito reali (gli ID saranno incrementali)
        prestito1 = new Prestito("0123456789", "1234567890123", dataFutura);
        prestito2 = new Prestito("0123456789", "1234567890124", dataFutura);
        prestito3 = new Prestito("0123456789", "1234567890125", dataFutura);
        prestitoExtra = new Prestito("0123456789", "1234567890126", dataFutura);
    }

    // =========================================================
    // TEST COSTRUTTORE
    // =========================================================

    @Test
    void testCostruttoreInizializzazioneCorretta() {
        assertEquals("Marco", utenteStandard.getNome());
        assertEquals("Rossi", utenteStandard.getCognome());
        assertEquals("0123456789", utenteStandard.getMatricola());
        assertEquals("marco.rossi@uni.it", utenteStandard.getEmail());
    }

    // =========================================================
    // TEST LIMITE PRESTITI (hasMaxNumPrestiti) 
    // =========================================================

    @Test
    void testHasMaxNumPrestitiFalseConMenoDiTre() {
        gestore.add(prestito1);
        gestore.add(prestito2);
        
        assertFalse(utenteStandard.hasMaxNumPrestiti());
    }
    
    @Test
    void testHasMaxNumPrestitiTrueConTre() {
        gestore.add(prestito1);
        gestore.add(prestito2);
        gestore.add(prestito3);
        
        assertTrue(utenteStandard.hasMaxNumPrestiti());
    }

    @Test
    void testHasMaxNumPrestitiTrueConPiuDiTre() {
        // Di norma un utente non può mai trovarsi in questa situazione, ma la gestiamo ugualmente ritornando false
        gestore.add(prestito1);
        gestore.add(prestito2);
        gestore.add(prestito3); 
        gestore.add(prestitoExtra);// Aggiunta oltre il limite

        assertTrue(utenteStandard.hasMaxNumPrestiti());
    }
    
    // =========================================================
    // TEST GET LISTA PRESTITI, EQUALS E TO CSV
    // =========================================================
    
    @Test
    void testGetListaPrestitiVuota() {
        assertEquals(0, utenteStandard.getListaPrestiti().size());
        assertTrue(utenteStandard.getListaPrestiti().isEmpty());
    }
    
    @Test
    void testGetListaPrestitiNonVuota() {
        gestore.add(prestito1);
        gestore.add(prestito2);
        
        assertEquals(2, utenteStandard.getListaPrestiti().size());
        assertTrue(utenteStandard.getListaPrestiti().contains(prestito1));
        assertTrue(utenteStandard.getListaPrestiti().contains(prestito2));
    }
    
    @Test
    void testEqualsMatricolaEmailCorrette() {
        Utente utenteUguale = new Utente("Laura", "Verdi", "0123456789", "marco.rossi@uni.it");
        
        assertTrue(utenteStandard.equals(utenteUguale));
    }

    @Test
    void testEqualsMatricolaCorretta() {
        Utente utenteUguale = new Utente("Laura", "Verdi", "0123456789", "marco.rossi2@uni.it");
        
        assertTrue(utenteStandard.equals(utenteUguale));
    }
    
    @Test
    void testEqualsEmailCorretta() {
        Utente utenteUguale = new Utente("Laura", "Verdi", "0123456788", "marco.rossi@uni.it");
        
        assertTrue(utenteStandard.equals(utenteUguale));
    }
    
    @Test
    void testEqualsMatricolaEmailDiverse() {
        // Matricola diversa
        Utente utenteDiverso = new Utente("Marco", "Rossi", "0123456788", "marco.rossi2@uni.it");
        
        assertFalse(utenteStandard.equals(utenteDiverso));
    }
    
    @Test
    void testToCSVFormatoCorretto() {
        String expectedCSV = "Marco,Rossi,0123456789,marco.rossi@uni.it";
        assertEquals(expectedCSV, utenteStandard.toCSV());
    }
    
    @Test
    void testToCSVFormatoSbagliato() {
        String unexpectedCSV = "Marco,Rossi,0123456799,marco.rossi@uni.it";
        assertNotEquals(unexpectedCSV, utenteStandard.toCSV());
    }
    
    // =========================================================
    // TEST CONTAINS PATTERN
    // =========================================================
    
    @Test
    void testContainsPatternTrovaMatchCaseInsensitiveInNome() {
        // Ricerca per nome (case insensitive)
        assertTrue(utenteStandard.containsPattern("mAr"));
    }

    @Test
    void testContainsPatternTrovaMatchCaseInsensitiveInCognome() {
        // Ricerca per cognome (case insensitive)
        assertTrue(utenteStandard.containsPattern("rOs"));
    }
    
    @Test
    void testContainsPatternTrovaMatchInMatricola() {
        // Ricerca per matricola (numerica)
        assertTrue(utenteStandard.containsPattern("1234"));
    }
    
    @Test
    void testContainsPatternTrovaMatchInEmail() {
        // Ricerca per email (case insensitive)
        assertTrue(utenteStandard.containsPattern("rco.roS"));
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
    void testContainsPatternAccettaNullEmptyBlank() {
        assertTrue(utenteStandard.containsPattern(null));
        assertTrue(utenteStandard.containsPattern(""));
        assertTrue(utenteStandard.containsPattern(" "));
    }
}