package de.citec.sc.dudes;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.expr.E_Exists;
import com.hp.hpl.jena.sparql.expr.E_LogicalNot;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementFilter;
import java.util.Objects;
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
    public Element convertToRDF(Query top) {
               
        return new ElementFilter(new E_LogicalNot(new E_Exists(drs.convertToRDF(top))));
    }
    
    @Override
    public String toString() {
        return "NOT" + drs.toString();
    }
    
    @Override
    public Negation clone() {
        return new Negation(drs.clone());
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.drs);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Negation other = (Negation) obj;
        if (!Objects.equals(this.drs, other.drs)) {
            return false;
        }
        return true;
    }
    
}
