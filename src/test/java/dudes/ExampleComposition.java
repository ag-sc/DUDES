package dudes;

import de.citec.sc.dudes.*;
import de.citec.sc.dudes.rdf.DUDESFactory;

/**
 *
 * @author cunger
 */
public class ExampleComposition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        DUDESFactory dudesFactory = new DUDESFactory();
              
        System.out.println("--------- Word meanings -------- \n");
        
        Variable v1 = new Variable(1);
        DUDES which = new DUDES();
        which.addReturnVariable(v1);
        which.setMainVariable(v1);
        which.setMainDRS(0);
        DRS which_drs = new DRS(0);
        which_drs.addVariable(v1);
        which.setDRS(which_drs);
        which.addSlot(new Slot(v1,"det"));

        System.out.println("which:       " + which.toString());
        
        DUDES etrek       = dudesFactory.create(DUDESFactory.Type.INDIVIDUAL,"Etrek");
        DUDES river       = dudesFactory.create(DUDESFactory.Type.CLASS,"River");
        DUDES city        = dudesFactory.create(DUDESFactory.Type.CLASS,"City");
        DUDES flowThrough = dudesFactory.create(DUDESFactory.Type.PROPERTY,"flowThrough","subj","dobj");
        DUDES turkmen     = dudesFactory.create(DUDESFactory.Type.RESTRICTIONCLASS,"country","Turkmenistan");
        
        System.out.println("Etrek:       " + etrek.toString());
        System.out.println("river:       " + river.toString());
        System.out.println("city:        " + city.toString());
        System.out.println("flowThrough: " + flowThrough.toString());
        System.out.println("Turkmen:     " + turkmen.toString());
        
        System.out.println("\n-------- Composition ----------");
        
        System.out.println("\n---- Which rivers flow through Turkmen cities?");
        System.out.println("     (flowThrough + (which + river)) + (Turkmen + city)\n");
               
        System.out.println(flowThrough.merge(which.merge(river,"det"),"subj").merge(turkmen.merge(city,"amod"),"dobj"));
        
        System.out.println("\n---- Through which Turkmen city does Etrek flow?");
        System.out.println("     (flowThrough + etrek) + (city + which)\n");
        
        System.out.println(flowThrough.merge(etrek,"subj").merge(city.merge(turkmen,"amod").merge(which,"det"),"dobj"));
        }
    
}
