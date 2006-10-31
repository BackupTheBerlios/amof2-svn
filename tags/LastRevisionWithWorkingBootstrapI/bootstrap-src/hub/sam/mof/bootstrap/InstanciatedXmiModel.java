package hub.sam.mof.bootstrap;

import java.util.*;
import hub.sam.mof.xmi.*;
import hub.sam.mof.instancemodel.*;

public class InstanciatedXmiModel extends InstanceModel<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,java.lang.Object> 
        implements SemanticsFactory<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,String> {

    private final InstanceModel<XmiClassifier,String,String> sourceModel;
    private final Map<cmof.reflection.Object, ClassInstance<XmiClassifier,String,String>> instances;
    private final Map<ClassInstance<XmiClassifier,String,String>, cmof.reflection.Object> objects;
    
    public InstanciatedXmiModel(InstanceModel<XmiClassifier,String,String> sourceModel) {
        this.sourceModel = sourceModel;
        this.objects = null;
        this.instances = null;
    }
    
    public ClassInstance<XmiClassifier,String,String> getInstance(cmof.reflection.Object aObject) {
        return instances.get(aObject);
    }
    
    public cmof.reflection.Object getObject(ClassInstance<XmiClassifier,String,String> instance) {
        return objects.get(instance);
    }
    
    private Map<ClassInstance<XmiClassifier,String,String>, ClassifierSemantics<ClassInstance<XmiClassifier,String,String>,String>> cache = new HashMap();
    public ClassifierSemantics<ClassInstance<XmiClassifier, String, String>, String> createClassifierSemantics(ClassInstance<XmiClassifier, String, String> classifier) {
        ClassifierSemantics<ClassInstance<XmiClassifier,String,String>,String> result = cache.get(classifier);
        if (result == null) {
            result = new InstantiatedXmiSemantics(classifier, sourceModel);
            cache.put(classifier, result);
        }
        return result;
    }

    public static String get(ClassInstance<XmiClassifier,String,String> instance, String property) {
        try {
            ValueSpecification<XmiClassifier,String,String> value = instance.get(property).getValues().get(0);
            if (value.asUnspecifiedValue() != null) {
                return value.asUnspecifiedValue().getUnspecifiedData().toString();
            } else {
                return value.asDataValue().getValue();
            }
        } catch (Exception e) {
            return "";
        }
    } 
    
    public static String getQualifiedName(ClassInstance<XmiClassifier,String,String> instance) {
        String result = get(instance,"name");
        while(instance.getComposite() != null) {
            instance = instance.getComposite();
            result = get(instance,"name") + "." + result;            
        }
        return result;
    }
}
