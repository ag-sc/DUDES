package de.citec.sc.dudes.rdf;

import de.citec.sc.dudes.DUDES;

/**
 *
 * @author cunger
 */
public class DUDESFactory {
        
    public enum Type { INDIVIDUAL, CLASS, RESTRICTIONCLASS, PROPERTY };
    
    IndividualDUDES       individualDUDES;
    ClassDUDES            classDUDES;
    RestrictionClassDUDES restrclassDUDES;
    PropertyDUDES         propertyDUDES;
    
    
    public DUDESFactory() {
        
        individualDUDES = new IndividualDUDES();
        classDUDES      = new ClassDUDES();
        restrclassDUDES = new RestrictionClassDUDES();
        propertyDUDES   = new PropertyDUDES();
    }
    
    
    public DUDES create(Type type) {
        
        switch (type) {
            case INDIVIDUAL:       return individualDUDES.create();
            case CLASS:            return classDUDES.create();
            case RESTRICTIONCLASS: return restrclassDUDES.create();
            case PROPERTY:         return propertyDUDES.create();
            default:               return null; 
        }
    }
    
    public DUDES create(Type type, String uri) {
        
        switch (type) {
            case INDIVIDUAL: return individualDUDES.create(uri);
            case CLASS:      return classDUDES.create(uri);
            case PROPERTY:   return propertyDUDES.create(uri);
            default:         return null; 
        }
    }

    public DUDES create(Type type, String uri, String anchor1, String anchor2) {
        
        switch (type) {
            case PROPERTY: return propertyDUDES.create(uri,anchor1,anchor2);
            default:       return null; 
        }
    }
        
    public DUDES create(Type type, String uri1, String uri2) {
        
        switch (type) {
            case RESTRICTIONCLASS: return restrclassDUDES.create(uri1,uri2);
            default:               return null; 
        }
    }
}
