package de.citec.sc.dudes;

import java.util.Set;

/**
 *
 * @author cunger
 */
public interface Statement {
    
    public Set<Integer> collectVariables();
    
    public void union(DRS drs, int label);
    
    public void replace(int i_old, int i_new); 
    
    public Statement clone();
}
