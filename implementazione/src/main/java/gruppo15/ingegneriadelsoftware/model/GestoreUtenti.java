package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.UtenteNonValidoException;
import javafx.collections.ObservableList;

public class GestoreUtenti implements Manager<Utente> {

    private ObservableList<Utente> listaUtenti;

    public GestoreUtenti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Utente add(Utente object) throws UtenteNonValidoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Utente remove(Utente object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Utente search(Utente object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ObservableList<Utente> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean contains(Utente object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ObservableList<Utente> startsWith(String regex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
