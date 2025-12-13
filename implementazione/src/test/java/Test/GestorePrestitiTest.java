package Test;

import gruppo15.ingegneriadelsoftware.model.GestorePrestiti;
import gruppo15.ingegneriadelsoftware.model.Libro;
import gruppo15.ingegneriadelsoftware.model.Prestito;
import gruppo15.ingegneriadelsoftware.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestorePrestitiTest {

    private GestorePrestiti gestore;
    private Prestito prestitoA;
    private Prestito prestitoB;
    private Prestito prestitoC;

    @BeforeEach
    void setUp() {
        // 1. Recupera l'istanza Singleton e pulisci lo stato
        gestore = GestorePrestiti.getInstance();
        gestore.getList().clear(); 
        
        // 2. Creazione di oggetti reali di supporto
        Utente utenteA = new Utente("Gino", "Rossi", "G100", "gino@uni.it");
        Utente utenteB = new Utente("Anna", "Verdi", "A200", "anna@uni.it");
        Libro libro1 = new Libro("Software", "Sommerville", LocalDate.of(2010, 1, 1), "001A", 1, 10f);
        Libro libro2 = new Libro("Hardware", "Tannenbaum", LocalDate.of(2015, 1, 1), "002B", 1, 20f);
        
        // 3. Creazione di Prestiti reali
        LocalDate dataFutura = LocalDate.now().plusDays(15);
        
        // Prestito A (ID X)
        prestitoA = new Prestito(utenteA, libro1, dataFutura);
        // Prestito B (ID Y)
        prestitoB = new Prestito(utenteB, libro2, dataFutura);
        // Prestito C ha lo stesso ID logico di A, ma ID diversi (dovrebbe fallire l'equals)
        // Nota: Poiché Prestito usa contatore statico, Prestito.equals si basa solo sull'ID
        // Qui creiamo un Prestito che non sarà MAI uguale ad A o B
        prestitoC = new Prestito(utenteB, libro1, dataFutura);
    }

    // =========================================================
    // TEST SINGLETON
    // =========================================================

    @Test
    void testGetInstanceRestituisceSingolaIstanza() {
        GestorePrestiti istanza1 = GestorePrestiti.getInstance();
        GestorePrestiti istanza2 = GestorePrestiti.getInstance();
        
        // Le due istanze devono essere lo stesso oggetto
        assertSame(istanza1, istanza2);
    }
    
    // =========================================================
    // TEST GET LIST, ADD E REMOVE
    // =========================================================
    
    @Test
    void testAddAggiungePrestitoValido() {
        List<Prestito> lista = gestore.add(prestitoA);
        
        assertTrue(lista.contains(prestitoA));
        assertEquals(1, lista.size());
    }
    
    @Test
    void testAddIgnoraPrestitoNull() {
        int dimensioneIniziale = gestore.getList().size();
        gestore.add(null);
        
        assertEquals(dimensioneIniziale, gestore.getList().size());
    }

    @Test
    void testRemoveRimuovePrestitoEsistente() {
        gestore.add(prestitoA);
        gestore.add(prestitoB);
        
        List<Prestito> lista = gestore.remove(prestitoA);
        
        assertFalse(lista.contains(prestitoA));
        assertEquals(1, lista.size());
    }
    
    @Test
    void testRemoveIgnoraPrestitoNonEsistente() {
        gestore.add(prestitoA);
        int dimensioneIniziale = gestore.getList().size();
        
        gestore.remove(prestitoB); // Prestito B non è stato aggiunto (ID diversi)
        
        assertEquals(dimensioneIniziale, gestore.getList().size());
    }
    
    // =========================================================
    // TEST CONTAINS
    // =========================================================

    @Test
    void testContainsTrovaPrestitoUgualePerID() {
        gestore.add(prestitoA);
        
        // Creiamo un riferimento esatto, che deve essere trovato
        Prestito prestitoUguale = prestitoA;
        assertTrue(gestore.contains(prestitoUguale)); 
    }
    
    @Test
    void testContainsNonTrovaPrestitoDiverso() {
        gestore.add(prestitoA);
        
        // Prestito B ha un ID diverso da A
        assertFalse(gestore.contains(prestitoB));
    }

    // =========================================================
    // TEST RICERCA (containsString)
    // =========================================================

    @Test
    void testContainsStringTrovaCorrispondenzeInLibro() {
        gestore.add(prestitoA); // Libro: Software
        gestore.add(prestitoB); // Libro: Hardware
        
        // Cerca la sottostringa "ware" (presente in entrambi i libri)
        List<Prestito> risultati = gestore.containsString("ware");
        
        assertEquals(2, risultati.size());
        assertTrue(risultati.contains(prestitoA));
        assertTrue(risultati.contains(prestitoB));
    }
    
    @Test
    void testContainsStringTrovaCorrispondenzeInUtente() {
        gestore.add(prestitoA); // Utente: Gino Rossi (G100)
        gestore.add(prestitoB); // Utente: Anna Verdi (A200)
        
        // Cerca la matricola "200"
        List<Prestito> risultati = gestore.containsString("200");
        
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(prestitoB));
        assertFalse(risultati.contains(prestitoA));
    }
    
    @Test
    void testContainsStringRitornaListaVuotaPerNessunMatch() {
        gestore.add(prestitoA);
        
        List<Prestito> risultati = gestore.containsString("Impossibile");
        
        assertTrue(risultati.isEmpty());
    }
}