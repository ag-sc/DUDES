package de.citec.sc.dudes;

/**
 *
 * @author cunger
 */
public class Disjunction implements Statement {
    
    DUDES left;
    DUDES right;
    
    public Disjunction(DUDES d1, DUDES d2) {
        left  = d1;
        right = d2;
    }
    
    @Override
    public String toString() {
        return left.toString() + "OR" + right.toString();
    }
    
}
