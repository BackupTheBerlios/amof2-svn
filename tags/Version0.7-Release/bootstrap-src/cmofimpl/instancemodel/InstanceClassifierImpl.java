package cmofimpl.instancemodel;

import java.util.HashMap;
import java.util.Map;

import core.abstractions.namespaces.NamedElement;
import cmof.*;
import cmof.common.ReflectiveCollection;
import cmofimpl.util.*;

/**
 * This class is not specified within MOF or UML. It offers helper functionalty for analysis of inter property relations 
 * with in a single UML classifier.
 */
public class InstanceClassifierImpl {

    private final Classifier classifier;
    private ReflectiveCollection<Property> propertys;
    private ReflectiveCollection<Property> finalPropertys;
    private final Map<Property, Property> finalRedefinitionForProperty = new HashMap<Property, Property>();
    private MultiMap<Property, Property> subsetGraph = new MultiMap<Property, Property>();
    private MultiMap<Property, Property> supersetGraph = new MultiMap<Property, Property>();
    
    protected InstanceClassifierImpl(Classifier classifier) {
        this.classifier = classifier;
        initialize();
    }

    private void initialize() {
        // Classifier::member is the only set containing really all possibile
        // features (containing especially inherited features        
        ReflectiveCollection<Property> finalPropertys = new SetImpl<Property>();
        ReflectiveCollection<Property> allPropertiesIncludeRedefined = new SetImpl<Property>();
        if (classifier != null) {
            collectAllPropertys(classifier, allPropertiesIncludeRedefined);
            for (NamedElement possibleFeature: classifier.getMember()) {
                if (possibleFeature instanceof Property) {
                    finalPropertys.add((Property)possibleFeature);
                }
            }
        }
        
        // setup redefinition graph, subset and superset graph
        
        Map<Property, Property> redefinitionParents = new HashMap<Property, Property>();
        Tree<Property> subsetParents = new Tree<Property>();
        Tree<Property> supersetParents = new Tree<Property>();
        for (Property property: allPropertiesIncludeRedefined) {
            for (Property redefinedProperty: property.getRedefinedProperty()) {
                redefinitionParents.put(redefinedProperty, property);
            }            
            for (Property subsettedProperty: property.getSubsettedProperty()) {
                supersetParents.put(property, subsettedProperty);
                subsetParents.put(subsettedProperty, property);
            }
        }
        for (Property property: allPropertiesIncludeRedefined) {            
            Property iterator = property;
            while (redefinitionParents.get(iterator) != null) {
                iterator = redefinitionParents.get(iterator);
            }
            finalRedefinitionForProperty.put(property, iterator);
        }
        
        this.propertys = allPropertiesIncludeRedefined;
        this.finalPropertys = finalPropertys;
        this.subsetGraph = subsetParents.collapseTree();
        this.supersetGraph = supersetParents.collapseTree();
    }
            
    private static final Map<UmlClass, InstanceClassImpl> classInstances = new HashMap<UmlClass, InstanceClassImpl>();
       
    /**
     * Creates an instance that will represent the given class.
     * @param classifier the UmlClass
     * @return A new instance that represents the given class.
     */
    public static InstanceClassImpl createClassClassifierForUmlClass(UmlClass classifier) {
        InstanceClassImpl result = classInstances.get(classifier);
        if (result == null) {
            result = new InstanceClassImpl(classifier);
            classInstances.put(classifier, result);
        }
        return result;
    }
    
	/**
     * Creates an instance that will represent the given data type.
     * @param classifier the data type
     * @return A new instance that represents the given data type.
     */
    public static DataValueTypeImpl createDataValueTypeForDataType(DataType dataType) {
        return new DataValueTypeImpl(dataType);
    }
    
    /**
     * Returns the represented classifier.
     */
    public Classifier getClassifier() {
        return classifier;
    }
    
    /**
     * Returns a property that redefines the given property (not nessesarily direct) but that
     * is not redefined itself, atleast not within the represented class. This takes inherited
     * propertys into account.
     */
    public Property resolveFinalProperty(Property forProperty) {
        return finalRedefinitionForProperty.get((Property)forProperty);        
    }
        
    /**
     * Returns all propertys that are not redefined with in the represented class.
     * This takes inherited propertys into account.
     */
    public ReflectiveCollection<Property> getFinalProperty() {
        return finalPropertys;
    }            
    
    /**
     * Returns all propertys, even redefined and inherited ones.
     */
    public ReflectiveCollection<Property> getProperty() {
        return propertys;
    }
    
    /**
     * Returns all propertys (within the represented classifier) that the given property is a superset for.
     */
    public ReflectiveCollection<Property> getAllSupersettedPropertys(Property forProperty) {
        return subsetGraph.get(forProperty);
    }
    
    /**
     * Returns all propertys (within the representd classifier) that the given property is a subset of.
     */
    public ReflectiveCollection<Property> getAllSubsettedPropertys(Property forProperty) {
        return supersetGraph.get(forProperty);
    }
    
    private void collectAllPropertys(Classifier classifier, ReflectiveCollection<Property> allPropertys) {
        for (Element element: classifier.getOwnedMember()) {
            if (element instanceof Property) {
                allPropertys.add((Property)element);        
            }
        }        
        for (Classifier general: classifier.getGeneral()) {
            collectAllPropertys(general, allPropertys);
        }
    }     
}
