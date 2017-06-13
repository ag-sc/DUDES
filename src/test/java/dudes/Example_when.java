package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example_when {

    public static void main(String[] args) {
        
        ExpressionFactory expressions = new ExpressionFactory();
        
        // Without copula 
        
//        RDFDUDES someProperty = new RDFDUDES(RDFDUDES.Type.PROPERTY, "1", "2");
//        someProperty.instantiateProperty("http://dbpedia.org/ontology/date");
//        
//        RDFDUDES what  = expressions.what();
        
        RDFDUDES r = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        r.instantiateIndividual("http://dbpedia.org/resource/Overlord");
        
        RDFDUDES when = expressions.when("http://dbpedia.org/ontology/date");
        RDFDUDES when_overlord = when.merge(r, "1");
        
        
        System.out.println("when + overlord = " + when_overlord);
        
        
        when_overlord.postprocess();
        
        System.out.println("\nSPARQL:\n" + when_overlord.convertToSPARQL());
    }
    
}
