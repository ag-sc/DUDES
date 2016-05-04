package dudes;

import de.citec.sc.dudes.*;
import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example_morethan {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ExpressionFactory expressions = new ExpressionFactory();
        
        RDFDUDES morethan = expressions.morethan("num","noun");
        RDFDUDES ten      = expressions.num(10);
        RDFDUDES caves    = new RDFDUDES(RDFDUDES.Type.CLASS);
        caves.instantiateObject("http://dbpedia.org/ontology/Cave");
        caves.instantiateProperty("rdf:type");
               
        System.out.println("more than: " + morethan);
        System.out.println("ten:       " + ten);
        System.out.println("caves:     " + caves);
       
        System.out.println("\nExample expression: more than ten caves\n");
        
        RDFDUDES morethan_ten = morethan.merge(ten,"num");
        RDFDUDES morethan_ten_caves = morethan_ten.merge(caves,"noun");

        System.out.println(" morethan + ten:          " + morethan_ten);
        System.out.println("(morethan + ten) + caves: " + morethan_ten_caves);
        
        morethan_ten_caves.postprocess();
        
        System.out.println("After postprocessing:     " + morethan_ten_caves);
        
        System.out.println("\nSPARQL:\n" + morethan_ten_caves.convertToSPARQL());

    }
    
}
