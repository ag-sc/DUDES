package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.ExprAggregator;
import com.hp.hpl.jena.sparql.expr.aggregate.AggCount;
import com.hp.hpl.jena.sparql.expr.aggregate.AggCountVarDistinct;
import java.util.Objects;


/**
 *
 * @author cunger
 */
public class Function implements Term {
    
    public enum Func { COUNT };
    
    
    Func function;
    Term term;
    
    
    public Function(Func f, Term t) {
        function = f;
        term     = t;
    }
    
    
   @Override
    public boolean isVariable() {
        return false;
    }
    
    @Override
    public boolean isFunction() {
        return true;
    }
    
    public Term getTerm() {
        return term;
    }
        
    @Override
    public void rename(int i_old, int i_new) {
        term.rename(i_old,i_new);
    }
    
    @Override
    public void rename(String s_old, String s_new) {        
        term.rename(s_old,s_new);
    }
    
    @Override 
    public Term replace(Term t_old, Term t_new) {
        if (this.equals(t_old)) return t_new;
        if (term.equals(t_old)) term = t_new;
        return this;
    }
    
    @Override
    public Node convertToNode(Query top) {
        
        return term.convertToNode(top);
    }
    
    @Override
    public Expr convertToExpr(Query top) {
        
        if (term.isVariable()) 
           return new ExprAggregator(null,new AggCountVarDistinct(term.convertToExpr(top)));
            
        return null;
    }
    
    @Override
    public String toString() {
        return function + "(" + term.toString() + ")";
    }
    
    @Override
    public Function clone() {
        return new Function(function,term.clone());
    }

    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.function);
        hash = 47 * hash + Objects.hashCode(this.term);
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
        final Function other = (Function) obj;
        if (this.function != other.function) {
            return false;
        }
        if (!Objects.equals(this.term, other.term)) {
            return false;
        }
        return true;
    }
    
}
