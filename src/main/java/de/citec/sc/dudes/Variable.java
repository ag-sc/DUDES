package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Node_Variable;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.ExprVar;

/**
 *
 * @author cunger
 */
public class Variable implements Term {
    
    int i;
    

    public Variable(Integer i) {
        this.i = i;
    }
    
    
    public int getInt() {
        return this.i;
    }
    
    public void setInt(int i) {
        this.i = i;
    }
    
    @Override
    public boolean isVariable() {
        return true;
    }
    
    @Override
    public void rename(int i_old, int i_new) {
        if (this.i == i_old) this.i = i_new;
    }
    
    @Override
    public void rename(String s_old, String s_new) {
    }
    
    @Override 
    public Term replace(Term t_old, Term t_new) {
        if (this.equals(t_old)) return t_new;
        else                    return this;
    }

    @Override
    public Node convertToNode(Query top) {       
        return NodeFactory.createVariable(this.toString());
    }
    
    @Override 
    public Expr convertToExpr(Query top) {

        return new ExprVar(""+i);
    }
    
    @Override
    public String toString() {
        return "v" + i;
    }
    
    @Override
    public Variable clone() {
        return new Variable(i);
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.i;
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
        final Variable other = (Variable) obj;
        if (this.i != other.i) {
            return false;
        }
        return true;
    }  
    
}
