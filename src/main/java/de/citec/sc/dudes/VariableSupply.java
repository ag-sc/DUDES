package de.citec.sc.dudes;

/**
 *
 * @author cunger
 */
public class VariableSupply {
    
    int fresh;
    
    
    public VariableSupply() {
        fresh = 0;
    }
    
    public int getFresh() {
        fresh++;
        return fresh;
    }

    public void reset() {
        fresh = 0;
    }
    
    public void reset(int i) {
        fresh = i;
    }
    
}
