package de.citec.sc.dudes;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import java.util.Collections;
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
        
        drs   = new DRS(0);
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
    
    private boolean hasSlot(String anchor) {
        for (Slot s : slots) {
             if (s.getAnchor().equals(anchor)) {
                 return true;
             }
        }
        return false;
    }
    
    private Set<Integer> collectVariables() {
        
        HashSet<Integer> vars = new HashSet<>();
        
        if  (mainVariable != null) {
             vars.add(mainVariable.getInt());
        }
        for (Variable v : returnVariables) {
             vars.add(v.getInt());
        }
        for (Slot s : slots) {
             vars.add(s.getVariable().getInt());
        }
        vars.add(mainDRS);
        vars.addAll(drs.collectVariables());
        
        return vars;
    }
    
    public void replace(int i_old, int i_new) {
        
        if (mainVariable != null) {
            mainVariable.replace(i_old,i_new);
        }
        if (mainDRS == i_old) {
            mainDRS = i_new;
        }
        for (Variable v : returnVariables) {
             v.replace(i_old,i_new);
        }
        for (Slot s : slots) {
             s.replace(i_old,i_new);
        }
        drs.replace(i_old,i_new);
    }

    public void replace(String s_old, String s_new) {
        
        drs.replace(s_old,s_new);
    }
        
    
    // Combining DUDES
    
    public DUDES merge(DUDES other, String anchor) {
        
        DUDES d1 = this.clone();
        DUDES d2 = other.clone();

        VariableSupply vars = new VariableSupply();
        vars.reset(Collections.max(d1.collectVariables()));
                
        for (int i : d2.collectVariables()) {
             d2.replace(i,vars.getFresh());
        }
                        
        if (d1.hasSlot(anchor)) return d1.applyTo(d2,anchor);
        if (d2.hasSlot(anchor)) return d2.applyTo(d1,anchor); 
        
        if (d1.mainVariable == null) return d2.union(d1); 
        if (d2.mainVariable == null) return d1.union(d2);
        
        return null;
    }
    
    private DUDES applyTo(DUDES other, String anchor) {
                
        if (other.mainVariable != null) {
        for (Slot s : this.slots) {
             if (s.getAnchor().equals(anchor)) {
                 
                 this.slots.remove(s);
                 this.replace(s.getVariable().getInt(),other.mainVariable.getInt());
                 this.returnVariables.addAll(other.returnVariables);
                 this.drs.union(other.drs,s.label);
                 this.slots.addAll(other.slots);
                 return this;
             }
        }}
        
        return null;
    }
    
    private DUDES union(DUDES other) {
        
        if (other.mainVariable == null) {
            this.returnVariables.addAll(other.returnVariables); 
            this.drs.union(other.drs,this.drs.label);
            this.slots.addAll(other.slots);
            return this;
        }
        
        return null;
    }
    
    // Converting into RDF and SPARQL 
    
    public Set<Triple> convertToRDF() {
        
        Set<Triple> triples = new HashSet<>();
        
        for (Statement s : drs.statements) {
            triples.addAll(s.convertToRDF());
        }
        
        return triples;
    }
    
    public Query convertToSPARQL() {
        
        Query query = new Query();
        
        return query;
    }
    
    // Printing and cloning
    
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
    
    @Override
    public DUDES clone() {
        
        DUDES clone = new DUDES();

        clone.setMainDRS(this.mainDRS);

        if (this.mainVariable != null) {
            clone.setMainVariable(this.mainVariable.clone());
        }
        
        for (Variable v : this.returnVariables) {
             clone.addReturnVariable(v.clone());
        }
        for (Slot s : this.slots) {
             clone.addSlot(s.clone());
        }
        
        clone.setDRS(this.drs.clone());
        
        return clone;
    }
    
}
