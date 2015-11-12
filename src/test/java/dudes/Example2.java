package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author sherzod
 */
public class Example2 {
    public static void main(String[] args){     
        
        ExpressionFactory expressions = new ExpressionFactory();

        RDFDUDES someIndividual = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        RDFDUDES someClass = new RDFDUDES(RDFDUDES.Type.CLASS);
        RDFDUDES someProperty = new RDFDUDES(RDFDUDES.Type.PROPERTY, "1", "2");
        
        RDFDUDES which = expressions.which("1");
        
        
        someProperty.instantiateProperty("cross");
        someIndividual.instantiateIndividual("Brooklyn_Bridge");
        someClass.instantiateClass("River");
        someClass.instantiateProperty("rdf:type");
        
        
        RDFDUDES d1 = which.merge(someClass, "1");
        RDFDUDES d2 = someClass.merge(which);
        
        System.out.println("d1 (with applyTo): " + d1.toString());
        System.out.println("d2 (with union):   " + d2.toString());
        
        RDFDUDES d3 = someProperty.merge(someIndividual, "1");
        RDFDUDES d4 = d3.merge(d1, "2");
        RDFDUDES d5 = d3.merge(d2, "2");

        
        System.out.println("With d1 (applyTo):\n" + d4.convertToSPARQL());
        System.out.println("With d2 (union):\n"   + d5.convertToSPARQL());
       

    }
}