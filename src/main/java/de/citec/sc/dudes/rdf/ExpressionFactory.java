package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.*;

/**
 *
 * @author cunger
 */
public class ExpressionFactory {
    
    VariableSupply vars = new VariableSupply();
    
    
    public ExpressionFactory() {
    }
    
    
    public RDFDUDES what() {
        
        vars.reset();

        Variable var = new Variable(vars.getFresh());

        DUDES what = new DUDES();
        what.addReturnVariable(var);
        what.setMainVariable(var);
        what.setMainDRS(0);      
        
        DRS what_drs = new DRS(0);
        what_drs.addVariable(var);
        what.setDRS(what_drs);
    
        return new RDFDUDES(what,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES which(String anchor) {
        
        vars.reset();

        Variable var = new Variable(vars.getFresh());       

        DUDES which = new DUDES();
        which.addReturnVariable(var);
        which.setMainVariable(var);
        which.setMainDRS(0);
        
        DRS which_drs = new DRS(0);
        which_drs.addVariable(var);
        which.setDRS(which_drs);
        which.addSlot(new Slot(var,anchor));
    
        return new RDFDUDES(which,RDFDUDES.Type.OTHER);
    }
    
}
