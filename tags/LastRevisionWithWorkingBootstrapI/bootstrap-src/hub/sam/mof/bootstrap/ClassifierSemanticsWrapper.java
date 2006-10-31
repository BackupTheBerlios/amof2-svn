package hub.sam.mof.bootstrap;

import java.util.*;
import cmof.*;
import hub.sam.mof.instancemodel.*;

public class ClassifierSemanticsWrapper implements ClassifierSemantics<Property, String> {

    private final BootstrapSemantics semantics;
    private final BootstrapModel model;
    private Collection<Property> properties = null;
    private Collection<Property> finalProperties = null;
    
    public ClassifierSemanticsWrapper(BootstrapSemantics semantics, BootstrapModel model) {
        this.semantics = semantics;
        this.model = model;
    }
            
    public Collection<Property> getProperties() {        
        if (properties == null) {
            Collection<ClassInstance<ClassInstance,ClassInstance,Object>> wrappedProperties = semantics.getProperties();
            properties = new Vector<Property>(wrappedProperties.size());
            for (ClassInstance<ClassInstance,ClassInstance,Object> property: wrappedProperties) {
                properties.add((Property)model.getObjectImpl(property));
            }
        }
        return properties;
    }

    public Collection<Property> getFinalProperties() {
        if (finalProperties == null) {
            Collection<ClassInstance<ClassInstance,ClassInstance,Object>> wrappedProperties = semantics.getFinalProperties();
            finalProperties = new Vector<Property>(wrappedProperties.size());
            for (ClassInstance<ClassInstance,ClassInstance,Object> property: wrappedProperties) {
                finalProperties.add((Property)model.getObjectImpl(property));
            }
        }
        return finalProperties;
    }

    public Property getFinalProperty(Property forProperty) {
        return (Property)model.getObjectImpl(semantics.getFinalProperty(model.getBootstrapElement(forProperty)));
    }

    public Collection<Property> getSupersettedProperties(Property forProperty) {
        return new Vector<Property>();
    }

    public Collection<Property> getSubsettedProperties(Property forProperty) {
        return new Vector<Property>();
    }

    public String getName(Property forProperty) {
        return semantics.getName(model.getBootstrapElement(forProperty));
    }

    public Property getProperty(String forName) {
        return (Property)model.getObjectImpl(semantics.getProperty(forName));
    }

    public boolean isCollectionProperty(Property forProperty) {
        return semantics.isCollectionProperty(model.getBootstrapElement(forProperty));
    }

    public String getJavaGetMethodNameForProperty(Property forProperty) {
        return semantics.getJavaGetMethodNameForProperty(model.getBootstrapElement(forProperty));        
    }
    
    protected BootstrapSemantics getSemantics() {
        return semantics;
    }
}
