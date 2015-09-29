package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;
import com.hp.hpl.jena.graph.Node_URI;

/**
 *
 * @author cunger
 */
public class Constant implements Term {
    
    String name; 
    
    
    public Constant(String name) { 
        this.name = name;
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
        if (name.equals(s_old)) name = s_new;
    }
    
    @Override
    public Node convertToNode() {
        
        return NodeFactory.createURI(name);
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public Constant clone() {
        return new Constant(name);
    }
    
}
