package Test;

import gruppo15.ingegneriadelsoftware.model.GestoreRestituzioni;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import gruppo15.ingegneriadelsoftware.model.Restituzione;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestoreRestituzioniTest {

    private GestoreRestituzioni gestore;
    private Restituzione restituzioneA;
    private Restituzione restituzioneB;
    private Restituzione restituzioneC;

    @BeforeEach
    void setUp() {
        // 1. Recupera l'istanza Singleton e pulisci lo stato
        gestore = GestoreRestituzioni.getInstance();
        gestore.getList().clear(); 
        
        // 2. Creazione di oggetti reali di supporto
        Utente utenteMarco = new Utente("Marco", "Neri", "M555", "m.neri@uni.it");
        Utente utenteLuca = new Utente("Luca", "Bianchi", "L666", "l.bianchi@uni.it");
        Libro libro1 = new Libro("Fantasy", "Tolkien", LocalDate.of(1950, 1, 1), "FAN001", 1, 10f);
        Libro libro2 = new Libro("Scienza", "Newton", LocalDate.of(1700, 1, 1), "SCI002", 1, 20f);
        
        // 3. Creazione di Prestiti (necessari per le Restituzioni)
        LocalDate dataFutura = LocalDate.now().plusDays(15);
        
        // Prestito P1 (ID X)
        Prestito prestitoP1 = new Prestito(utenteMarco, libro1, dataFutura);
        // Prestito P2 (ID Y)
        Prestito prestitoP2 = new Prestito(utenteLuca, libro2, dataFutura); 
        
        // 4. Creazione delle Restituzioni
        restituzioneA = new Restituzione(prestitoP1);
        restituzioneB = new Restituzione(prestitoP2);
        
        // Per testare contains/equals: una nuova restituzione dello stesso prestito P1
        restituzioneC = new Restituzione(prestitoP1); 
    }

    // =========================================================
    // TEST SINGLETON
    // =========================================================

    @Test
    void testGetInstanceRestituisceSingolaIstanza() {
        GestoreRestituzioni istanza1 = GestoreRestituzioni.getInstance();
        GestoreRestituzioni istanza2 = GestoreRestituzioni.getInstance();
        
        assertSame(istanza1, istanza2);
    }
    
    // =========================================================
    // TEST GEST LIST, ADD E REMOVE
    // =========================================================
    
    @Test
    void testAddAggiungeRestituzioneValida() {
        List<Restituzione> lista = gestore.add(restituzioneA);
        
        assertTrue(lista.contains(restituzioneA));
        assertEquals(1, lista.size());
    }
    
    @Test
    void testAddIgnoraRestituzioneNull() {
        int dimensioneIniziale = gestore.getList().size();
        gestore.add(null);
        
        assertEquals(dimensioneIniziale, gestore.getList().size());
    }

    @Test
    void testRemoveRimuoveRestituzioneEsistente() {
        gestore.add(restituzioneA);
        gestore.add(restituzioneB);
        
        List<Restituzione> lista = gestore.remove(restituzioneA);
        
        assertFalse(lista.contains(restituzioneA));
        assertEquals(1, lista.size());
    }
    
    // =========================================================
    // TEST CONTAINS
    // =========================================================
    
    @Test
    void testContainsTrovaRestituzioneUgualePerPrestitoID() {
        gestore.add(restituzioneA);
        
        // Restituzione C contiene lo stesso Prestito P1 di Restituzione A
        assertTrue(gestore.contains(restituzioneC)); 
    }
    
    @Test
    void testContainsNonTrovaRestituzioneInesistente() {
        gestore.add(restituzioneA);
        
        // Restituzione B ha un prestito con ID diverso da restituzione A
        assertFalse(gestore.contains(restituzioneB));
    }
    
    // =========================================================
    // TEST RICERCA (containsString)
    // =========================================================

    @Test
    void testContainsStringTrovaCorrispondenzeInUtente() {
        gestore.add(restituzioneA); // Utente: Marco Neri (M555)
        gestore.add(restituzioneB); // Utente: Luca Bianchi (L666)
        
        // Cerca la matricola "M555"
        List<Restituzione> risultati = gestore.containsString("M555");
        
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(restituzioneA));
        assertFalse(risultati.contains(restituzioneB));
    }
    
    @Test
    void testContainsStringTrovaCorrispondenzeInLibro() {
        gestore.add(restituzioneA); // Libro: Fantasy
        gestore.add(restituzioneB); // Libro: Scienza
        
        // Cerca il titolo "Scienza"
        List<Restituzione> risultati = gestore.containsString("Scienza");
        
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(restituzioneB));
    }
    
    @Test
    void testContainsStringRitornaListaVuotaPerNessunMatch() {
        gestore.add(restituzioneA);
        
        List<Restituzione> risultati = gestore.containsString("Inesistente");
        
        assertTrue(risultati.isEmpty());
    }
}