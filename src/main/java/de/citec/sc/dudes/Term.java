package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.expr.Expr;

/**
 *
 * @author cunger
 */
public interface Term {
    
    public boolean isVariable();
    public boolean isFunction();
    
    public void rename(int i_old, int i_new);
    public void rename(String s_old, String s_new);
    public Term replace(Term t_old, Term t_new);
    
    public Term clone();
    
    public Node convertToNode(Query top);
    public Expr convertToExpr(Query top);

}