package cmofimpl.reflection;

import java.util.*;
import cmof.*;
import cmof.common.ReflectiveSequence;
import cmof.reflection.*;
import cmofimpl.instancemodel.*;

public class FactoryImpl extends ObjectImpl implements Factory {
    
    private final ExtentImpl extent;
    private final boolean bootstrap;
    private final cmofimpl.javamapping.JavaMapping javaMapping = new cmofimpl.javamapping.JavaMapping();
    private String path = null;
    private cmof.Package metaModel = null;
    
    protected FactoryImpl(ExtentImpl extent, boolean bootstrap) {
        super(null, null);
        this.extent = extent;
        this.bootstrap = bootstrap;
    }
    
    protected FactoryImpl(ExtentImpl extent, String path) {    
        this(extent, (path == null));        
        this.path = path;
    }
    
    protected FactoryImpl(ExtentImpl extent, cmof.Package metaModel) {
        super(null, null);
        this.extent = extent;
        this.bootstrap = false;
        this.path = null;
        this.metaModel = metaModel;
    }
    
    public static FactoryImpl createFactory(ExtentImpl extent, cmof.Package forPackage) {
        cmofimpl.javamapping.JavaMapping javaMapping = new cmofimpl.javamapping.JavaMapping();
        String className = javaMapping.getFullQualifiedImplFactoryNameForPackage(forPackage);
              
        ObjectImpl object = null;       
        try {                    
            java.lang.Class implementation = Thread.currentThread().getContextClassLoader().loadClass(className);  
            java.lang.reflect.Constructor constructor = implementation.getConstructor(new java.lang.Class[] {ExtentImpl.class, cmof.Package.class });
            object = (ObjectImpl) constructor.newInstance(extent, forPackage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return (FactoryImpl)object;
    }
    
    public java.lang.Object createFromString(DataType dataType, String string) {
        try {
        if (dataType instanceof PrimitiveType) {
            if (dataType.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                return string;
            } else if (dataType.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return new Boolean(string);
            } else if (dataType.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                return new Integer(string);
            } else if (dataType.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                if (string.equals("*")) {
                    return new Long(-1);
                } else {
                    return new Long(string);
                }
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }    
        } catch (Exception ex) {
            // some kind of parse exception
            if (ex instanceof IllegalArgumentException) {
                throw (RuntimeException)ex;
            } else {
                throw new IllegalArgumentException(ex);
            }
        }
    }

    public String convertToString(DataType dataType, java.lang.Object value) {
        return null;
    }

    private Map<UmlClass, ObjectImpl> objectMasterCopys = new HashMap<UmlClass, ObjectImpl>();    
    
    private Map<String, UmlClass> umlClassForName = new HashMap<String, UmlClass>();
    
    protected cmof.reflection.Object create(String metaClassName) {
        UmlClass metaClass = umlClassForName.get(metaClassName);        
        if (metaClass == null) {
            if (metaModel != null) {
                loop: for (cmof.Element element: metaModel.getMember()) {
                    if (element instanceof UmlClass) {
                        if (((UmlClass)element).getQualifiedName().equals(metaClassName)) {
                            metaClass = (UmlClass)element;
                            break loop;
                        }
                    }
                }
            }
        }
        if (metaClass == null) {
            throw new RuntimeException("Class not found");
        } else {
            return create(metaClass);
        }
    }
    
    public cmof.reflection.Object create(UmlClass metaClass) {       
        ObjectImpl masterCopy = objectMasterCopys.get(metaClass);        
        if (masterCopy == null) {
            ClassInstanceImpl instance = new ClassInstanceImpl(extent, metaClass);            
            if (bootstrap) {            
                ObjectImpl object = new ObjectImpl(instance, extent);                            
                masterCopy = object;
            } else  {
                java.lang.Class implementation = null;
                String javaClassName = null;
                if (path == null) { 
                    javaClassName = javaMapping.getImplClassNameForMetaClass(metaClass);
                } else {
                    javaClassName = path + "." + javaMapping.getJavaIdentifierForName(metaClass.getName()) + "Impl";
                }
                try {                
                    masterCopy = createAnObjectImplInstance(javaClassName, instance);
                } catch (Exception ex) {
                    throw new IllegalArgumentException(ex);
                }                                            
            }
            initializeAttributesWithDefaultValues(masterCopy, metaClass);
            initializeInstanceWithDelegates(masterCopy, metaClass, instance);
            objectMasterCopys.put(metaClass, masterCopy);
            
        }
        ObjectImpl result = null;
        try {
            result = masterCopy.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace(System.out);
            throw new RuntimeException(ex);
        }
        extent.addObject(result);
        return result;
    }
    
    private ObjectImpl createAnObjectImplInstance(String className, ClassInstanceImpl instance) throws Exception {
        java.lang.Class implementation = null;        
        implementation = Thread.currentThread().getContextClassLoader().loadClass(className);                
        ObjectImpl object = null;        
        java.lang.reflect.Constructor constructor = implementation.getConstructor(new java.lang.Class[] {ClassInstanceImpl.class, ExtentImpl.class });
        object = (ObjectImpl) constructor.newInstance(instance, extent);        
        return object;
    }
    
    private void initializeInstanceWithDelegates(ObjectImpl object, UmlClass metaClass, ClassInstanceImpl instance) {
        Collection<UmlClass> allMetaClasses = new HashSet<UmlClass>();
        collectAllSuperClasses(metaClass, allMetaClasses);
        for(UmlClass umlClass: allMetaClasses) {
            ObjectImpl delegate = null;
            try {
                delegate = createAnObjectImplInstance(javaMapping.getCustomClassNameForMetaClass(umlClass), instance);
                delegate.isDelegate = true;
            } catch (ClassNotFoundException ex) {
                
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }
            if (delegate != null) {
                object.addDelegate(delegate);
            }
        }
    }
    
    private void initializeAttributesWithDefaultValues(ObjectImpl object, UmlClass metaClass) {
        for (core.abstractions.elements.Element member: metaClass.getMember()) {
            if (member instanceof Property) {
                Property property = (Property)member;
                Type type = property.getType();
                String defaultValue = collectDefaultValue(property);
                if (type instanceof cmof.DataType && defaultValue != null) {
                    object.set(property, createFromString((DataType)type, defaultValue));
                }
            }
        }
    }
    
    private String collectDefaultValue(Property property) {
        String result = property.getDefault();
        if (result != null) {
            return result;
        } else {
            for (Property redefinedProperty: property.getRedefinedProperty()) {
                result = collectDefaultValue(redefinedProperty);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }        
    }
    
    private void collectAllSuperClasses(UmlClass umlClass, Collection<UmlClass> superClasses) {
        superClasses.add(umlClass);
        for (UmlClass superClass: umlClass.getSuperClass()) {
            collectAllSuperClasses(superClass, superClasses);
        }
    }

    public cmof.reflection.Package getPackage() {
        return null;
    }

    public cmof.reflection.Object createObject(UmlClass umlClass, ReflectiveSequence<Argument> arguments) {
        return null;
    }

    public cmof.reflection.Link createLink(Association association, cmof.reflection.Object firstObject, cmof.reflection.Object secondObject) {
        return null;
    }
}