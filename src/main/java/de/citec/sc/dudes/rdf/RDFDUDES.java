package de.citec.sc.dudes.rdf;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import de.citec.sc.dudes.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class RDFDUDES {
    
    public enum Type { INDIVIDUAL, CLASS, RESTRICTIONCLASS, PROPERTY, OTHER };
    
    DUDES dudes;
    Type  type;
    
    VariableSupply vars = new VariableSupply();
    
    String wildcard_individual = "*i*";
    String wildcard_class      = "*c*";
    String wildcard_property   = "*p*";
    
    String subj = "subj";
    String dobj = "dobj";
    String amod = "amod";
    
    
    public RDFDUDES(DUDES dudes, Type type) {
        
        this.dudes = dudes;
        this.type  = type;
    }
    
    // Constructor with default anchor   
    
    public RDFDUDES(Type type) {
        
        this.type = type;
        
        switch (type) {
            case INDIVIDUAL:       createIndividualDUDES(); break;
            case CLASS:            createClassDUDES(); break;
            case PROPERTY:         createPropertyDUDES(); break;   
            case RESTRICTIONCLASS: createRestrictionClassDUDES(); break;
            default:               ; 
        }
    }
    
    // Constructors with custom anchors 
    
    public RDFDUDES(Type type, String subj_anchor, String obj_anchor) {
        
        this.type = type;
        
        switch (type) {
            case PROPERTY: createPropertyDUDES(subj_anchor,obj_anchor); break;   
            default:       ; 
        }
    } 
    
    public RDFDUDES(Type type, String anchor) {
        
        this.type = type;
        
        switch (type) {
            case RESTRICTIONCLASS: createRestrictionClassDUDES(anchor); break;   
            default:               ; 
        }
    }    

    
    // Getter 
    
    public Type getType() {
        return type;
    }
    
    
    // Creating DUDES
 
    public void createIndividualDUDES() {
        createIndividualDUDES(wildcard_individual);
    }
    public void createIndividualDUDES(String uri) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var = new Variable(vars.getFresh());
        
        DRS drs = new DRS(0);
        drs.addVariable(var);
        drs.addStatement(new Equals(var,new Constant(uri)));

        dudes.setMainDRS(0);
        dudes.setMainVariable(var);
        dudes.setDRS(drs);
        
        this.dudes = dudes;
    }
    
    public void createClassDUDES() {
        createClassDUDES(wildcard_class);
    }
    public void createClassDUDES(String uri) {
        
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
        
        this.dudes = dudes;
    }    
    
    public void createPropertyDUDES() {
        createPropertyDUDES(wildcard_property,subj,dobj);
    }
    public void createPropertyDUDES(String subj_anchor, String obj_anchor) {
        createPropertyDUDES(wildcard_property,subj_anchor,obj_anchor);
    }
    public void createPropertyDUDES(String uri, String subj_anchor, String obj_anchor) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var = new Variable(vars.getFresh());
        
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
        
        this.dudes = dudes;
    } 
    
    public void createRestrictionClassDUDES() {
        createRestrictionClassDUDES(wildcard_property,wildcard_individual,amod);
    }
    public void createRestrictionClassDUDES(String anchor) {
        createRestrictionClassDUDES(wildcard_property,wildcard_individual,anchor);
    }
    public void createRestrictionClassDUDES(String uri_p, String uri_o, String anchor) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
                
        Variable var = new Variable(vars.getFresh());
        
        DRS drs = new DRS(0);
        List<Term> args = new ArrayList<>();
        args.add(var);
        args.add(new Constant(uri_o));
        drs.addStatement(new Proposition(new Constant(uri_p),args));
        
        dudes.setMainDRS(0);
        dudes.setMainVariable(var);
        dudes.setDRS(drs);
        
        dudes.addSlot(new Slot(var,anchor));
        
        this.dudes = dudes;
    }   
    
    // Instantiating DUDES
    
    public void instantiateIndividual(String uri) {
        dudes.replace(wildcard_individual,uri);
    }
    
    public void instantiateClass(String uri) {
        dudes.replace(wildcard_class,uri);
    }
    
    public void instantiateProperty(String uri) {
        dudes.replace(wildcard_property,uri);
    }
    
    // Wrappers for merging and conversions 
    
    public RDFDUDES merge(RDFDUDES other, String anchor) {
        
        return new RDFDUDES(this.dudes.merge(other.dudes,anchor),Type.OTHER);
    }

    public Set<Triple> convertToRDF() {
        
        return this.dudes.convertToRDF();
    }
    
    public Query convertToSPARQL() {
        
        return this.dudes.convertToSPARQL();
    }
    
    
    // Printing
    
    @Override
    public String toString() {
        return this.dudes.toString();
    }
}
