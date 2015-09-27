package de.citec.sc.dudes;

/**
 *
 * @author cunger
 */
public interface Term {
    
    public boolean isVariable();
    
    public void replace(int i_old, int i_new);
    
    public Term clone();
}