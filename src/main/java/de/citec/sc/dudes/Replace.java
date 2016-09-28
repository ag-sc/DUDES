package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class Replace implements Statement {
    
    Term source;
    Term target;
    
    
    public Replace(Term t1, Term t2) {
        source = t1;
        target = t2;
    }
    
    
    @Override
    public Set<Integer> collectVariables() {
        
        HashSet<Integer> vars = new HashSet<>();
        
        if (source.isVariable()) vars.add(((Variable) source).getInt());
        if (target.isVariable()) vars.add(((Variable) target).getInt());
        
        return vars;
    }
    
    @Override
    public void union(DRS drs, int label) {
    }
    
    @Override
    public void rename(int i_old, int i_new) {
        
        source.rename(i_old,i_new);
        target.rename(i_old,i_new);
    }
    
    @Override
    public void rename(String s_old, String s_new) {
        
        source.rename(s_old,s_new);
        target.rename(s_old,s_new);
    }    
    
    @Override 
    public void replace(Term t_old, Term t_new) {
        
        if (source.equals(t_old)) source = t_new;
        if (target.equals(t_old)) target = t_new;
    }
    
    @Override 
    public Set<Replace> collectReplacements() {
        
        Set<Replace> replacements = new HashSet<>();        
        replacements.add(this);
        return replacements;
    }
    
    @Override
    public Element convertToRDF(Query top) {
        
        ElementGroup group = new ElementGroup();
        
        group.addTriplePattern(new Triple(source.convertToNode(top),NodeFactory.createURI("http://www.w3.org/2002/07/owl#sameAs"),target.convertToNode(top)));
        
        return group;
    }
    
    @Override
    public String toString() {
        
        return "REPLACE(" + source.toString() + "," + target.toString() + ")"; 
    }
    
    @Override
    public Replace clone() {
        return new Replace(source.clone(),target.clone());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.source);
        hash = 59 * hash + Objects.hashCode(this.target);
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
        final Replace other = (Replace) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        return true;
    }
        
}
