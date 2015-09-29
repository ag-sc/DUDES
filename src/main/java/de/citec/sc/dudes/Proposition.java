package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class Proposition implements Statement {
    
    Term       predicate;
    List<Term> arguments;
    
    
    public Proposition(Term predicate, List<Term> arguments) {
        this.predicate = predicate;
        this.arguments = arguments;
    }
    
    
    @Override
    public Set<Integer> collectVariables() {
        
        HashSet<Integer> vars = new HashSet<>();
        
        if (predicate.isVariable()) {
            vars.add(((Variable) predicate).getInt());
        }
        for (Term a : arguments) {
            if (a.isVariable()) {
                vars.add(((Variable) a).getInt());
            }
        }
            
        return vars;
    }
    
    
    @Override 
    public void union(DRS drs, int label) {
    }
    
    @Override 
    public void replace(int i_old, int i_new) {
        
        predicate.replace(i_old,i_new);
        for (Term a : arguments) a.replace(i_old,i_new);
    }
    
    @Override
    public void replace(String s_old, String s_new) {
        
        predicate.replace(s_old,s_new);
        for (Term a : arguments) a.replace(s_old,s_new);
    }
    
    @Override
    public Set<Triple> convertToRDF() {
        
        Set<Triple> triples = new HashSet<>();
    
        if (arguments.size() == 2) {
            triples.add(new Triple(arguments.get(0).convertToNode(),predicate.convertToNode(),arguments.get(1).convertToNode()));
        }
        
        return triples;
    }
    
    @Override
    public String toString() {
        
        String proposition = predicate.toString() + "(";
        
        Iterator<Term> i = arguments.iterator();
        while (i.hasNext()) {
            proposition += i.next().toString();
            if (i.hasNext()) proposition += ",";
        }
        
        return proposition + ")";
    }
    
    @Override
    public Proposition clone() {
    
        List<Term> clonedArguments = new ArrayList<>();
        for (Term a : arguments) {
             clonedArguments.add(a.clone());
        }
        
        return new Proposition(predicate.clone(),clonedArguments);
    }
    
}
