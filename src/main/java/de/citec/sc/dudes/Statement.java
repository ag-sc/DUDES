package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
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
    
    public Set<Triple> convertToRDF();
}
