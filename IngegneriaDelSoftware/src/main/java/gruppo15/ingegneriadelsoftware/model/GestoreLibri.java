package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.LibroNonValidoException;
import javafx.collections.ObservableList;

public class GestoreLibri implements Manager<Libro> {

    private ObservableList<Libro> listaLibri;

    public GestoreLibri() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Libro add(Libro object) throws LibroNonValidoException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Libro remove(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Libro search(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ObservableList<Libro> getList(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean contains(Libro object){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
