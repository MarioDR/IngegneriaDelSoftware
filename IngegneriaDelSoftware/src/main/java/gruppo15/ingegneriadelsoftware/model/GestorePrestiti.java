package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.PrestitoNonValidoException;
import javafx.collections.ObservableList;

public class GestorePrestiti implements Manager<Prestito> {

    private ObservableList<Prestito> listaPrestitiAttivi;

    public GestorePrestiti() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Prestito add(Prestito object) throws PrestitoNonValidoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Prestito remove(Prestito object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Prestito search(Prestito object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ObservableList<Prestito> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean contains(Prestito object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
