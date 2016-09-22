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
        
        // longest/shortest river
        
        RDFDUDES longest  = expressions.superlativeMax("amod");
        longest.instantiateProperty("http://dbpedia.org/ontology/length");
        
        RDFDUDES shortest = expressions.superlativeMin("amod");
        shortest.instantiateProperty("http://dbpedia.org/ontology/length");

        RDFDUDES river    = new RDFDUDES(RDFDUDES.Type.CLASS);
        river.instantiateObject("http://dbpedia.org/ontology/River");
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
        
        // highest/lowest density
        
        RDFDUDES highest = expressions.highest("amod");        
        RDFDUDES lowest  = expressions.lowest("amod");

        RDFDUDES density = new RDFDUDES(RDFDUDES.Type.PROPERTY,"nsubj","amod");
        density.instantiateProperty("http://dbpedia.org/ontology/density");
         
        System.out.println("density: " + density);
        System.out.println("highest: " + highest);
        System.out.println("lowest:  " + lowest);
             
        RDFDUDES highest_density = highest.merge(density,"amod");
        RDFDUDES lowest_density  = lowest.merge(density,"amod");

        System.out.println("\nhighest + density: " + highest_density);
        System.out.println("\nSPARQL:\n" + highest_density.convertToSPARQL());
        
        System.out.println("lowest + density: " + lowest_density);
        System.out.println("\nSPARQL:\n" + lowest_density.convertToSPARQL());
    }
    
}
