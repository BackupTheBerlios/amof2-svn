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

import cmof.*;
import cmof.extension.Tag;
import cmof.reflection.*;

import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.conversion.Conversion;
import hub.sam.mof.util.SetImpl;
import hub.sam.mof.xmi.XmiClassifier;

import java.util.*;
import org.jdom.Namespace;

public class XmiToCMOF implements Conversion<XmiClassifier,String,String, UmlClass, Property, Type, DataType, java.lang.Object>{

    private Factory factory = null;
    private Map<UmlClass, Map<String,Property>> properties = new HashMap<UmlClass, Map<String,Property>>();    
    private Map<String, Map<String, UmlClass>> namespaces = new HashMap<String, Map<String, UmlClass>>();
    private Map<Element, Namespace > xmiNamespaces = new HashMap<Element, Namespace>();
    private cmof.common.ReflectiveCollection<? extends cmof.reflection.Object> allElements = new SetImpl<cmof.reflection.Object>();
        
    /**
     * The parameter extent is only used to retrieve a factory, which is only used to create primitive data from strings.
     */
    public XmiToCMOF(Extent extent, cmof.Package model) {
        factory = hub.sam.mof.reflection.FactoryImpl.createFactory(extent, model);
        collectAllElements(model);        
        initialize();
    }
    
    private void collectAllElements(cmof.Package thePackage) {
        for (Element e: thePackage.getOwnedElement()) {            
            allElements.add(e);
            if (e instanceof PackageImport) {
                PackageImport packageImport = (PackageImport)e;
                collectAllElements(packageImport.getImportedPackage());
            }
        }
    }
    
    private void initialize() {
        for (cmof.reflection.Object o: allElements) {
            if (o instanceof Tag) {
                Tag tag = (Tag)o;
                if (tag.getName().equals("org.omg.xmi.nsPrefix")) {
                    String prefix = tag.getValue();
                    for (Element taggedElement: tag.getElement()) {
                        xmiNamespaces.put(taggedElement, getNamespace(prefix));
                    }
                }
            }
        }
        
        for (cmof.reflection.Object o: allElements) {
            if (o instanceof UmlClass) {
                UmlClass umlClass = (UmlClass)o;
                if (!umlClass.isAbstract()) {
                    // find nsPrefix for class
                    Namespace xmiNamespace = null;
                    String prefix = null;
                    for (cmof.Namespace ns = umlClass; ns != null && xmiNamespace == null; ns = ns.getNamespace()) {
                        xmiNamespace = xmiNamespaces.get(ns);
                    }
 
                    if (xmiNamespace == null) {
                        prefix = "";
                    } else {
                        prefix = xmiNamespace.getPrefix();
                    }
                    Map<String, UmlClass> classForNames = namespaces.get(prefix);
                    if (classForNames == null) {
                        classForNames = new HashMap<String, UmlClass>();
                        namespaces.put(prefix, classForNames);
                    }
                    classForNames.put(umlClass.getName(), umlClass);
                    classForNames.put(umlClass.getQualifiedName(), umlClass);
                }
            }
        }        
    }
               
    private UmlClass getClassForTagName(String xmiName, String ns) {  
        Map<String, UmlClass> classForName = namespaces.get(ns);
        if (classForName == null) {
            return null;
        } else {
            if (classForName.get(xmiName) == null) {
                if (ns.equals("")) {
                    // look in all namespaces if ns not specified
                    int foundCount = 0;
                    UmlClass result = null;
                    for (String otherNs: namespaces.keySet()) {
                        classForName = namespaces.get(otherNs);
                        if (classForName.get(xmiName) != null) {
                            foundCount++; result = classForName.get(xmiName);
                        }
                    }
                    if (foundCount != 1) {
                        return null;
                    } else {
                        return result;
                    }
                } else {
                    return null;
                }
            } else {
                return classForName.get(xmiName);
            }
        }
    }
    
    private org.jdom.Namespace getNamespace(String prefix) {
        return org.jdom.Namespace.getNamespace(prefix, "http://www.abcdefguuh.de/URL");
    }
    
    public UmlClass getClassifier(XmiClassifier element) throws MetaModelException {
        if (element.isDefinedByContext()) {
            Type result = getPropertyType(getProperty(element.getContextProperty(),getClassifier(element.getContextClass())));
            if (result instanceof UmlClass) {
                if (new String("Parameter(14)").equals(result.toString())) {
                    System.out.println("#### HERE");
                }
                return (UmlClass)result;
            } else {
                throw new MetaModelException("Property \"" + element.getContextProperty() + "\" is used with wrong type.");
            }
        } else {
            UmlClass result = getClassForTagName(element.getName(), element.getNamespacePrefix());
            if (result == null) {
                throw new MetaModelException("Classifier \"" + element + "\" does not exist.");
            } else {
                if (new String("Parameter(14)").equals(result.toString())) {
                    System.out.println("#### HERE");
                }
                return result;
            }       
        }
    }

    public Property getProperty(String property, UmlClass classifier) throws MetaModelException {
        Map<String,Property> propertiesOfClassifier = properties.get(classifier);
        if (propertiesOfClassifier == null) {
            propertiesOfClassifier = new HashMap<String, Property>();
            for(cmof.NamedElement member: classifier.getMember()) {
                String name = member.getName();
                if (new String("Parameter(14)").equals(classifier.toString())) {
                    System.out.println("### Parameter" + classifier.getOwner() + ": " + member);
                }
                if (name != null && member instanceof cmof.Property) {                   
                    propertiesOfClassifier.put(name, (Property)member);
                }
            } 
            properties.put(classifier, propertiesOfClassifier);
        }
        Property result = propertiesOfClassifier.get(property);
        if (result == null) {
            throw new MetaModelException("Property \"" + property + "\" of classifier \"" + classifier.getQualifiedName() + "\" does not exist");
        } else {
            return result;
        }
    }

    public Type getPropertyType(Property property) throws MetaModelException {
        return property.getType();
    }

    public java.lang.Object createFromString(DataType type, String stringRepresentation) throws MetaModelException {
        java.lang.Object result = factory.createFromString(type, stringRepresentation);
        return result;
    }

    public DataType asDataType(Type type) {
        if (type instanceof DataType) {
            return (DataType)type;
        } else {
            return null;
        }
    }

    public boolean doConvert(ValueSpecificationImpl<XmiClassifier, String, String> value, StructureSlot<XmiClassifier, String, String> slot, ClassInstance<XmiClassifier, String, String> instance) {
        return true;       
    }         
}
