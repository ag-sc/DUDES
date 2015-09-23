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
    
    public String toString() {
        return "(" + var.toString() + "," + anchor + "," + label + ")";
    }
    
}
