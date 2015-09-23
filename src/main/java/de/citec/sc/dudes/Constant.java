package de.citec.sc.dudes;

/**
 *
 * @author cunger
 */
public class Constant implements Term {
    
    String name; 
    
    public Constant(String name) { 
        this.name = name;
    }
    
    public String toString() {
        return name;
    }

}
