package de.citec.sc.dudes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementGroup;

/**
 *
 * @author cunger
 */
public class Proposition implements Statement {
    
    Term       predicate;
    List<Term> arguments;
    
    
    public Proposition(Term predicate, List<Term> arguments) {
        this.predicate = predicate;
        this.arguments = arguments;
    }
    
    
    @Override
    public Set<Integer> collectVariables() {
        
        HashSet<Integer> vars = new HashSet<>();
        
        if (predicate.isVariable()) {
            vars.add(((Variable) predicate).getInt());
        }
        for (Term a : arguments) {
            if (a.isVariable()) {
                vars.add(((Variable) a).getInt());
            }
        }
            
        return vars;
    }
    
    
    @Override 
    public void union(DRS drs, int label) {
    }
    
    @Override 
    public void rename(int i_old, int i_new) {
        
        predicate.rename(i_old,i_new);
        for (Term a : arguments) a.rename(i_old,i_new);
    }
    
    @Override
    public void rename(String s_old, String s_new) {
        
        predicate.rename(s_old,s_new);
        for (Term a : arguments) a.rename(s_old,s_new);
    }
    
    @Override 
    public void replace(Term t_old, Term t_new) {
        
        predicate = predicate.replace(t_old,t_new);
        
        List<Term> new_arguments = new ArrayList<>();
        for (Term a : arguments) { 
             new_arguments.add(a.replace(t_old,t_new));
        }
        arguments = new_arguments;
    }
    
    @Override 
    public Set<Replace> collectReplacements() {
        
        return new HashSet<>();
    }
    
    @Override
    public Element convertToRDF(Query top) {
        
        ElementGroup group = new ElementGroup();
    
        if (arguments.size() == 2) {
            group.addTriplePattern(new Triple(arguments.get(0).convertToNode(top),predicate.convertToNode(top),arguments.get(1).convertToNode(top)));
        }
                
        if (group.getElements().size() == 1) {
            return group.getElements().get(0);
        } else {
            return group;
        }
    }
    
    @Override
    public String toString() {
        
        String proposition = predicate.toString() + "(";
        
        Iterator<Term> i = arguments.iterator();
        while (i.hasNext()) {
            proposition += i.next().toString();
            if (i.hasNext()) proposition += ",";
        }
        
        return proposition + ")";
    }
    
    @Override
    public Proposition clone() {
    
        List<Term> clonedArguments = new ArrayList<>();
        for (Term a : arguments) {
             clonedArguments.add(a.clone());
        }
        
        return new Proposition(predicate.clone(),clonedArguments);
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.predicate);
        hash = 53 * hash + Objects.hashCode(this.arguments);
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
        final Proposition other = (Proposition) obj;
        if (!Objects.equals(this.predicate, other.predicate)) {
            return false;
        }
        if (!Objects.equals(this.arguments, other.arguments)) {
            return false;
        }
        return true;
    }
    
}
