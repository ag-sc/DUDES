package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.expr.*;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueNode;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class Comparison implements Statement {
    
    public enum Operator { EQUALS, LESS, LESSEQUALS, GREATER, GREATEREQUALS };
    
    
    Term left;
    Term right;
    Operator operator;
    
    
    public Comparison(Term t1, Operator o, Term t2) {
        left  = t1;
        right = t2;
        operator = o;
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
    public void removeActions() {
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
    
        left  = left.replace(t_old,t_new);
        right = right.replace(t_old,t_new);
    }
    
    @Override
    public DUDES postprocess(DUDES top) {

        return top;
    }
    
    @Override
    public Set<Triple> convertToRDF(Query top) {
        
        Set<Triple> triples = new HashSet<>();
        
        switch (operator) { 
            case EQUALS:        top.addHavingCondition(new E_Equals(left.convertToExpr(top),right.convertToExpr(top))); break;
            case LESS:          top.addHavingCondition(new E_LessThan(left.convertToExpr(top),right.convertToExpr(top))); break;
            case LESSEQUALS:    top.addHavingCondition(new E_LessThanOrEqual(left.convertToExpr(top),right.convertToExpr(top))); break;
            case GREATER:       top.addHavingCondition(new E_GreaterThan(left.convertToExpr(top),right.convertToExpr(top))); break;
            case GREATEREQUALS: top.addHavingCondition(new E_GreaterThanOrEqual(left.convertToExpr(top),right.convertToExpr(top))); break;
        }
        
        return triples;
    }
    
    @Override
    public String toString() {
        
        String op = ""; 
        switch (operator) {
            case EQUALS:        op = "=" ; break;
            case LESS:          op = "<" ; break;
            case LESSEQUALS:    op = "<="; break;
            case GREATER:       op = ">" ; break;
            case GREATEREQUALS: op = ">="; break;
        }
        return left.toString() + op + right.toString();
    }
    
    @Override
    public Comparison clone() {
        return new Comparison(left.clone(),operator,right.clone());
    }
    
}
