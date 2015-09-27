package de.citec.sc.dudes;

/**
 *
 * @author cunger
 */
public class Slot {
    
    Variable var;
    String   anchor;
    int      label;
    
    
    public Slot(Variable var) {
        this(var,"*",0);
    }
    
    public Slot(Variable var, String anchor) {
        this(var,anchor,0);
    }
    
    public Slot(Variable var, String anchor, int label) {
        this.var    = var;
        this.anchor = anchor;
        this.label  = label;
    }
    
    
    public Variable getVariable() {
        return this.var;
    }
    
    public String getAnchor() {
        return this.anchor;
    }
    
    public void replace(int i_old, int i_new) {
        var.replace(i_old,i_new);
        if (label == i_old) label = i_new;
    }
            
    
    @Override
    public String toString() {
        return "(" + var.toString() + "," + anchor + "," + label + ")";
    }
    
    @Override
    public Slot clone() {
        return new Slot(new Variable(this.var.getInt()),this.anchor,this.label);
    }
    
}
