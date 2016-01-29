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
    
    public void removeActions() {
        
        Set<Statement> new_statements = new HashSet<>();
        for (Statement s : statements) { 
             if (!s.getClass().equals(Action.class)) {
                  s.removeActions();
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
    
    
    // Postprocessing 
    
    public DUDES postprocess(DUDES top) {
        
        DUDES fold = top;
        for (Statement s : statements) { 
             fold = s.postprocess(fold);            
        }
        
        return fold;
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
    
}
