package de.citec.sc.dudes;

import java.util.Set;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.syntax.Element;

/**
 *
 * @author cunger
 */
public interface Statement {
    
    public Set<Integer> collectVariables();
    
    public void union(DRS drs, int label);
    
    public void rename(int i_old, int i_new); 
    public void rename(String s_old, String s_new);
    public void replace(Term t_old, Term t_new);
            
    public Set<Replace> collectReplacements();
    
    public Statement clone();
    
    public Element convertToRDF(Query top);
}
