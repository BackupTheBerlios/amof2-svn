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
import java.io.*;
import cmof.*;
import cmof.common.*;
import cmof.reflection.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.mofinstancemodel.*;
import hub.sam.mof.reflection.query.*;
import hub.sam.mof.util.*;
import hub.sam.util.*;

public class ExtentImpl implements cmof.reflection.Extent {
    private ReflectiveCollection<cmof.reflection.Object> objects;
	private ReflectiveCollection<cmof.reflection.Object> outermostComposits;
    private final Map<ClassInstance<UmlClass,Property,java.lang.Object>, cmof.reflection.Object> objectForInstance = new HashMap<ClassInstance<UmlClass,Property,java.lang.Object>, cmof.reflection.Object>();
    private final Map<cmof.reflection.Object, ClassInstance<UmlClass,Property,java.lang.Object>> instanceForObject = new HashMap<cmof.reflection.Object, ClassInstance<UmlClass,Property,java.lang.Object>>();
    private final MultiMap<UmlClass, cmof.reflection.Object> objectsForTypes = new MultiMap<UmlClass, cmof.reflection.Object>();
    private final MultiMap<UmlClass, cmof.reflection.Object> objectsForTypesWithSubtypes = new MultiMap<UmlClass, cmof.reflection.Object>();
    
    protected final InstanceModel<UmlClass,Property,java.lang.Object> model = new MofInstanceModel();//TODO
    
    private ExtentImpl(boolean bootstrap, String path) {      
        objects = new SetImpl<cmof.reflection.Object>();
		outermostComposits = new SetImpl<cmof.reflection.Object>();      
    }       
    
    public ExtentImpl(boolean bootstrap) {
        this(bootstrap, null);
    }
    
    public ExtentImpl(String path) {
        this(false, path);        
    }
    
    public ExtentImpl() {
        this(false, null);        
    }
    
    public InstanceModel<UmlClass,Property,java.lang.Object> getModel() {
        return model;
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
    
    protected cmof.reflection.Object getObjectForInstance(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        return objectForInstance.get(instance);
    }
    
    protected final ValueSpecification<UmlClass,Property,java.lang.Object> specificationForValue(java.lang.Object value) {
        if (value == null) {
            return null;//TODO
        }
        if (value instanceof cmof.reflection.Object) {
            cmof.reflection.Object object = (cmof.reflection.Object)value;
            if (!objectsOfType(null, true).contains(object)) {
                throw new IllegalArgumentException();
            }
            return model.createInstanceValue(instanceForObject.get(value));        
        } else if (value instanceof Integer || value instanceof Long || value instanceof Boolean || value instanceof String || value instanceof Enum) {
            return model.createPrimitiveValue(value);
        } else {            
            throw new RuntimeException("not implemented value type " + value.getClass().getCanonicalName());
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
        
        @Override
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
        ReflectiveCollection<? extends cmof.reflection.Object> result = null;
        if (type == null) {
            return objects;
        } else {            
            result = new SetImpl<cmof.reflection.Object>((includeSubtypes) ? objectsForTypes.get(type) : objectsForTypesWithSubtypes.get(type));                               
        }
        return result;
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
            ((ObjectImpl)object).setExtent(this);
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
            objectsForTypes.put(metaClass, object);
            objectsForTypesWithSubtypes.put(metaClass, object);
            if (!((ObjectImpl)metaClass).isStatic) {
                ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> superClasses = metaClass.allParents();
                for (core.abstractions.umlsuper.Classifier superClass: superClasses) {
                    if (superClass instanceof UmlClass) {
                        objectsForTypesWithSubtypes.put((UmlClass)superClass, object);
                    }
                }
            }            
        }
		
        this.objects.add(object);
        object.setExtent(this);
        if (object.container() == null) {
            this.outermostComposits.add(object);
        }		
    }       
    
    protected void removeObject(cmof.reflection.Object object, ClassInstance<UmlClass,Property,java.lang.Object> instance) {        
        if (!objects.remove(object)) {
            throw new RuntimeException("assert");
        }
        instanceForObject.remove(object);
        objectForInstance.remove(instance);
        if (object.getMetaClass() != null) {
            objectsForTypes.removeValue(object);
            objectsForTypesWithSubtypes.removeValue(object);
        }
        outermostComposits.remove(object);
        instance.delete();
    }
        
    public static void writeStaticModel(String fileName, String packageName, String className, Extent extent) throws IOException {
       new File(fileName.substring(0,fileName.lastIndexOf("/"))).mkdir(); 
    	PrintWriter out = new PrintWriter(new File(fileName));
        if (packageName != null) {
            out.println("package " + packageName + ";");
        }
        out.println("public class " + className + "{");
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
        out.println("    public static " + Extent.class.getCanonicalName() + " createModel() {");
        out.println("        " + ExtentImpl.class.getCanonicalName() + " extent = new " + ExtentImpl.class.getCanonicalName() + "(true);");
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
	
	public cmof.reflection.Object query(String queryString) throws cmof.exception.QueryParseException {
        Query query = null;
        try {
            query = Query.createQuery(queryString);
        } catch (ParseException ex) {
            throw new cmof.exception.QueryParseException(ex.getMessage());
        }
		cmof.reflection.Object result = null;
		for (cmof.reflection.Object outermost: getObject()) {
			result = query.evaluate(outermost);
			if (result != null) {
				return result;
			}
		}
		return result;
	}
    
    protected boolean contains(cmof.reflection.Object object) {
        return objects.contains(object);
    }
    
    @Override
	public void finalize() {
        for (cmof.reflection.Object obj: objects) {
            if (obj instanceof ObjectImpl) {
                ((ObjectImpl)obj).finalize();
            }
        }
        model.finalize();
    }
}
