package de.citec.sc.dudes;

/**
 *
 * @author cunger
 */
public class Equals implements Statement {
    
    Term left;
    Term right;
    
    public Equals(Term t1, Term t2) {
        left  = t1;
        right = t2;
    }
    
    @Override
    public String toString() {
        return left.toString() + " = " + right.toString();
    }
}
