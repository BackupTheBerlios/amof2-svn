package hub.sam.mof.bootstrap;

import java.util.*;
import hub.sam.mof.xmi.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.conversion.*;

public class SelfClassification implements Conversion<ClassInstance<XmiClassifier,String,String>, ClassInstance<XmiClassifier,String,String>, Object,
        ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,
        ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> {

    private Converter<ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, Object, ClassInstance<ClassInstance, ClassInstance, Object>, ClassInstance<ClassInstance, ClassInstance, Object>, ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,java.lang.Object> converterForSelfClassification = null;
    private Converter<XmiClassifier, String, String, ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, java.lang.Object> converterForInstantiation = null;
    private Map<String, ClassInstance<ClassInstance,ClassInstance,Object>> propertyNames = new HashMap<String,ClassInstance<ClassInstance,ClassInstance,Object>>();
    
    public void setConverter(
            Converter<XmiClassifier, String, String, ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, java.lang.Object> converterForInstantiation,
            Converter<ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, Object, ClassInstance<ClassInstance, ClassInstance, Object>, ClassInstance<ClassInstance, ClassInstance, Object>, ClassInstance<ClassInstance, ClassInstance, Object>, ClassInstance<ClassInstance, ClassInstance, Object>, java.lang.Object> converterForSelfClassification) {
        this.converterForSelfClassification = converterForSelfClassification;
        this.converterForInstantiation = converterForInstantiation;
    }
    
    public ClassInstance<ClassInstance, ClassInstance, Object> getClassifier(ClassInstance<XmiClassifier, String, String> classifier) throws MetaModelException {
        return (ClassInstance)converterForSelfClassification.getFluxBox().getObject(
                converterForSelfClassification.getId(converterForInstantiation.getFluxBox().getObject(
                        converterForInstantiation.getId(classifier), null)), null);
    }

    public ClassInstance<ClassInstance, ClassInstance, Object> getProperty(ClassInstance<XmiClassifier, String, String> property, ClassInstance<ClassInstance, ClassInstance, Object> classifier) throws MetaModelException {
        ClassInstance<ClassInstance,ClassInstance,Object> result = (ClassInstance)converterForSelfClassification.getFluxBox().getObject(
                converterForSelfClassification.getId(converterForInstantiation.getFluxBox().getObject(
                        converterForInstantiation.getId(property), null)), null);
        propertyNames.put(InstanciatedXmiModel.getQualifiedName(property), result);
        return result;
    }

    public ClassInstance<ClassInstance, ClassInstance, Object> getPropertyType(ClassInstance<ClassInstance, ClassInstance, Object> property) throws MetaModelException {
        return property; // in this special ocasion we just return something not null. The only thing that will happen to this value is that it will be checked agains null.
    }

    public Object createFromString(ClassInstance<ClassInstance, ClassInstance, Object> type, Object stringRepresentation) throws MetaModelException {
        return stringRepresentation;
    }

    public ClassInstance<ClassInstance, ClassInstance, Object> asDataType(ClassInstance<ClassInstance, ClassInstance, Object> type) {
        return type; // in this special ocasion asDataType is only called when it is already clear that type is a dataType
    }
    
    public Map<String,ClassInstance<ClassInstance,ClassInstance,Object>> getPropertyNames() {
        return propertyNames;
    }
}
