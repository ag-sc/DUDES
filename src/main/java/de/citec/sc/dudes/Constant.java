package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueInteger;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueNode;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueString;
import java.util.Objects;

/**
 *
 * @author cunger
 */
public class Constant implements Term {
    
    public enum Datatype { URI, STRING, INT, DATE, NONE };
    
    
    String value; 
    Datatype type;
    
    
    public Constant(String name) {
        
        this(name,Datatype.URI); 
    }
    
    public Constant(String value, Datatype type) { 
                
        this.value = value;
        this.type  = type;
    }
    
    
    public String getValue() {
        return value;
    }
    
    @Override
    public boolean isVariable() {
        return false;
    }
    
    @Override
    public void rename(int i_old, int i_new) {
    }

    @Override
    public void rename(String s_old, String s_new) {
        if (value.equals(s_old)) value = s_new;
    }
    
    @Override 
    public Term replace(Term t_old, Term t_new) {
        if (this.equals(t_old)) return t_new;
        else                    return this;
    }
    
    @Override
    public Node convertToNode(Query top) {
        
        Node node;
        
        switch (type) { 
            case URI:    node = NodeFactory.createURI(value); break;
            case STRING: node = NodeFactory.createLiteral(value); break; // TODO set language if available
            case INT:    node = NodeFactory.createLiteral(value,NodeFactory.getType("http://www.w3.org/2001/XMLSchema#int")); break;
            case DATE:   node = NodeFactory.createLiteral(value,NodeFactory.getType("http://www.w3.org/2001/XMLSchema#date")); break;
            default:     node = NodeFactory.createLiteral(value); break;
        }
                
        return node;
    }
    
    @Override
    public Expr convertToExpr(Query top) {
        
        Expr expr;
        
        switch (type) { 
            case STRING: expr = new NodeValueString(value); break; 
            case INT:    expr = new NodeValueInteger(Long.parseLong(value)); break;
            default:     expr = new NodeValueNode(this.convertToNode(top)); break;
        }
                
        return expr;
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public Constant clone() {
        return new Constant(value,type);
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.value);
        hash = 67 * hash + Objects.hashCode(this.type);
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
        final Constant other = (Constant) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
    
}
