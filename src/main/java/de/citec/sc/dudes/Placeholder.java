package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;

/**
 *
 * @author cunger
 */
public class Placeholder implements Term {
    
    String  name;
    boolean filled;
    
    
    public Placeholder(String name) {
        this.name   = name;
        this.filled = false;
    }

    public Placeholder(String name, boolean filled) {
        this.name = name;
        this.filled = filled;
    }
    
    @Override
    public boolean isVariable() {
        return false;
    }
    
    @Override
    public void replace(int i_old, int i_new) {
    }
    
    @Override
    public void replace(String s_old, String s_new) {
        if (this.name.equals(s_old)) {
            this.name   = s_new;
            this.filled = true;
        }
    }
       
    @Override
    public Node convertToNode() {
        if (filled) return NodeFactory.createURI(this.toString());
        else        return NodeFactory.createVariable(this.toString());
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public Term clone() {
        //return new Placeholder(this.name);
        return new Placeholder(this.name, this.filled);
    }
}
