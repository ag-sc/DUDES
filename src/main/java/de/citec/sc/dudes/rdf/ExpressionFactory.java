package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cunger
 */
public class ExpressionFactory {
    
    VariableSupply vars = new VariableSupply();
    
    
    public ExpressionFactory() {
    }
    
    
    public RDFDUDES what() {
        
        return wh(null,null);
    }
    
    public RDFDUDES wh(String p, String c) {
        
        vars.reset();

        Variable var = new Variable(vars.getFresh());

        DUDES wh = new DUDES();
        wh.addProjection(var);
        wh.setMainVariable(var);
        wh.setMainDRS(0);      
        
        DRS wh_drs = new DRS(0);
        wh_drs.addVariable(var);
        
        if (p != null && c != null) {
            List<Term> args = new ArrayList<>();
            args.add(var);
            args.add(new Constant(c));
            wh_drs.addStatement(new Proposition(new Constant(p),args));
        }
        
        wh.setDRS(wh_drs);
    
        return new RDFDUDES(wh,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES which(String anchor) {
        
        vars.reset();

        Variable var = new Variable(vars.getFresh()); // noun

        DUDES which = new DUDES();
        which.addProjection(var);
        which.setMainVariable(var);
        which.setMainDRS(0);
        
        DRS which_drs = new DRS(0);
        which_drs.addVariable(var);
        which.setDRS(which_drs);
        which.addSlot(new Slot(var,anchor));
    
        return new RDFDUDES(which,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES how() {
        
        vars.reset();

        Variable var = new Variable(vars.getFresh()); // noun

        DUDES how = new DUDES();
        how.addProjection(var);
        how.setMainVariable(var);
        how.setMainDRS(0);
        how.setDRS(new DRS(0));
    
        return new RDFDUDES(how,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES did() {
        
        vars.reset();
        
        DUDES did = new DUDES();
        did.setMainDRS(0);
        
        DRS did_drs = new DRS(0);
    
        return new RDFDUDES(did,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES copula(String anchor1, String anchor2) {
        
        vars.reset();
        
        Variable var1 = new Variable(vars.getFresh());
        Variable var2 = new Variable(vars.getFresh());
        
        DUDES cop = new DUDES();
        cop.setMainDRS(0);
        
        DRS cop_drs = new DRS(0);
        cop_drs.addStatement(new Replace(var2,var1));
        cop.setDRS(cop_drs);
        cop.addSlot(new Slot(var1,anchor1));
        cop.addSlot(new Slot(var2,anchor2));
    
        return new RDFDUDES(cop,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES num(int n) {
        
        vars.reset();
        
        Variable var = new Variable(vars.getFresh()); 
        
        DUDES num = new DUDES();
        
        num.setMainDRS(0);
        num.setMainVariable(var);
        
        DRS num_drs = new DRS(0);
        num_drs.addVariable(var);
        num_drs.addStatement(new Replace(var,new Constant(""+n,Constant.Datatype.INT)));
        num.setDRS(num_drs);
        
        return new RDFDUDES(num,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES howmany(String anchor) {
        
        vars.reset();

        Variable var = new Variable(vars.getFresh()); // noun

        DUDES howmany = new DUDES();
        howmany.addProjection(new Function(Function.Func.COUNT,var));
        howmany.setMainVariable(var);
        howmany.setMainDRS(0);
        
        DRS howmany_drs = new DRS(0);
        howmany_drs.addVariable(var);
        howmany.setDRS(howmany_drs);
        howmany.addSlot(new Slot(var,anchor));
    
        return new RDFDUDES(howmany,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES morethan(String anchor1, String anchor2) {
        
        vars.reset();
        
        Variable var1 = new Variable(vars.getFresh()); // num
        Variable var2 = new Variable(vars.getFresh()); // noun
        
        DUDES morethan = new DUDES();
        
        morethan.setMainDRS(0);
        morethan.setMainVariable(var2);
        
        DRS morethan_drs = new DRS(0);
        morethan_drs.addStatement(new OperatorStatement(new Function(Function.Func.COUNT,var2),OperatorStatement.Operator.GREATER,var1));
        morethan.setDRS(morethan_drs);
        morethan.addSlot(new Slot(var1,anchor1));
        morethan.addSlot(new Slot(var2,anchor2));
        
        return new RDFDUDES(morethan,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES superlativeMax(String anchor) {
        return superlative(anchor,OperatorStatement.Operator.MAX,true);
    }
    public RDFDUDES superlativeMin(String anchor) {
        return superlative(anchor,OperatorStatement.Operator.MIN,true);
    }
    
    public RDFDUDES highest(String anchor) {
        return superlative(anchor,OperatorStatement.Operator.MAX,false);
    }
    public RDFDUDES lowest(String anchor) {
        return superlative(anchor,OperatorStatement.Operator.MIN,false);
    }
    
    public RDFDUDES superlative(String anchor,OperatorStatement.Operator op,boolean withProperty) {
        
        vars.reset();

        Variable var1 = new Variable(vars.getFresh()); 
        Variable var2 = new Variable(vars.getFresh()); 

        DUDES superlative = new DUDES();
        if (withProperty) {
            superlative.setMainVariable(var1);
        } else {
            superlative.setMainVariable(var2);
        }
        superlative.setMainDRS(0);
        
        DRS superlative_drs = new DRS(0);
        
        if (withProperty) {
            List<Term> args = new ArrayList<>();
            args.add(var1);
            args.add(var2);
            superlative_drs.addStatement(new Proposition(new Variable(vars.getFresh()),args));
            superlative.addSlot(new Slot(var1,anchor));
        }
        superlative_drs.addStatement(new OperatorStatement(var1,op,var2));
        superlative.setDRS(superlative_drs);
    
        return new RDFDUDES(superlative,RDFDUDES.Type.OTHER);
    }

}
