package cmofimpl.instancemodel;

import java.util.*;
import cmof.*;
import hub.sam.mof.instancemodel.*;

// TODO, is this class needed anyway
public class MofStaticClassifierSemantics implements ClassifierSemantics<Property,String> {
    private final UmlClass classifier;
    private Collection<Property> properties = null;
    private Map<String,Property> names = null;
    
    public MofStaticClassifierSemantics(UmlClass classifier) {
        this.classifier = classifier;
        this.names = new HashMap<String,Property>();
        properties = new Vector<Property>();
        for(Property property: classifier.getOwnedAttribute()) {
            properties.add(property);
            names.put(getName(property), property);
        }
    }

    public Collection<Property> getProperties() {
        return properties;        
    }

    public Collection<Property> getFinalProperties() {
        return properties;
    }

    public Property getFinalProperty(Property forProperty) {
        return forProperty;
    }

    public Collection<Property> getSupersettedProperties(Property forProperty) {
        return new Vector<Property>();
    }

    public Collection<Property> getSubsettedProperties(Property forProperty) {        
        return new Vector<Property>();
    }

    public String getName(Property forProperty) {
        return forProperty.getName();
    }

    public boolean isCollectionProperty(Property forProperty) {
        return forProperty.getUpper() != 1;
    }
    
    public Property getProperty(String forName) {
        return names.get(forName);
    }

    public String getJavaGetMethodNameForProperty(Property forProperty) {
        return null;
    }
}
