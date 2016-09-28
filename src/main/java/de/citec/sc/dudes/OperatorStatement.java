package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.expr.*;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class OperatorStatement implements Statement {
    
    public enum Operator { EQUALS, LESS, LESSEQUALS, GREATER, GREATEREQUALS, MAX, MIN };
    
    
    Term left;
    Term right;
    Operator operator;
    
    
    public OperatorStatement(Term t1, Operator o, Term t2) {
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
    public Element convertToRDF(Query top) {
                
        if (left.isVariable()) { 
            // top.addGroupBy(left.toString());
        }
        else if (left.isFunction()) {
            Term t = ((Function) left).getTerm(); 
            if (t.isVariable()) top.addGroupBy(t.toString());
        }
        else if (right.isVariable()) {
            // top.addGroupBy(right.toString());
        } 
        
        switch (operator) { 
            case EQUALS:        top.addHavingCondition(new E_Equals(left.convertToExpr(top),right.convertToExpr(top))); break;
            case LESS:          top.addHavingCondition(new E_LessThan(left.convertToExpr(top),right.convertToExpr(top))); break;
            case LESSEQUALS:    top.addHavingCondition(new E_LessThanOrEqual(left.convertToExpr(top),right.convertToExpr(top))); break;
            case GREATER:       top.addHavingCondition(new E_GreaterThan(left.convertToExpr(top),right.convertToExpr(top))); break;
            case GREATEREQUALS: top.addHavingCondition(new E_GreaterThanOrEqual(left.convertToExpr(top),right.convertToExpr(top))); break;
            case MAX:           // top.addGroupBy(left.convertToExpr(top)); 
                                top.addOrderBy(right.convertToExpr(top),Query.ORDER_DESCENDING); top.setOffset(0); top.setLimit(1); break;
            case MIN:           // top.addGroupBy(left.convertToExpr(top)); 
                                top.addOrderBy(right.convertToExpr(top),Query.ORDER_ASCENDING);  top.setOffset(0); top.setLimit(1); break;
        }

        return new ElementGroup();
    }
    
    @Override
    public String toString() {
        
        if (operator == Operator.MAX) return "max_" + left.toString() + "(" + right.toString() + ")";
        if (operator == Operator.MIN) return "min_" + left.toString() + "(" + right.toString() + ")";
        
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
    public OperatorStatement clone() {
        return new OperatorStatement(left.clone(),operator,right.clone());
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.left);
        hash = 97 * hash + Objects.hashCode(this.right);
        hash = 97 * hash + Objects.hashCode(this.operator);
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
        final OperatorStatement other = (OperatorStatement) obj;
        if (!Objects.equals(this.left, other.left)) {
            return false;
        }
        if (!Objects.equals(this.right, other.right)) {
            return false;
        }
        if (this.operator != other.operator) {
            return false;
        }
        return true;
    }
    
}
