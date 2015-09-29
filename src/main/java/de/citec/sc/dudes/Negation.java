package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class Negation implements Statement {
    
    DRS drs;
    
    
    public Negation(DRS drs) {
        this.drs = drs;
    }
    
    
    @Override
    public Set<Integer> collectVariables() {
        return drs.collectVariables();
    }
    
    @Override
    public void union(DRS drs, int label) {
        this.drs.union(drs,label);
    }
    
    @Override
    public void replace(int i_old, int i_new) {
        drs.replace(i_old,i_new);
    }
    
    @Override
    public void replace(String s_old, String s_new) {
        
        drs.replace(s_old,s_new);
    }    
    
    @Override
    public Set<Triple> convertToRDF() {
        
        Set<Triple> triples = new HashSet<>();
        
        return triples;
    }
    
    @Override
    public String toString() {
        return "NOT" + drs.toString();
    }
    
    @Override
    public Negation clone() {
        return new Negation(drs.clone());
    }
    
}
