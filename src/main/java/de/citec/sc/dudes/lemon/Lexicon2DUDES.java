package de.citec.sc.dudes.lemon;

import de.citec.sc.dudes.rdf.RDFDUDES;
import de.citec.sc.dudes.rdf.RDFDUDES.Type;
import de.citec.sc.lemon.core.LexicalEntry;
import de.citec.sc.lemon.core.Lexicon;
import de.citec.sc.lemon.core.Reference;
import de.citec.sc.lemon.core.Restriction;
import de.citec.sc.lemon.core.Sense;
import de.citec.sc.lemon.core.SimpleReference;
import de.citec.sc.lemon.core.SyntacticBehaviour;
import de.citec.sc.lemon.io.LexiconLoader;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author cunger
 */
public class Lexicon2DUDES {
    
    LexiconLoader loader = new LexiconLoader();
    Lexicon lexicon;
    
    boolean verbose = true;
    
    
    public Lexicon2DUDES() {
    }
    
    public Map<String,Set<RDFDUDES>> extractDUDES(String pathToLexicon) {
        
        if (!(new File(pathToLexicon).exists())) {
            throw new RuntimeException(pathToLexicon + " not found.");
        }
        
        Map<String,Set<RDFDUDES>> map = new HashMap<>();
        
        lexicon = loader.loadFromFile(pathToLexicon);

        for (LexicalEntry entry : lexicon.getEntries()) {
                        
            String pos  = entry.getPOS();
            String form = entry.getCanonicalForm();
            
            if (verbose) System.out.println("\n---- " + form + " (" + pos + ") ----\n");
            
            HashMap<Sense,HashSet<SyntacticBehaviour>> senseFrameMap = entry.getSenseBehaviours();
            
            for (Sense sense : senseFrameMap.keySet()) {
                 
                String uri = "";
                String property = "";
                String value = "";
                
                Reference ref = sense.getReference(); 
                if (ref instanceof SimpleReference) {                    
                    uri = ref.getURI();
                    if (verbose) System.out.println("Simple sense: " + uri);
                }
                if (ref instanceof Restriction) {
                    property = ((Restriction) ref).getProperty();
                    value    = ((Restriction) ref).getValue();
                    if (verbose) System.out.println("Complex sense: " + property + " - " + value);
                }
                
                if (senseFrameMap.get(sense).isEmpty()) {
                    
                    switch (pos) { 
                        
                        case "http://www.lexinfo.net/ontology/2.0/lexinfo#noun": 
                            
                            addDUDES(map,form,classDUDES(uri));
                            break;
                            
                        case "http://www.lexinfo.net/ontology/2.0/lexinfo#adjective":
                            
                            if (!uri.isEmpty()) {
                                addDUDES(map,form,classDUDES(uri));
                            }
                            if (!property.isEmpty() && !value.isEmpty()) {
                                addDUDES(map,form,restrictionclassDUDES(property,value));
                            }
                            break;
                    }
                }
                
                for (SyntacticBehaviour syn : senseFrameMap.get(sense)) {
                    
                    String frame = syn.getFrame();
                    
                    if (verbose) System.out.println("Frame: " + frame);
                    
                    switch (frame) {
                        
                        // Verb frames 
                        
                        case "http://www.lexinfo.net/ontology/2.0/lexinfo#IntransitivePPFrame":
                         
                            addDUDES(map,form,null);
                            break;
                            
                        case "http://www.lexinfo.net/ontology/2.0/lexinfo#TransitiveFrame":
                         
                            if (!uri.isEmpty()) {
                                addDUDES(map,form,propertyDUDES(uri));
                            }
                            break;
                            
                        case "http://www.lexinfo.net/ontology/2.0/lexinfo#TransitivePPFrame":
                         
                            if (!uri.isEmpty()) {
                                addDUDES(map,form,propertyDUDES(uri));
                            }
                            break;
                            
                        // Noun frames    
                            
                        case "http://www.lexinfo.net/ontology/2.0/lexinfo#NounPPFrame": 
                            
                            if (!uri.isEmpty()) {
                                addDUDES(map,form,propertyDUDES(uri));
                            }
                            break;
                            
                        // TODO Adjective frames 
                            
                        // TODO Preposition frames
                    }                     
                }
            }            
        }
        
        if (verbose) System.out.println("\n---> " + map);
        
        return map;
    }
    
    private void addDUDES(Map<String,Set<RDFDUDES>> map, String form, RDFDUDES dudes) {
        
        if (!map.containsKey(form)) {
             map.put(form,new HashSet<RDFDUDES>());
        }
        map.get(form).add(dudes);
    }
    
    private RDFDUDES classDUDES(String uri) {
        
        RDFDUDES dudes = new RDFDUDES(Type.CLASS);
        dudes.instantiateProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        dudes.instantiateObject(uri);
        
        return dudes;
    }
    
    private RDFDUDES restrictionclassDUDES(String property, String value) {
        
        RDFDUDES dudes = new RDFDUDES(Type.PROPERTY);
        dudes.instantiateProperty(property);
        dudes.instantiateObject(value);
        
        return dudes;
    }
    
    private RDFDUDES propertyDUDES(String property) {
        
        RDFDUDES dudes = new RDFDUDES(Type.PROPERTY);
        dudes.instantiateProperty(property);
        
        return dudes;
    }
    
}
