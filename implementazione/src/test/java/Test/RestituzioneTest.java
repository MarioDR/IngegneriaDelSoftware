package Test;

import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import gruppo15.ingegneriadelsoftware.model.Restituzione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RestituzioneTest {

    private Utente utenteStandard;
    private Libro libroStandard;
    
    // Date usate per simulare le condizioni di ritardo/anticipo
    private final LocalDate DATA_OGGI = LocalDate.now();
    private final int GIORNI_RITARDO_TEST = 5;

    @BeforeEach
    void setUp() {
        // Oggetti di base necessari per creare un Prestito reale
        utenteStandard = new Utente("Marco", "Neri", "U900", "m.neri@uni.it");
        libroStandard = new Libro("Test Ritorno", "Autore", LocalDate.now(), "ISBN-RET", 1, 1f);
    }

    // =========================================================
    // TEST COSTRUTTORE
    // =========================================================

    @Test
    void testCostruttoreInizializzazioneCorretta() {
        // Creiamo un prestito che sarà restituito (la sua dataInizio sarà oggi)
        Prestito prestitoDaRestituire = new Prestito(utenteStandard, libroStandard, DATA_OGGI.plusDays(7));
        
        Restituzione restituzione = new Restituzione(prestitoDaRestituire);
        
        assertEquals(prestitoDaRestituire, restituzione.getPrestitoDaRestituire());
        // La data effettiva di restituzione DEVE essere uguale a oggi
        assertEquals(DATA_OGGI, restituzione.getDataRestituzione()); 
    }

    // =========================================================
    // TEST LOGICA RITARDO (isRestituitoInRitardo / getRitardoDefinitivo)
    // =========================================================

    @Test
    void testIsRestituitoInRitardoQuandoInRitardo() {
        // 1. Prestito la cui restituzione era prevista N giorni fa
        LocalDate dataPrevista = DATA_OGGI.minusDays(GIORNI_RITARDO_TEST);
        Prestito prestitoInRitardo = new Prestito(utenteStandard, libroStandard, dataPrevista);
        
        // 2. La Restituzione avviene OGGI (dopo la data prevista)
        Restituzione restituzione = new Restituzione(prestitoInRitardo);
        
        assertTrue(restituzione.isRestituitoInRitardo());
        
        // Risultato atteso: +5 giorni di ritardo
        assertEquals(GIORNI_RITARDO_TEST, restituzione.getRitardoDefinitivo());
    }

    @Test
    void testIsRestituitoInRitardoQuandoInAnticipo() {
        // 1. Prestito la cui restituzione è prevista tra N giorni
        LocalDate dataPrevista = DATA_OGGI.plusDays(GIORNI_RITARDO_TEST);
        Prestito prestitoInAnticipo = new Prestito(utenteStandard, libroStandard, dataPrevista);
        
        // 2. La Restituzione avviene OGGI (prima della data prevista)
        Restituzione restituzione = new Restituzione(prestitoInAnticipo);
        
        assertFalse(restituzione.isRestituitoInRitardo());
        
        // Risultato atteso: -5 giorni (restituzione - prevista)
        assertEquals(-GIORNI_RITARDO_TEST, restituzione.getRitardoDefinitivo());
    }
    
    @Test
    void testIsRestituitoInRitardoQuandoPuntuale() {
        // 1. Prestito la cui restituzione è prevista OGGI (lo stesso giorno della Restituzione)
        LocalDate dataPrevista = DATA_OGGI;
        Prestito prestitoPuntuale = new Prestito(utenteStandard, libroStandard, dataPrevista);
        
        // 2. La Restituzione avviene OGGI
        Restituzione restituzione = new Restituzione(prestitoPuntuale);
        
        assertFalse(restituzione.isRestituitoInRitardo());
        
        // Ritardo = 0 giorni
        assertEquals(0L, restituzione.getRitardoDefinitivo());
    }

    // =========================================================
    // TEST EQUALS, SEARCHABLE E ToCSV
    // =========================================================

    @Test
    void testEqualsStessoPrestito() {
        // Due restituzioni sono uguali se si riferiscono allo stesso Prestito (stesso ID)
        Prestito p1 = new Prestito(utenteStandard, libroStandard, DATA_OGGI.plusDays(7));
        
        Restituzione r1 = new Restituzione(p1);
        Restituzione r2 = new Restituzione(p1); 
        
        assertTrue(r1.equals(r2));
    }
    
    @Test
    void testEqualsPrestitiDiversi() {
        // Poiché il Prestito usa un ID incrementale, due chiamate a new Prestito creano due ID diversi
        Prestito p1 = new Prestito(utenteStandard, libroStandard, DATA_OGGI.plusDays(7));
        Prestito p2 = new Prestito(utenteStandard, libroStandard, DATA_OGGI.plusDays(7)); 
        
        Restituzione r1 = new Restituzione(p1);
        Restituzione r2 = new Restituzione(p2);
        
        assertFalse(r1.equals(r2));
        assertFalse(r1.equals(null));
        assertFalse(r1.equals(new Object()));
    }

    @Test
    void testContainsPatternDelegaAlPrestito() {
        // Dobbiamo simulare che il Prestito (p1) risponda alla ricerca
        
        // La logica di containsPattern è delegata al Prestito, che a sua volta cerca in Utente/Libro
        Prestito prestitoConNome = new Prestito(utenteStandard, libroStandard, DATA_OGGI.plusDays(7));
        Restituzione restituzione = new Restituzione(prestitoConNome);
        
        // Cerchiamo il nome "Marco" (presente in utenteStandard)
        assertTrue(restituzione.containsPattern("Marco"));
        
        // Cerchiamo il titolo "Test Ritorno"
        assertTrue(restituzione.containsPattern("Ritorno"));
        
        // Cerchiamo un pattern inesistente
        assertFalse(restituzione.containsPattern("Inesistente"));
    }
    
    @Test
    void testToCSVFormatoCorretto() {
        LocalDate dataPrevista = DATA_OGGI.plusDays(15);
        Prestito prestito = new Prestito(utenteStandard, libroStandard, dataPrevista);
        Restituzione restituzione = new Restituzione(prestito);
        
        // Il toCSV deve essere: Prestito.toCSV() + "," + DataEffettivaRestituzione
        
        // Formato Prestito (Utente + Libro + DataInizio + DataPrevista):
        // Marco,Neri,U900,m.neri@uni.it,Test Ritorno,ISBN-RET,1,1.0,Autore,2025-12-12,2026-01-01 (Esempio)
        
        String prestitoCSV = prestito.toCSV();
        String expectedCSV = DATA_OGGI + "," + prestitoCSV; // DATA_OGGI è la data effettiva di restituzione
        
        assertEquals(expectedCSV, restituzione.toCSV());
    }
}