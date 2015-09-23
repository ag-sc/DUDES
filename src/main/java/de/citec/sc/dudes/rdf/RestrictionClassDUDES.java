package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cunger
 */
public class RestrictionClassDUDES {
    
    VariableSupply vars; 
    
    
    public RestrictionClassDUDES() {
        vars = new VariableSupply();
    }
    
    
    public DUDES create() {
        return create("*","*");
    }
    
    public DUDES create(String prop, String obj) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var = new Variable(vars.getFresh());
        
        DRS drs = new DRS();
        List<Term> args = new ArrayList<>();
        args.add(var);
        args.add(new Constant(obj));
        drs.addStatement(new Proposition(prop,args));
        
        dudes.setMainVariable(var);
        dudes.setDRS(drs);
        
        return dudes;
    }
    
    public void setProperty(DUDES dudes,String uri) {
        
        // TODO
    }
    
    public void setObject(DUDES dudes,String uri) {
    
        // TODO
    }
    
}
