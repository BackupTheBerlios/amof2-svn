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

import cmof.DataType;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;
import cmof.reflection.Extent;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.InstanceValue;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.instancemodel.ReferenceValue;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.FactoryImpl;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

public class Xmi2Reader {
    private final InstanceModel<XmiClassifier, String, String> model;
    private Namespace xmiNamespace = null;
    private Namespace xsiNamespace = null;
    private static final String XMI_ROOT_ELEMENT = "XMI";
    private String actualNamespacePrefix = null;

    public Xmi2Reader(InstanceModel<XmiClassifier, String, String> model) {
        super();
        this.model = model;
    }

    public void read(java.io.File file) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        read(new SAXBuilder().build(file));
    }

    public void read(InputStream in) throws JDOMException, IOException, XmiException, MetaModelException {
        read(new SAXBuilder().build(in));
    }

    private void read(Document document) throws JDOMException, IOException, XmiException, MetaModelException {
        Element xmi = document.getRootElement();
        if (!xmi.getName().equals(XMI_ROOT_ELEMENT)) {
            throw new XmiException("Unexpected root element \"" + xmi.getName() + "\"");
        }
        xmiNamespace = xmi.getNamespace();
        for (Object oNs : xmi.getAdditionalNamespaces()) {
            Namespace namespace = (Namespace) oNs;
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

        for (Object oChild : xmi.getChildren()) {
            if (oChild instanceof Element) {
                Element child = (Element) oChild;
                readInstance(child);
            }
        }
    }

    public void readInstance(Element child) throws XmiException, MetaModelException {
        String id = child.getAttributeValue("id", xmiNamespace);
        actualNamespacePrefix = child.getNamespacePrefix();
        ClassInstance<XmiClassifier, String, String> instance = model.createInstance(id,
                new XmiClassifier(child.getName(), actualNamespacePrefix));
        readContentForInstance(child, instance);
    }

    public void readContentForInstance(Element element, ClassInstance<XmiClassifier, String, String> instance) throws XmiException, MetaModelException {
        for (Object oAttr : element.getAttributes()) {
            Attribute attr = (Attribute) oAttr;
            String namespace = attr.getNamespacePrefix();
            if (namespace.equals("")) {
                String name = attr.getName();
                String value = attr.getValue();
                instance.addValue(name, model.createUnspecifiedValue(value));
            }
        }
        for (Object oChild : element.getChildren()) {
            Element child = (Element) oChild;
            String name = child.getName();
            String propertyName = null;
            String namespace = child.getNamespacePrefix();
            if (namespace.equals("")) {
                namespace = null;
                //throw new XmiException("Unexpected namespace prefix \"" + child.getNamespacePrefix() +
                //        "\" for element \"" + name + "\"");
                //instance.getClassifier().setNamespacePrefix(namespace);
            }
            String id = child.getAttributeValue("id", xmiNamespace);
            String type = child.getAttributeValue("type", xsiNamespace);
            String idref = child.getAttributeValue("idref", xmiNamespace);
            if (child.getAttributes().size() + child.getChildren().size() == 0) {
                instance.addValue(child.getName(), model.createUnspecifiedValue(child.getText()));
            } else if (idref == null) {
                InstanceValue<XmiClassifier, String, String> value = null;
                XmiClassifier elementForChild;
                String nsPrefix = null;
                if (type == null) {
                    if (namespace != null) {
                        elementForChild = new XmiClassifier(name, namespace);
                    } else {
                        elementForChild = new XmiClassifier(instance.getClassifier(), name);
                        propertyName = name;
                    }
                } else {
                    propertyName = name;
                    if (type.contains(":")) {
                        String[] typeElements = type.split(":");
                        if (typeElements.length != 2) {
                            throw new XmiException("badly formatted type identifier: " + type);
                        }
                        nsPrefix = typeElements[0];
                        elementForChild = new XmiClassifier(typeElements[1], nsPrefix);
                    } else {
                        elementForChild = new XmiClassifier(type, actualNamespacePrefix);
                    }
                }
                InstanceValue<XmiClassifier, String, String> instanceValue =
                        model.createInstanceValue(model.createInstance(id, elementForChild));

                String oldNsPrefix = actualNamespacePrefix;
                if (nsPrefix != null) {
                    actualNamespacePrefix = nsPrefix;
                }
                readContentForInstance(child, instanceValue.getInstance());
                actualNamespacePrefix = oldNsPrefix;

                if (propertyName != null) {
                    instance.addValue(propertyName, instanceValue);
                }
                instanceValue.getInstance().setComposite(instance);
            } else {
                for (ReferenceValue<XmiClassifier, String, String> ref : model.createReferences(idref)) {
                    instance.addValue(name, ref);
                }
            }
        }
    }

    /**
     * Reads XMI with a m1 model of the given m2 in an extent. The extent must be based on a MofInstanceModel.
     */
    public static void readMofXmi(java.io.File file, Extent extent, cmof.Package m2, XmiKind xmiKind) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        readMofXmi(new FileInputStream(file), extent, m2, xmiKind);
    }

    public static void tranformMDXmi(ClassInstance<XmiClassifier, String, String> instance, Collection<ClassInstance<XmiClassifier, String, String>> toDelete, Collection<ClassInstance<XmiClassifier, String, String>> topLevel) {
        boolean hasHRef = false;
        for (StructureSlot<XmiClassifier, String, String> slot : instance.getSlots()) {
            if (slot.getProperty().equals("href")) {
                hasHRef = true;
            }
        }
        if (hasHRef) {
            toDelete.add(instance);
            instance.setComposite(null);
            return;
        }
        if (!instance.getClassifier().isDefinedByContext()) {
            if (instance.getClassifier().getNamespacePrefix().equals("uml")) {
                instance.getClassifier().setNamespacePrefix("cmof");
            }
            if (instance.getClassifier().getNamespacePrefix().equals("xmi")) {
                instance.setComposite(null);
                toDelete.add(instance);
            }
            if (instance.getClassifier().getName().equals("Model")) {
                for (ClassInstance<XmiClassifier, String, String> childInstance : new Vector<ClassInstance<XmiClassifier, String, String>>(
                        instance.getComponentsAsCollection())) {
                    topLevel.add(childInstance);
                    childInstance.setComposite(null);
                    tranformMDXmi(childInstance, toDelete, topLevel);
                }
                instance.setComposite(null);
                toDelete.add(instance);
            }
        }
        for (ClassInstance<XmiClassifier, String, String> childInstance : new Vector<ClassInstance<XmiClassifier, String, String>>(instance.getComponentsAsCollection()))
        {
            tranformMDXmi(childInstance, toDelete, topLevel);
        }
    }

    public static void readMofXmi(InputStream in, Extent extent, cmof.Package m2, XmiKind xmiKind) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        InstanceModel<XmiClassifier, String, String> xmiModel = new InstanceModel<XmiClassifier, String, String>();
        Xmi2Reader reader = new Xmi2Reader(xmiModel);
        reader.read(in);

        if (xmiKind == XmiKind.unisys) {
            throw new XmiException("Wrong xmi reader.");
        } else if (xmiKind == XmiKind.ea) {
            throw new XmiException("Wrong xmi reader.");
        } else if (xmiKind == XmiKind.md) {
            Collection<ClassInstance<XmiClassifier, String, String>> toDelete = new HashSet<ClassInstance<XmiClassifier, String, String>>();
            Collection<ClassInstance<XmiClassifier, String, String>> topLevel = new HashSet<ClassInstance<XmiClassifier, String, String>>();
            for (ValueSpecification<XmiClassifier, String, String> topLevelInstance :
                    new Vector<ValueSpecification<XmiClassifier, String, String>>(xmiModel.getOutermostComposites())) {
                tranformMDXmi(topLevelInstance.asInstanceValue().getInstance(), toDelete, topLevel);
            }

            for (ValueSpecification<XmiClassifier, String, String> outermostComposite : xmiModel.getOutermostComposites()) {
                if (!topLevel.contains(outermostComposite.asInstanceValue().getInstance())) {
                    toDelete.add(outermostComposite.asInstanceValue().getInstance());
                }
            }
            for (ClassInstance<XmiClassifier, String, String> instance : toDelete) {
                instance.delete();
            }

            XmiTransformator transformator = new MagicDrawXmi2ToMOF2(xmiModel);

            for (ValueSpecification<XmiClassifier,String,String> instance:
                    new Vector<ValueSpecification<XmiClassifier, String, String>>(xmiModel.getOutermostComposites())) {
                transformator.transform(hub.sam.mof.xmi.mopa.XmiMopaTreeNode.createNode(instance.asInstanceValue().getInstance()));
            }
        }

        XmiToCMOF conversion = new XmiToCMOF(extent, m2);
        InstanceModel<UmlClass, Property, Object> mofModel = ((ExtentImpl) extent).getModel();
        new Converter<XmiClassifier, String, String, UmlClass, Property, Type, DataType, java.lang.Object>(conversion).
                convert(xmiModel, mofModel);
        FactoryImpl factory = FactoryImpl.createFactory(extent, m2);
        for (ClassInstance<UmlClass, Property, Object> instance : mofModel.getInstances()) {
            factory.create(instance);
        }

        // TODO Remove this when opposite of Properties is realy derived.
        if (xmiKind == XmiKind.md) {
            for(cmof.reflection.Object o: extent.getObject()) {
                if (o instanceof Property) {
                    Property p = (Property)o;
                    if (p.getAssociation() != null) {
                        if (p.getOpposite() == null) {
                            for (Property end: p.getAssociation().getMemberEnd()) {
                                if (!end.equals(p)) {
                                     p.setOpposite(end);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
