package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example1 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Domain DUDES
        
        RDFDUDES someIndividual       = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        RDFDUDES someClass1           = new RDFDUDES(RDFDUDES.Type.CLASS);
        RDFDUDES someClass2           = new RDFDUDES(RDFDUDES.Type.CLASS,"1");
        RDFDUDES someProperty         = new RDFDUDES(RDFDUDES.Type.PROPERTY,"1","2");
        
        System.out.println("someIndividual: " + someIndividual + " >> isIndividualInstantiated(): " + someIndividual.isIndividualInstantiated());
        System.out.println("someClass1:     " + someClass1 + " >> isPropertyInstantiated(): " + someClass1.isPropertyInstantiated() + ", isObjectInstantiated(): " + someClass1.isObjectInstantiated());
        System.out.println("someClass2:     " + someClass2 + " >> isPropertyInstantiated(): " + someClass2.isPropertyInstantiated() + ", isObjectInstantiated(): " + someClass2.isObjectInstantiated());
        System.out.println("someProperty:   " + someProperty + " >> isPropertyInstantiated(): " + someProperty.isPropertyInstantiated());

        System.out.println("\nInstantiating:\n");
        
        someIndividual.instantiateIndividual("http://dbpedia.org/resource/Orangina");
        
        someClass1.instantiateObject("http://dbpedia.org/ontology/Company");
        someClass1.instantiateProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        
        someClass2.instantiateObject("http://dbpedia.org/resource/Scottland");
                
        someProperty.instantiateProperty("http://dbpedia.org/ontology/product");
        
        System.out.println("Instantiating someIndividual: " + someIndividual + " >> isIndividualInstantiated(): " + someIndividual.isIndividualInstantiated());
        System.out.println("Instantiating someClass1:     " + someClass1 + " >> isPropertyInstantiated(): " + someClass1.isPropertyInstantiated() + ", isObjectInstantiated(): " + someClass1.isObjectInstantiated());
        System.out.println("Instantiating someClass2:     " + someClass2 + " >> isPropertyInstantiated(): " + someClass2.isPropertyInstantiated() + ", isObjectInstantiated(): " + someClass2.isObjectInstantiated());
        System.out.println("Instantiating someProperty:   " + someProperty + " >> isPropertyInstantiated(): " + someProperty.isPropertyInstantiated());
                        
        // Domain-independent DUDES
        
        ExpressionFactory expressions = new ExpressionFactory();

        RDFDUDES who   = expressions.what();
        RDFDUDES who2  = expressions.what();
        RDFDUDES which = expressions.which("3");
        
        System.out.println("who:   " + who.toString());
        System.out.println("who2:  " + who2.toString());

        System.out.println("which: " + which.toString());
                
        // Getting the types
        
        System.out.println("\nGetting the types...\n");
        System.out.println("someIndividual: " + someIndividual.getType());
        System.out.println("someClass1/2:   " + someClass1.getType());
        System.out.println("someProperty:   " + someProperty.getType());
        System.out.println("who/which:      " + who.getType());
        
        // Combining DUDES
        
        System.out.println("\nExample question: Which Scottish company procudes Orangina?\n");
        
        RDFDUDES cn = someClass2.merge(someClass1,"1");
        
        System.out.println("CN (Scottish + company):  " + cn);
        
        RDFDUDES np = which.merge(cn,"3");
        
        System.out.println("NP (which + CN):          " + np);

        RDFDUDES vp = someProperty.merge(someIndividual,"2");
        
        System.out.println("VP (produces + Orangina): " + vp);
        
        RDFDUDES cl = vp.merge(np,"1");

        System.out.println("Cl (NP + VP):             " + cl);
        
        cl.postprocess();

        System.out.println("\nCl after postprocessing:  " + cl);
        
        System.out.println("\nSPARQL:\n" + cl.convertToSPARQL().toString());
        
        RDFDUDES whodoeswhat = someProperty.merge(who,"1").merge(who2,"2");
        System.out.println("whodoeswhat: " + whodoeswhat);
    }
    
}
