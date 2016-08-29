package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class Action implements Statement {
    
    public enum Operation { REPLACE };
    
    Operation operation;
    Term source;
    Term target;
    
    
    public Action(Term t1, Operation op, Term t2) {
        operation = op;
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
    public DUDES postprocess(DUDES top) {
        
        DUDES dudes = top.clone();   
        dudes.drs.removeStatement(this);
        
        switch (operation) {
            case REPLACE:    
                 if (!source.equals(target)) {
                     dudes.replace(source,target);
                 }
                 break;
        }

        return dudes;
    }
    
    @Override
    public Set<Triple> convertToRDF(Query top) {
        
        Set<Triple> triples = new HashSet<>();
        
        if (operation == Operation.REPLACE) {
            triples.add(new Triple(source.convertToNode(top),NodeFactory.createURI("http://www.w3.org/2002/07/owl#sameAs"),target.convertToNode(top)));
        }
        
        return triples;
    }
    
    @Override
    public String toString() {
        
        String s = ""; 
        
        switch (operation) {
            case REPLACE: s = "REPLACE(" + source.toString() + "," + target.toString() + ")"; break;
        }
        
        return s;
    }
    
    @Override
    public Action clone() {
        return new Action(source.clone(),operation,target.clone());
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.operation);
        hash = 37 * hash + Objects.hashCode(this.source);
        hash = 37 * hash + Objects.hashCode(this.target);
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
        final Action other = (Action) obj;
        if (this.operation != other.operation) {
            return false;
        }
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        return true;
    }
    
}
