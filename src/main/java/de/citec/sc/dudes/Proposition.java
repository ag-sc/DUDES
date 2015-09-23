package de.citec.sc.dudes;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author cunger
 */
public class Proposition implements Statement {
    
    String     predicate;
    List<Term> arguments;
    
    public Proposition(String predicate, List<Term> arguments) {
        this.predicate = predicate;
        this.arguments = arguments;
    }
    
    @Override
    public String toString() {
        
        String proposition = predicate + "(";
        
        Iterator<Term> i = arguments.iterator();
        while (i.hasNext()) {
            proposition += i.next().toString();
            if (i.hasNext()) proposition += ",";
        }
        
        return proposition + ")";
    }
}
