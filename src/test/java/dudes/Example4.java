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
        RDFDUDES when  = expressions.wh("rdf:type","xsd:DateTime");
        RDFDUDES where = expressions.wh("rdf:type","dbo:Location");
        
        someProperty.instantiateProperty("birth");
        someIndividual1.instantiateIndividual("Elvis");
        someIndividual2.instantiateIndividual("USA");
        
        RDFDUDES d1 = someProperty.merge(someIndividual2,"2");
        RDFDUDES d2 = someProperty.merge(someIndividual1,"1");
        
        RDFDUDES d3 = d1.merge(who,"1"); 
        d3.postprocess();
        System.out.println("Who was born in the US?\n" + d3.convertToSPARQL());

        RDFDUDES d4 = d2.merge(where,"2"); 
        d4.postprocess();
        System.out.println("Where was Elvis born?\n" + d4.convertToSPARQL());
        
        RDFDUDES d5 = d2.merge(when,"2"); 
        d5.postprocess();
        System.out.println("When was Elvis born?\n" + d5.convertToSPARQL());
    }
}