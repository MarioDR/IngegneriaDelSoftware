package gruppo15.ingegneriadelsoftware.model;

import gruppo15.ingegneriadelsoftware.model.exceptions.ValidationException;
import javafx.collections.ObservableList;

public interface Manager<T> {
    public T add(T object) throws ValidationException;
    
    public T remove(T object);
    
    public T search(T object);
    
    public ObservableList<T> getList();
    
    public boolean contains(T object);
    
    public ObservableList<T> startsWith(String regex);
}
