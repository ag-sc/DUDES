package dudes;

import de.citec.sc.dudes.*;
import de.citec.sc.dudes.rdf.DUDESFactory;

/**
 *
 * @author cunger
 */
public class Example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DUDESFactory dudesFactory = new DUDESFactory();

        DUDES someIndividual       = dudesFactory.create(DUDESFactory.Type.INDIVIDUAL);
        DUDES someClass            = dudesFactory.create(DUDESFactory.Type.CLASS);
        DUDES someProperty         = dudesFactory.create(DUDESFactory.Type.PROPERTY);
        DUDES someRestrictionClass = dudesFactory.create(DUDESFactory.Type.RESTRICTIONCLASS);
        
        System.out.println("someIndividual:       " + someIndividual.toString());
        System.out.println("someClass:            " + someClass.toString());
        System.out.println("someProperty:         " + someProperty.toString());
        System.out.println("someRestrictionClass: " + someRestrictionClass.toString());
        
        Variable v1 = new Variable(1);

        DUDES who = new DUDES();
        who.addReturnVariable(v1);
        who.setMainVariable(v1);
        who.setMainDRS(0);      
        DRS who_drs = new DRS(0);
        who_drs.addVariable(v1);
        who.setDRS(who_drs);
        
        System.out.println("who:   " + who.toString());
        
        DUDES which = new DUDES();
        which.addReturnVariable(v1);
        which.setMainVariable(v1);
        which.setMainDRS(0);
        DRS which_drs = new DRS(0);
        which_drs.addVariable(v1);
        which.setDRS(which_drs);
        which.addSlot(new Slot(v1,"det"));

        System.out.println("which: " + which.toString());
        
        System.out.println("\nCombining...\n");
        
        DUDES np = which.merge(someClass,"det");
        DUDES vp = someProperty.merge(someIndividual,"dobj");
        DUDES cl = vp.merge(np,"subj");
        
        System.out.println("NP (which + class):         " + np);
        System.out.println("VP (property + individual): " + vp);
        System.out.println("Cl (NP + VP):               " + cl);
        
        System.out.println("\nRDF(NP):\n"+np.convertToRDF().toString());
        System.out.println("\nRDF(VP):\n"+vp.convertToRDF().toString());
        System.out.println("\nRDF(Cl):\n"+cl.convertToRDF().toString());
        
        System.out.println("\nInstantiating:\n");
        
        DUDES theIndividual = dudesFactory.instantiateIndividual(someIndividual,"http://example.org/individual");
        DUDES theClass      = dudesFactory.instantiateClass(someClass,"http://example.org/class");
        DUDES theRestrClass = dudesFactory.instantiateRestrictionClass(someRestrictionClass,"http://example.org/property","http://example.org/individual");
        DUDES theProperty   = dudesFactory.instantiateProperty(someProperty,"http://example.org/property");
        
        System.out.println("Instantiating someIndividual:       " + theIndividual);
        System.out.println("Instantiating someClass:            " + theClass);
        System.out.println("Instantiating someRestrictionClass: " + theRestrClass);
        System.out.println("Instantiating someProperty:         " + theProperty);
        
        DUDES question = theProperty.merge(theIndividual,"dobj").merge(which.merge(theClass,"det"),"subj");
        
        System.out.println("\nComplete question: " + question);
        
        System.out.println("\nSPARQL:\n" + question.convertToSPARQL().toString());
    }
    
}
