package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cunger
 */
public class RestrictionClassDUDES {
    
    String amod = "amod";
    
    VariableSupply vars; 
    
    String property_wildcard = "*p*";
    String object_wildcard   = "*o*";
    
    
    public RestrictionClassDUDES() {
        vars = new VariableSupply();
    }
    
    
    public DUDES create() {
        return create(property_wildcard,object_wildcard);
    }
    
    public DUDES create(String prop, String obj) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var = new Variable(vars.getFresh());
        
        DRS drs = new DRS(0);
        List<Term> args = new ArrayList<>();
        args.add(var);
        args.add(new Constant(obj));
        drs.addStatement(new Proposition(new Constant(prop),args));
        
        dudes.setMainDRS(0);
        dudes.setMainVariable(var);
        dudes.setDRS(drs);
        
        dudes.addSlot(new Slot(var,amod));
        
        return dudes;
    }
    
    public DUDES instantiate(DUDES dudes, String uri_p, String uri_o) {
        
        DUDES newdudes = dudes.clone();
        newdudes.replace(property_wildcard,uri_p);
        newdudes.replace(object_wildcard,uri_o);
        return newdudes;
    }
    
}