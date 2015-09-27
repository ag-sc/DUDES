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
    
    
    @Override
    public boolean isVariable() {
        return false;
    }
    
    @Override
    public void replace(int i_old, int i_new) {
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public Constant clone() {
        return new Constant(name);
    }
    
}
