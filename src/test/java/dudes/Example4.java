package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author sherzod
 */
public class Example4 {
    public static void main(String[] args){     
        
        ExpressionFactory expressions = new ExpressionFactory();

        RDFDUDES someIndividual1 = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        RDFDUDES someIndividual2 = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        RDFDUDES someProperty = new RDFDUDES(RDFDUDES.Type.PROPERTY, "1", "2");
        
        RDFDUDES who   = expressions.wh("rdf:type","dbo:Person");
        // RDFDUDES when  = expressions.wh("rdf:type","xsd:DateTime");
        // RDFDUDES where = expressions.wh("rdf:type","dbo:Location");
        
        someProperty.instantiateProperty("cross");
        someIndividual1.instantiateIndividual("Nile");
        someIndividual2.instantiateIndividual("Brooklyn_Bridge");
        
        RDFDUDES d1 = someProperty.merge(someIndividual1,"2");
        
        RDFDUDES d2 = d1.merge(who,"1"); 
        d2.postprocess();
        System.out.println("Who crosses Nile? " + d2.convertToSPARQL());

    }
}