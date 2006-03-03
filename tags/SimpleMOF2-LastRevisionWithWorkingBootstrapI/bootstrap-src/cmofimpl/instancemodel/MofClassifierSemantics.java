package cmofimpl.instancemodel;

import java.util.*;
import cmof.*;
import cmof.common.*;
import cmofimpl.javamapping.*;
import hub.sam.mof.instancemodel.*;

/**
 * This class is not specified within MOF or UML. It offers helper functionalty for analysis of inter property relations 
 * with in a single UML classifier.
 */
public abstract class MofClassifierSemantics extends CommonClassifierSemantics<Classifier,Property,String> implements ClassifierSemantics<Property, String> {

    private JavaMapping javaMapping = new JavaMapping();
    
    protected MofClassifierSemantics(Classifier classifier) {
        super(classifier);
        initialize();
    }
              
    private static final Map<UmlClass, MofClassSemantics> classInstances = new HashMap<UmlClass, MofClassSemantics>();
       
    /**
     * Creates an instance that will represent the given class.
     * @param classifier the UmlClass
     * @return A new instance that represents the given class.
     */
    public static MofClassSemantics createClassClassifierForUmlClass(UmlClass classifier) {
        MofClassSemantics result = classInstances.get(classifier);
        if (result == null) {
            result = new MofClassSemantics(classifier);
            classInstances.put(classifier, result);
        }
        return result;
    }
    
	/**
     * Creates an instance that will represent the given data type.
     * @param classifier the data type
     * @return A new instance that represents the given data type.
     */
    public static MofDataTypeSemantics createDataValueTypeForDataType(DataType dataType) {
        return new MofDataTypeSemantics(dataType);
    }
   
    public String getName(Property forProperty) {
        return forProperty.getName();
    }
    
    public boolean isCollectionProperty(Property forProperty) {
        return forProperty.getUpper() != 1;
    }

    public String getJavaGetMethodNameForProperty(Property forProperty) {
        return javaMapping.getJavaGetMethodNameForProperty(forProperty);
    }
    
    protected <E> Collection<E> toCollection(ReflectiveCollection<E> reflectiveCollection) {
        Collection<E> result = new Vector<E>(reflectiveCollection.size());
        for (E e: reflectiveCollection) {
            result.add(e);
        }        
        return result;
    }
    
    protected Collection<? extends Property> redefinedProperties(Property p) {
        return toCollection(p.getRedefinedProperty());
    }

    protected Collection<? extends Property> subsettedProperties(Property p) {
        return toCollection(p.getSubsettedProperty());
    }      
}
