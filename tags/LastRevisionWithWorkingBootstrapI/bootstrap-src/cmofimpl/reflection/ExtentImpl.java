package cmofimpl.reflection;

import java.util.*;
import java.io.*;
import cmof.*;
import cmof.common.*;
import cmof.reflection.*;
import cmofimpl.util.*;
import cmofimpl.reflection.query.*;
import cmofimpl.instancemodel.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.ValueSpecification;

public class ExtentImpl extends PackageImpl implements cmof.reflection.Extent {
    private ReflectiveCollection<cmofimpl.reflection.ObjectImpl> objects;
	private ReflectiveCollection<cmof.reflection.Object> outermostComposits;
    private final boolean bootstrap;
    private final Map<ClassInstance<UmlClass,Property,java.lang.Object>, ObjectImpl> objectForInstance = new HashMap<ClassInstance<UmlClass,Property,java.lang.Object>, ObjectImpl>();
    private final Map<ObjectImpl, ClassInstance<UmlClass,Property,java.lang.Object>> instanceForObject = new HashMap<ObjectImpl, ClassInstance<UmlClass,Property,java.lang.Object>>();
    private final Map<UmlClass, ReflectiveCollection<ObjectImpl>> objectsForTypes = new HashMap<UmlClass, ReflectiveCollection<ObjectImpl>>();
    private final Map<UmlClass, ReflectiveCollection<ObjectImpl>> objectsForTypesWithSuper = new HashMap<UmlClass, ReflectiveCollection<ObjectImpl>>();
    private final String path = null;
    private final FactoryImpl factory;
    protected final InstanceModel<UmlClass,Property,java.lang.Object> model = new MofInstanceModel();//TODO
    
    private ExtentImpl(String name, boolean bootstrap, String path) {
        super(name);        
        objects = new SetImpl<cmofimpl.reflection.ObjectImpl>();
		outermostComposits = new SetImpl<cmof.reflection.Object>();
        this.bootstrap = bootstrap;

            if (path == null) {
            this.factory = new FactoryImpl(this, bootstrap);
        } else {
            this.factory = new FactoryImpl(this, path);
        } 
        
    }       
    
    public ExtentImpl(String name, boolean bootstrap) {
        this(name, bootstrap, null);
    }
    
    public ExtentImpl(String name, String path) {
        this(name, false, path);        
    }
    
    public ExtentImpl(String name) {
        this(name, false, null);        
    }

    public Factory getFactory() {
        return factory;
    }
   
    protected final java.lang.Object valueForSpecification(ValueSpecification<UmlClass,Property,java.lang.Object> spec) {
        if (spec.asDataValue() != null) {            
            return spec.asDataValue().getValue();           
        } else if (spec.asInstanceValue() != null) {
            if (getObjectForInstance(spec.asInstanceValue().getInstance())==null) {
                throw new NullPointerException();
            }
            return getObjectForInstance(spec.asInstanceValue().getInstance());
        } else {
            throw new RuntimeException("assert");
        }
    }
    
    protected ObjectImpl getObjectForInstance(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        return objectForInstance.get(instance);
    }
    
    protected final ValueSpecification<UmlClass,Property,java.lang.Object> specificationForValue(java.lang.Object value) {
        if (value instanceof cmofimpl.reflection.ObjectImpl) {
            cmofimpl.reflection.ObjectImpl object = (cmofimpl.reflection.ObjectImpl)value;
            if (!objectsOfType(null, true).contains(object)) {
                throw new IllegalArgumentException();
            }
            return model.createInstanceValue(instanceForObject.get((ObjectImpl)value));        
        } else if (value instanceof Integer || value instanceof Long || value instanceof Boolean || value instanceof String || value instanceof Enum) {
            return model.createPrimitiveValue(value);
        } else {
            throw new RuntimeException("not implemented");
        }        
    }
    
    protected final ReflectiveSequence<java.lang.Object> valuesForSpecificationList(ValueSpecificationList<UmlClass,Property,java.lang.Object> list) {
        return new ValueList(list);        
    }
    
    class ValueList implements cmof.common.ReflectiveSequence<java.lang.Object> {
        private final ReflectiveSequence<ValueSpecification<UmlClass,Property,java.lang.Object>> valueSpecs;
                
        private ValueList(ReflectiveSequence<ValueSpecification<UmlClass,Property,java.lang.Object>> valueSpecs) {
            this.valueSpecs = valueSpecs;
        }
        
        public ValueList clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }
        public java.lang.Object get(int index) {
            return valueForSpecification(valueSpecs.get(index));
        }

        public void set(int index, java.lang.Object element) {
            valueSpecs.set(index, specificationForValue(element));
        }

        public void add(int index, java.lang.Object element) {
            valueSpecs.add(index, specificationForValue(element));
        }

        
        public void remove(int index) {
            valueSpecs.remove(index);
        }

       
        public ReflectiveSequence<java.lang.Object> subList(int from, int to) {
            return new ValueList(valueSpecs.subList(from, to));            
        }

        public boolean add(java.lang.Object element) {
            return valueSpecs.add(specificationForValue(element));
        }

        public boolean contains(java.lang.Object element) {
            return valueSpecs.contains(specificationForValue(element));
        }

        public boolean remove(java.lang.Object element) {
            return valueSpecs.remove(specificationForValue(element));
        }
        
        class SpecificationIterable implements Iterable<java.lang.Object>, Iterator<java.lang.Object> {
            private final Iterator<? extends java.lang.Object> values;
            
            SpecificationIterable(Iterable<? extends java.lang.Object> values) {
                this.values = values.iterator();
            }
            
            public Iterator<java.lang.Object> iterator() {
                return this;
            }

            public boolean hasNext() {
                return values.hasNext();
            }

            public ValueSpecification<UmlClass,Property,java.lang.Object> next() {
                return specificationForValue(values.next());
            }

            public void remove() {
                values.remove();
            }            
        }

        public boolean addAll(Iterable<? extends java.lang.Object> elements) {
            return valueSpecs.addAll(new SpecificationIterable(elements));
        }

        public boolean containsAll(Iterable<? extends java.lang.Object> elements) {
            return valueSpecs.containsAll(new SpecificationIterable(elements));
        }

        public boolean removeAll(Iterable<? extends java.lang.Object> elements) {
            return valueSpecs.removeAll(new SpecificationIterable(elements));
        }
        
        class ValueIterator implements Iterator<java.lang.Object> {
            private Iterator<ValueSpecification<UmlClass,Property,java.lang.Object>> specIterator = valueSpecs.iterator();
            
            public boolean hasNext() {
                return specIterator.hasNext();
            }

            public java.lang.Object next() {
                return valueForSpecification(specIterator.next());
            }

            public void remove() {
                specIterator.remove();
            }
            
        }

        public Iterator<java.lang.Object> iterator() {
            return new ValueIterator();
        }

        public void addAll(int index, Iterable<? extends java.lang.Object> elements) {
            valueSpecs.addAll(index, new SpecificationIterable(elements));
        }

        public int size() {
            return valueSpecs.size();
        }  
        
        public String toString() {
            return valueSpecs.toString();
        }

        public java.util.List<java.lang.Object> toJavaUtil() {
            java.util.List<java.lang.Object> result = new java.util.Vector<java.lang.Object>();
            for (java.lang.Object o: this) {
                result.add(o);
            }
            return Collections.unmodifiableList(result);           
        }
        
        
    }
    
    public ReflectiveCollection<? extends cmof.reflection.Object> objectsOfType(UmlClass type, boolean includeSubtypes) {   
        if (type == null) {
            return objects; //TBD
        } else {
            ReflectiveCollection<? extends cmof.reflection.Object> result = objectsForTypes.get(type);
            if (result == null) {
                throw new NullPointerException();
            }
            return objectsForTypes.get(type);        
        }
    }

    public ReflectiveCollection<cmof.reflection.Link> linksOfType(Association type) {
        return null;
    }

    public ReflectiveCollection<cmof.reflection.Object> linkedObjects(Association association, cmof.reflection.Object endObject, boolean end1to2direction) {
        return null;
    }

    public boolean linkExists(Association association, cmof.reflection.Object firstObject, cmof.reflection.Object secondObject) {
        return false;
    }
	
	public void addObject(cmof.reflection.Object object) {
		if (!(object instanceof ObjectImpl)) {
			objects.add(object);
			return;
		} else if (!((ObjectImpl)object).isStatic) {
			addObject((ObjectImpl)object);	
		} else {
			objects.add(object);
		}
		if (object.container() == null) {
			this.outermostComposits.add(object);
		}
	}

    protected void addObject(ObjectImpl object) {
	    instanceForObject.put(object, object.getClassInstance());
		objectForInstance.put(object.getClassInstance(), object);
		UmlClass metaClass = object.getMetaClass();
		if (metaClass != null) {
			ReflectiveCollection<ObjectImpl> objectss = objectsForTypes
					.get(metaClass);
			if (objectss == null) {
				objectss = new SetImpl<ObjectImpl>();
				objectsForTypes.put(metaClass, objects);
			}
			objectss.add(object);
			// TBD objectsForTypeWithSuper;
			}
		
        this.objects.add(object);
        if (object.container() == null) {
            this.outermostComposits.add(object);
        }		
    }       
    
    protected void removeObject(ObjectImpl object, ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        objects.remove(object);
        instanceForObject.remove(object);
        objectForInstance.remove(instance);
        if (object.getMetaClass() != null) {
            objectsForTypes.get(object.getMetaClass()).remove(object);
        }
        instance.delete();
    }
        
    public static void writeStaticModel(String fileName, String packageName, Extent extent) throws IOException {
        PrintWriter out = new PrintWriter(new File(fileName));
        if (packageName != null) {
            out.println("package " + packageName + ";");
        }
        out.println("public class " + extent.getName() + "{");
        int index = 0;
        for (java.lang.Object o: extent.objectsOfType(null, true)) {
            if (o instanceof ObjectImpl) {
                out.println("    public static " + cmof.reflection.Object.class.getCanonicalName() + " getObject" + index + "() {");
                out.println("        " + cmof.reflection.Object.class.getCanonicalName() + " " + ObjectImpl.createFromObject((ObjectImpl)o).serialize());
                out.println("        return object;");
                out.println("    }");
                index ++;
            }
        }
        out.println("    public static " + Extent.class.getCanonicalName() + " createModel(String name) {");
        out.println("        " + ExtentImpl.class.getCanonicalName() + " extent = new " + ExtentImpl.class.getCanonicalName() + "(name, true);");
        out.println("        " + cmof.reflection.Object.class.getCanonicalName() + " object = null;");
        index = 0;
        for (java.lang.Object o: extent.objectsOfType(null, true)) {
            if (o instanceof ObjectImpl) {                
                out.println("extent.addObject(getObject" + index + "());");
                index++;
            }            
        }        
        out.println("        return extent;");
        out.println("    }");
        out.println("}");
        out.close();
    }
	
	public ReflectiveCollection<cmof.reflection.Object> getObject() {
		return outermostComposits;
	}
	
	public cmof.reflection.Object query(String queryString) throws cmofimpl.reflection.query.ParseException {
		Query query = Query.createQuery(queryString);
		cmof.reflection.Object result = null;
		for (cmof.reflection.Object outermost: getObject()) {
			result = query.evaluate(outermost);
			if (result != null) {
				return result;
			}
		}
		return result;
	}
}
