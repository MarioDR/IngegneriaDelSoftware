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

import static org.junit.jupiter.api.Assertions.*;

class PrestitoTest {

    private Utente utenteReale;
    private Libro libroReale;
    private Libro libroB;
    private Prestito prestitoStandard;
    private LocalDate dataPrevistaRestituzione;
    
    // Date per i test (la data di oggi è implicita nel costruttore)
    private final LocalDate DATA_OGGI = LocalDate.now();

    @BeforeEach
    void setUp() {
        // Oggetti reali necessari per il Prestito
        utenteReale = new Utente("Marco", "Bianchi", "0123456789", "m.bianchi@uni.it");
        libroReale = new Libro("Clean Code", "Robert C. Martin", LocalDate.of(2008, 1, 1), "1234567890123", 2, 35.00f);
        libroB = new Libro("Code", "Robert C. Martin", LocalDate.of(2008, 5, 2), "1234567890124", 2, 35.00f);
        
        // Prestito standard con scadenza tra 30 giorni
        dataPrevistaRestituzione = DATA_OGGI.plusDays(30);
        
        // Aggiungo il libro e l'utente nei manager
        GestoreUtenti.getInstance().getList().clear();
        GestoreLibri.getInstance().getList().clear();
        
        GestoreUtenti.getInstance().add(utenteReale);
        GestoreLibri.getInstance().add(libroReale);
        GestoreLibri.getInstance().add(libroB);
        
        prestitoStandard = new Prestito("0123456789", "1234567890123", dataPrevistaRestituzione);
    }

    // =========================================================
    // TEST COSTRUTTORE
    // =========================================================

    @Test
    void testCostruttoreInizializzaCorrettamente() {
        // Verifica l'ID: deve essere positivo (invariante implicita)
        assertTrue(prestitoStandard.getID() > 0); 
        
        // Verifica che la data di inizio sia oggi
        assertEquals(DATA_OGGI, prestitoStandard.getDataInizioPrestito());
        
        // Verifica le associazioni
        assertEquals(utenteReale, prestitoStandard.getUtenteAssegnatario());
        assertEquals(libroReale, prestitoStandard.getLibroPrestato());
        assertEquals(DATA_OGGI.plusDays(30), prestitoStandard.getDataPrevistaRestituzione());
    }
    
    @Test
    void testCostruttore2InizializzaCorrettamente() {
        Prestito prestito2 = new Prestito(1, "0123456789", "1234567890123", DATA_OGGI, dataPrevistaRestituzione);
        
        // Verifica l'ID: deve essere positivo (invariante implicita)
        assertTrue(prestito2.getID() == 1); 
        
        // Verifica che la data di inizio sia oggi
        assertEquals(DATA_OGGI, prestito2.getDataInizioPrestito());
        
        // Verifica le associazioni
        assertEquals(utenteReale, prestito2.getUtenteAssegnatario());
        assertEquals(libroReale, prestito2.getLibroPrestato());
        assertEquals(DATA_OGGI.plusDays(30), prestito2.getDataPrevistaRestituzione());
    }
    
    @Test
    void testIDUnivocoEPositivo() {
        // Creazione di un secondo prestito
        Prestito prestitoSuccessivo = new Prestito("0123456789", "1234567890124", DATA_OGGI.plusDays(10));
        
        assertTrue(prestitoSuccessivo.getID() > prestitoStandard.getID());
        assertTrue(prestitoSuccessivo.getID() > 0);
    }

    // =========================================================
    // TEST GESTIONE RITARDO
    // =========================================================

    @Test
    void testIsInRitardoQuandoInAnticipoOppurePuntuale() {
        // Data prevista è nel futuro (30 giorni da oggi)
        assertFalse(prestitoStandard.isInRitardo());
        
        // Creo un prestito in scandeza oggi
        LocalDate dataPrevistaRestituzione2 = DATA_OGGI;
        Prestito prestitoScadenzaOggi = new Prestito("0123456789", "1234567890123", dataPrevistaRestituzione2);
        
        // Data prevista è oggi, quindi non è ancora in ritardo
        assertFalse(prestitoStandard.isInRitardo());
    }

    @Test
    void testIsInRitardoQuandoInRitardo() {
        // Simula che la data prevista fosse ieri
        LocalDate dataPassata = DATA_OGGI.minusDays(1);
        Prestito prestitoInRitardo = new Prestito("0123456789", "1234567890123", dataPassata);
        
        assertTrue(prestitoInRitardo.isInRitardo());
    }
    
    @Test
    void testGetGiorniDiRitardoQuandoInRitardo() {
        // Data prevista: 5 giorni fa
        LocalDate dataPassata = DATA_OGGI.minusDays(5);
        Prestito prestitoInRitardo = new Prestito("0123456789", "1234567890123", dataPassata);
        
        // Calcolo: (Oggi - (Oggi - 5)) = +5 giorni
        assertEquals(5L, prestitoInRitardo.getGiorniDiRitardo());
    }

    @Test
    void testGetGiorniDiRitardoQuandoPuntuale() {
        LocalDate dataFutura = DATA_OGGI;
        Prestito prestitoInAnticipo = new Prestito("0123456789", "1234567890123", dataFutura);
        
        assertEquals(0L, prestitoInAnticipo.getGiorniDiRitardo());
    }
    
    @Test
    void testGetGiorniDiRitardoQuandoInAnticipo() {
        // Data prevista: 10 giorni nel futuro
        LocalDate dataFutura = DATA_OGGI.plusDays(10);
        Prestito prestitoInAnticipo = new Prestito("0123456789", "1234567890123", dataFutura);
        
        // Calcolo: (Oggi - (Oggi + 10)) = -10 giorni
        assertEquals(-10L, prestitoInAnticipo.getGiorniDiRitardo());
    }

    // =========================================================
    // TEST EQUALS, CONTAINS PATTERN E TO CSV
    // =========================================================

    @Test
    void testEquals() {
        // Creiamo un prestito p1 e ne copiamo l'ID
        Prestito p1 = new Prestito("0123456789", "1234567890123", DATA_OGGI.plusDays(1));
        Prestito p1copia = p1;

        // Due istanze consecutive hanno ID diversi
        Prestito p2 = new Prestito("0123456789", "1234567890123", DATA_OGGI.plusDays(1));
        assertFalse(p1.equals(p2));
        
        // La stessa istanza è uguale
        assertTrue(p1.equals(p1copia));
    }
    
    @Test
    void testEqualsConNullEOggettoDiverso() {
        assertFalse(prestitoStandard.equals(null));
        assertFalse(prestitoStandard.equals(new Object()));
    }

    @Test
    void testContainsPatternDelegaRicerca() {
        // 1. Ricerca che trova corrispondenza in Utente (Cognome)
        assertTrue(prestitoStandard.containsPattern("Bianchi"));

        // 2. Ricerca che trova corrispondenza in Libro (Titolo)
        assertTrue(prestitoStandard.containsPattern("Clean"));

        // 3. Ricerca inesistente
        assertFalse(prestitoStandard.containsPattern("NonEsisteQui"));
    }
    
    @Test
    void testToCSVFormatoCorretto() {
        String expectedDates = prestitoStandard.getDataInizioPrestito() + "," + prestitoStandard.getDataPrevistaRestituzione();
        String expectedCSV = prestitoStandard.getID() + "," + expectedDates + "," + "0123456789" + "," + "1234567890123";
        
        assertEquals(expectedCSV, prestitoStandard.toCSV());
    }
    
    @Test
    void testToCSVFormatoSbagliato() {
        String expectedDates = prestitoStandard.getDataInizioPrestito().plusDays(1) + "," + prestitoStandard.getDataPrevistaRestituzione();
        String unexpectedCSV = prestitoStandard.getID() + "," + expectedDates + "," + "0123456789" + "," + "1234567890123";
        
        assertNotEquals(unexpectedCSV, prestitoStandard.toCSV());
    }
}