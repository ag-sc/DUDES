package de.citec.sc.dudes;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.syntax.Element;
import java.util.Set;

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
