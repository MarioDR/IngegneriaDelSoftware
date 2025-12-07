package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.PrestitoNonValidoException;
import java.util.List;

public class Utente implements Checkable {

    private String nome;

    private String cognome;

    private String matricola;

    private String email;

    private List<Prestito> listaPrestiti;

    public Utente(String nome, String cognome, String matricola, String email) {
    }

    public String setNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String setCognome(String cognome) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String setMatricola(String matricola) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String setEmail(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getNome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getCognome() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getMatricola() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getEmail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Prestito> getListaPrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Prestito addPrestito(Prestito prestito) throws PrestitoNonValidoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Prestito removePrestito(Prestito p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean hasMaxNumPrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String toCSV() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
