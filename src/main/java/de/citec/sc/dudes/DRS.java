package de.citec.sc.dudes;

import java.util.HashSet;
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
    
    
    public void setLabel(int i) {
        label = i;
    }
    
    public void addVariable(Variable variable) {
        variables.add(variable);
    }
    
    public void addStatement(Statement statement) {
        statements.add(statement);
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
    
    public void replace(int i_old, int i_new) {
        
        if (label == i_old) label = i_new;
        for (Variable  v : variables)  v.replace(i_old,i_new);
        for (Statement s : statements) s.replace(i_old,i_new);
    }
    
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
    
}
