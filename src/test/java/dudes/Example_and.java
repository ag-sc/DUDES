package dudes;

import de.citec.sc.dudes.rdf.ExpressionFactory;
import de.citec.sc.dudes.rdf.RDFDUDES;

/**
 *
 * @author cunger
 */
public class Example_and {

    public static void main(String[] args) {
        
        ExpressionFactory expressions = new ExpressionFactory();
        
        RDFDUDES live_in = new RDFDUDES(RDFDUDES.Type.PROPERTY,"nsubj","dobj");
        live_in.instantiateProperty("http://example.org/ontology/live_in");
        
        RDFDUDES work_in = new RDFDUDES(RDFDUDES.Type.PROPERTY,"nsubj","dobj");
        work_in.instantiateProperty("http://example.org/ontology/work_in");

        RDFDUDES Berlin = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        Berlin.instantiateIndividual("http://dbpedia.org/resource/Berlin");
        
        RDFDUDES Vienna = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        Vienna.instantiateIndividual("http://dbpedia.org/resource/Vienna");
         
        System.out.println("Berlin:  " + Berlin);
        System.out.println("Vienna:  " + Vienna);
        System.out.println("live in: " + live_in);
        System.out.println("work in: " + work_in);
             
        RDFDUDES live_in_Vienna = live_in.merge(Vienna,"dobj");
        RDFDUDES work_in_Berlin = work_in.merge(Berlin,"dobj");

        System.out.println("\nlive in + Vienna: " + live_in_Vienna);
        System.out.println("work in + Berlin: " + work_in_Berlin);
        
        RDFDUDES live_in_Vienna_work_in_Berlin = live_in_Vienna.merge(work_in_Berlin);
        
        System.out.println("\n(live in + Vienna) + (work in + Berlin): " + live_in_Vienna_work_in_Berlin);
        System.out.println("\nSPARQL:\n" + live_in_Vienna_work_in_Berlin.convertToSPARQL());
        
        RDFDUDES John = new RDFDUDES(RDFDUDES.Type.INDIVIDUAL);
        John.instantiateIndividual("http://example.org/resource/John");
        
        RDFDUDES John_live_in_Vienna_work_in_Berlin = live_in_Vienna_work_in_Berlin.merge(John,"nsubj"); 
        
        System.out.println("John + ((live in + Vienna) + (work in + Berlin)): " + John_live_in_Vienna_work_in_Berlin);
        System.out.println("\nSPARQL:\n" + John_live_in_Vienna_work_in_Berlin.convertToSPARQL());
        
        John_live_in_Vienna_work_in_Berlin.postprocess();
        
        System.out.println("\nAfter postprocessing:\n" + John_live_in_Vienna_work_in_Berlin);
        System.out.println("\nSPARQL:\n" + John_live_in_Vienna_work_in_Berlin.convertToSPARQL());
        
        RDFDUDES who_live_in_Vienna_work_in_Berlin = live_in_Vienna_work_in_Berlin.merge(expressions.what(),"nsubj"); 
        who_live_in_Vienna_work_in_Berlin.postprocess();
        
        System.out.println("who + ((live in + Vienna) + (work in + Berlin)): " + who_live_in_Vienna_work_in_Berlin);
        System.out.println("\nSPARQL:\n" + who_live_in_Vienna_work_in_Berlin.convertToSPARQL());
    }
    
}
