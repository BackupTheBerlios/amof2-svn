package cmofimpl.reflection;

import java.lang.reflect.Method;
import java.util.*;
import cmof.exception.IllegalArgumentException;
import cmof.common.*;
import cmof.reflection.*;
import cmof.*;
import cmofimpl.util.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.ValueSpecification;

public class ObjectImpl implements cmof.reflection.Object, Cloneable {
	protected boolean isStatic; //TODO
	
    private ClassInstance<UmlClass,Property,java.lang.Object> instance;
    private ClassifierSemantics<Property,String> semantics;
    private Identifier id;
    private final ExtentImpl extent;        
    private final cmofimpl.javamapping.JavaMapping javaMapping = new cmofimpl.javamapping.JavaMapping();
    private java.util.Collection<ObjectImpl> delegates = new HashSet<ObjectImpl>();
    protected boolean isDelegate = false;
        
    protected ObjectImpl(ClassInstance<UmlClass,Property,java.lang.Object> instance, ExtentImpl extent) {
        ClassifierSemantics<Property,String> semantics = null;
        if (instance instanceof cmofimpl.instancemodel.MofClassInstance) {
            this.semantics = ((cmofimpl.instancemodel.MofClassInstance)instance).getInstanceClassifier(); //TODO
        }
        this.instance = instance;
        this.extent = extent;
        this.id = Identifier.getUniqueIdentifier();    
        this.metaId = null;
        this.implementingClassName = null;
		this.isStatic = false;
    }    
            
    protected ObjectImpl clone() throws CloneNotSupportedException {
        ObjectImpl copy = (ObjectImpl) super.clone();
        if (!isDelegate) {                
            copy.instance = this.instance.clone();
        }
        copy.delegates = new java.util.Vector<ObjectImpl>();
        for (ObjectImpl delegate : this.delegates) {
            ObjectImpl delegateClone = delegate.clone();
            delegateClone.instance = copy.instance;
            copy.addDelegate(delegateClone);
        }
        copy.id = Identifier.getUniqueIdentifier();
        return copy;        
    }
    
    public void addDelegate(ObjectImpl delegate) {
        delegates.add(delegate);
        delegate.isDelegate = true;
    }        
    
    public Identifier getId() {
        return id;
    }    
      
    public cmof.UmlClass getMetaClass() {
		if (isStatic) {
			return null;
		} else {
			return instance.getClassifier();
		}
    }
    
    public ClassifierSemantics<Property,String> getSemantics() {
        return semantics;
    }
    
    public void setSemantics(ClassifierSemantics<Property,String> semantics) {
        this.semantics = semantics;
    }
    
    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
    
    public ClassInstance<UmlClass,Property,java.lang.Object> getClassInstance() {
        return instance;
    }

    public java.lang.Object get(String propertyName) {
        if (attributes != null) {
            return valueForStaticValue(attributes.get(propertyName));
        }
        Property property = semantics.getProperty(propertyName);
        if (property == null) {
            throw new cmof.exception.IllegalArgumentException(propertyName);
        } else {
            return get(property);
        }
    }
    
    public java.lang.Object get(Property property) throws IllegalArgumentException {
        if (attributes != null) {
            return valueForStaticValue(attributes.get(property.getName()));
        }
        for (ObjectImpl delegate: delegates) {
            Method customImpl = getCustomImplementation(property, delegate);
            if (customImpl != null) {
                java.lang.Object returnValue = null;
                try {
                    returnValue = customImpl.invoke(delegate, new java.lang.Object[]{});
                } catch (Exception ex) {
                    ex.printStackTrace(System.out);
                    throw new IllegalArgumentException(ex);
                }
                return returnValue;
            }
        }
        
        ValueSpecificationList<UmlClass,Property,java.lang.Object> values = instance.get(property).getValuesAsList();
        if (!semantics.isCollectionProperty(property)) {
            if (values.size() == 0) {
                return null;
            } else {
                return extent.valueForSpecification(values.get(0));
            }
        } else {
            return extent.valuesForSpecificationList(values);
        }             
    }
    
    public void set(String propertyName, java.lang.Object value) {
        Property property = semantics.getProperty(propertyName);
        if (property == null) {
            throw new IllegalArgumentException(propertyName);
        } else {
            set(property, value);
        }
    }
    
    private boolean typeCheckValue(java.lang.Object value, cmof.Type type) {
        if (value == null) {
            return true;
        }
        if (type instanceof cmof.PrimitiveType) {
            if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                return value instanceof String;
            } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                return value instanceof Integer;
            } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return value instanceof Boolean;
            } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                return value instanceof Long;
            } else {
                throw new RuntimeException("assert");
            }
        } else if (type instanceof cmof.UmlClass) {
            if (value instanceof cmof.reflection.Object) {
                //return ((cmof.reflection.Object)value).getMetaClass().equals(type);
                return ((cmof.reflection.Object)value).isInstanceOfType((cmof.UmlClass)type, true);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
      
    public void set(Property property, java.lang.Object value) {
        if (instance == null) {
            throw new RuntimeException("Static modelelements cant be changed");
        }
        if (property.getUpper() == 1) {
            if (value instanceof ReflectiveCollection) {
                throw new IllegalArgumentException(value);
            }
            if (!typeCheckValue(value, property.getType())) {
                throw new IllegalArgumentException(value);
            }
            ReflectiveSequence<? extends ValueSpecification<UmlClass,Property,java.lang.Object>> values = instance.get(property).getValuesAsList();
            if (values.size() == 0) {
                values.add(0, extent.specificationForValue(value));
            } else {
                values.set(0, extent.specificationForValue(value));    
            }            
        } else {
            throw new IllegalArgumentException(property);
        }
    }

    public boolean isSet(Property property) {      
        if (instance == null) {
            return attributes.get(property) != null;
        } else {
            return instance.get(property).getValuesAsList().size() != 0;
        }
    }

    public void unset(Property property) { 
        if (instance == null) {
            throw new RuntimeException("Static modelelements cant be changed");
        }
        instance.get(property).getValuesAsList().removeAll(new cmofimpl.util.SetImpl<ValueSpecification<UmlClass,Property,java.lang.Object>>(instance.get(property).getValuesAsList()));
    }

    public int hashCode() {
        if (instance != null) {
            return instance.hashCode();    
        } else {
            return id.hashCode();
        }        
    }
    public boolean equals(java.lang.Object element) {
        if (element instanceof ObjectImpl) {
            if (instance != null) {
                return instance.equals(((ObjectImpl)element).instance) && element.getClass().equals(this.getClass());
            } else {
                return id.equals(((ObjectImpl)element).id) && element.getClass().equals(this.getClass());
            }
        } else {
            return false;
        }        
    }
    
    public void delete() {          
        if (instance == null || isStatic) {
            throw new RuntimeException("Static modelelements cant be changed");
        }
        extent.removeObject(this, getClassInstance());                
    }

    public java.lang.Object invokeOperation(cmof.Operation op, ReflectiveSequence<Argument> arguments) {
        throw new RuntimeException("not implemented");
    }
    
    public boolean isInstanceOfType(cmof.UmlClass type, boolean includeSubTypes) {
        ReflectiveCollection<cmof.UmlClass> allPossibleTypes = new SetImpl<cmof.UmlClass>();
        allPossibleTypes.add(getMetaClass());
        if (includeSubTypes) {
            collectSuperClass(getMetaClass(), allPossibleTypes);
        }
        return allPossibleTypes.contains(type);
    }
    
    private void collectSuperClass(cmof.UmlClass forUmlClass, ReflectiveCollection<cmof.UmlClass> superClasses) {
        superClasses.add(forUmlClass);
        for (cmof.UmlClass umlClass: forUmlClass.getSuperClass()) {
            collectSuperClass(umlClass, superClasses);
        }
    }
    
    public String toString() {
        String result = "";
        try {
            result =  (String)get("name");
        } catch (Throwable e) {
            return super.toString();
        }
        result += "(" + hashCode() + ")";
        return result;
    }
    
    private Method getCustomImplementation(Property forFeature, ObjectImpl delegate) {
        Property boundFeature = semantics.getFinalProperty(forFeature);
        Method result = null;
        if (boundFeature instanceof Property) {
            try {
                result = delegate.getClass().getMethod(semantics.getJavaGetMethodNameForProperty((Property)boundFeature), new java.lang.Class[]{});
                if (result.getDeclaringClass() == delegate.getClass()) {
                    return result;
                } else {
                    return null;
                }
            } catch (NoSuchMethodException ex) {
                return null;
            }
        } else {
            return null; // TBD
        }
    }

    public Extent getExtent() {
        return extent;
    }
    
    public cmof.reflection.Object container() {
        if (isStatic || !(instance instanceof cmofimpl.instancemodel.MofClassInstance)) {
			return null;
        } else {
			return extent.getObjectForInstance(((cmofimpl.instancemodel.MofClassInstance)instance).getComposite());
        }
	}
	
	public ReflectiveCollection<cmof.reflection.Object> getComponents() {
        if (!(instance instanceof cmofimpl.instancemodel.MofClassInstance)) {
            return new ListImpl<cmof.reflection.Object>();
        } else {
    		ReflectiveCollection<ClassInstance<UmlClass,Property,java.lang.Object>> contents = 
    				new cmofimpl.util.SetImpl<ClassInstance<UmlClass,Property,java.lang.Object>>(((cmofimpl.instancemodel.MofClassInstance)instance).getComponents());
    		ReflectiveCollection<cmof.reflection.Object> result = 
    				new cmofimpl.util.ListImpl<cmof.reflection.Object>();
    		for (ClassInstance<UmlClass,Property,java.lang.Object> instance: contents) {
    			result.add(extent.getObjectForInstance(instance));
    		}
    		return result;
        }
	}

    public cmof.reflection.Object getOutermostContainer() {
        cmof.reflection.Object iterator = this;
        cmof.reflection.Object actualComposite = iterator.container();
        while (actualComposite != null) {
            iterator = actualComposite;
            actualComposite = actualComposite.container();
        }
        return iterator;
    }

    // the static part  
    private Map<String, java.lang.Object> attributes = null; 
    private final String implementingClassName;
    private final Identifier metaId;
    private String[] delegateClassNames = null;
    
    private static Map<Identifier, ObjectImpl> objectForId = new HashMap<Identifier, ObjectImpl>();
    
    private static ObjectImpl objectForIdentifier(Identifier id) {
        return objectForId.get(id);
    }
    
    protected static ObjectImpl createFromObject(ObjectImpl object) {
        String[] delegateClassNames = new String[object.delegates.size()]; int index = 0;
        for (ObjectImpl delegate: object.delegates) {
            delegateClassNames[index] = delegate.getClass().getCanonicalName();
            index++;
        }        
        ObjectImpl result = new ObjectImpl(object.getId(), null, object.getClass().getCanonicalName(), delegateClassNames);
        cmof.UmlClass metaClass = object.getMetaClass();
        for (Property property: object.semantics.getFinalProperties()) {
            String propertyName = object.semantics.getName(property);
            java.lang.Object value = object.get(propertyName);
            if (value instanceof cmof.common.ReflectiveCollection) {
                cmof.common.ReflectiveCollection<java.lang.Object> valueSet = (cmof.common.ReflectiveCollection)value;                
                java.lang.Object[] values = new java.lang.Object[valueSet.size()];
                java.util.Iterator<java.lang.Object> valueSetIterator = valueSet.iterator();
                for(int i = 0; i < valueSet.size(); i++) {
                    values[i] = staticValueForObject(valueSetIterator.next());
                }
                result.putAttribute(propertyName, values);
            } else {
                result.putAttribute(propertyName, staticValueForObject(value));
            }
        }
        return result;
    }
    
    protected ObjectImpl(Identifier id, Identifier metaId, String implementingClassName, String[] delegateClassNames) {
        isStatic = true;
		attributes = new HashMap<String, java.lang.Object>();
        this.delegateClassNames = delegateClassNames;
        extent = null;
        instance = null;
        this.id = id;
        this.metaId = metaId;
        this.implementingClassName = implementingClassName;        
        if (delegateClassNames == null) {
            isDelegate = true;
        } else {
            objectForId.put(id, this);
            try {
                for (String delegateClassName: delegateClassNames) {
                    java.lang.Class implementation = null;        
                    implementation = Thread.currentThread().getContextClassLoader().loadClass(delegateClassName);                
                    ObjectImpl object = null;        
                    java.lang.reflect.Constructor constructor = implementation.getConstructor(
                            new java.lang.Class[] {Identifier.class, Identifier.class, String.class, String[].class});
                    object = (ObjectImpl) constructor.newInstance(id, metaId, implementingClassName, null);
                    addDelegate(object);
                }
            } catch (Exception e) {
                throw new RuntimeException("assert");
            }
        }
    }        
    
    public void putAttribute(String name, java.lang.Object values) {
        attributes.put(name, values);
    }
    
    private String serializeValue(java.lang.Object value) {        
        if (value == null) {
            return null;
        } if (value instanceof Integer) {
            return "new Integer(" + value + ")";
        } else if (value instanceof Long) {
            return "new Long(" + value + ")";
        } else if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Boolean) {
            return "new Boolean(" + value + ")";
        } else if (value.getClass().isEnum()) {
            return value.getClass().getCanonicalName() + "." + value;
        } else if (value instanceof Identifier) {
            return "new " + Identifier.class.getCanonicalName() + "(" + value + ")";
        } else {
            throw new RuntimeException("assert");
        }
    }
    
    public String serialize() {
        String delegateClassNamesString = "new String[]{"; boolean first = true;        
        for (String delegate: delegateClassNames) {
            delegateClassNamesString += (first ? "" : ", ") + "\"" + delegate + "\""; first = false; 
        }
        delegateClassNamesString += "}";
        StringBuffer result = new StringBuffer();
        if (metaId == null) {
            result.append("object = new " + implementingClassName + "(new " + Identifier.class.getCanonicalName() + "(" + id + "), null, \"" + implementingClassName + "\", " + delegateClassNamesString + ");");
        } else {
            result.append("object = new " + implementingClassName + "(new " + Identifier.class.getCanonicalName() + "(" + id + "), new " + Identifier.class.getCanonicalName() + "(" + metaId + "), \"" + implementingClassName + "\", " + delegateClassNamesString + ");");
        }
        for (String attrName: attributes.keySet()) {
            result.append("((" + ObjectImpl.class.getCanonicalName() + ")object).putAttribute(\"" + attrName + "\", ");
            java.lang.Object value = attributes.get(attrName);
            if (value instanceof java.lang.Object[]) {                            
                result.append("new java.lang.Object[] {");
                java.lang.Object[] values = (java.lang.Object[])value;
                int index = 0;
                for (java.lang.Object itera: values) {
                    result.append(serializeValue(itera));
                    index++;
                    if (index < values.length) {
                        result.append(",");
                    }
                }
                result.append("}");
            } else {
                result.append(serializeValue(value));
            }
            result.append(");");
        }        
        return result.toString();
    }
    
    static class StaticValueList implements cmof.common.ReflectiveSequence {
        final java.util.List<java.lang.Object> values;
                
        StaticValueList(java.lang.Object[] values) {
            this.values = java.util.Arrays.asList(values);
            
        }
        
        public java.lang.Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }
        
        public java.lang.Object get(int index) {
            return valueForStaticValue(values.get(index));
        }

        public void set(int index, java.lang.Object element) {
            throw new IllegalArgumentException("This object is static.");
        }

        public void add(int index, java.lang.Object element) {
            throw new IllegalArgumentException("This object is static.");
        }

        public void addAll(int index, Iterable elements) {
            throw new IllegalArgumentException("This object is static.");
        }

        public void remove(int index) {
            throw new IllegalArgumentException("This object is static.");
        }

        public ReflectiveSequence subList(int from, int to) {
            throw new IllegalArgumentException("This object is static.");            
        }

        public boolean add(java.lang.Object element) {
            throw new IllegalArgumentException("This object is static.");
        }

        public boolean contains(java.lang.Object element) {
            return values.contains(staticValueForObject(element));
        }

        public boolean remove(java.lang.Object element) {
            throw new IllegalArgumentException("This object is static.");
        }

        public boolean addAll(Iterable elements) {
            throw new IllegalArgumentException("This object is static.");
        }

        public boolean containsAll(Iterable elements) {
            boolean result = false;
            for(java.lang.Object element: elements) {
                result = values.contains(staticValueForObject(element)) && result;
            }
            return result;
        }

        public boolean removeAll(Iterable elements) {
            throw new IllegalArgumentException("This object is static.");
        }

        public int size() {
            return values.size();
        }
        
        class Iterator implements java.util.Iterator {
            final java.util.Iterator<java.lang.Object> values;
            Iterator(java.util.Iterator<java.lang.Object> values) {
                this.values = values;
            }
            
            public boolean hasNext() {
                return values.hasNext();
            }

            public java.lang.Object next() {
                return valueForStaticValue(values.next());
            }

            public void remove() {
                throw new IllegalArgumentException("This object is static.");
            }           
        }

        public java.util.Iterator iterator() {
            return new Iterator(values.iterator());
        }       
    
        public String toString() {
            return values.toString();
        }
    }

    private static java.lang.Object valueForStaticValue(java.lang.Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Identifier) {
            return objectForIdentifier((Identifier)o);
        } else if (o instanceof java.lang.Object[]) {
            return new StaticValueList((java.lang.Object[])o);
        } else {
            return o;
        }
    }
    
    private static java.lang.Object staticValueForObject(java.lang.Object o) {
        if (o instanceof ObjectImpl) {
            return ((ObjectImpl)o).getId();
        } else {
            return o;
        }
    }

}
