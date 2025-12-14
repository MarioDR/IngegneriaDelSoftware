package Test;

import gruppo15.ingegneriadelsoftware.model.GestoreLibri;
import gruppo15.ingegneriadelsoftware.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestoreLibriTest {

    private GestoreLibri gestore;
    private Libro libroA;
    private Libro libroB;
    private Libro libroC;

    @BeforeEach
    void setUp() {
        // Poiché GestoreLibri è un Singleton, dobbiamo recuperare l'istanza
        gestore = GestoreLibri.getInstance();
        
        // Puliamo la lista prima di ogni test per garantire l'isolamento (necessario per i Singleton)
        gestore.getList().clear(); 
        
        // Creiamo oggetti Libro reali per i test
        libroA = new Libro("Libro Zero", "Autore Uno", LocalDate.of(2020, 1, 1), "1234567890123", 1, 10f);
        libroB = new Libro("Libro Test", "Autore Due", LocalDate.of(2021, 1, 1), "1234567890124", 5, 20f);
        // Libro C ha lo stesso ISBN di Libro B (dovrebbe essere considerato "uguale" in base a Libro.equals())
        libroC = new Libro("Libro Diverso", "Autore Tre", LocalDate.of(2022, 1, 1), "1234567890124", 2, 30f); 
    }

    // =========================================================
    // TEST SINGLETON
    // =========================================================

    @Test
    void testGetInstanceRestituisceSingolaIstanza() {
        GestoreLibri istanza1 = GestoreLibri.getInstance();
        GestoreLibri istanza2 = GestoreLibri.getInstance();
        
        // Le due istanze devono essere lo stesso oggetto
        assertSame(istanza1, istanza2);
    }
    
    // =========================================================
    // TEST FUNZIONALITA' GET LIST, ADD E REMOVE
    // =========================================================
    
    @Test
    void testAddAggiungeLibroValido() {
        List<Libro> lista = gestore.add(libroA);
        
        assertTrue(lista.contains(libroA));
        assertEquals(1, lista.size());
    }
    
    @Test
    void testAddIgnoraLibroNull() {
        int dimensioneIniziale = gestore.getList().size();
        gestore.add(null);
        
        assertEquals(dimensioneIniziale, gestore.getList().size());
    }

    @Test
    void testRemoveRimuoveLibroEsistente() {
        gestore.add(libroA);
        gestore.add(libroB);
        
        List<Libro> lista = gestore.remove(libroA);
        
        assertFalse(lista.contains(libroA));
        assertEquals(1, lista.size());
    }
    
    @Test
    void testRemoveIgnoraLibroNonEsistente() {
        gestore.add(libroA);
        int dimensioneIniziale = gestore.getList().size();
        
        gestore.remove(libroB); // Libro B non è stato aggiunto
        
        assertEquals(dimensioneIniziale, gestore.getList().size());
    }
    
    @Test
    void testGetListRestituisceCollezioneCorretta() {
        gestore.add(libroA);
        gestore.add(libroB);
        
        List<Libro> lista = gestore.getList();
        
        assertEquals(2, lista.size());
        assertTrue(lista.contains(libroA));
    }

    // =========================================================
    // TEST CONTAINS
    // =========================================================

    @Test
    void testContainsTrovaLibroUgualePerISBN() {
        gestore.add(libroA);
        gestore.add(libroB);
        
        // Libro C ha lo stesso ISBN di Libro B, quindi deve essere considerato contenuto
        assertTrue(gestore.contains(libroC)); 
    }
    
    @Test
    void testContainsNonTrovaLibroInesistente() {
        gestore.add(libroA);
        
        // Libro B ha ISBN diverso da Libro A
        assertFalse(gestore.contains(libroB));
    }

    // =========================================================
    // TEST RICERCA (containsString)
    // =========================================================

    @Test
    void testContainsStringTrovaCorrispondenze() {
        // Contiene "Zero" nel titolo
        gestore.add(libroA); 
        // Contiene "Test" nel titolo
        gestore.add(libroB); 
        
        // Cerca la sottostringa "Test" (case insensitive)
        List<Libro> risultati = gestore.containsString("test");
        
        assertEquals(1, risultati.size());
        assertTrue(risultati.contains(libroB));
    }
    
    @Test
    void testContainsStringTrovaCorrispondenzeMultiple() {
        // Libro A: Autore Uno
        gestore.add(libroA); 
        // Libro B: Autore Due
        gestore.add(libroB); 
        // Libro C: Titolo (Libro Diverso), Autore Tre
        gestore.add(libroC);
        
        // Cerca la sottostringa "Autore" (presente in tutti e tre i libri)
        List<Libro> risultati = gestore.containsString("autore");
        
        assertEquals(3, risultati.size());
        assertTrue(risultati.contains(libroA));
        assertTrue(risultati.contains(libroB));
        assertTrue(risultati.contains(libroC));
    }
    
    @Test
    void testContainsStringRitornaListaVuotaPerNessunMatch() {
        gestore.add(libroA);
        
        List<Libro> risultati = gestore.containsString("Inesistente");
        
        assertTrue(risultati.isEmpty());
    }
    
    @Test
    void testContainsStringRitornaTuttaLaListaPerPatternNullo() {
        gestore.add(libroA);
        gestore.add(libroB);
        
        // Assumendo che Libro.containsPattern gestisca null/vuoto/blank ritornando false,
        // la lista finale dovrebbe essere vuota.
        List<Libro> risultati = gestore.containsString(null);
        
        assertEquals(2, risultati.size());
        assertTrue(risultati.contains(libroA));
        assertTrue(risultati.contains(libroB));
    }
}