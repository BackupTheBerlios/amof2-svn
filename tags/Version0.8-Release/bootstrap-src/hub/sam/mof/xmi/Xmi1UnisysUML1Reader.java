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

package hub.sam.mof.xmi;

import hub.sam.mof.mofinstancemodel.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.reflection.*;

import org.jdom.*;
import org.jdom.Element;
import org.jdom.input.*;

import cmof.*;
import cmof.reflection.Extent;

import java.util.*;

public class Xmi1UnisysUML1Reader {

    private final InstanceModel<XmiClassifier,String,String> model; 
    private final static String XMI_ROOT_ELEMENT = "XMI";
    private String actualNamespacePrefix = null;
    
    public Xmi1UnisysUML1Reader(InstanceModel<XmiClassifier,String,String> model) {
        this.model = model;
    }
    
    public void read(java.io.File file) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        Document document = new SAXBuilder().build(file);                
        Element xmi = document.getRootElement();
        if (!xmi.getName().equals(XMI_ROOT_ELEMENT)) {
            throw new XmiException("Unexpected root element \"" + xmi.getName() + "\"");
        }
        
        for (Object oChild: xmi.getChildren()) {
            if (oChild instanceof Element) {
                if (((Element)oChild).getName().equals("XMI.content")) {
                    for (Object ooChild: ((Element)oChild).getChildren()) {
                        if (ooChild instanceof Element) {
                            Element child = (Element)ooChild;
                            readInstance(child);
                        }
                    }
                }
            }
        }  
    }
    
    public void readInstance(Element child) throws XmiException, MetaModelException {        
        String id = child.getAttributeValue("xmi.id");
        actualNamespacePrefix = child.getNamespacePrefix();        
        ClassInstance<XmiClassifier,String,String> instance = model.createInstance(id, 
                new XmiClassifier(child.getName(), actualNamespacePrefix));        
        readContentForInstance(child, instance);
    }
                
    public void readContentForInstance(Element element, ClassInstance<XmiClassifier,String,String> instance) throws XmiException, MetaModelException {
        for(Object oAttr: element.getAttributes()) {
            Attribute attr = (Attribute)oAttr;            
            if (!attr.getName().equals("xmi.id")) {               
                String name = attr.getName();
                String value = attr.getValue();
                instance.addValue(name, model.createUnspecifiedValue(value));
            }
        }
        for (Object attrChild: element.getChildren()) {
            if (attrChild instanceof Element) {
                Element attrElement = (Element)attrChild;
                String[] fullAttrName = attrElement.getName().split("\\.");
                String attrName = fullAttrName[fullAttrName.length - 1];
                for (Object typeChild: attrElement.getChildren()) {
                    if (typeChild instanceof Element) {
                        Element typeElement = (Element)typeChild;
                        String name = attrName;
                        String namespace = attrElement.getNamespacePrefix();
                        if (!namespace.equals(actualNamespacePrefix)) {
                            throw new XmiException("Unexpected namespace prefix \"" + attrElement.getNamespacePrefix() + 
                                    "\" for element \"" + name + "\"");
                        }
                        String id = typeElement.getAttributeValue("xmi.id");
                        String[] fullType = typeElement.getName().split("\\.");
                        String type = fullType[fullType.length - 1];
                        if (typeElement.getAttributes().size() + typeElement.getChildren().size() == 0) {
                            instance.addValue(attrName, model.createUnspecifiedValue(typeElement.getText()));
                        } else {         
                            XmiClassifier elementForChild = new XmiClassifier(type, actualNamespacePrefix);                                         
                            InstanceValue<XmiClassifier,String,String> value = 
                                    model.createInstanceValue(model.createInstance(id, elementForChild));                            
                            readContentForInstance(typeElement, value.getInstance());
                            instance.addValue(name, value);
                            value.getInstance().setComposite(instance);
                        }                      
                    }
                }
            }
        }
    }        
        
    public static void readMofXmi(java.io.File file, Extent extent, cmof.Package m2) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        InstanceModel<XmiClassifier,String,String> xmiModel = new InstanceModel<XmiClassifier,String,String>();
        new Xmi1UnisysUML1Reader(xmiModel).read(file);
        XmiUnisysUML1ToMOF2 transformator = new XmiUnisysUML1ToMOF2(xmiModel); 
        for (ClassInstance<XmiClassifier,String,String> instance: xmiModel.getOutermostComposites()) {
            if (instance.getClassifier().getName().equals("Model")) {
                transformator.transform(hub.sam.mof.xmi.mopa.XmiMopaTreeNode.createNode(instance));
            }
        }
        for (ClassInstance<XmiClassifier,String,String> topLevelElement: transformator.getTopLevelElements()) {
            topLevelElement.setComposite(null);
        }
        List<ClassInstance<XmiClassifier,String,String>> toDelete = new Vector();
        for (ClassInstance<XmiClassifier,String,String> outermostComposite: xmiModel.getOutermostComposites()) {
            if (!transformator.getTopLevelElements().contains(outermostComposite)) {                
                toDelete.add(outermostComposite);                
            }
        }
        for (ClassInstance<XmiClassifier,String,String> instance: toDelete) {
            instance.delete();
        }
        
        XmiToCMOF conversion = new XmiToCMOF(extent, m2);
        InstanceModel<UmlClass,Property,Object> mofModel = ((ExtentImpl)extent).getModel();
        new Converter<XmiClassifier,String,String, UmlClass, Property, Type, DataType, java.lang.Object>(conversion).
                convert(xmiModel, mofModel);
        FactoryImpl factory = (FactoryImpl)extent.getFactory();
        for (ClassInstance<UmlClass,Property,Object> instance: mofModel.getInstances()) {            
            factory.create(instance);
        }
        
        for (Object e: extent.objectsOfType(null, false)) {
            if (e instanceof Property) {
                Property property = (Property)e;
                if (property.getDetails() != null) {
                    evaluatePropertyString(property.getDetails(), property);
                }
            }
        }
    }
    
    private static void evaluatePropertyString(String spec, Property property) throws XmiException {
        String[] details;
        if (spec.contains(",")) {
            details = spec.split(",");
        } else {
            details = new String[] { spec };
        }
        for (String detail: details) {
            detail = detail.trim();
            if (detail.equals("union")) {
                property.setIsUnique(true);
            } else if (detail.equals("derived union")) {
                property.setIsDerivedUnion(true);
            } else if (detail.startsWith("subsets")) {
                property.getSubsettedProperty().addAll(derivePropertiesFromPropertyString(detail, property));
            } else if (detail.equals("ordered")) {
                property.setIsOrdered(true);
            } else if (detail.startsWith("redefines")) {
                property.getRedefinedProperty().addAll(derivePropertiesFromPropertyString(detail, property));
            } else {
                throw new XmiException("unknown property string: " + detail);
            }
        }
    }
    
    private static Collection<Property> derivePropertiesFromPropertyString(String propertyString, Property property) throws XmiException {
        String[] words;
        Collection<Property> result = new Vector<Property>();
        if (propertyString.contains(" ")) {
            words = propertyString.split(" ");
        } else {
            words = new String[] { propertyString };
        }
        if (words.length == 1) {
            throw new XmiException("badly formated property string: " + propertyString);
        } else {
            for (int i = 1; i < words.length; i++) {
                result.add(resolvePropertyName(words[i], property));
            }
        }
        return result;
    }
    
    private static Property resolvePropertyName(String name, Property property) throws XmiException {
        if (property.getOwner() instanceof UmlClass) {
            for (Property aProperty: new MofClassSemantics((UmlClass)property.getOwner()).getProperties()) {
                if (name.equals(aProperty.getName())) {
                    return aProperty;
                }                 
            }
            throw new XmiException("property " + name + " could not be resolved");
        } else if (property.getOwner() instanceof Association) {
            for (Property aProperty: new MofClassSemantics((UmlClass)property.getOpposite().getType()).getProperties()) {
                if (name.equals(aProperty.getName())) {
                    return aProperty;
                }          
            }
            throw new XmiException("property " + name + " could not be resolved");
        } else {
            throw new XmiException("property string in invalid context: " + property.getOwner());
        }
    }
}
