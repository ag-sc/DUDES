package de.citec.sc.dudes;

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
    public String toString() {
        return "NOT" + drs.toString();
    }
    
    @Override
    public Negation clone() {
        return new Negation(drs.clone());
    }
    
}
