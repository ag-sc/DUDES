package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cunger
 */
public class ClassDUDES {
    
    VariableSupply vars; 
    
    String class_wildcard = "*c*";
    
    
    public ClassDUDES() {
        vars = new VariableSupply();
    }
    
      
    public DUDES create() {
        return create(class_wildcard);
    }
    
    public DUDES create(String uri) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var = new Variable(vars.getFresh());
        
        DRS drs = new DRS(0);
        List<Term> args = new ArrayList<>();
        args.add(var);
        args.add(new Constant(uri));
        drs.addStatement(new Proposition(new Constant("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),args));

        dudes.setMainDRS(0);
        dudes.setMainVariable(var);
        dudes.setDRS(drs);
        
        return dudes;
    }
    
    public DUDES instantiate(DUDES dudes, String uri) {
        
        DUDES newdudes = dudes.clone();
        newdudes.replace(class_wildcard,uri);
        return newdudes;
    }
}
