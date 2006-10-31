package cmofimpl.xmi;

import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XmiReader {           
    private final cmof.reflection.Extent extent;
    private final FluxBox fluxBox = new FluxBox();
    private final XmiModelFacade model;
    
    class FluxBox {
        final Map<String, cmof.reflection.Object> fluxBox = new HashMap<String, cmof.reflection.Object>();
        cmof.reflection.Object get(String id, cmof.UmlClass definingClass) {
            cmof.reflection.Object instance = fluxBox.get(id);
            if (instance == null) {
                instance = extent.getFactory().create(definingClass);
                fluxBox.put(id, instance);
            } 
            return instance;
        }
        boolean contains(String id) {
            return fluxBox.get(id) != null;
        }
    }
    
    public XmiReader(cmof.reflection.Extent extent, cmof.reflection.Extent metaModel) {
        this.extent = extent;
        this.model = new XmiModelFacade(metaModel);
    }
    
    public XmiReader(cmof.reflection.Extent extent, cmof.Package metaModel) {
        this.extent = extent;
        this.model = new XmiModelFacade(metaModel);
    }
    
    public Collection<cmof.reflection.Object> readXmi(InputStream in) 
            throws IOException, org.xml.sax.SAXException, BadXmiException {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
            return readXmi(document);
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException("assert");
        }        
    }
    
    public Collection<cmof.reflection.Object> readXmi(Document xmi ) 
            throws BadXmiException {
        Collection<cmof.reflection.Object> result = new Vector<cmof.reflection.Object>();
        Element docElem = xmi.getDocumentElement();
        String docElemName = docElem.getTagName();        
        if (!docElemName.substring(docElemName.length()-3, docElemName.length()).equals("XMI")) {
            error(BadXmiReason.UnexpectedXml, null);
        }
        
        // a first pass to collect all xmi:ids
        String actualNamespace = "";
        for (Node topLevelNode: new NodeListIterator(docElem.getChildNodes())) {
            if (topLevelNode instanceof Element) {          
                Element contentElem = (Element)topLevelNode;
                cmof.Element metaModelElement = model.getClassForTagName(getNameForXmlName(contentElem.getTagName()), 
                        getNamespaceForXmlName(contentElem.getTagName(), actualNamespace));
                if (metaModelElement == null) {                    
                    error(BadXmiReason.DoesNotMatchMetaModel, contentElem.getTagName());
                }
                if (metaModelElement instanceof cmof.UmlClass) {                    
                    readObjectToCollectionId(contentElem, (cmof.UmlClass)metaModelElement, getNamespaceForXmlName(contentElem.getTagName(), actualNamespace));
                }
            }
        }
       
        // a second pass to do the real job
        for (Node topLevelNode: new NodeListIterator(docElem.getChildNodes())) {                       
            if (topLevelNode instanceof Element) {
                Element contentElem = (Element)topLevelNode;
                //TBD namesapces, dots, etc.
                cmof.Element metaModelElement = model.getClassForTagName(getNameForXmlName(contentElem.getTagName()), 
                        getNamespaceForXmlName(contentElem.getTagName(), actualNamespace));
                if (metaModelElement == null) {                    
                    error(BadXmiReason.DoesNotMatchMetaModel, contentElem.getTagName() + " is not an element of the metamodel.");
                }
                if (metaModelElement instanceof cmof.UmlClass) {                    
                    result.addAll(readObject(contentElem, (cmof.UmlClass)metaModelElement, getNamespaceForXmlName(contentElem.getTagName(), actualNamespace)));
                }
            } else {
                //TBD all attributes are ignored
            }
        }
               
        return result;
    }
    
    private void readObjectToCollectionId(Element element, cmof.UmlClass definingClass, String ns) throws
            BadXmiException {
        cmof.UmlClass newDefiningClass = readTypeAttribute(element, ns);
        if (newDefiningClass != null) {
            definingClass = newDefiningClass;
            ns = updateNamespaceFromTypeAttribute(element, ns);
        }
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node objectNode = attributes.item(i);
            if (objectNode instanceof Attr) {
                Attr attr = (Attr)objectNode;                                                                            
                if (attr.getName().equals("xmi:id")) {
                    fluxBox.get(attr.getValue(), definingClass);                    
                } 
            } 
        }
        for (Node objectNode: new NodeListIterator(element.getChildNodes())) {            
            if (objectNode instanceof Element) {
                Element objectContent = (Element)objectNode;
                cmof.Property definingProperty = getPropertyForXmiName(definingClass, objectContent.getTagName());
                if (definingProperty == null) {
                    error(BadXmiReason.DoesNotMatchMetaModel, objectContent.getTagName() + " is not an element of the metamodel.");
                }                
                cmof.Type definingType = definingProperty.getType();
                if (definingType instanceof cmof.UmlClass) {
                    readObjectToCollectionId(objectContent, (cmof.UmlClass)definingType, ns);
                }
            }
        }
    }
        
    private java.lang.Object readProperty(Node node, cmof.Property definingProperty, String ns) 
            throws BadXmiException {
        cmof.Type definingType = definingProperty.getType();
        if (definingType instanceof cmof.DataType) {
            String elementValue = null;
            if (node instanceof Attr) {
                elementValue = ((Attr)node).getValue();
            } else if (node instanceof Element) {
                Element element = (Element)node;
                for (Node childNode: new NodeListIterator(element.getChildNodes())) {
                    if (childNode instanceof Text) {
                        elementValue = ((Text)childNode).getWholeText();
                    }
                }            
            }
            if (elementValue == null) {
                error(BadXmiReason.DoesNotMatchMetaModel, "Value for " + ((Attr)node).getName() + " must not be undefined.");
            }
            try {
                return extent.getFactory().createFromString((cmof.DataType)definingType, elementValue);
            } catch (IllegalArgumentException ex) {
                error(BadXmiReason.DoesNotMatchMetaModel, ex);
            }            
        } else if (definingType instanceof cmof.UmlClass) {
            if (node instanceof Element) {
                return readObject((Element)node, (cmof.UmlClass)definingType, ns).get(0);
            } else if (node instanceof Attr){
                List<cmof.reflection.Object> result = readObject(((Attr)node).getValue() /*refid*/, (cmof.UmlClass)definingType, ns);
                if (result.size() == 1) {
                    return result.get(0);
                } else {
                    return result;
                }
            }            
        } else {
            error(BadXmiReason.UnexpectedXml, null);
        }
        return null;
    }
    
    private List<cmof.reflection.Object> readObject(String refIds, cmof.UmlClass definingClass, String ns) 
            throws BadXmiException {
        List<cmof.reflection.Object> result = new Vector<cmof.reflection.Object>();
        for (String refId : refIds.split(" ")) {
            if (!fluxBox.contains(refId)) {
                error(BadXmiReason.MissingRefid, refId);
            }
            result.add(fluxBox.get(refId, definingClass));                    
        }
        return result;
    }
    
    private String typeAttribute(final Element element) {
        String typeName = null;
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node objectNode = attributes.item(i);
            if (objectNode instanceof Attr) {
                Attr attr = (Attr)objectNode;
                if (attr.getName().equals("xmi:type")) {
                    typeName =  attr.getTextContent();
                }
            }
        }
        return typeName;
    }
    
    private cmof.UmlClass readTypeAttribute(final Element element, String ns) throws BadXmiException {
        String typeName = typeAttribute(element);
        if (typeName != null) {
            cmof.Element metaModelElement = model.getClassForTagName(getNameForXmlName(typeName), getNamespaceForXmlName(typeName, ns));
            if (metaModelElement == null || !(metaModelElement instanceof cmof.UmlClass)) {
                error(BadXmiReason.DoesNotMatchMetaModel, typeName + " is not an element of the metamodel.");
            } else {
                return (cmof.UmlClass)metaModelElement;
            }
        }
        return null;
    }
    
    private String updateNamespaceFromTypeAttribute(final Element element, String ns) throws BadXmiException {
        String typeName = typeAttribute(element);
        if (typeName != null) {
            return getNamespaceForXmlName(typeName, ns);
        } else {
            return null;
        }
    }
    
    private List<cmof.reflection.Object> readStartAttributes(final Element element, final cmof.UmlClass definingClass, String ns) 
            throws BadXmiException {
        cmof.reflection.Object newInstance = null;        
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node objectNode = attributes.item(i);
            if (objectNode instanceof Attr) {
                Attr attr = (Attr)objectNode;
                if (attr.getName().equals("idref")) {                                        
                    return readObject(attr.getValue(), definingClass, ns);                    
                } else if (attr.getName().equals("xmi:id")) {
                    newInstance = fluxBox.get(attr.getValue(), definingClass);                    
                }                 
            } 
        }
        if (newInstance == null) {
            newInstance = extent.getFactory().create(definingClass);
        }
        List<cmof.reflection.Object> result = new Vector<cmof.reflection.Object>();
        result.add(newInstance);
        return result;
    }
    
    private void readMember(final Node node, cmof.UmlClass definingClass, String memberName, cmof.reflection.Object theInstance, String ns) 
            throws BadXmiException {
        cmof.Property definingProperty = getPropertyForXmiName(definingClass, memberName);
        if (definingProperty == null) {
            error(BadXmiReason.DoesNotMatchMetaModel, memberName + " is not an element of the metamodel.");
        }   
        java.lang.Object propertyValue = readProperty(node, definingProperty, ns);
        if (definingProperty.getUpper() == 1) {
            if (propertyValue instanceof java.util.List) {
                error(BadXmiReason.DoesNotMatchMetaModel, null);
            } else {
                theInstance.set(definingProperty, propertyValue);
            }
        } else {            
            if (propertyValue instanceof java.util.List) {
                ((cmof.common.ReflectiveCollection<? extends java.lang.Object>)theInstance.get(definingProperty)).addAll((java.util.List<? extends java.lang.Object>)propertyValue);
            } else {
                ((cmof.common.ReflectiveCollection<? extends java.lang.Object>)theInstance.get(definingProperty)).add(propertyValue);
            }
        }                       
    }
    
    private List<cmof.reflection.Object> readObject(final Element element, cmof.UmlClass definingClass, String ns) 
            throws BadXmiException {
        cmof.UmlClass newDefiningClass = readTypeAttribute(element, ns);
        if (newDefiningClass != null) {
            definingClass = newDefiningClass;
            ns = updateNamespaceFromTypeAttribute(element, ns);
        }
        List<cmof.reflection.Object> newInstances = readStartAttributes(element, definingClass, ns);
        cmof.reflection.Object newInstance = newInstances.get(0);
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node objectNode = attributes.item(i);
            if (objectNode instanceof Attr) {
                Attr attr = (Attr)objectNode;
                if (!(attr.getName().equals("idref") || attr.getName().equals("xmi:id") || attr.getName().equals("xmi:type"))) {                                                                                                
                    // assumet that the attribute is a model attribute
                    readMember(objectNode, definingClass, attr.getName(), newInstance, ns);
                }
            } 
        }
        for (Node objectNode: new NodeListIterator(element.getChildNodes())) {            
            if (objectNode instanceof Element) {                
                readMember(objectNode, definingClass, ((Element)objectNode).getTagName(), newInstance, ns);                
            }
        }
        return newInstances;
    }
    
    private cmof.Property getPropertyForXmiName(cmof.Classifier owner, String xmiName) {
        // TBD performance
        cmof.Property result = null;
        for(cmof.NamedElement member: owner.getMember()) {
            if (member.getName().equals(xmiName)) {
                if (member instanceof cmof.Property) {
                    return result = (cmof.Property)member;
                }
            }
        }
        return null;
    }
    
    private String getNamespaceForXmlName(String xmlName, String inheritedNs) {
        String ns = "";
        String xmiName = "";
        if (xmlName.contains(":")) {
            int index = xmlName.indexOf(":");
            ns = xmlName.substring(0, index);
            xmiName = xmlName.substring(index +1, xmlName.length());
        } else {
            ns = "";
            xmiName = xmlName;
        }
        if (ns.equals("")) {
            ns = inheritedNs;
        }
        return ns;
    }
    
    private String getNameForXmlName(String xmlName) {
        String ns = "";
        String xmiName = "";
        if (xmlName.contains(":")) {
            int index = xmlName.indexOf(":");
            ns = xmlName.substring(0, index);
            xmiName = xmlName.substring(index +1, xmlName.length());
        } else {
            ns = "";
            xmiName = xmlName;
        }
        return xmiName;
    }
    
    private void error(BadXmiReason reason, Object explaination) throws BadXmiException {
        if (reason == BadXmiReason.DoesNotMatchMetaModel) {
            throw new BadXmiException("The xmi does not match the given meta-model. Because of " + explaination);
        } else if (reason == BadXmiReason.UnexpectedXml) {
            throw new BadXmiException("Unexpected nodes in xmi document.");
        } else if (reason == BadXmiReason.MissingRefid) {
            throw new BadXmiException("There is a refid but no element with that id. Because of " + explaination);
        } else if (reason == BadXmiReason.NotImplemented) {
            throw new BadXmiException("There is a feature used that is not implemented.");
        }
    }
    public enum BadXmiReason { UnexpectedXml, DoesNotMatchMetaModel, MissingRefid, NotImplemented }    
    
    public class NodeListIterator implements Iterator<Node>, Iterable<Node> {
        private int index;
        private final int length;
        private final NodeList list;
        NodeListIterator(NodeList list) {
            index = 0;
            this.length = list.getLength();
            this.list = list;
        }
        public boolean hasNext() {
            return index < length;
        }

        public Node next() {
            return list.item(index++);
        }

        public void remove() {
            throw new RuntimeException("assert");
        }
        public Iterator<Node> iterator() {            
            return new NodeListIterator(list);
        }
    }
}
