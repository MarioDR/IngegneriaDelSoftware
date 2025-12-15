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
        Utente utenteA = new Utente("Gino", "Rossi", "0123456789", "gino@uni.it");
        Utente utenteB = new Utente("Anna", "Verdi", "0123456780", "anna@uni.it");
        Libro libro1 = new Libro("Software", "Sommerville", LocalDate.of(2010, 1, 1), "1234567890123", 1, 10f);
        Libro libro2 = new Libro("Hardware", "Tannenbaum", LocalDate.of(2015, 1, 1), "1234567890124", 1, 20f);
        
        // 3. Creazione di Prestiti reali
        LocalDate dataFutura = LocalDate.now().plusDays(15);
        
        // Aggiungo i libri e gli utenti nei manager
        GestoreUtenti.getInstance().getList().clear();
        GestoreLibri.getInstance().getList().clear();
        
        GestoreUtenti.getInstance().add(utenteA);
        GestoreLibri.getInstance().add(libro1);
        GestoreUtenti.getInstance().add(utenteB);
        GestoreLibri.getInstance().add(libro2);
        
        prestitoA = new Prestito("0123456789", "1234567890123", dataFutura);
        prestitoB = new Prestito("0123456780", "1234567890124", dataFutura);
        prestitoC = new Prestito("0123456780", "1234567890123", dataFutura);
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
    
    @Test
    void testGetListRestituisceCollezioneCorretta() {
        gestore.add(prestitoA);
        gestore.add(prestitoB);
        
        List<Prestito> lista = gestore.getList();
        
        assertEquals(2, lista.size());
        assertTrue(lista.contains(prestitoA));
        assertTrue(lista.contains(prestitoB));
    }
    
    // =========================================================
    // TEST CONTAINS
    // =========================================================

    @Test
    void testContainsTrovaPrestitoUgualePerID() {
        gestore.add(prestitoA);
        
        Prestito prestitoUguale = prestitoA;
        assertTrue(gestore.contains(prestitoUguale)); 
    }
    
    @Test
    void testContainsNonTrovaPrestitoDiverso() {
        gestore.add(prestitoA);
        
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
        gestore.add(prestitoA); // Utente: Gino Rossi
        gestore.add(prestitoB); // Utente: Anna Verdi 
        
        // Cerca la matricola "124"
        List<Prestito> risultati = gestore.containsString("124");
        
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