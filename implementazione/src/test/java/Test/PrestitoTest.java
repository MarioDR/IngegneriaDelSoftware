package Test;

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
    private Prestito prestitoStandard;
    
    // Date per i test (la data di oggi è implicita nel costruttore)
    private final LocalDate DATA_OGGI = LocalDate.now();

    @BeforeEach
    void setUp() {
        // Oggetti reali necessari per il Prestito
        utenteReale = new Utente("Marco", "Bianchi", "U777", "m.bianchi@uni.it");
        libroReale = new Libro("Clean Code", "Robert C. Martin", LocalDate.of(2008, 1, 1), "0132350882", 2, 35.00f);
        
        // Prestito standard con scadenza tra 30 giorni
        LocalDate dataPrevistaRestituzione = DATA_OGGI.plusDays(30);
        
        prestitoStandard = new Prestito(utenteReale, libroReale, dataPrevistaRestituzione);
    }

    // =========================================================
    // TEST COSTRUTTORE
    // =========================================================

    @Test
    void testCostruttoreInizializzaCorrettamente() {
        // Verifica l'ID: deve essere positivo (invariante implicita)
        assertTrue(prestitoStandard.getID() > 0); 
        System.out.print(prestitoStandard.getID() + "\n\n\n\n\n");
        // Verifica che la data di inizio sia oggi
        assertEquals(DATA_OGGI, prestitoStandard.getDataInizioPrestito());
        
        // Verifica le associazioni
        assertEquals(utenteReale, prestitoStandard.getUtenteAssegnatario());
        assertEquals(libroReale, prestitoStandard.getLibroPrestato());
        assertEquals(DATA_OGGI.plusDays(30), prestitoStandard.getDataPrevistaRestituzione());
    }
    
    @Test
    void testIDUnivocoEPositivo() {
        // L'ID dovrebbe essere maggiore dell'ID precedente (testa l'invariante ID)
        
        // Creazione di un secondo prestito
        Prestito prestitoSuccessivo = new Prestito(utenteReale, libroReale, DATA_OGGI.plusDays(10));
        
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
        Prestito prestitoScadenzaOggi = new Prestito(utenteReale, libroReale, dataPrevistaRestituzione2);
        
        // Data prevista è oggi, quindi non è ancora in ritardo
        assertFalse(prestitoStandard.isInRitardo());
    }

    @Test
    void testIsInRitardoQuandoInRitardo() {
        // Simula che la data prevista fosse ieri
        LocalDate dataPassata = DATA_OGGI.minusDays(1);
        Prestito prestitoInRitardo = new Prestito(utenteReale, libroReale, dataPassata);
        
        assertTrue(prestitoInRitardo.isInRitardo());
    }
    
    @Test
    void testGetGiorniDiRitardoCalcoloCorretto() {
        // Data prevista: 5 giorni fa
        LocalDate dataPassata = DATA_OGGI.minusDays(5);
        Prestito prestitoInRitardo = new Prestito(utenteReale, libroReale, dataPassata);
        
        // Calcolo: (Oggi - (Oggi - 5)) = +5 giorni
        assertEquals(5L, prestitoInRitardo.getGiorniDiRitardo());
    }

    @Test
    void testGetGiorniDiRitardoQuandoInAnticipo() {
        // Data prevista: 10 giorni nel futuro
        LocalDate dataFutura = DATA_OGGI.plusDays(10);
        Prestito prestitoInAnticipo = new Prestito(utenteReale, libroReale, dataFutura);
        
        // Calcolo: (Oggi - (Oggi + 10)) = -10 giorni
        assertEquals(-10L, prestitoInAnticipo.getGiorniDiRitardo());
    }

    // =========================================================
    // TEST EQUALS, SEARCHABLE E TO CSV
    // =========================================================

    @Test
    void testEqualsConStessoID() {
        // Creiamo un prestito p1 e ne copiamo l'ID
        Prestito p1 = new Prestito(utenteReale, libroReale, DATA_OGGI.plusDays(1));
        
        // Creiamo un oggetto fittizio che simuli lo stesso ID di p1 per l'equals
        // (Nota: in assenza di mock, questo è difficile da testare senza violare l'invariante ID,
        // ma verifichiamo che il metodo 'equals' funzioni sulla stessa istanza)
        
        // Due istanze consecutive hanno ID diversi
        Prestito p2 = new Prestito(utenteReale, libroReale, DATA_OGGI.plusDays(1));
        assertFalse(p1.equals(p2));
        
        // La stessa istanza è uguale
        assertTrue(p1.equals(p1));
    }
    
    @Test
    void testEqualsConNullEOggettoDiverso() {
        assertFalse(prestitoStandard.equals(null));
        assertFalse(prestitoStandard.equals(new Object()));
    }

    @Test
    void testContainsPatternDelegaRicerca() {
        // Utente: Marco, Bianchi, U777, Libro: Clean Code, 0132350882
        
        // 1. Ricerca che trova corrispondenza in Utente (Cognome)
        assertTrue(prestitoStandard.containsPattern("Bianchi"));

        // 2. Ricerca che trova corrispondenza in Libro (Titolo)
        assertTrue(prestitoStandard.containsPattern("Clean"));

        // 3. Ricerca inesistente
        assertFalse(prestitoStandard.containsPattern("NonEsisteQui"));
    }
    
    @Test
    void testToCSVFormatoCorretto() {
        utenteReale.addPrestito(prestitoStandard);
        
        String expectedUtente = "Marco,Bianchi,U777,m.bianchi@uni.it,#" + prestitoStandard.getID();
        String expectedLibro = "Clean Code,0132350882,2,35.0,2008-01-01,Robert C. Martin";
        String expectedDates = prestitoStandard.getDataInizioPrestito() + "," + prestitoStandard.getDataPrevistaRestituzione();
        
        String expectedCSV = prestitoStandard.getID() + "," + expectedDates + "," + expectedUtente + "," + expectedLibro;
        
        assertEquals(expectedCSV, prestitoStandard.toCSV());
    }
}