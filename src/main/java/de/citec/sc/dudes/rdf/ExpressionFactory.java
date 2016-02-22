package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.*;

/**
 *
 * @author cunger
 */
public class ExpressionFactory {
    
    VariableSupply vars = new VariableSupply();
    
    
    public ExpressionFactory() {
    }
    
    
    public RDFDUDES what() {
        
        vars.reset();

        Variable var = new Variable(vars.getFresh());

        DUDES what = new DUDES();
        what.addProjection(var);
        what.setMainVariable(var);
        what.setMainDRS(0);      
        
        DRS what_drs = new DRS(0);
        what_drs.addVariable(var);
        what.setDRS(what_drs);
    
        return new RDFDUDES(what,RDFDUDES.Type.OTHER);
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
    
    public RDFDUDES num(int n) {
        
        vars.reset();
        
        Variable var = new Variable(vars.getFresh()); 
        
        DUDES num = new DUDES();
        
        num.setMainDRS(0);
        num.setMainVariable(var);
        
        DRS num_drs = new DRS(0);
        num_drs.addVariable(var);
        num_drs.addStatement(new Action(var,Action.Operation.REPLACE,new Constant(""+n,Constant.Datatype.INT)));
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
        morethan_drs.addStatement(new Comparison(new Function(Function.Func.COUNT,var2),Comparison.Operator.GREATER,var1));
        morethan.setDRS(morethan_drs);
        morethan.addSlot(new Slot(var1,anchor1));
        morethan.addSlot(new Slot(var2,anchor2));
        
        return new RDFDUDES(morethan,RDFDUDES.Type.OTHER);
    }
    
    public RDFDUDES and(String anchor1,String anchor2) {
        
        vars.reset();
        
        Variable var1 = new Variable(vars.getFresh()); // conjunct 1
        Variable var2 = new Variable(vars.getFresh()); // conjunct 2
        
        DUDES and = new DUDES();
        
        and.setMainDRS(0);
        
        DRS and_drs = new DRS(0);
        and_drs.addStatement(null);
        
        and.addSlot(new Slot(var1,anchor1));
        and.addSlot(new Slot(var1,anchor1));

        
        return new RDFDUDES(and,RDFDUDES.Type.OTHER);
    }
    
}
