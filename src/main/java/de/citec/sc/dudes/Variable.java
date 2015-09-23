package de.citec.sc.dudes;

/**
 *
 * @author cunger
 */
public class Variable implements Term {
    
    int i;
    
    public Variable(Integer i) {
        this.i = i;
    }
    
    @Override
    public String toString() {
        return "v" + i;
    }

}
