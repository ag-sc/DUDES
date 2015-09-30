package dudes;

import de.citec.sc.dudes.*;
import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Domain DUDES
        
        RDFDUDES someIndividual       = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        RDFDUDES someClass            = new RDFDUDES(RDFDUDES.Type.CLASS);
        RDFDUDES someProperty         = new RDFDUDES(RDFDUDES.Type.PROPERTY,"subj","dobj");
        // Alternative: 
        // RDFDUDES someProperty      = new RDFDUDES(RDFDUDES.Type.PROPERTY);
        // uses "subj" and "dobj" as default anchors (can be changed in RDFDUDES.java
        RDFDUDES someRestrictionClass = new RDFDUDES(RDFDUDES.Type.RESTRICTIONCLASS,"amod");
        // Alternative:
        // RDFDUDES someRestrictionClass = new RDFDUDES(RDFDUDES.Type.RESTRICTIONCLASS);
        // uses "amod" as default anchor (can be changed in RDFDUDES.java)
        
        System.out.println("someIndividual:       " + someIndividual);
        System.out.println("someClass:            " + someClass);
        System.out.println("someProperty:         " + someProperty);
        System.out.println("someRestrictionClass: " + someRestrictionClass);

        System.out.println("\nInstantiating:\n");
        
        someIndividual.instantiateIndividual("http://example.org/individual");
        someClass.instantiateClass("http://example.org/class");
        someProperty.instantiateProperty("http://example.org/property");
        someRestrictionClass.instantiateProperty("http://example.org/property");
        someRestrictionClass.instantiateIndividual("http://example.org/individual");
        
        System.out.println("Instantiating someIndividual:       " + someIndividual);
        System.out.println("Instantiating someClass:            " + someClass);
        System.out.println("Instantiating someRestrictionClass: " + someRestrictionClass);
        System.out.println("Instantiating someProperty:         " + someProperty);
        
        // Domain-independent DUDES
        
        ExpressionFactory expressions = new ExpressionFactory();

        RDFDUDES who   = expressions.what();
        RDFDUDES which = expressions.which("det");
        
        System.out.println("who:   " + who.toString());
        System.out.println("which: " + which.toString());
        
        System.out.println("\nGetting the types...\n");
        System.out.println(someIndividual.getType());
        System.out.println(someClass.getType());
        System.out.println(someProperty.getType());
        System.out.println(someRestrictionClass.getType());
        
        // Combining DUDES
        
        System.out.println("\nCombining...\n");
        
        RDFDUDES np = which.merge(someClass,"det");
        RDFDUDES vp = someProperty.merge(someIndividual,"dobj");
        RDFDUDES cl = vp.merge(np,"subj");
        
        System.out.println("NP (which + class):         " + np);
        System.out.println("VP (property + individual): " + vp);
        System.out.println("Cl (NP + VP):               " + cl);
        
        System.out.println("\nRDF(NP):\n"+np.convertToRDF().toString());
        System.out.println("\nRDF(VP):\n"+vp.convertToRDF().toString());
        System.out.println("\nRDF(Cl):\n"+cl.convertToRDF().toString());
        
        RDFDUDES question = someProperty.merge(someIndividual,"dobj").merge(which.merge(someClass,"det"),"subj");
        
        System.out.println("\nComplete question: " + question);
        
        System.out.println("\nSPARQL:\n" + question.convertToSPARQL().toString());
    }
    
}
