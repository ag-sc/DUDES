package de.citec.sc.dudes;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class DUDES {

    Variable mainVariable;  
    int      mainDRS;
    
    Set<Variable> returnVariables;
    
    DRS drs;
    
    Set<Slot> slots;
    
    
    public DUDES() {

        mainVariable    = null;
        mainDRS         = 0;
        returnVariables = new HashSet<>();
        
        drs   = new DRS();
        slots = new HashSet<>();
    }
    
    public void setMainVariable(Variable var) {
        mainVariable = var;
    }
    
    public void setMainDRS(int i) {
        mainDRS = i;
    }
    
    public void addReturnVariable(Variable var) {
        returnVariables.add(var);
    }

    public void setDRS(DRS drs) {
        this.drs = drs;
    }
    
    public void addSlot(Slot slot) {
        slots.add(slot);
    }

    
    // Combining DUDES
    
    public void combineWith(DUDES other) {
        
        // TODO
    }
    
    
    // Printing
    
    @Override
    public String toString() {
        
        String dudes = "";
    
        dudes += "( "; 
        dudes += "return: ";
        for (Variable v : returnVariables) {
            dudes += v.toString() + " ";
        }
        if (returnVariables.isEmpty()) {
            dudes += "- ";
        }
        dudes += ", main: (";
        if (mainVariable != null) { 
            dudes += mainVariable.toString();
        } else {
            dudes += "-";
        }
        dudes += "," + mainDRS + ")";
        dudes += " , drs: " + drs.toString();
        dudes += " , slots: ";
        for (Slot s : slots) {
            dudes += s.toString() + " ";
        }
        if (slots.isEmpty()) dudes += "- ";
        dudes += ")";
        
        return dudes;
    }
    
}
