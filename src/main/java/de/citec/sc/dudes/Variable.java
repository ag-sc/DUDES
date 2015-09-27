package de.citec.sc.dudes;

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
    public String toString() {
        return "v" + i;
    }
    
    @Override
    public Variable clone() {
        return new Variable(i);
    }

}
