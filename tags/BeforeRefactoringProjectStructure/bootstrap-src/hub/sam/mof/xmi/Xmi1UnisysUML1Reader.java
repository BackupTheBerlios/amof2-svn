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
import org.jdom.input.SAXBuilder;

import cmof.*;
import cmof.reflection.Extent;

import java.util.*;

public class Xmi1UnisysUML1Reader {

    private final InstanceModel<XmiClassifier,String,String> model; 
    private final static String XMI_ROOT_ELEMENT = "XMI";
    private String actualNamespacePrefix = null;
    
    public Xmi1UnisysUML1Reader(InstanceModel<XmiClassifier,String,String> model) {
        super();
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
        // remember the original contents
        Map<String, Classifier> classifiers = new HashMap<String, Classifier>();
        Map<String, Type> types = new HashMap<String, Type>();
        Map<String, cmof.NamedElement> elements = new HashMap<String, cmof.NamedElement>();
        Collection<cmof.NamedElement> elementsToRemove = new HashSet<cmof.NamedElement>();
        for (cmof.reflection.Object obj: extent.objectsOfType(null, true)) {
            if ( obj instanceof cmof.Package || obj instanceof cmof.UmlClass) {                
                elements.put(((cmof.NamedElement)obj).getQualifiedName(), (cmof.NamedElement)obj);
            }
            if (obj instanceof Classifier) {
                classifiers.put(((Classifier)obj).getQualifiedName(), (Classifier)obj);
            } 
            if (obj instanceof Type) {
                types.put(((Type)obj).getQualifiedName(), (Type)obj);
            }
        }
        
        // read xmi 1
        InstanceModel<XmiClassifier,String,String> xmiModel = new InstanceModel<XmiClassifier,String,String>();
        new Xmi1UnisysUML1Reader(xmiModel).read(file);
        
        // transform UnisysUml to MOF 2
        XmiUnisysUML1ToMOF2 transformator = new XmiUnisysUML1ToMOF2(xmiModel); 
        for (ClassInstance<XmiClassifier,String,String> instance: xmiModel.getOutermostComposites()) {
            if (instance.getClassifier().getName().equals("Model")) {
                transformator.transform(hub.sam.mof.xmi.mopa.XmiMopaTreeNode.createNode(instance));
            }
        }
        for (ClassInstance<XmiClassifier,String,String> topLevelElement: transformator.getTopLevelElements()) {
            topLevelElement.setComposite(null);
        }
        List<ClassInstance<XmiClassifier,String,String>> toDelete = new Vector<ClassInstance<XmiClassifier,String,String>>();
        for (ClassInstance<XmiClassifier,String,String> outermostComposite: xmiModel.getOutermostComposites()) {
            if (!transformator.getTopLevelElements().contains(outermostComposite)) {                
                toDelete.add(outermostComposite);                
            }
        }
        for (ClassInstance<XmiClassifier,String,String> instance: toDelete) {
            instance.delete();
        }
                
        // convert MOF 2 XMI to MOF 2 InstanceModel
        XmiToCMOF conversion = new XmiToCMOF(extent, m2);
        InstanceModel<UmlClass,Property,Object> mofModel = ((ExtentImpl)extent).getModel();
        new Converter<XmiClassifier,String,String, UmlClass, Property, Type, DataType, java.lang.Object>(conversion).
                convert(xmiModel, mofModel);
        FactoryImpl factory = FactoryImpl.createFactory(extent, m2);
        for (ClassInstance<UmlClass,Property,Object> instance: mofModel.getInstances()) {            
            factory.create(instance);
        }
        
        // resolve library skeletons and replace them by the real ones based on the knowledge gathered before                
        loop: for (cmof.reflection.Object obj: extent.objectsOfType(null, true)) {
            if (obj instanceof cmof.Package || obj instanceof cmof.UmlClass) {
                cmof.NamedElement element = (cmof.NamedElement)obj;
                String qualifiedName = element.getQualifiedName();
                if (elements.get(qualifiedName) != null) {
                    if (!elements.get(qualifiedName).equals(element)) {
                        elementsToRemove.add(element); // scedule reread element for removal
                    }
                    continue loop; // ignore all prior existing or reread elements                    
                } 
            }
            if (obj instanceof Classifier) {
                Classifier classifier = (Classifier)obj;
                for (Classifier superClassifier: new hub.sam.mof.util.SetImpl<Classifier>(classifier.getGeneral())) {
                    String qualifiedName = superClassifier.getQualifiedName();                    
                    if (classifiers.get(qualifiedName) != null) {
                        if (classifier instanceof UmlClass) {
                            ((UmlClass)classifier).getSuperClass().remove(superClassifier);
                            ((UmlClass)classifier).getSuperClass().add(classifiers.get(qualifiedName));
                        } else {
                            throw new RuntimeException("not implemented");
                        }
                    }
                }
            }
            if (obj instanceof TypedElement) {
                TypedElement typedElement = (TypedElement)obj;
                if (typedElement.getType() != null) {                                   
                    if (types.get(typedElement.getType().getQualifiedName()) != null) {
                        typedElement.setType(types.get(typedElement.getType().getQualifiedName()));
                    }
                }
            }
        }
        
        // remove reread elements
        for (cmof.NamedElement element: new HashSet<cmof.NamedElement>(elementsToRemove)) { 
            for (Object obj: ((cmof.reflection.Object)element).getComponents()) {
                elementsToRemove.remove(obj);
            }
        }
        for (Object obj: elementsToRemove) {
            ((cmof.reflection.Object)obj).delete();
        }        
        
        // evaluate property constrains like subsets, redefines, etc.
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
            MofClassSemantics semantics = new MofClassSemantics((UmlClass)property.getOwner());
            for (Property aProperty: semantics.getProperties()) {
                if (name.equals(aProperty.getName()) && !aProperty.equals(property)) {
                    return aProperty;
                }                 
            }
            // it still could be the name of an unnavigable association end            
            for (Property aProperty: semantics.getNotNavigablePropertys(name)) {
                if (!aProperty.equals(property)) {
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
