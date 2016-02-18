package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueNode;

/**
 *
 * @author cunger
 */
public class Placeholder implements Term {
    
    String  value;
    boolean filled;
    
    
    public Placeholder(String value) {
        this.value  = value;
        this.filled = false;
    }

    public Placeholder(String value, boolean filled) {
        this.value  = value;
        this.filled = filled;
    }
    
    @Override
    public boolean isVariable() {
        return false;
    }
   
    @Override
    public boolean isFunction() {
        return false;
    }
    
    @Override
    public void rename(int i_old, int i_new) {
    }
    
    @Override
    public void rename(String s_old, String s_new) {
        if (this.value.equals(s_old)) {
            this.value  = s_new;
            this.filled = true;
        }
    }
    
    @Override
    public Term replace(Term t_old, Term t_new) {
        // TODO
        return this;
    }
       
    @Override
    public Node convertToNode(Query top) {
        if (filled) return NodeFactory.createURI(this.toString());
        else        return NodeFactory.createVariable(this.toString());
    }
    
    @Override 
    public Expr convertToExpr(Query top) {
        return new NodeValueNode(this.convertToNode(top));
    }
    
    @Override
    public String toString() {
        return this.value;
    }
    
    @Override
    public Term clone() {
        //return new Placeholder(this.name);
        return new Placeholder(this.value, this.filled);
    }
}
