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
import cmof.reflection.*;
import java.lang.Object;
import hub.sam.mof.instancemodel.conversion.*;
import hub.sam.mof.reflection.*;

import hub.sam.mof.instancemodel.*;
import org.jdom.*;
import org.jdom.Namespace;
import org.jdom.Element;
import org.jdom.input.*;

public class Xmi2Reader {
    private final InstanceModel<XmiClassifier,String,String> model;
    private Namespace xmiNamespace = null;
    private Namespace xsiNamespace = null;
    private final static String XMI_ROOT_ELEMENT = "XMI";
    private String actualNamespacePrefix = null;
    
    public Xmi2Reader(InstanceModel<XmiClassifier,String,String> model) {
        super();
        this.model = model;
    }
    
    public void read(java.io.File file) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        Document document = new SAXBuilder().build(file);                
        Element xmi = document.getRootElement();
        if (!xmi.getName().equals(XMI_ROOT_ELEMENT)) {
            throw new XmiException("Unexpected root element \"" + xmi.getName() + "\"");
        }
        xmiNamespace = xmi.getNamespace();
        for (Object oNs: xmi.getAdditionalNamespaces()) {
            Namespace namespace = (Namespace)oNs;
            if (namespace.getPrefix().equals("xmi")) {
                xmiNamespace = namespace;
            } else if (namespace.getPrefix().equals("xsi")) {
                xsiNamespace = namespace; 
            }
        } 
        if (xsiNamespace == null) {
            xsiNamespace = xmiNamespace; // TODO
        }
        if (xmiNamespace == null) {
            xmiNamespace = Namespace.NO_NAMESPACE;
        }
        if (xsiNamespace == null) {
            xsiNamespace = Namespace.NO_NAMESPACE;
        }

        for (Object oChild: xmi.getChildren()) {
            if (oChild instanceof Element) {
                Element child = (Element)oChild;
                readInstance(child);
            }
        }  
    }
    
    public void readInstance(Element child) throws XmiException, MetaModelException {        
        String id = child.getAttributeValue("id", xmiNamespace);
        actualNamespacePrefix = child.getNamespacePrefix();        
        ClassInstance<XmiClassifier,String,String> instance = model.createInstance(id, 
                new XmiClassifier(child.getName(), actualNamespacePrefix));        
        readContentForInstance(child, instance);
    }
                
    public void readContentForInstance(Element element, ClassInstance<XmiClassifier,String,String> instance) throws XmiException, MetaModelException {        
        for(Object oAttr: element.getAttributes()) {
            Attribute attr = (Attribute)oAttr;
            String namespace = attr.getNamespacePrefix();
            if (namespace.equals("")) {
                String name = attr.getName();
                String value = attr.getValue();
                instance.addValue(name, model.createUnspecifiedValue(value));
            }
        }
        for (Object oChild: element.getChildren()) {
            Element child = (Element)oChild;
            String name = child.getName();
            String namespace = child.getNamespacePrefix();
            if (!namespace.equals("")) {
                throw new XmiException("Unexpected namespace prefix \"" + child.getNamespacePrefix() + 
                        "\" for element \"" + name + "\"");
            }
            String id = child.getAttributeValue("id", xmiNamespace);             
            String type = child.getAttributeValue("type", xsiNamespace);
            String idref = child.getAttributeValue("idref", xmiNamespace);
            if (child.getAttributes().size() + child.getChildren().size() == 0) {
                instance.addValue(child.getName(), model.createUnspecifiedValue(child.getText()));
            } else if (idref == null) {         
                InstanceValue<XmiClassifier,String,String> value = null;
                XmiClassifier elementForChild;
                String nsPrefix = null;
                if (type == null) {
                    elementForChild = new XmiClassifier(instance.getClassifier(), name);
                } else {
                    if (type.contains(":")) {
                        String[] typeElements = type.split(":");
                        if (typeElements.length != 2) {
                            throw new XmiException("badly formatted type identifier: " + type);
                        }
                        nsPrefix = typeElements[0];
                        type = typeElements[1];
                        elementForChild = new XmiClassifier(type, nsPrefix);
                    } else {
                        elementForChild = new XmiClassifier(type, actualNamespacePrefix);
                    }
                }                
                InstanceValue<XmiClassifier,String,String> instanceValue = 
                        model.createInstanceValue(model.createInstance(id, elementForChild));
                value = instanceValue;   
                
                String oldNsPrefix = actualNamespacePrefix;
                if (nsPrefix != null) {
                    actualNamespacePrefix = nsPrefix;
                }
                readContentForInstance(child, instanceValue.getInstance());
                actualNamespacePrefix = oldNsPrefix;
                
                instance.addValue(name, value);
                value.getInstance().setComposite(instance);
            } else {                
                for(ReferenceValue<XmiClassifier,String,String> ref: model.createReferences(idref)) {
                    instance.addValue(name, ref);
                }
            }                     
        }
    }        
    
    /**
     * Reads XMI with a m1 model of the given m2 in an extent. The extent must be based on a MofInstanceModel.
     */
    public static void readMofXmi(java.io.File file, Extent extent, cmof.Package m2) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        InstanceModel<XmiClassifier,String,String> xmiModel = new InstanceModel<XmiClassifier,String,String>();
        Xmi2Reader reader = new Xmi2Reader(xmiModel);
        reader.read(file);
        XmiToCMOF conversion = new XmiToCMOF(extent, m2);
        InstanceModel<UmlClass,Property,Object> mofModel = ((ExtentImpl)extent).getModel();
        new Converter<XmiClassifier,String,String, UmlClass, Property, Type, DataType, java.lang.Object>(conversion).
                convert(xmiModel, mofModel);
        FactoryImpl factory = FactoryImpl.createFactory(extent, m2);
        for (ClassInstance<UmlClass,Property,Object> instance: mofModel.getInstances()) {            
            factory.create(instance);
        }
    }
    
}
