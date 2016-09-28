package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementUnion;
import java.util.HashSet;
import java.util.Objects;
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
    public void rename(int i_old, int i_new) {
        
        left.rename(i_old,i_new);
        right.rename(i_old,i_new);
    }
    
    @Override
    public void rename(String s_old, String s_new) {
        
        left.rename(s_old,s_new);
        right.rename(s_old,s_new);
    }
    
    @Override 
    public void replace(Term t_old, Term t_new) {
        
        left.replace(t_old,t_new);
        right.replace(t_old,t_new);
    }
    
    @Override
    public DUDES postprocess(DUDES top) {

        DUDES fold; 
        fold = left.postprocess(top);
        fold = right.postprocess(fold);
        return fold;
    }
    
    @Override
    public Element convertToRDF(Query top) {
        
        // TODO add { left.convertToRDF() } UNION { right.convertToRDF() } to top
        
        ElementGroup union_left  = new ElementGroup();
        ElementGroup union_right = new ElementGroup();
        union_left.addElement(left.convertToRDF(top));
        union_right.addElement(right.convertToRDF(top));

        ElementUnion union = new ElementUnion();
        union.addElement(union_left);
        union.addElement(union_right);
        
        return union;
    }
    
    @Override
    public String toString() {
        return left.toString() + "OR" + right.toString();
    }
    
    @Override 
    public Disjunction clone() {
        return new Disjunction(left.clone(),right.clone());
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.left);
        hash = 79 * hash + Objects.hashCode(this.right);
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
        final Disjunction other = (Disjunction) obj;
        if (!Objects.equals(this.left, other.left)) {
            return false;
        }
        if (!Objects.equals(this.right, other.right)) {
            return false;
        }
        return true;
    }
    
}
