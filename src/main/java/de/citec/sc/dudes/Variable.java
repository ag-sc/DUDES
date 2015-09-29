package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.NodeFactory;

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
    public void replace(int i_old, int i_new) {
        if (this.i == i_old) this.i = i_new;
    }
    
    @Override
    public void replace(String s_old, String s_new) {
    }

    @Override
    public Node convertToNode() {
        
        return NodeFactory.createVariable(this.toString());
    }
    
    @Override
    public String toString() {
        return "v" + i;
    }
    
    @Override
    public Variable clone() {
        return new Variable(i);
    }

}
