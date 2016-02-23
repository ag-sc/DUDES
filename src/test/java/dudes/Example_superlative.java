package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example_superlative {

    public static void main(String[] args) {
        
        ExpressionFactory expressions = new ExpressionFactory();
        
        RDFDUDES longest  = expressions.superlativeMax("amod");
        longest.instantiateProperty("http://dbpedia.org/ontology/length");
        
        RDFDUDES shortest = expressions.superlativeMin("amod");
        shortest.instantiateProperty("http://dbpedia.org/ontology/length");

        RDFDUDES river    = new RDFDUDES(RDFDUDES.Type.CLASS);
        river.instantiateClass("http://dbpedia.org/ontology/River");
        river.instantiateProperty("rdf:type");
         
        System.out.println("river:    " + river);
        System.out.println("longest:  " + longest);
        System.out.println("shortest: " + shortest);
             
        RDFDUDES longest_river  = longest.merge(river,"amod");
        RDFDUDES shortest_river = shortest.merge(river,"amod");

        System.out.println("\nlongest + river: " + longest_river);
        System.out.println("\nSPARQL:\n" + longest_river.convertToSPARQL());
        
        System.out.println("shortest + river: " + shortest_river);
        System.out.println("\nSPARQL:\n" + shortest_river.convertToSPARQL());
    }
    
}
