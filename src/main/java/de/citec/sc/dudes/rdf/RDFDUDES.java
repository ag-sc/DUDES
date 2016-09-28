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
    
    public enum Type { INDIVIDUAL, CLASS, PROPERTY, OTHER };
    
    DUDES dudes;
    Type  type;

    boolean i_instantiated;
    boolean s_instantiated;
    boolean p_instantiated;
    boolean o_instantiated;
    
    VariableSupply vars = new VariableSupply();
    
    Variable placeholder_i  = new Variable(vars.getFresh());
    Variable placeholder_s  = new Variable(vars.getFresh());
    Variable placeholder_p  = new Variable(vars.getFresh());
    Variable placeholder_o  = new Variable(vars.getFresh());
    
    
    public RDFDUDES(DUDES dudes, Type type) {
        
        this.dudes = dudes;
        this.type  = type;
        
        this.i_instantiated = false;
        this.s_instantiated = false;
        this.p_instantiated = false;
        this.o_instantiated = false;
    }
    
    // Constructor with default anchor   
    
    public RDFDUDES(Type type) {
        
        this.type = type;
        
        switch (type) {
            case INDIVIDUAL: createIndividualDUDES(); break;
            case CLASS:      createTripleDUDES(); break;
            case PROPERTY:   createTripleDUDES("1","2"); break;
            default: ; 
        }
    }
    
    // Constructors with custom anchors 
    
    public RDFDUDES(Type type, String anchor) {
        
        this.type = type;
        
        switch (type) {
            case CLASS: createTripleDUDES(anchor); break;   
            default: ; 
        }
    } 
    
    public RDFDUDES(Type type, String subj_anchor, String obj_anchor) {
        
        this.type = type;
        
        switch (type) {
            case PROPERTY: createTripleDUDES(subj_anchor,obj_anchor); break;   
            default: ; 
        }
    } 
    
    
    // Creating DUDES
 
    // Individuals 
    
    public void createIndividualDUDES() {
        createIndividualDUDES(placeholder_i);
    }
    public void createIndividualDUDES(String uri) {
        createIndividualDUDES(new Constant(uri));
    }
    
    private void createIndividualDUDES(Term term) {
                
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
    
    // Simple DUDES with one (uninstantiated) triple

    private void createTripleDUDES() {
                
        DUDES dudes = new DUDES();
                        
        DRS drs = new DRS(0);
        
        List<Term> args = new ArrayList<>();
        args.add(placeholder_s);
        args.add(placeholder_o);

        drs.addStatement(new Proposition(placeholder_p,args));

        dudes.setMainDRS(0);
        dudes.setMainVariable(placeholder_s);
        dudes.setDRS(drs);
        
        this.dudes = dudes;
    }    
    
    private void createTripleDUDES(String anchor) {
        
        createTripleDUDES();
        
        dudes.addSlot(new Slot(placeholder_s,anchor));
    }
    
    private void createTripleDUDES(String subj_anchor, String obj_anchor) {
        
        createTripleDUDES();
        
        dudes.addSlot(new Slot(placeholder_s,subj_anchor));
        dudes.addSlot(new Slot(placeholder_o,obj_anchor));
    }
    

    // Getter 
    
    public Type getType() {
        
        return type;
    }
    
    public int getSlotSize(){
    
        if (dudes == null) return 0;
        return dudes.getSlots().size();
    }
    
    public boolean isIndividualInstantiated() {
        return i_instantiated;
    }
    public boolean isSubjectInstantiated() {
        return s_instantiated;
    }
    public boolean isPropertyInstantiated() {
        return p_instantiated;
    }
    public boolean isObjectInstantiated() {
        return o_instantiated;
    }
    
   
    // Instantiating DUDES
    
    public void instantiateIndividual(String uri) {
        dudes.replace(placeholder_i,new Constant(uri));
        i_instantiated = true;
    }
    public void instantiateIndividual(int v) {
        dudes.replace(placeholder_i,new Variable(v));
    }
    
    public void instantiateSubject(String uri) {
        dudes.replace(placeholder_s,new Constant(uri));
        s_instantiated = true;
    }
    public void instantiateSubject(int v) {
        dudes.replace(placeholder_s,new Variable(v));
    }
    
    public void instantiateProperty(String uri) {
        dudes.replace(placeholder_p,new Constant(uri));
        p_instantiated = true;
    }
    public void instantiateProperty(int v) {
        dudes.replace(placeholder_p,new Variable(v));
    }

    public void instantiateObject(String uri) {
        dudes.replace(placeholder_o,new Constant(uri));
        o_instantiated = true;
    }
    public void instantiateObject(int v) {
        dudes.replace(placeholder_o,new Variable(v));
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
    
    public Query convertToSPARQL() {
        
        return convertToSPARQL(true);
    }
    
    public Query convertToSPARQL(boolean select) {
        
        return this.dudes.convertToSPARQL(select);
    }
    
    
    // Printing
    
    @Override
    public String toString() {
        return this.dudes.toString();
    }

}
