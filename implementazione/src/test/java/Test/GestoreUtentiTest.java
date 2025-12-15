package Test;

import gruppo15.ingegneriadelsoftware.model.GestoreUtenti;
import gruppo15.ingegneriadelsoftware.model.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GestoreUtentiTest {

    private GestoreUtenti gestore;
    private Utente utenteA;
    private Utente utenteB;
    private Utente utenteUgualeA; // Utente con dati diversi ma stessa matricola/email di A

    @BeforeEach
    void setUp() {
        // 1. Recupera l'istanza Singleton e pulisci lo stato
        gestore = GestoreUtenti.getInstance();
        gestore.getList().clear(); 
        
        // 2. Creazione di oggetti reali di Utente
        utenteA = new Utente("Mario", "Rossi", "R001", "mario.rossi@uni.it");
        utenteB = new Utente("Luisa", "Bianchi", "B002", "luisa.bianchi@uni.it");
        
        // Utente con dati diversi, ma IDENTICO LOGICAMENTE a utenteA (stessa matricola/email)
        utenteUgualeA = new Utente("FALSO", "FALSO", "R001", "mario.rossi@uni.it"); 
    }

    // =========================================================
    // TEST SINGLETON
    // =========================================================

    @Test
    void testGetInstanceRestituisceSingolaIstanza() {
        GestoreUtenti istanza1 = GestoreUtenti.getInstance();
        GestoreUtenti istanza2 = GestoreUtenti.getInstance();
        
        assertSame(istanza1, istanza2);
    }
    
    // =========================================================
    // TEST GEST LIST, ADD E REMOVE
    // =========================================================
    
    @Test
    void testAddAggiungeUtenteValido() {
        List<Utente> lista = gestore.add(utenteA);
        
        assertTrue(lista.contains(utenteA));
        assertEquals(1, lista.size());
    }
    
    @Test
    void testAddIgnoraUtenteNull() {
        int dimensioneIniziale = gestore.getList().size();
        gestore.add(null);
        
        assertEquals(dimensioneIniziale, gestore.getList().size());
    }

    @Test
    void testRemoveRimuoveUtenteEsistente() {
        gestore.add(utenteA);
        gestore.add(utenteB);
        
        List<Utente> lista = gestore.remove(utenteA);
        
        assertFalse(lista.contains(utenteA));
        assertEquals(1, lista.size());
    }
    
    @Test
    void testRemoveIgnoraUtenteNonEsistente() {
        gestore.add(utenteA);
        int dimensioneIniziale = gestore.getList().size();
        
        gestore.remove(utenteB); // Utente B non è stato aggiunto
        
        assertEquals(dimensioneIniziale, gestore.getList().size());
    }
    
    @Test
    void testGetListRestituisceCollezioneCorretta() {
        gestore.add(utenteA);
        gestore.add(utenteB);
        
        List<Utente> lista = gestore.getList();
        
        assertEquals(2, lista.size());
        assertTrue(lista.contains(utenteA));
        assertTrue(lista.contains(utenteB));
    }
    
    // =========================================================
    // TEST CONTAINS (Basato su Matricola e Email)
    // =========================================================

    @Test
    void testContainsTrovaUtenteUgualePerMatricolaEmail() {
        gestore.add(utenteA);
        
        // utenteUgualeA ha la stessa matricola o email di utenteA
        assertTrue(gestore.contains(utenteUgualeA)); 
    }
    
    @Test
    void testContainsNonTrovaUtenteInesistente() {
        gestore.add(utenteA);
        
        // utenteB ha matricola e email diverse
        assertFalse(gestore.contains(utenteB));
    }

    // =========================================================
    // TEST RICERCA (containsString)
    // =========================================================

    @Test
    void testContainsStringTrovaCorrispondenzeInNome() {
        gestore.add(utenteA); // Mario Rossi
        gestore.add(utenteB); // Luisa Bianchi
        
        List<Utente> risultati = gestore.containsString("oss");
        
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(utenteA));
    }
    
    @Test
    void testContainsStringTrovaCorrispondenzaInEmail() {
        gestore.add(utenteA); // R001, mario.rossi
        gestore.add(utenteB); // B002, luisa.bianchi
        
        List<Utente> risultati = gestore.containsString("uni");
        
        assertEquals(2, risultati.size());
        assertTrue(risultati.contains(utenteA));
        assertTrue(risultati.contains(utenteB));
    }
    
    @Test
    void testContainsStringRitornaListaVuotaPerNessunMatch() {
        gestore.add(utenteA);
        
        List<Utente> risultati = gestore.containsString("Giovanni");
        
        assertTrue(risultati.isEmpty());
    }
}