package de.citec.sc.dudes;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.syntax.Element;

/**
 *
 * @author cunger
 */
public class DUDES {

    Variable mainVariable;  
    int      mainDRS;
    
    Set<Term> projection;
    
    DRS drs;
    
    Set<Slot> slots;
    
    
    public DUDES() {

        mainVariable = null;
        mainDRS      = 0;
        projection   = new HashSet<>();
        
        drs   = new DRS(0);
        slots = new HashSet<>();
    }
    
    
    public Set<Slot> getSlots() {
        return slots;
    }
    
    public void setMainVariable(Variable var) {
        mainVariable = var;
    }
    
    public void setMainDRS(int i) {
        mainDRS = i;
    }
    
    public void addProjection(Term t) {
        projection.add(t);
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
        for (Term t : projection) {
            if (t.isVariable()) vars.add(((Variable) t).getInt());
        }
        for (Slot s : slots) {
             vars.add(s.getVariable().getInt());
        }
        vars.add(mainDRS);
        vars.addAll(drs.collectVariables());
        
        return vars;
    }
    
    
    // Refactoring
    
    public void rename(int i_old, int i_new) {
        
        if (mainVariable != null) {
            mainVariable.rename(i_old,i_new);
        }
        if (mainDRS == i_old) {
            mainDRS = i_new;
        }
        for (Term t : projection) {
             t.rename(i_old,i_new);
        }
        for (Slot s : slots) {
             s.replace(i_old,i_new);
        }
        drs.rename(i_old,i_new);
    }
    
    public void rename(String s_old, String s_new) {
        
        drs.rename(s_old,s_new);
    }

    public void replace(Term t_old, Term t_new) {
    
        if (t_old.isVariable() && t_new.isVariable()) {
            rename(((Variable) t_old).getInt(),((Variable) t_new).getInt());
        }
        else 
        if (!t_old.isVariable() && !t_new.isVariable()) {
            rename(((Constant) t_old).getValue(),((Constant) t_new).getValue());
        }
        else 
        if (!t_old.isVariable() && t_new.isVariable()) { 
        // TODO This is only to avoid that alreay replaced constants are again replaced by a variable.
        }
        else {
            boolean canBeReplaced = true;
            // t_old can only be replaced by t_new if it is not contained in any slot
            if (t_old.isVariable()) {
                for (Slot slot : slots) {
                if  (slot.getVariable().equals((Variable) t_old)) 
                     canBeReplaced = false;
                }
            } 
            if (canBeReplaced) {
                drs.replace(t_old,t_new);
                if (t_old.isVariable()) {
                // if t_old now doesn't occur anywhere in the main body
                if (!this.drs.collectVariables().contains(((Variable) t_old).getInt())) {
                    // but t_old is the mainVariable, then delete the main variable
                    if (mainVariable != null && mainVariable.equals((Variable) t_old))
                        mainVariable = null;  
                    // but t_old is a projection variable, then delete that as well 
                    if (projection.contains((Variable) t_old)) 
                        projection.remove((Variable) t_old);
                }}
            }
        }
    }
    
    
    // Combining DUDES
    
    public DUDES merge(DUDES other) {
        
        return this.merge(other,"");
    }
    
    public DUDES merge(DUDES other, String anchor) {
        
        if (this  == null) return other.clone();
        if (other == null) return this.clone();
        
        DUDES d1 = this.clone();
        DUDES d2 = other.clone();
        
        // avoid variable name clashes
        Set<Integer> allVariables = d1.collectVariables();
        allVariables.addAll(d2.collectVariables());
        VariableSupply vars = new VariableSupply();
        vars.reset(Collections.max(allVariables));
        for (int i : d2.collectVariables()) {
            d2.rename(i,vars.getFresh());
        }
        
        if (!d1.hasSlot(anchor) && !d2.hasSlot(anchor)) {
            
            return d1.union(d2,true);
        }
        else {

            if (d1.hasSlot(anchor)) return d1.applyTo(d2,anchor);
            if (d2.hasSlot(anchor)) return d2.applyTo(d1,anchor); 
            return null;
        }      
    }
    
    private DUDES applyTo(DUDES other, String anchor) {
                
        if (other.mainVariable != null) {
        for (Slot s : this.slots) {
             if (s.getAnchor().equals(anchor)) {
                 
                 this.slots.remove(s);
                 this.rename(s.getVariable().getInt(),other.mainVariable.getInt());
                 this.projection.addAll(other.projection);
                 this.drs.union(other.drs,s.label);
                 this.slots.addAll(other.slots);
                 return this;
             }
        }}
        
        return null;
    }
    
    private DUDES union(DUDES other,boolean unify) {
        
        if (unify) {
            other.rename(other.mainDRS,this.mainDRS);
            if (this.mainVariable != null && other.mainVariable != null) {
            other.rename(other.mainVariable.getInt(),this.mainVariable.getInt());
            }
        }
        
        this.projection.addAll(other.projection); 
        this.drs.union(other.drs,this.drs.label);
        for (Slot s : this.slots) {
            if (!other.slots.contains(s)) {
                 other.addSlot(s);
            }
        }
        
        return this;
    }
    
    // Postprocessing 
    
    public void postprocess() {
                
        drs.postprocess();
    }
    
    // Converting into RDF and SPARQL 
    
    public Query convertToSPARQL() {
        
        return convertToSPARQL(true);
    }
        
    public Query convertToSPARQL(boolean select) {
        
        Query query = QueryFactory.make();
        
        // projection variables
        for (Term t : projection) {
            query.addResultVar(t.convertToExpr(query));
        }
        
        // query body
        Element queryBody = drs.convertToRDF(query);
        query.setQueryPattern(queryBody);

        // query type
        if (select) {
            query.setQuerySelectType();
            if (query.getProjectVars().isEmpty()) {
                query.setQueryResultStar(true);
            } else {
                query.setDistinct(true);
            }
        }
        else {
            query.setQueryAskType();
        }
        
        return query;
    }
    
    // Printing and cloning
    
    @Override
    public String toString() {
        
        String dudes = "";
    
        dudes += "( "; 
        dudes += "return: ";
        for (Term t : projection) {
            dudes += t.toString() + " ";
        }
        if (projection.isEmpty()) {
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
        
        for (Term t : this.projection) {
             clone.addProjection(t.clone());
        }
        for (Slot s : this.slots) {
             clone.addSlot(s.clone());
        }
        
        clone.setDRS(this.drs.clone());
        
        return clone;
    }
    
}
