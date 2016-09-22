package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author sherzod
 */
public class Example_copula {
    public static void main(String[] args){     
        
        ExpressionFactory expressions = new ExpressionFactory();

        RDFDUDES is = expressions.copula("1","2");
        RDFDUDES proinsulin = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        proinsulin.instantiateIndividual("http://dbpedia.org/resource/Proinsulin");
        RDFDUDES protein = new RDFDUDES(RDFDUDES.Type.CLASS);
        protein.instantiateProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        protein.instantiateObject("http://dbpedia.org/ontology/Protein");
        
        System.out.println("is:         " + is.toString());
        System.out.println("protein:    " + protein.toString());
        System.out.println("proinsulin: " + proinsulin.toString());
        
        RDFDUDES is_a_protein = is.merge(protein,"2");
        System.out.println("is + protein:\n" + is_a_protein.toString());
                
        RDFDUDES is_proinsulin_a_protein = is_a_protein.merge(proinsulin,"1");
        System.out.println("(is + protein) + proinsulin:\n" + is_proinsulin_a_protein.toString());

        is_proinsulin_a_protein.postprocess();
        System.out.println("After postprocess():\n" + is_proinsulin_a_protein.toString());

        System.out.println("Is proinsulin a protein?\n" + is_proinsulin_a_protein.convertToSPARQL(false));

        RDFDUDES how = expressions.what();
        RDFDUDES tall = new RDFDUDES(RDFDUDES.Type.PROPERTY);
        tall.instantiateProperty("http://dbpedia.org/ontology/height");
        RDFDUDES jordan = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        jordan.instantiateIndividual("http://dbpedia.org/resource/Michael_Jordan");
        
        RDFDUDES howtall = tall.merge(how,"1");
        RDFDUDES howtallis = is.merge(howtall,"2");
        RDFDUDES howtallisjordan = howtallis.merge(jordan,"1");
        howtallisjordan.postprocess();
        
        System.out.println("How tall is Michael Jordan?\n" + howtallisjordan.toString());
        System.out.println("SPARQL:\n" + howtallisjordan.convertToSPARQL());        
        
    }
}