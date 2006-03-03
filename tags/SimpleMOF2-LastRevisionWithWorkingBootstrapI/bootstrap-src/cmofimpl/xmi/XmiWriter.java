package cmofimpl.xmi;

import org.jdom.*;
import org.jdom.output.*;
import cmof.*;
import cmof.common.ReflectiveCollection;
import cmofimpl.reflection.*;

public class XmiWriter {

    private final DefaultJDOMFactory jdom;
    private final Document document;
    private final org.jdom.Namespace xmi;
    private org.jdom.Element rootElement;
    private final XmiModelFacade model;
    private java.util.Collection<String> writtenIds = new java.util.HashSet<String>();
    private java.util.Collection<String> referencedIds = new java.util.HashSet<String>();

    public XmiWriter(cmof.reflection.Extent metaModel) {        
        this(new XmiModelFacade(metaModel));        
    }
    
    public XmiWriter(cmof.Package metaModel) {
        this(new XmiModelFacade(metaModel));        
    }
    
    private XmiWriter(XmiModelFacade model) {
        this.model = model;    
        this.jdom = new DefaultJDOMFactory();
        this.xmi = org.jdom.Namespace.getNamespace("xmi", "http://www.omg.org/XMI");
        rootElement = jdom.element("XMI", xmi);
        document = jdom.document(rootElement);
        
        rootElement.addNamespaceDeclaration(xmi);
        for (org.jdom.Namespace ns: model.getNamespaces()) {
            rootElement.addNamespaceDeclaration(ns);
        }        
    }
    
    public void writeXmi(ReflectiveCollection<? extends cmof.reflection.Object> elements) {
        for(cmof.reflection.Object element: elements) {
            writeXmi((cmofimpl.reflection.ObjectImpl)element);
        }
        if (!writtenIds.containsAll(referencedIds)) {
            System.out.println("WARNING: written xmi references elements not written");
        }
    }
    
    private void writeXmi(cmofimpl.reflection.ObjectImpl object) {                
        org.jdom.Element element = jdom.element(model.getXmiNameForMetaClass(object.getMetaClass()), model.getXmiNamespaceForMetaClass(object.getMetaClass()));
        rootElement.addContent(element);
        writtenIds.add(object.getId().toString());
        element.setAttribute("id", object.getId().toString(), xmi);       
        writeObjectContents(element, object, null);
    }
    
    /**
     * Checks wheathe a value should be exported as value of the given property. Due to the subsetting of propertys,
     * it is possible that a single value is value for multiple propertys. But it should only be exported once.
     * The method returns true if the given value is only stored in the given property and in subsetted propertys of
     * that property within the given instance (object).
     */
    private boolean exportValueOfProperty(java.lang.Object value, cmof.Property property, ObjectImpl object) {
        java.util.Collection<Property> allSubsets = object.getSemantics().getSupersettedProperties(property);
        for (Property subset: allSubsets) {
            java.lang.Object valueOfSubset = object.get(subset);
            if (valueOfSubset instanceof ReflectiveCollection) {
                if (((ReflectiveCollection)valueOfSubset).contains(value)) {
                    return false;
                }
            } else {
                if (valueOfSubset == value) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void writeObjectContents(org.jdom.Element element, ObjectImpl object, java.lang.Object allReadyExported) {
        for (Property property: object.getSemantics().getFinalProperties()) {
            if (property.isDerived()) {
                
            } else if (object.get(property) == null) {
                
            } else if (property.getType() instanceof DataType) {
                if (property.getUpper() == 1) {                    
                    element.setAttribute(property.getName(), object.get(property).toString());
                } else {
                    for(java.lang.Object value: (ReflectiveCollection)object.get(property)) {
                        if (exportValueOfProperty(value, property, object) && value != allReadyExported) {
                            org.jdom.Element elementForProperty = jdom.element(property.getName());
                            elementForProperty.addContent(jdom.cdata(value.toString()));
                            element.addContent(elementForProperty);
                        }
                    }
                }
            } else if (property.isComposite()) {
                Object valuesAsObject = object.get(property);
                ReflectiveCollection values = null;
                if (valuesAsObject instanceof ReflectiveCollection) {
                    values = (ReflectiveCollection)valuesAsObject;
                } else {
                    values = new cmofimpl.util.SetImpl<Object>();
                    values.add(valuesAsObject);
                }
                for (Object valueAsObject: values) {
                    if (exportValueOfProperty(valueAsObject, property, object) && valueAsObject != allReadyExported) {
                        ObjectImpl value = (ObjectImpl)valueAsObject;
                        org.jdom.Element elementForProperty = jdom.element(property.getName());                              
                        if (value.getMetaClass() != property.getType()) {
                            elementForProperty.setAttribute("type", model.getTagNameForMetaClass(value.getMetaClass()), xmi);
                        }                         
                        elementForProperty.setAttribute("id", value.getId().toString(), xmi);                    
                        writtenIds.add(object.getId().toString());
                        writeObjectContents(elementForProperty, value, object);
                        element.addContent(elementForProperty);
                    }
                }
            } else {
                String attributeForPropertyName = property.getName();
                String attributeForPropertyValue = null;                
                Object value = object.get(property);
                if (value instanceof ReflectiveCollection) {
                   String references = "";
                   for (Object singleValue: (ReflectiveCollection)value) {
                       if (exportValueOfProperty(singleValue, property, object) && singleValue != allReadyExported) {
                           references += ((ObjectImpl)singleValue).getId().toString() + " ";
                           referencedIds.add(((ObjectImpl)singleValue).getId().toString());
                       }
                   }
                   if (references.trim() != "") {
                       attributeForPropertyValue = references.trim();
                   }
                } else {
                   if (exportValueOfProperty(value, property, object) && value != allReadyExported) {
                       attributeForPropertyValue = ((ObjectImpl)value).getId().toString();                   
                       referencedIds.add(((ObjectImpl)value).getId().toString());
                   }
                }
                if (attributeForPropertyValue != null) {
                    element.setAttribute(attributeForPropertyName, attributeForPropertyValue);
                }
            }
        }
    }
    
    public void toFile(java.io.FileOutputStream out) throws java.io.IOException {                
        new XMLOutputter(Format.getPrettyFormat()).output(document, out);        
    }
}
