package cmofimpl.codegeneration;

import cmof.reflection.*;
import cmof.*;
import java.util.*;

public class Analyser {

    public void analyse(Extent extent) {
        List<List<Property>> potentialDeletes= new Vector<List<Property>>();        
        for (core.abstractions.elements.Element element: extent.objectsOfType(null, true)) {
            if (element instanceof UmlClass) {
                UmlClass umlClass = (UmlClass)element;
                for(Property property: umlClass.getOwnedAttribute()) {
                    // properties that redefine at least 2
                    if (property.getRedefinedProperty().size() >= 2) {
                        // with equal name and different type
                        String name = property.getName();
                        Type type = property.getType();
                        List<Property> withThatNameAndThatType = new Vector<Property>();
                        for (Property redefinedProperty: property.getRedefinedProperty()) {                                                                                   
                            if (redefinedProperty.getName().equals(name) && !redefinedProperty.getType().equals(type)) {
                                withThatNameAndThatType.add(redefinedProperty);
                            }                            
                        }   
                        if (withThatNameAndThatType.size() > 1) {
                            potentialDeletes.add(withThatNameAndThatType);
                        }
                    }
                }
            }
        }
        Set<Property> toDeletes = new HashSet<Property>();
        for (List<Property> potentialDelete: potentialDeletes) {
            for (Property toDelete: potentialDelete) {                
                toDeletes.add(toDelete);                
            }
        }
        for (Property toDelete: toDeletes) {
            System.out.println("WARNING A property was removed: " + toDelete.getName() + " in " + toDelete.getOwner());
            ((cmof.reflection.Object)toDelete).delete();            
        }
        
    }
}
