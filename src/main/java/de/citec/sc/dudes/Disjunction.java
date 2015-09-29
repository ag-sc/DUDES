package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class Disjunction implements Statement {
    
    DRS left;
    DRS right;
    
    
    public Disjunction(DRS d1, DRS d2) {
        left  = d1;
        right = d2;
    }
    
    
    @Override
    public Set<Integer> collectVariables() {
        
        Set<Integer> vars = new HashSet<>();
        
        vars.addAll(left.collectVariables());
        vars.addAll(right.collectVariables());
        
        return vars;
    }
    
    @Override
    public void union(DRS drs, int label) {
        left.union(drs,label);
        right.union(drs,label);
    }
    
    @Override
    public void replace(int i_old, int i_new) {
        
        left.replace(i_old,i_new);
        right.replace(i_old,i_new);
    }
    
    @Override
    public void replace(String s_old, String s_new) {
        
        left.replace(s_old,s_new);
        right.replace(s_old,s_new);
    }
    
    @Override
    public Set<Triple> convertToRDF() {
        
        Set<Triple> triples = new HashSet<>();
        
        return triples;
    }
    
    @Override
    public String toString() {
        return left.toString() + "OR" + right.toString();
    }
    
    @Override 
    public Disjunction clone() {
        return new Disjunction(left.clone(),right.clone());
    }
    
}
