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
    
    public DRS() {
        this(0);
    }
    
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
    
}
