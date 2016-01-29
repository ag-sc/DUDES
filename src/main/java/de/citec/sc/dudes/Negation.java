package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
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
        drs.union(drs,label);
    }
    
    @Override
    public void removeActions() {
        drs.removeActions();
    }
    
    @Override
    public void rename(int i_old, int i_new) {
        drs.rename(i_old,i_new);
    }
    
    @Override
    public void rename(String s_old, String s_new) {
        
        drs.rename(s_old,s_new);
    }    
    
    @Override 
    public void replace(Term t_old, Term t_new) {
        
        drs.replace(t_old,t_new);
    }
    
    @Override
    public DUDES postprocess(DUDES top) {

        return drs.postprocess(top);
    }
    
    @Override
    public Set<Triple> convertToRDF(Query top) {
        
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
