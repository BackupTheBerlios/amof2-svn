/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package hub.sam.mof.reflection;

import java.util.*;
import cmof.*;
import cmof.common.ReflectiveSequence;
import cmof.reflection.*;
import hub.sam.mof.instancemodel.*;

public class FactoryImpl extends ObjectImpl implements Factory {
    
    private final ExtentImpl extent;
    private final boolean bootstrap;
    private final hub.sam.mof.javamapping.JavaMapping javaMapping = hub.sam.mof.javamapping.JavaMapping.mapping;
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
        hub.sam.mof.javamapping.JavaMapping javaMapping = hub.sam.mof.javamapping.JavaMapping.mapping;
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
    
    // TODO
    public static java.lang.Object staticCreateFromString(DataType dataType, String string) {
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
            } else if (dataType instanceof cmof.Enumeration) {
                for (cmof.EnumerationLiteral literal: ((cmof.Enumeration)dataType).getOwnedLiteral()) {
                    if (literal.getName().equals(string)) {
                        return string;
                    }
                }
                throw new IllegalArgumentException("Unrecognized enum value");
            } else {
                throw new IllegalArgumentException();
            }    
        } catch (Exception ex) {
            // some kind of parse exception
            if (ex instanceof IllegalArgumentException) {
                throw (RuntimeException) ex;
            } else {
                throw new IllegalArgumentException(ex);
            }
        }
    }
    
    public java.lang.Object createFromString(DataType dataType, String string) {
        return staticCreateFromString(dataType, string);
    }

    public String convertToString(DataType dataType, java.lang.Object value) {
        return null;
    }

    private Map<UmlClass, ObjectImpl> objectMasterCopys = new HashMap<UmlClass, ObjectImpl>();    
    
    private Map<String, UmlClass> umlClassForName = new HashMap<String, UmlClass>();
    
    /**
     * Creates on object for a given meta-class. Uses {@link create(UmlClass)}.
     */
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
    
    /**
     * Creates an object based on existing instance. Uses the meta-class to construct java implementation class names. 
     * Uses {@link createAnObjectImplInstance}.
     */
    private ObjectImpl createWithOutExtent(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        ObjectImpl result;
        if (bootstrap) {            
            result = new ObjectImpl(instance, extent);                                        
        } else  {
            java.lang.Class implementation = null;
            String javaClassName = null;
            if (path == null) { 
                javaClassName = javaMapping.getImplClassNameForMetaClass(instance.getClassifier());
            } else {
                javaClassName = path + "." + javaMapping.getJavaIdentifierForName(instance.getClassifier().getName()) + "Impl";
            }
            try {                
                result = createAnObjectImplInstance(javaClassName, instance);
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex);
            }                                            
        }        
        initializeInstanceWithDelegates(result, instance.getClassifier(), instance);
        return result;
    }
    
    /**
     * Creates an object for an existing instance. Is based on {@link createWithOutExtent}.
     */
    public cmof.reflection.Object create(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        ObjectImpl result = createWithOutExtent(instance);
        extent.addObject(result);
        return result;
    }
    
    /**
     * Creates a hole new instance and object arround it. Uses {@link createWithOutExtent}.
     */
    public cmof.reflection.Object create(UmlClass metaClass) {   
        if (metaClass == null) {
            throw new NullPointerException();
        }

        ClassInstance<UmlClass,Property,java.lang.Object> instance = extent.model.createInstance(null,metaClass);            
        ObjectImpl result = createWithOutExtent(instance);           
        
        extent.addObject(result);
        return result;
    }
    
    /**
     * Creates an object based on an existing instance and a given java class name.
     */
    public ObjectImpl createAnObjectImplInstance(String className, ClassInstance<UmlClass,Property,java.lang.Object> instance) throws Exception {
        java.lang.Class implementation = null;        
        implementation = Thread.currentThread().getContextClassLoader().loadClass(className);                
        ObjectImpl object = null;        
        java.lang.reflect.Constructor constructor = implementation.getConstructor(new java.lang.Class[] {hub.sam.mof.instancemodel.ClassInstance.class, ExtentImpl.class });//TODO
        object = (ObjectImpl) constructor.newInstance(instance, extent);        
        return object;
    }
    
    private void initializeInstanceWithDelegates(ObjectImpl object, UmlClass metaClass, ClassInstance<UmlClass,Property,java.lang.Object> instance) {
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
