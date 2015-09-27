package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cunger
 */
public class PropertyDUDES {
    
    VariableSupply vars; 
    
    
    public PropertyDUDES() {
        vars = new VariableSupply();
    }
    
    
    public DUDES create() {
        return create("*","*","*");
    }
    
    public DUDES create(String uri) {
        return create(uri,"*","*");
    }
    
    public DUDES create(String uri, String subj_anchor, String obj_anchor) {
               
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var1 = new Variable(vars.getFresh());
        Variable var2 = new Variable(vars.getFresh());
        
        DRS drs = new DRS(0);
        List<Term> args = new ArrayList<>();
        args.add(var1);
        args.add(var2);
        drs.addStatement(new Proposition(new Constant(uri),args));

        dudes.setMainDRS(0);
        dudes.setDRS(drs);
        
        dudes.addSlot(new Slot(var1,subj_anchor));
        dudes.addSlot(new Slot(var2,obj_anchor));
        
        return dudes;
    }
}
