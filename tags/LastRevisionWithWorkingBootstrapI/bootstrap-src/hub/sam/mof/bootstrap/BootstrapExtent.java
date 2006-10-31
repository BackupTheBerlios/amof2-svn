package hub.sam.mof.bootstrap;

import java.util.*;

import hub.sam.mof.instancemodel.*;
import cmof.*;
import cmof.common.*;
import cmofimpl.reflection.*;
import cmofimpl.util.*;

public class BootstrapExtent extends ExtentImpl {
    
    private final BootstrapModel model;
    private final Map<ClassInstance, ObjectImpl> objects;
    private Map<ClassInstance, ClassInstanceWrapper> instanceWrapper = new HashMap<ClassInstance,ClassInstanceWrapper>();
    private Map<ClassInstance, ClassifierSemanticsWrapper> semanticsWrapper = new HashMap<ClassInstance,ClassifierSemanticsWrapper>();
    private cmofimpl.javamapping.JavaMapping javaMapping = new cmofimpl.javamapping.JavaMapping();
    
    public BootstrapExtent(BootstrapModel model) {
        super("Bootstrap",false);
        this.model = model;
        model.setExtent(this);
        objects = new HashMap<ClassInstance,ObjectImpl>();
        for(ClassInstance<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> instance: model.getInstances()) {
            ClassInstanceWrapper instanceWrapper = model.getWrapper(instance);
            objects.put(instanceWrapper, createObject(instanceWrapper,model.getSemanticsWrapper(instance.getClassifier()),this));
        }
    }
    
    private String getName(ClassInstance<ClassInstance,ClassInstance,Object> instance) {
        ClassifierSemanticsWrapper semantics = model.getSemanticsWrapper(instance.getClassifier());        
        return instance.get(semantics.getSemantics().getProperty("name")).getValues().get(0).asDataValue().getValue().toString();
    }
    
    private String getClassName(ClassInstance classifier) {
        String className = javaMapping.getJavaIdentifierForName(getName(classifier));
        while (classifier.getComposite() != null) {
            classifier = classifier.getComposite();
            className = javaMapping.getJavaIdentifierForName(getName(classifier)) + "." + className;
        }
        return className;
    }
    
    private Map<ClassInstance, Collection<ClassInstance>> superClassifiers = new HashMap<ClassInstance, Collection<ClassInstance>>();
    private Collection<ClassInstance> getSuperClassifier(ClassInstance classifier) {
        
        Collection<ClassInstance> result = superClassifiers.get(classifier);
        if (result == null) {
            result = new Vector<ClassInstance>();
            result.add(classifier);
            for(ClassInstance superClass: ((BootstrapSemantics)model.createBootstrapSemantics(classifier)).superClasses(classifier)) {                
                result.addAll(getSuperClassifier(superClass));
            }
            superClassifiers.put(classifier, result);
        }
        return result;
    }
    
    private Collection<ClassInstance> classifierWithNoDelegator = new HashSet<ClassInstance>();
    private ObjectImpl createObject(ClassInstanceWrapper instance, ClassifierSemantics<Property,String> semantics, ExtentImpl extent) {
        ClassInstance classifier = instance.getInstance().getClassifier();
        String className = getClassName(classifier);
        
        ObjectImpl result = null;
        try {
            result = ((FactoryImpl)extent.getFactory()).createAnObjectImplInstance(className + "Impl", instance);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new RuntimeException("assert");
        }
        result.setIsStatic(true);
        result.setSemantics(semantics);
                            
        CreateDelegator: for(ClassInstance superClassifier: getSuperClassifier(classifier)) {
            if (classifierWithNoDelegator.contains(superClassifier)) {
                continue CreateDelegator;
            }
            ObjectImpl delegate = null;
            try {
                delegate = ((FactoryImpl)extent.getFactory()).createAnObjectImplInstance(getClassName(superClassifier) + "Custom", instance);
                delegate.setSemantics(semantics);
            } catch (ClassNotFoundException ex) {
                classifierWithNoDelegator.add(superClassifier);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
            if (delegate != null) {
                result.addDelegate(delegate);
            }
        }
        
        return result;
    }
    
    public ReflectiveCollection<cmof.reflection.Object> getObject() {
        return new SetImpl<cmof.reflection.Object>((Collection)objects.values());
    }

    public ReflectiveCollection<cmof.reflection.Object> objectsOfType(UmlClass type,
            boolean includeSubtypes) {
        if (type == null) {
            return getObject();
        } else {
            throw new RuntimeException("assert");
        }
    }
        
    protected ObjectImpl getObjectForInstance(ClassInstance<UmlClass,Property,java.lang.Object> instanceWrapper) {
        return objects.get(instanceWrapper);
    }
        
}
