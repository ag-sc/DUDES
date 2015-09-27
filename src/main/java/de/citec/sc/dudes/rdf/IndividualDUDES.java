package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.*;

/**
 *
 * @author cunger
 */
public class IndividualDUDES {
    
    VariableSupply vars; 
    
    
    public IndividualDUDES() {
        vars = new VariableSupply();
    }
    
    
    public DUDES create() {
        return create("*");
    }
    
    public DUDES create(String uri) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var = new Variable(vars.getFresh());
        
        DRS drs = new DRS(0);
        drs.addVariable(var);
        drs.addStatement(new Equals(var,new Constant(uri)));

        dudes.setMainDRS(0);
        dudes.setMainVariable(var);
        dudes.setDRS(drs);
        
        return dudes;
    }
    
}
