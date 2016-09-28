package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example_howhigh {

    public static void main(String[] args) {
        
        ExpressionFactory expressions = new ExpressionFactory();
        
        // Without copula 
        
        RDFDUDES how  = expressions.how();
        RDFDUDES high = new RDFDUDES(RDFDUDES.Type.PROPERTY,"1","2");
        high.instantiateProperty("http://dbpedia.org/ontology/elevation");
        RDFDUDES everest = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        everest.instantiateIndividual("http://dbpedia.org/resource/Mount_Everest");
        
        RDFDUDES howhigh = high.merge(how,"2");
        
        System.out.println("how + high = " + howhigh);
        
        RDFDUDES howhigheverest = howhigh.merge(everest,"1");

        System.out.println("(how + high) + everest = " + howhigheverest);
        
        howhigheverest.postprocess();
                
        System.out.println("\nSPARQL:\n" + howhigheverest.convertToSPARQL());
        
        // With copula 
        
        RDFDUDES is = expressions.copula("1","2");
        
        RDFDUDES howhighis = is.merge(howhigh,"1");
        RDFDUDES howhighiseverest = howhighis.merge(everest,"2");
        
        System.out.println(" is + (how + high)            = " + howhighis);
        System.out.println("(is + (how + high)) + everest = " + howhighiseverest);
        
        howhighiseverest.postprocess();
        
        System.out.println("\nSPARQL:\n" + howhighiseverest.convertToSPARQL());
    }
    
}
