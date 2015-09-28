package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Triple;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class Equals implements Statement {
    
    Term left;
    Term right;
    
    
    public Equals(Term t1, Term t2) {
        left  = t1;
        right = t2;
    }
    
    
    @Override
    public Set<Integer> collectVariables() {
        
        HashSet<Integer> vars = new HashSet<>();
        
        if (left.isVariable())  vars.add(((Variable) left).getInt());
        if (right.isVariable()) vars.add(((Variable) right).getInt());
        
        return vars;
    }
    
    @Override
    public void union(DRS drs, int label) {
    }
    
    @Override
    public void replace(int i_old, int i_new) {
        
        left.replace(i_old,i_new);
        right.replace(i_old,i_new);
    }
    
    @Override
    public Set<Triple> convertToRDF() {
        
        Set<Triple> triples = new HashSet<>();
        
        triples.add(new Triple(left.convertToNode(),NodeFactory.createURI("http://www.w3.org/2002/07/owl#sameAs"),right.convertToNode()));
        
        return triples;
    }
    
    @Override
    public String toString() {
        return left.toString() + "=" + right.toString();
    }
    
    @Override
    public Equals clone() {
        return new Equals(left.clone(),right.clone());
    }
    
}
