package dudes;

import de.citec.sc.dudes.*;
import de.citec.sc.dudes.rdf.DUDESFactory;

/**
 *
 * @author cunger
 */
public class ExampleDUDES {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DUDESFactory dudesFactory = new DUDESFactory();
              
        System.out.println("--------- Instantiated DUDES -------- \n");
        
        DUDES ashgabat = dudesFactory.create(DUDESFactory.Type.INDIVIDUAL,"<http://dbpedia.org/resource/Ashgabat>");
        DUDES country  = dudesFactory.create(DUDESFactory.Type.CLASS,"<http://dbpedia.org/resource/Country>");
        DUDES capital  = dudesFactory.create(DUDESFactory.Type.PROPERTY,"<http://dbpedia.org/resource/capital>");
        DUDES capital_linear  = dudesFactory.create(DUDESFactory.Type.PROPERTY,"<http://dbpedia.org/resource/capital>","subj","dobj");
        DUDES capital_reverse = dudesFactory.create(DUDESFactory.Type.PROPERTY,"<http://dbpedia.org/resource/capital>","dobj","subj");
        DUDES turkmen  = dudesFactory.create(DUDESFactory.Type.RESTRICTIONCLASS,"<http://dbpedia.org/resource/country>","<http://dbpedia.org/resource/Turkmenistan>");
        
        System.out.println("ashgabat:             " + ashgabat.toString());
        System.out.println("country:              " + country.toString());
        System.out.println("capital:              " + capital.toString());
        System.out.println("capital_linear:       " + capital_linear.toString());
        System.out.println("capital_reverse:      " + capital_reverse.toString());
        System.out.println("turkmen:              " + turkmen.toString());
        
        System.out.println("\n--------- Uninstantiated DUDES ------ \n");

        DUDES someIndividual       = dudesFactory.create(DUDESFactory.Type.INDIVIDUAL);
        DUDES someClass            = dudesFactory.create(DUDESFactory.Type.CLASS);
        DUDES someProperty         = dudesFactory.create(DUDESFactory.Type.PROPERTY);
        DUDES someRestrictionClass = dudesFactory.create(DUDESFactory.Type.RESTRICTIONCLASS);
        
        System.out.println("someIndividual:       " + someIndividual.toString());
        System.out.println("someClass:            " + someClass.toString());
        System.out.println("someProperty:         " + someProperty.toString());
        System.out.println("someRestrictionClass: " + someRestrictionClass.toString());
    
        System.out.println("\n--------- Domain-independent DUDES ------ \n");

        Variable v1 = new Variable(1);

        DUDES who = new DUDES();
        who.addReturnVariable(v1);
        who.setMainVariable(v1);
        
        DRS who_drs = new DRS();
        who_drs.addVariable(v1);
        who.setDRS(who_drs);
        
        System.out.println("who:   " + who.toString());
        
        DUDES which = new DUDES();
        which.addReturnVariable(v1);
        which.setMainVariable(v1);
        
        DRS which_drs = new DRS();
        which_drs.addVariable(v1);
        which.setDRS(which_drs);
        which.addSlot(new Slot(v1,"det"));

        System.out.println("which: " + which.toString());

    }
    
}
