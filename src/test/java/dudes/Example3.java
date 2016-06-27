package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example3 {
    public static void main(String[] args){     
        
        ExpressionFactory expressions = new ExpressionFactory();

        RDFDUDES did = expressions.did();
        RDFDUDES someIndividual1 = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        RDFDUDES someIndividual2 = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        RDFDUDES someProperty = new RDFDUDES(RDFDUDES.Type.PROPERTY, "1", "2");
        
        someProperty.instantiateProperty("cross");
        someIndividual1.instantiateIndividual("Brooklyn_Bridge");
        someIndividual2.instantiateIndividual("Nile");
            
        RDFDUDES d1 = someProperty.merge(someIndividual1, "1");
        RDFDUDES d2 = d1.merge(someIndividual2, "2");
                
        System.out.println("d1 (cross + Brooklyn Bridge): " + d1.toString());
        System.out.println("d2 ((cross + Brooklyn Bridge) + Nile): " + d2.toString());
        
        RDFDUDES d3 = did.merge(d2);
        
        System.out.println("d3 (did + ((cross + Brooklyn Bridge) + Nile)): " + d3.toString());
        
        d3.postprocess();
        System.out.println("Query:\n" + d3.convertToSPARQL());
       
    }
}