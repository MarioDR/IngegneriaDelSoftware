/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo15.ingegneriadelsoftware.model;

/**
 *
 * @author fgrim
 */
public interface Searchable {
    
    /**
     * Verifica se l'elemento ha almeno uno degli attributi (quelli di tipo String) che contengono una certa stringa.
     * 
     * @param pattern la stringa usata per la ricerca
     * @return  {@code true} se l'oggetto corrisponde al pattern
     *          {@code false} in ogni altro caso
     */
    
    public boolean containsPattern(String pattern);
}
