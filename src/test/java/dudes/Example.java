package dudes;

import de.citec.sc.dudes.*;
import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Domain DUDES
        
        RDFDUDES someIndividual       = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        RDFDUDES someClass1           = new RDFDUDES(RDFDUDES.Type.CLASS);
        RDFDUDES someClass2           = new RDFDUDES(RDFDUDES.Type.CLASS);
        RDFDUDES someProperty         = new RDFDUDES(RDFDUDES.Type.PROPERTY,"1","2");
        
        System.out.println("someIndividual: " + someIndividual);
        System.out.println("someClass1:     " + someClass1);
        System.out.println("someClass2:     " + someClass2);
        System.out.println("someProperty:   " + someProperty);

        System.out.println("\nInstantiating:\n");
        
        someIndividual.instantiateIndividual("http://dbpedia.org/resource/Orangina");
        
        someClass1.instantiateClass("http://dbpedia.org/ontology/Company");
        someClass1.instantiateProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        
        someClass2.instantiateClass("http://dbpedia.org/resource/Scottland");
        
        someProperty.instantiateProperty("http://dbpedia.org/ontology/product");
        
        System.out.println("Instantiating someIndividual: " + someIndividual);
        System.out.println("Instantiating someClass1:     " + someClass1);
        System.out.println("Instantiating someClass2:     " + someClass2);
        System.out.println("Instantiating someProperty:   " + someProperty);
        
        // Domain-independent DUDES
        
        ExpressionFactory expressions = new ExpressionFactory();

        RDFDUDES who   = expressions.what();
        RDFDUDES which = expressions.which("1");
        
        System.out.println("who:   " + who.toString());
        System.out.println("which: " + which.toString());
        
        // Getting the types
        
        System.out.println("\nGetting the types...\n");
        System.out.println("someIndividual: " + someIndividual.getType());
        System.out.println("someClass1/2:   " + someClass1.getType());
        System.out.println("someProperty:   " + someProperty.getType());
        System.out.println("who/which:      " + who.getType());
        
        // Combining DUDES
        
        System.out.println("\nExample question: Which Scottish company procudes Orangina?\n");
        
        RDFDUDES cn = someClass2.merge(someClass1,"3");
        RDFDUDES np = which.merge(cn,"1");
        RDFDUDES vp = someProperty.merge(someIndividual,"2");
        RDFDUDES cl = vp.merge(np,"1");

        System.out.println("CN (Scottish + company):  " + np);
        System.out.println("NP (which + CN):          " + np);
        System.out.println("VP (produces + Orangina): " + vp);
        System.out.println("Cl (NP + VP):             " + cl);
        
        System.out.println("\nRDF(CN):\n"+cn.convertToRDF().toString());
        System.out.println("\nRDF(NP):\n"+np.convertToRDF().toString());
        System.out.println("\nRDF(VP):\n"+vp.convertToRDF().toString());
        System.out.println("\nRDF(Cl):\n"+cl.convertToRDF().toString());
        
        System.out.println("\nSPARQL:\n" + cl.convertToSPARQL().toString());
    }
    
}
