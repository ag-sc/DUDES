package de.citec.sc.dudes;

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
public class DRS {
    
    int label;
    
    Set<Variable>  variables;    
    Set<Statement> statements;
    
   
    public DRS(int label) {
        this.label = label;
        variables  = new HashSet<>();
        statements = new HashSet<>();
    }
    
    
    public Set<Statement> getStatements() {
        return statements;
    }
    
    public void setLabel(int i) {
        label = i;
    }
    
    public void addVariable(Variable variable) {
        variables.add(variable);
    }
    
    public void addStatement(Statement statement) {
        statements.add(statement);
    }
    
    public void removeStatement(Statement statement) {
        
        Set<Statement> new_statements = new HashSet<>();
        for (Statement s : statements) {
             if (!s.equals(statement)) {
                 new_statements.add(s);
             } 
        }
        statements = new_statements;
    }
    
    public Set<Integer> collectVariables() {
        
        HashSet<Integer> vars = new HashSet<>();
        
        vars.add(label);
        for (Variable v : variables) {
             vars.add(v.getInt());
        }
        for (Statement s : statements) {
             vars.addAll(s.collectVariables());
        }
                
        return vars;
    }
    
    // Refactoring
    
    public void rename(int i_old, int i_new) {
        
        if (label == i_old) label = i_new;
        for (Variable  v : variables)  v.rename(i_old,i_new);
        for (Statement s : statements) s.rename(i_old,i_new);
    }
    
    public void rename(String s_old, String s_new) {
        
        for (Statement s : statements) s.rename(s_old,s_new);
    }
    
    public void replace(Term t_old, Term t_new) {
                
        Set<Variable> new_variables = new HashSet<>();
        for (Variable var : variables) {
            if (var.equals((Variable) t_old)) {
                if (t_new.isVariable())  {
                    new_variables.add((Variable) t_new);
                }
            } else  new_variables.add(var);
        }
        variables = new_variables;
        
        for (Statement s : statements) s.replace(t_old,t_new);
    }
    
    
    // Union 
    
    public void union(DRS other, int label) {
        
        if (this.label == label) {
            this.variables.addAll(other.variables);
            this.statements.addAll(other.statements);
        }
        else {
            for (Statement s : this.statements) {
                 s.union(other,label);
            }
        }
    }
    
    // Conversion to RDF 
    
    public Element convertToRDF(Query top) {
        
        ElementGroup group = new ElementGroup();
        for (Statement s : statements) {
             group.addElement(s.convertToRDF(top));
        }
        return group;
    }
    
    
    // Postprocessing 
    
    public Set<Replace> collectReplacements() {
                
        Set<Replace> replacements = new HashSet<>();
        for (Statement s : statements) {
             replacements.addAll(s.collectReplacements());
        }
        return replacements;
    }
    
    public void postprocess() {
                
        // Replacements 
                
        // collect all replace statements      
        
        Set<Replace> replaces = collectReplacements();
        
        Set<Replace> var2var = new HashSet<>();
        Set<Replace> var2con = new HashSet<>();
        Set<Replace> delete  = new HashSet<>();
        
        for (Replace r : replaces) {
             if (r.source.equals(r.target)) {
                 delete.add(r);
                 continue;
             }
             if (r.source.isVariable()) {
                 if (r.target.isVariable()) {
                     var2var.add(r);
                 } else { 
                     var2con.add(r);
                 }
             } 
        }
        
        // remove all those where source=target
        for (Replace r : delete) {
             removeStatement(r);
        }
        // first replace those of form REPLACE(var1,var2)
        for (Replace r : var2var) {
             removeStatement(r);
             replace(r.source,r.target);
        }
        // then replace those of form REPLACE(var,cons)
        for (Replace r : var2con) {
             removeStatement(r);
             replace(r.source,r.target);
        }
    }

    
    // Printing and cloning
    
    @Override
    public String toString() {
        
        String drs = label + ":";
        
        drs += "[ ";
        for (Variable v : variables) {
            drs += v.toString() + " ";
        }
        drs += "| ";
        for (Statement s : statements) {
            drs += s.toString() + " ";
        }
        drs += "]";
        
        return drs;
    }
    
    @Override
    public DRS clone() {
        
        DRS clone = new DRS(this.label);
        
        for (Variable v : this.variables) {
            clone.addVariable(v.clone());
        }
        for (Statement s : this.statements) {
            clone.addStatement(s.clone());
        }
        
        return clone;
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.label;
        hash = 59 * hash + Objects.hashCode(this.variables);
        hash = 59 * hash + Objects.hashCode(this.statements);
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
        final DRS other = (DRS) obj;
        if (this.label != other.label) {
            return false;
        }
        if (!Objects.equals(this.variables, other.variables)) {
            return false;
        }
        if (!Objects.equals(this.statements, other.statements)) {
            return false;
        }
        return true;
    }
    
}
