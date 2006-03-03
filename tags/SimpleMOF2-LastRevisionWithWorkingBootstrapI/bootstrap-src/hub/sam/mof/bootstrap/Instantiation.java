package hub.sam.mof.bootstrap;

import java.util.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.xmi.*;

/**
 * Be aware, this hole thing will only work for the bootstrap m3 model. It is not capable of
 * importing package, nesting with overloading names, correct property redefinition or inheritence
 * in general. This is only for bootstrapping the m3 model.
 */
public class Instantiation extends AbstractXmiConversion<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,java.lang.Object> {

    private final InstanceModel<XmiClassifier,String,String> original;
    private final InstanciatedXmiModel image;
    private final Map<String, ClassInstance<XmiClassifier,String,String>> classifiers;
    private static final String M3_PACKAGE = "cmof";     
    private static String[] M3_CLASSIFIERS = new String[]{"Class", "Enumeration", "PrimitiveType", "DataType"}; 
    static {
        Arrays.sort(M3_CLASSIFIERS);
    }

    public Instantiation(InstanceModel<XmiClassifier,String,String> original, InstanciatedXmiModel image) {
        this.original = original;
        this.image = image;
        classifiers = new HashMap<String, ClassInstance<XmiClassifier,String,String>>();
        for (ClassInstance<XmiClassifier,String,String> aPackage: original.getOutermostComposites()) {
            if (InstanciatedXmiModel.get(aPackage,"name").equals(M3_PACKAGE)) {
                collectClassifiersFromPackage(aPackage);         
            }            
        }        
    }

    private void collectClassifiersFromPackage(ClassInstance<XmiClassifier,String,String> aPackage) {
        for (ValueSpecification<XmiClassifier,String,String> value: aPackage.get("ownedType").getValues()) {
            ClassInstance<XmiClassifier,String,String> instance = value.asInstanceValue().getInstance();
            if (!instance.getClassifier().isDefinedByContext()) {
                if (Arrays.binarySearch(M3_CLASSIFIERS, instance.getClassifier().getName()) != -1) {
                    classifiers.put(InstanciatedXmiModel.get(instance,"name"),instance);
                    classifiers.put(InstanciatedXmiModel.getQualifiedName(instance), instance);
                }
            }
        }
        StructureSlot<XmiClassifier,String,String> nestedPackageSlot = aPackage.get("nestedPackage");
        if (nestedPackageSlot != null) {
            for (ValueSpecification<XmiClassifier,String,String> value: nestedPackageSlot.getValues()) {
                collectClassifiersFromPackage(value.asInstanceValue().getInstance());
            }
        }
    }
    
    protected ClassInstance<XmiClassifier,String,String> getClassForTagName(String tagName, String nsPrefix) {
        return classifiers.get(tagName);
    }

    public ClassInstance<XmiClassifier, String, String> getProperty(String property, ClassInstance<XmiClassifier, String, String> classifier) throws MetaModelException {        
        ClassInstance<XmiClassifier, String, String> result = image.createClassifierSemantics(classifier).getProperty(property);
        if (result == null) {
            throw new MetaModelException("property \"" + property + "\" does not exist in \"" + InstanciatedXmiModel.get(classifier,"name") + "\"");    
        } else {
            return result;
        }
    }

    public ClassInstance<XmiClassifier,String,String> getPropertyType(ClassInstance<XmiClassifier, String, String> property) throws MetaModelException {
        return original.getInstance(InstanciatedXmiModel.get(property,"type"));
    }

    public java.lang.Object createFromString(ClassInstance<XmiClassifier,String,String> type, String stringRepresentation) throws MetaModelException {
        if (type.getClassifier().getName().equals(cmof.PrimitiveType.class.getSimpleName())) {
        String name = InstanciatedXmiModel.get(type, "name");
            if (name.equals(core.primitivetypes.String.class.getSimpleName())) {
                return stringRepresentation;
            } else if (name.equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return new Boolean(stringRepresentation);
            } else if (name.equals(core.primitivetypes.Integer.class.getSimpleName())) {
                return new Integer(stringRepresentation);
            } else if (name.equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                if (stringRepresentation.equals("*")) {
                    return new Long(-1);
                } else {
                    return new Long(stringRepresentation);
                }
            } else {
                throw new MetaModelException("data type not supported");
            }
        } else {
            throw new MetaModelException("data type not supported");
        }
    }

    public ClassInstance<XmiClassifier,String,String> asDataType(ClassInstance<XmiClassifier,String,String> type) {
        if (type.getClassifier().getName().equals(cmof.PrimitiveType.class.getSimpleName()) || type.getClassifier().getName().equals(cmof.Enumeration.class.getSimpleName())) {
            return type;
        } else {
            return null;
        }
    }

}
