package de.citec.sc.dudes;

/**
 *
 * @author cunger
 */
public class Negation implements Statement {
    
    DUDES dudes;
    
    public Negation(DUDES dudes) {
        this.dudes = dudes;
    }
    
    @Override
    public String toString() {
        return "NOT" + dudes.toString();
    }
    
}
