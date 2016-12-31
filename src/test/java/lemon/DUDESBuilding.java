package lemon;

import de.citec.sc.dudes.lemon.Lexicon2DUDES;
import de.citec.sc.dudes.rdf.RDFDUDES;
import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class DUDESBuilding {

    final static String testLexicon = "src/test/java/lemon/test.ttl";
    
    public static void main(String[] args) {

        Lexicon2DUDES builder = new Lexicon2DUDES();
        
        Map<String,Set<RDFDUDES>> extracted = builder.extractDUDES(testLexicon);

    }
    
}
