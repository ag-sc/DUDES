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
    
    public enum Type { INDIVIDUAL, CLASS, REVERSECLASS, PROPERTY, OTHER };
    
    DUDES dudes;
    Type  type;
    
    VariableSupply vars = new VariableSupply();
    
    String placeholder_i  = "i";
    String placeholder_c  = "c";
    String placeholder_p  = "p";
    
    
    public RDFDUDES(DUDES dudes, Type type) {
        
        this.dudes = dudes;
        this.type  = type;
    }
    
    // Constructor with default anchor   
    
    public RDFDUDES(Type type) {
        
        this.type = type;
        
        switch (type) {
            case INDIVIDUAL:   createIndividualDUDES(); break;
            case CLASS:        createClassDUDES(); break;
            case REVERSECLASS: createReverseClassDUDES(); break;
            default: ; 
        }
    }
    
    // Constructors with custom anchors 
    
    public RDFDUDES(Type type, String subj_anchor, String obj_anchor) {
        
        this.type = type;
        
        switch (type) {
            case PROPERTY: createPropertyDUDES(subj_anchor,obj_anchor); break;   
            default: ; 
        }
    } 

    
    // Getter 
    
    public Type getType() {
        
        return type;
    }
    
    public int getSlotSize(){
    
        if (dudes == null) return 0;
        return dudes.getSlots().size();
    }
    
    
    // Creating DUDES
 
    // Individuals 
    
    public void createIndividualDUDES() {
        createIndividualDUDES(new Placeholder(placeholder_i));
    }
    public void createIndividualDUDES(String uri) {
        createIndividualDUDES(new Constant(uri));
    }
    
    private void createIndividualDUDES(Term term) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var = new Variable(vars.getFresh());
        
        DRS drs = new DRS(0);
        drs.addVariable(var);
        drs.addStatement(new Action(var,Action.Operation.REPLACE,term));

        dudes.setMainDRS(0);
        dudes.setMainVariable(var);
        dudes.setDRS(drs);
        
        this.dudes = dudes;
    }
    
    // Classes
    
    public void createClassDUDES() {
        createClassDUDES(new Placeholder(placeholder_p),new Placeholder(placeholder_c),false);
    }
    public void createClassDUDES(String uri) {
        createClassDUDES(new Placeholder(placeholder_p),new Constant(uri),false);
    }
    
    public void createReverseClassDUDES() {
        createClassDUDES(new Placeholder(placeholder_p),new Placeholder(placeholder_c),true);
    }
    public void createReverseClassDUDES(String uri) {
        createClassDUDES(new Placeholder(placeholder_p),new Constant(uri),true);
    }
    
    private void createClassDUDES(Term prop, Term ent, boolean reverse) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
        
        Variable var = new Variable(vars.getFresh());
                
        DRS drs = new DRS(0);
        List<Term> args = new ArrayList<>();
        if (reverse) {
            args.add(ent);  
            args.add(var);
        }
        else {
            args.add(var);
            args.add(ent);
        }
        drs.addStatement(new Proposition(prop,args));

        dudes.setMainDRS(0);
        dudes.setMainVariable(var);
        dudes.setDRS(drs);
        
        this.dudes = dudes;
    }    
    
    // Properties
    
    public void createPropertyDUDES(String subj_anchor, String obj_anchor) {
        createPropertyDUDES(new Placeholder(placeholder_p),subj_anchor,obj_anchor);
    }
    public void createPropertyDUDES(String uri, String subj_anchor, String obj_anchor) {
        createPropertyDUDES(new Constant(uri),subj_anchor,obj_anchor);
    }
    
    private void createPropertyDUDES(Term prop, String subj_anchor, String obj_anchor) {
        
        vars.reset();
        
        DUDES dudes = new DUDES();
               
        Variable var1 = new Variable(vars.getFresh());
        Variable var2 = new Variable(vars.getFresh());
        
        DRS drs = new DRS(0);
        List<Term> args = new ArrayList<>();
        args.add(var1);
        args.add(var2);
        drs.addStatement(new Proposition(prop,args));

        dudes.setMainVariable(var1);
        dudes.setMainDRS(0);
        dudes.setDRS(drs);
        
        dudes.addSlot(new Slot(var1,subj_anchor));
        dudes.addSlot(new Slot(var2,obj_anchor));
        
        this.dudes = dudes;
    } 

    
    // Instantiating DUDES
    
    public void instantiateIndividual(String uri) {
        dudes.rename(placeholder_i,uri);
    }
    
    public void instantiateClass(String uri) {
        dudes.rename(placeholder_c,uri);
    }
    
    public void instantiateProperty(String uri) {
        dudes.rename(placeholder_p,uri);
    }
    
    // Wrappers for DUDES functionality

    public RDFDUDES merge(RDFDUDES other) {
        
        return new RDFDUDES(this.dudes.merge(other.dudes),Type.OTHER);
    }
    
    public RDFDUDES merge(RDFDUDES other, String anchor) {
        
        return new RDFDUDES(this.dudes.merge(other.dudes,anchor),Type.OTHER);
    }
    
    public void postprocess() {
        
        dudes = dudes.postprocess();
    }

    public Set<Triple> convertToRDF(Query top) {
        
        return this.dudes.convertToRDF(top);
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
