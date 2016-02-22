package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example_howmany {

    public static void main(String[] args) {
        
        ExpressionFactory expressions = new ExpressionFactory();
        
        RDFDUDES howmany = expressions.howmany("amod");
        RDFDUDES caves   = new RDFDUDES(RDFDUDES.Type.CLASS);
        caves.instantiateClass("http://dbpedia.org/ontology/Cave");
        caves.instantiateProperty("rdf:type");
               
        System.out.println("how many: " + howmany);
        System.out.println("caves:    " + caves);
             
        RDFDUDES howmany_caves = howmany.merge(caves,"amod");

        System.out.println("howmany + caves: " + howmany_caves);
        
        howmany_caves.postprocess();
                
        System.out.println("\nSPARQL:\n" + howmany_caves.convertToSPARQL());
    }
    
}
